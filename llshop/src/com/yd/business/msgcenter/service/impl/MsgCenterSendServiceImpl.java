/**
 * 
 */
package com.yd.business.msgcenter.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterSendLogBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.bean.MsgCenterWaitSendBean;
import com.yd.business.msgcenter.dao.IMsgCenterSendDao;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.msgcenter.service.IMsgCenterSendService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.sms.bean.SmsCustomerBean;
import com.yd.business.sms.service.ISmsCustomerService;
import com.yd.business.sms.service.ISmsService;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.supplier.service.ISupplierTopicService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.ArticleBean;
import com.yd.business.wechat.bean.MaterialBean;
import com.yd.business.wechat.bean.TemplateMessage;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatTemplateMsgBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;
import com.yd.util.JsonUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("msgCenterSendService")
public class MsgCenterSendServiceImpl extends BaseService implements IMsgCenterSendService {
	
	@Resource
	private IMsgCenterSendDao msgCenterSendDao;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private ISupplierEventService supplierEventService;
	@Resource
	private ISupplierTopicService supplierTopicService;
	@Resource
	private ISmsService smsService;
	@Resource
	private ISmsCustomerService smsCustomerService;
	@Resource
	private IUserWechatService userWechatService;

	public static final String split_symbol = "#";
	public static final String http_pre = "http://";
	public static final String http_end = "/llshop/";
	
	/**
	 * 将等待发送的信息，入表保存
	 */
	@Override
	public void createMsgWaitSendInfo(MsgCenterActionArticleRelationBean relation,MsgCenterUserActionBean action,MsgCenterArticleBean article){
		
		Calendar c = Calendar.getInstance();
		
		
		MsgCenterWaitSendBean bean = new MsgCenterWaitSendBean();
		//action需要以relation中的为准
		bean.setAction_name(relation.getAction_name());
		bean.setAction_type(relation.getAction_type());
		bean.setArticle_id(article.getId());
		bean.setCreate_time(DateUtil.formatDateSSS(c.getTime()));
		bean.setNick_name(action.getNick_name());
		bean.setOpenid(action.getOpenid());
		bean.setStatus(MsgCenterWaitSendBean.STATUS_WAIT);
		bean.setUser_action_id(action.getId());
		bean.setUser_id(action.getUser_id());
		
		//添加延迟时间
		c.add(Calendar.SECOND, relation.getDelay_time());
		bean.setExec_time(DateUtil.formatDateSSS(c.getTime()));
		
		//入库数据
		createMsgWaitSendInfo(bean);
		
	}
	
	/**
	 * 将等待发送的信息，入表保存
	 * @param bean
	 */
	public void createMsgWaitSendInfo(MsgCenterWaitSendBean bean){
		msgCenterSendDao.createWaitSend(bean);
	}
	
	
	/**
	 * 发送文章给目标用户，目标用户来自于 消息动作定义表，可能是个列表
	 * @param article
	 * @param action
	 */
	@Override
	public void sendArticleToTargetObject(MsgCenterArticleBean article,MsgCenterUserActionBean action){
		
		//action的目标用户可能是多个，所以需要循环
		MsgCenterActionDefineBean define = msgCenterActionService.queryActionDefineByActionType(action.getAction_type());
		String openid = action.getOpenid();
		String sql = define.getTarget_object_sql(); //根据动作定义里的目标对象脚本，进行查询出目标对象列表
		
		if(StringUtil.isNotNull(sql)){
			sql = sql.replaceAll("#openid#", openid);
			sql = convertActionParameter(sql, action);
			List<Map<String,Object>> userList = msgCenterSendDao.execSQL(sql);
			
			for(Map<String,Object> m : userList){
				
				try{
					action.setOpenid(m.get("openid").toString());
					action.setNick_name(m.get("nick_name").toString());
					
					sendArticleToUser(article, action);
				}catch (Exception e) {
					log.error(e, e);
				}
			}
			
		}else{
			//如果脚本为空
			sendArticleToUser(article, action);
		}
		//保存发送日志
		createSendLog(article,action);
	}
	
	/**
	 * 保存发送日志
	 * @param article
	 * @param action
	 */
	private void createSendLog(MsgCenterArticleBean article, MsgCenterUserActionBean action) {
		
		String time = DateUtil.getNowDateStr();
		MsgCenterSendLogBean bean = new MsgCenterSendLogBean();
		bean.setAction_name(action.getAction_name());
		bean.setAction_type(action.getAction_type());
		bean.setArticle_id(article.getId());
		bean.setCreate_time(time);
		bean.setExec_time(time);
		bean.setNick_name(action.getNick_name());
		bean.setOpenid(action.getOpenid());
		bean.setStatus(MsgCenterSendLogBean.STATUS_SEND);
		bean.setUser_action_id(action.getId());
		bean.setUser_id(action.getUser_id());
		
		msgCenterSendDao.createSendLog(bean);
		
	}

	/**
	 * 发送文章给用户
	 * @param article
	 * @param action
	 */
	private void sendArticleToUser(MsgCenterArticleBean article,MsgCenterUserActionBean action){

		switch (article.getSend_type()) {
		case MsgCenterArticleBean.SEND_TYPE_WECHAT_TEXT:
			sendWechatTextToUser(article.getMaterial_code(), action,article);
			break;

		case MsgCenterArticleBean.SEND_TYPE_SMS_TEXT:
			sendSmsTextToUser(article.getMaterial_id(), action);
			break;

		case MsgCenterArticleBean.SEND_TYPE_WECHAT_NEWS:
			sendWechatNewsToUser(article.getMaterial_code(), action);
			break;

		case MsgCenterArticleBean.SEND_TYPE_SYSTEM_NEWS:
			sendSystemNewsToUser(article.getMaterial_id(), action);
			break;
		case MsgCenterArticleBean.SEND_TYPE_WECHAT_NEWS_ALL:
			sendWechatNewsAllToUser(article,action);
			break;
		case MsgCenterArticleBean.SEND_TYPE_SYSTEM_TOPIC:
			sendSystemTopicToUser(article.getMaterial_id(), action);
			break;
		case MsgCenterArticleBean.SEND_TYPE_WECHAT_TEMPLATE_MSG:
			sendWechatTemplateMsgToUser(article, action);
			break;
		}
	}
	
	/**
	 * 微信素材分发记录，通过消息定时打包发送
	 * @param article
	 * @param action
	 */
	private void sendWechatNewsAllToUser(MsgCenterArticleBean article,MsgCenterUserActionBean action){
		//得到素材的分发记录
		WechatMaterialRelationBean relBean = new WechatMaterialRelationBean();
		UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
		relBean.setOriginalid(user.getOriginalid());
		relBean.setMain_media_id(article.getMaterial_code());
		List<WechatMaterialRelationBean> beanList = wechatService.queryWechatMaterialRelationByBean(relBean);
		//有分发记录将所有的子素材发送
		for (WechatMaterialRelationBean relaBean : beanList) {
			sendWechatNewsToUser(relaBean.getChildren_media_id(), action);
		}
	}
	
	/**
	 * 发送微信文本给用户
	 */
	private void sendWechatTextToUser(String material_code,MsgCenterUserActionBean action,MsgCenterArticleBean article){

		UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
		String content = configCruxService.getValueByTypeAndKey(ConfigCruxBean.TYPE_WECHAT_NOTIFY_TEXT,material_code);

		WechatOriginalInfoBean info = wechatService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
		content = convertActionParameter(content, action);
		content = content.replaceAll("#server_domain#", info.getServer_domain());
		content = content.replaceAll("#server_url#", info.getServer_url());
		content = content.replaceAll("#appid#", info.getAppid());
		for(int i = 0 ; i < 8;i++){
			content = content.replace("#wildcard#", RandomUtil.randomString(3));
		}
		
		
		
		
		String openid = action.getOpenid();
		
		TextBean text = new TextBean();
		text.setContent(content);
		text.setToUserName(openid);
		text.setFromUserName(user.getOriginalid());
		
		if(StringUtil.isNotNull(article.getParam())){
			text.setNotifyType(Integer.parseInt(article.getParam()));
		}
		//发送文本数据给用户
		wechatService.sendMessageToUser(text);
		
	}
	
	/**
	 * 发送微信图文给用户
	 */
	private void sendWechatNewsToUser(String material_code,MsgCenterUserActionBean action){
		
		//取图文素材数据
//		WechatMaterialBean material = wechatService.findWechatMaterial(material_id);
//		String media_id = material.getSucai_media_id();
		String media_id = material_code;
		MaterialBean materialBean = new MaterialBean();
		materialBean.setMedia_id(media_id);
		materialBean.setToUserName(action.getOpenid());

		UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
		materialBean.setFromUserName(user.getOriginalid());
		//发送给用户
		wechatService.sendMessageToUser(materialBean);
		
	}
	
	/**
	 * 发送短信消息给用户
	 */
	private void sendSmsTextToUser(int material_id,MsgCenterUserActionBean action){

		Map<String,String> map = null;
		if(action.getAction_param() != null){
			map = JsonUtil.parseJSONToMap(action.getAction_param());
		}
		String phone = map.get("order_account");
		if(StringUtil.isNull(phone)){
			phone = map.get("phone");
		}
		if(StringUtil.isNull(phone)){
			phone = map.get("phoneNo");
		}
		
		//取短信模板
		SmsCustomerBean bean = smsCustomerService.querySmsCustomerById(material_id);
		//发送短信
		smsService.sendSMSAndSaveLog(phone, map, bean);
		
	}
	
	/**
	 * 发送系统文章给用户
	 */
	private void sendSystemNewsToUser(int material_id,MsgCenterUserActionBean action){
		
		SupplierEventBean supplierEvent = supplierEventService.queryByid(material_id);
		if(supplierEvent != null){
			UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
			ArticleBean article = new ArticleBean();
			article.setToUserName(action.getOpenid());
			article.setTitle(supplierEvent.getTitle());
			article.setDescription(supplierEvent.getDescrip());
			
			String url = supplierEvent.getUrl();

			WechatOriginalInfoBean info = wechatService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
			url = convertActionParameter(url, action);
			url = url.replaceAll("#server_domain#", info.getServer_domain());
			url = url.replaceAll("#server_url#", info.getServer_url());
			url = url.replaceAll("#appid#", info.getAppid());

			if(url.indexOf("#wildcard#") >= 0){
				for(int i = 0 ; i < 8;i++){
					url = url.replace("#wildcard#", RandomUtil.randomString(3));
				}
			}

			
			article.setUrl(url);
			article.setPicurl(supplierEvent.getImg_url());
			
			article.setFromUserName(user.getOriginalid());
			//发送给用户
			wechatService.sendMessageToUser(article);
		}
		
		
	}
	

	/**
	 * 发送系统话题给用户
	 */
	private void sendSystemTopicToUser(int material_id,MsgCenterUserActionBean action){
		
		SupplierTopicBean supplierTopic = supplierTopicService.findSupplierTopicById(material_id);
		if(supplierTopic != null){
			UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
			ArticleBean article = new ArticleBean();
			article.setToUserName(action.getOpenid());
			article.setTitle(supplierTopic.getTitle());
			article.setDescription(supplierTopic.getDescrip());
			
			String url = supplierTopic.getUrl();

			WechatOriginalInfoBean info = wechatService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
			url = convertActionParameter(url, action);
			url = url.replaceAll("#server_domain#", info.getServer_domain());
			url = url.replaceAll("#server_url#", info.getServer_url());
			url = url.replaceAll("#appid#", info.getAppid());
			
			if(url.indexOf("#wildcard#") >= 0){
				for(int i = 0 ; i < 8;i++){
					url = url.replace("#wildcard#", RandomUtil.randomString(3));
				}
			}

			
			article.setUrl(url);
			article.setPicurl(supplierTopic.getImg_url());
			
			article.setFromUserName(user.getOriginalid());
			//发送给用户
			wechatService.sendMessageToUser(article);
		}
		
		
	}
	
	/**
	 * 发送模板消息给用户
	 * @param article
	 * @param action
	 */
	private void sendWechatTemplateMsgToUser(MsgCenterArticleBean article,MsgCenterUserActionBean action){

		UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
		WechatOriginalInfoBean info = wechatService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
		String value = configCruxService.getValueByTypeAndKey(ConfigCruxBean.TYPE_WECHAT_TEMPLATE_MSG, article.getMaterial_code() );
		WechatTemplateMsgBean template = wechatService.queryWechatTemplateMsg(user.getOriginalid(), article.getMaterial_code() );

		value = convertActionParameter(value, action);
		value = value.replaceAll("#server_domain#", info.getServer_domain());
		value = value.replaceAll("#server_url#", info.getServer_url());
		value = value.replaceAll("#appid#", info.getAppid());
		value = value.replaceAll("#template_id#", template.getTemplate_id());
		value = value.replaceAll("#action_openid#", action.getOpenid());
		
		TemplateMessage tm = new TemplateMessage();
		tm.setToUserName(user.getOpenid());
		tm.setTouser(user.getOpenid());
		tm.setFromUserName(info.getOriginalid());
		tm.setContent(value);
		wechatService.sendMessageToUser(tm);
		
	}
	
	
	/**
	 * 转换参数数据
	 * @param content
	 * @param action
	 */
	private String convertActionParameter(String content,MsgCenterUserActionBean action){
		
//		String class_name = action.getAction_param_class();
		String param = action.getAction_param();
		JSONObject jso = null;  //参数转换为json对象
		if(StringUtil.isNotNull(param)){
			jso = new JSONObject(param);
			jso.put("action_openid", action.getOpenid());
			content = content+" ";
			String[] strs = content.split(split_symbol);
			StringBuilder sb = new StringBuilder();
			boolean isFind = false;
			for(int i = 0 ; i < strs.length ; i++){
				String str = strs[i];
					String param_key = strs[i];
					str = JsonUtil.getParamValueByKey(jso, param_key);

					//判断是否有找到参数
					if(param_key.equals(str)){
						//没找到的话，判断上一个参数有没有找到，如果本次和上次都没有找到，就在开始加上#号
						if(!isFind && i != 0){
							sb.append(split_symbol);
						}
						isFind = false;
					}else{
						isFind = true;
					}
				sb.append(str);
			}
			return sb.toString();
		}
		return content;
	}
	
	@Override
	public List<MsgCenterWaitSendBean> queryWaitSendList(MsgCenterWaitSendBean bean){
		
		return msgCenterSendDao.queryWaitSendList(bean);
	}
	
	@Override
	public List<MsgCenterSendLogBean> querySendLogList(MsgCenterSendLogBean bean){
		return msgCenterSendDao.querySendLogList(bean);
	}
	
	@Override
	public void updateWaitSend(MsgCenterWaitSendBean bean){
		msgCenterSendDao.updateWaitSend(bean);
	}
	
	
	
	
}
