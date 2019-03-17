/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.comment.service.ICommentInfoService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.bean.UserBWCBean;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatExtendBean;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.util.EmojiUtil;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.EventBean;
import com.yd.business.wechat.bean.ImageBean;
import com.yd.business.wechat.bean.MaterialBean;
import com.yd.business.wechat.bean.QrCodeBean;
import com.yd.business.wechat.bean.SignServerBean;
import com.yd.business.wechat.bean.TemplateMessage;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.bean.TokenBean;
import com.yd.business.wechat.bean.WechatArticlesBean;
import com.yd.business.wechat.bean.WechatMaterialDeliveryLogBean;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatParentMaterialBean;
import com.yd.business.wechat.bean.WechatSendAllJsonBean;
import com.yd.business.wechat.bean.WechatTemplateMsgBean;
import com.yd.business.wechat.bean.WechatSendAllJsonBean.WechatFilter;
import com.yd.business.wechat.bean.WechatUploadMaterialBean;
import com.yd.business.wechat.dao.IWechatDao;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.SHA1Util;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.business.wechat.util.WechatUtil;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.FileUploadUtil;
import com.yd.util.HttpUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 * 
 */
@Service("wechatService")
public class WechatServiceImpl extends BaseService implements IWechatService {
	@Resource
	protected IWechatDao wechatDao;
	@Resource
	protected IConfigAttributeService configAttributeService;
	@Resource
	protected IConfigCruxService configCruxService;
	@Resource
	protected IUserWechatService userWechatService; 
	@Resource
	protected IUserCommissionPointsService userCommissionPointsService; 
	@Resource
	protected IActivityService activityService;
	@Resource
	protected ThreadPoolTaskExecutor taskExecutor;
	@Resource
	protected IMsgCenterActionService msgCenterActionService;
	@Resource
	private ICommentInfoService commentInfoService;
	@Resource
	private ICustomerService customerService;
	
	private Map<String,String> wechatCodeMap = new HashMap<String, String>();
	private HashMap<Long,String> codeTimeMap = new HashMap<Long, String>();
	
	
	@Override
	public BaseMessage handlerWechatMessage(Document doc) throws Exception{
		System.out.print("进入核心类---------------------");
		boolean isUserOperate = true;
		final BaseMessage base = WechatUtil.parseXMLToBean(doc);
		BaseMessage result = null;
		System.out.print(base.getMsgType());
		//事件
		if(base.getMsgType().equalsIgnoreCase(WechatUtil.MESSAGE_TYPE_EVENT)){
			
			EventBean eventBean = (EventBean) base;
			String event = eventBean.getEvent();
			log.debug("user:"+eventBean.getFromUserName() +" EventKey:" + eventBean.getEventKey());
			
			//发送模板消息，微信会 回调，这种回调不能算是用户交互了
			if("TEMPLATESENDJOBFINISH".equalsIgnoreCase(event)){
				isUserOperate = false;
			}
			
			if(WechatUtil.EVENT_TYPE_SUBSCRIBE.equalsIgnoreCase(event)){
				
				result = handleUserSubscribeMessage(eventBean);
				
			}if(WechatUtil.EVENT_TYPE_UNSUBSCRIBE.equalsIgnoreCase(event)){
				
				result = handleUserUnSubscribeMessage(eventBean);
				
			}else if(WechatUtil.EVENT_TYPE_VIEW.equalsIgnoreCase(event) ){
				//view点击事件
				System.out.println("View点击事件--------------------------------");

			}else if(WechatUtil.EVENT_TYPE_CLICK.equalsIgnoreCase(event)){
				String key = eventBean.getEventKey();
				log.debug("user click menu: "+key);
				
				result = handleUserClickMenuMessage(eventBean);
				msgCenterActionService.saveAndHandleUserAction(eventBean.getFromUserName(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_CLICK, key, eventBean);

			}else if(WechatUtil.EVENT_TYPE_SCAN.equalsIgnoreCase(event)){
				
				String eventKey = eventBean.getEventKey();
//				String senceString = eventKey.substring(eventKey.indexOf("_")+1,eventKey.length());
				
				if(StringUtil.isNotNull(eventKey) && eventKey.length() >2)
				{
					Long senceValue = Long.parseLong(eventKey);
//					String senceCode = String.valueOf(WechatUtil.getSenceType(senceValue));
					String parentId = String.valueOf(WechatUtil.getUserId(senceValue));
					int senceType = WechatUtil.getSenceType(senceValue);
					int senceId = WechatUtil.getSenceId(senceValue);
					
					//没有用户的，就给添加上
					UserWechatBean user = userWechatService.findUserWechatByOpenId(eventBean.getFromUserName());
					if(user == null){
						createWechatUser(eventBean.getFromUserName(), parentId,senceType,senceId,eventBean.getToUserName());
						handleSenceCode(senceValue,eventBean.getFromUserName(),parentId);
						
					}
					eventBean.setProductId(senceType);
					eventBean.setProductId(senceId);
					msgCenterActionService.saveAndHandleUserAction(eventBean.getFromUserName(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_SCAN, senceType +","+ senceId, eventBean);
					
					
//					预防坏人，将所有没有父用户的人，都一直没有父用户
//					else if(user.getParentid() == null && user.getId().intValue() != Integer.parseInt(parentId)){
//						user.setParentid(Integer.parseInt(parentId));
//						userWechatService.update(user);
//					}
					
				}
				
			}
		}else if(base.getMsgType().equalsIgnoreCase(WechatUtil.MESSAGE_TYPE_IMAGE)){
			//用户上传图片
			ImageBean imageBean = (ImageBean) base;
			result = handleUserImageMessage(imageBean);
		}else if(base.getMsgType().equalsIgnoreCase(WechatUtil.MESSAGE_TYPE_TEXT)){
			//用户发文字
			TextBean textBean = (TextBean) base;
			result = handleUserTextMessage(textBean);
		
		}else if(base.getMsgType().equalsIgnoreCase(WechatUtil.MESSAGE_TYPE_NEWS)){
			//用户点击图文消息
			
		}else if(base.getMsgType().equalsIgnoreCase(WechatUtil.EVENT_TYPE_VIEW)){
			
		}
		
		//记录用户操作
		wechatDao.saveUserEvent(base);
		
		if(isUserOperate){   //如果是用户操作的
			final UserWechatBean user = userWechatService.findUserWechatByOpenId(base.getFromUserName());
			taskExecutor.execute(new BaseRunable() {
				@Override
				public void runMethod() {
					//查询是否有未收到过的消息，再次发送
					findUnSendMessage(base.getFromUserName(),user);
				}
			});
			//修改用户最后一次访问的时间
			userWechatService.updateUserLastAccessTime(base.getFromUserName(),new Date());
		}
		return result;
	}
	
	/**
	 * 处理用户上传图片动作
	 * @param bean
	 */
	protected BaseMessage handleUserImageMessage(ImageBean bean){
		BaseMessage result = null;
//		UserWechatBean user = userWechatService.findUserWechatByOpenId(bean.getFromUserName());
//		Date lastDate = user.getLast_upload_time();
//
//		TextBean text = new TextBean();
//		text.setFromUserName(bean.getToUserName());
//		text.setToUserName(bean.getFromUserName());
//		text.setMsgType(WechatUtil.MESSAGE_TYPE_TEXT);
//		text.setCreateTime(DateUtil.java2phpDate(System.currentTimeMillis()));
//		result = text;
//
//		
//		if(lastDate != null){
//			Calendar currentDate = Calendar.getInstance();
//			int today = currentDate.get(Calendar.DAY_OF_YEAR);
//			
//			Calendar last = Calendar.getInstance();
//			last.setTime(lastDate);
//			int lastDay = last.get(Calendar.DAY_OF_YEAR);
//			
//			//当天已经参加过活动
//			if(lastDay == today){
//				text.setContent("您今天已经参加过活动啦！不能再参加了哦！ ");
//			}else{
//				//当天未参加活动
////				String str = activityService.joinImageActivity(bean, user);
////				text.setContent(str);
//			}
//		}else{
//			//从未参加活动，也就是第一次上传图片
////			String str = activityService.joinImageActivity(bean, user);
////			text.setContent(str);
//		}
		
		return result;
	}
	
	/**
	 * 处理用户点击自定义菜单的消息
	 * @param bean
	 * @return
	 */
	protected BaseMessage handleUserClickMenuMessage(EventBean bean){
		
		String eventKeyStr = bean.getEventKey();

		String eventKey = eventKeyStr.split("_")[0];
		BaseMessage result = null;
//		UserWechatBean user =  new UserWechatBean();
		
		/**
		 * 用户签到
		 */
		if(WechatConstant.MENU_KEY_USERSIGN.equals(eventKey)){
			
			
		}
		//点击具体的某个营销活动菜单
		if(WechatConstant.MENU_KEY_SUPPLIEREVENT.equals(eventKey)){
			
			
		}
		//点击具体的某个菜单
		if(WechatConstant.MENU_KEY_WECHAT.equals(eventKey)){
			// 已不用，全部走 消息中心的回调

			String value = configAttributeService.getValueByCode(eventKeyStr);
			
//			if(value.startsWith("http")){
//				
//			}else{
			
				value = value.replaceAll("#1#", bean.getFromUserName()); //替换openId
				TextBean text = new TextBean();
				text.setFromUserName(bean.getToUserName());
				text.setToUserName(bean.getFromUserName());
				text.setMsgType(WechatUtil.MESSAGE_TYPE_TEXT);
				text.setCreateTime(DateUtil.java2phpDate(System.currentTimeMillis()));
				text.setContent(value);
				result = text;
//			}
		}
		
		return result;
	}
	
	/**
	 * 处理用户文本信息
	 * @param bean
	 * @return
	 */
	protected BaseMessage handleUserTextMessage(TextBean bean){
		
		msgCenterActionService.saveAndHandleUserAction(bean.getFromUserName(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_INPUT, bean.getContent(), bean);
		
		return null;
	}
	
	/**
	 * 用户订阅处理逻辑
	 * @param eventBean
	 * @throws Exception
	 */
	protected BaseMessage handleUserSubscribeMessage(EventBean eventBean) throws Exception{
		String eventKey = eventBean.getEventKey();
		System.out.println("eventKey----AAAAAAAAAAAAAAAAAAA-------"+eventKey);
		String weixin_id = eventBean.getFromUserName(); 
		String parentId = null;
//		String senceCode = null;
		Long senceValue =  null;
		Integer senceType = null;
		Integer senceId = null;
		
		if(StringUtil.isNotNull(eventKey) && eventKey.indexOf("_") >=0){
			// 普通关注发过来的eventKey竟然是一串不知道什么东西，加了个qrscene判断，如：
			//<EventKey><![CDATA[last_trade_no_1009570192201603154003927516]]></EventKey>
			String prefix = eventKey.substring(0,eventKey.indexOf("_")); 
			String senceString = eventKey.substring(eventKey.indexOf("_")+1,eventKey.length());
			
			//扫二维码的
			if("qrscene".equalsIgnoreCase(prefix) && senceString.length() >= 3){
				senceValue = Long.parseLong(senceString);
				senceType = WechatUtil.getSenceType(senceValue);
				senceId = WechatUtil.getSenceId(senceValue);
				parentId = String.valueOf(WechatUtil.getUserId(senceValue));
				
			}
		}

//		if(parentId == null){ // 没有父用户，直接结束，不做后面的业务逻辑
//			return null;
//		}	
		
		UserWechatBean uBean = userWechatService.findUserWechatByOpenId(weixin_id);
		//有eventkey 代表是二维码扫描过来的,反之，则是普通关注的用户
		//若库中已经存在该用户。说明用户之前已经关注过。判断该用户的parentId是否于扫码获取
		if(uBean == null){
			createWechatUser(weixin_id, parentId,senceType,senceId,eventBean.getToUserName());
			handleSenceCode(senceValue,weixin_id,parentId);
		}else{
			//再次关注的，没有父用户得要加上
			if(uBean.getParentid() == null && StringUtil.isNotNull(parentId)){
				uBean.setParentid(Integer.parseInt(parentId));
			}
			uBean.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
			userWechatService.update(uBean);
		}
		
		TextBean result = new TextBean();
		result.setToUserName(eventBean.getFromUserName());
		result.setFromUserName(eventBean.getToUserName());
		result.setCreateTime(DateUtil.java2phpDate(System.currentTimeMillis()));
		result.setMsgType(WechatUtil.MESSAGE_TYPE_TEXT);
		String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_SUBSCRIBE_WELCOM);
		
		String appendStr = querySubscriptAttachInfo(parentId);
		content += appendStr;
		result.setContent(content);
		
		return result;
	
	}
	
	/**
	 * 查询用户关注时的附加信息，目前主要是与此人的父用户相关的人中奖信息
	 * @param parentId
	 * @return
	 */
	private String querySubscriptAttachInfo(String parentId) {
		
		return "";
	}

	/**
	 * 用户取消订阅处理逻辑
	 * @param eventBean
	 * @throws Exception
	 */
	protected BaseMessage handleUserUnSubscribeMessage(EventBean eventBean) throws Exception{
		
		UserWechatBean bean = userWechatService.findUserWechatByOpenId(eventBean.getFromUserName());
		bean.setStatus(UserWechatBean.STATUS_UNSUBSCRIBE);
		
		userWechatService.update(bean );
		
		//子节点取消关注，是否取消点赞数量
		commentInfoService.deleteCommentInfoVotedInfo(bean.getOpenid());
		
		String str = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_UNSUBSCRIBE);
		
		if(StringUtil.isNotNull(str)){
			str = str.replaceAll("#userId#", bean.getId().toString());
			str = str.replaceAll("#nick_name#", bean.getNick_name());
			str = str.replaceAll("#offline_num#", String.valueOf(bean.getOffline_num()));
			
			
			UserBWCBean query = new UserBWCBean();
			query.setUserType(UserBWCBean.USERTYPE_MANAGER);
//			List<UserBWCBean> listManager = userWechatService.queryBwcUser(query );
//	
//			TextBean t = new TextBean();
//			for(UserBWCBean m : listManager){
//				t.setContent(str);
//				t.setToUserName(m.getOpenId());
//				sendMessageToUser(t);
//			}
		}
		return null;
	
	}
	
	/**
	 * 处理不同场景下的用户关注信息
	 * @param senceCode
	 * @param weixin_id
	 * @param parentId
	 */
	protected void handleSenceCode(Long senceValue, String weixin_id, String parentId) {
		if(senceValue == null) return;
//		String[] senceTypes = senceStr.split(SENCECODE_SPLIT_STR_2);
		int senceCode = WechatUtil.getSenceType(senceValue);
		int idValue = WechatUtil.getSenceId(senceValue);
		
		switch (senceCode) {
		case WechatConstant.TICKET_SENCE_CODE_UNDEFINE:
			
			break;
		case WechatConstant.TICKET_SENCE_CODE_LOTTERY:
			
			break;
		case WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT:
			if(parentId != null){
			}
			break;
		case WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS:
			
			break;
		default:
			break;
		}
		
	}

	/**
	 * 根据openId获取用户基本信息
	 * @param openId
	 * @return
	 * @throws Exception 
	 */
	@Override
	public UserWechatExtendBean getWechatUserInfo(String openid,String originalid) throws Exception{
		UserWechatExtendBean userBean ;
		String url = getUserInfoUrl(openid,originalid);
		String response = HttpUtil.get(url);
		log.debug("getWechatUserInfo response--------------------"+response);
		userBean = WechatUtil.parseJsonToUserWechatBean(new JSONObject(response));
		userBean.setOpenid(openid);
		String filterName = EmojiUtil.filterEmoji(userBean.getNick_name());
		userBean.setNick_name(filterName);
		return userBean;
	}
	
	/**
	 * 根据微信的OPENID 和 parentid 来创建用户
	 * @param weixin_id
	 * @param parentId
	 * @param originalid   微信公众号的原始ID，即从哪个公众号来的
	 * @throws Exception
	 */
	@Override
	public void createWechatUser(String weixin_id,String parentId,Integer senceType,Integer senceId,String originalid) throws Exception{
		UserWechatBean userBean =getWechatUserInfo(weixin_id,originalid);
		
		//父用户不为空
		if( StringUtil.isNotNull(parentId)){
			//根据参数先修改父Id的下线数量 +1
			userWechatService.updateUserOfflineNumWechat(parentId);
			userBean.setParentid(Integer.parseInt(parentId));
			
			//设置用户级别
			UserWechatBean parent = userWechatService.findUserWechatById(userBean.getParentid());
			if(parent != null){
				Integer level = parent.getLevel();
				if(level == null){
					level = 1;
				}
				userBean.setLevel(level + 1);
				
				//判断用户是否可以升级为VIP
				int vipNum = configAttributeService.getIntValueByCode(AttributeConstant.CODE_USER_LEVELUP_VIP_NUM);
				if(parent.getOffline_num() >= vipNum  && parent.getType() != null && parent.getType() == UserWechatBean.TYPE_NORMAL){
					parent.setType(UserWechatBean.TYPE_VIP);
				}
				
				if(parent.getType() != UserWechatBean.TYPE_DIDI){ //父级为滴滴用户的，不添加积分，其它类型的添加
					//父级添加积分
					int point = configAttributeService.getIntValueByCode(AttributeConstant.CODE_WECHAT_SUBCRIBE_PARENTADDPOINT);
					parent.setPoints(parent.getPoints() + point);
					userCommissionPointsService.createUserPointLog(parent.getId(), point, "推荐用户获得积分");
					userWechatService.update(parent);
				}
				

				//保存并处理用户动作
				msgCenterActionService.saveAndHandleUserAction(parent.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ADD_CHILD , null, userBean);
				
				
//				//给父用户发消息，通知有人关注了
//				String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_SUBCRIBE_ADDCHILD);
//				content = content.replaceAll("#nick_name#", userBean.getNick_name());
//				TextBean text = new TextBean();
//				text.setToUserName(parent.getOpenid());
//				text.setContent(content);
//				sendMessageToUser(text);
			}
			
		}
		//新关注用户入库，并且parentId存入所获取的参数
		//先根据用户id，获取用户基本信息,一起存入库中
//		System.out.print("用户基本信息-----------------"+userBean.getNick_name());
		userBean.setOpenid(weixin_id);
		userBean.setOriginalid(originalid);
		userBean.setSenceid(senceId);
		userBean.setSence_type(senceType);
		
		String date = DateUtil.getNowDateStr();
		userBean.setCreate_time(date);
		userBean.setLast_access_time(date);
		if(StringUtil.isNull(userBean.getNick_name())){
			userBean.setNick_name("微信用户"+ DateUtil.java2phpDate(System.currentTimeMillis()) );
		}
		
		//首次关注的积分
		int point = configAttributeService.getIntValueByCode(AttributeConstant.CODE_WECHAT_SUBCRIBE_INITPOINT);
		userBean.setPoints(point);
		userWechatService.addUser(userBean);
		userBean = userWechatService.findUserWechatByOpenId(userBean.getOpenid());
		userCommissionPointsService.createUserPointLog(userBean.getId(), point, "首次关注赠送积分");
		
	}
	
	/**
	 * 获取用户基本信息url
	 * @param openId
	 * @return
	 */
	private String getUserInfoUrl(String openid,String originalid){

		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		String url = server_url + WechatConstant.USERINFO + "?access_token="+access_token+"&openid="+openid+"&lang=zh_CN" ;
		return url;
	}
	
	/**
	 * 调用客服接口发消息给用户
	 * @param base
	 * @return
	 */
	@Override
	public BaseMessage sendMessageToUser(BaseMessage base){
		
		if(base == null ){
			log.error("sendMessage not be null!");
			return base;
		}
		if(base.getFromUserName() == null){
			log.error("base.getFromUserName not be null!");
			return base;
		}
		if(base instanceof TextBean){
			TextBean text = (TextBean) base;
			if(StringUtil.isNull(text.getContent())){
				log.debug("sendMessage content is null!");
				return base;
			}
		}

		try {
			String url = null;
			String content = null;
			//模板消息
			if(base instanceof TemplateMessage){
				url = getTemplateSendUrl(base.getFromUserName());
				TemplateMessage template = (TemplateMessage) base;
				//如果已有转换后的json数据在 msgContent里，那就直接用
				if(StringUtil.isNotNull(template.getContent())){
					content = template.getContent();
					
				}else{
					content = new JSONObject(base).toString();
				}
			}else{
				//客服消息
				url = getCustomerSendUrl(base.getFromUserName());
				content = WechatUtil.MessageToJson(base);
				base.setMsgContent(content);
			}
			String result = HttpUtil.post(url, content);
			
			log.debug("sendMessageToUser result : " + result);
			
			WechatUtil.parseJsonResponse(result, base);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			
			base.setErrcode(-2);
			base.setErrmsg("系统内部错误"+e.getMessage());
		}
		

		if(base.getFromUserName() == null){
			base.setFromUserName("bwc_self");
		}
		if(base.getCreateTime_Date() == null){
			base.setCreateTime_Date(new Date());
		}
		if(base.getStatus() == null){
			base.setStatus(String.valueOf(base.getErrcode()));
		}
		
		//记录服务端操作
		wechatDao.saveServerEvent(base);
		return base;
	}
	
	/**
	 * 开奖后给所有购买过的用户发送结果信息
	 * @param lotteryId
	 */
	@Override
	public void sendLotteryResultToUser(int lotteryId){
		
		
	}
	
	/**
	 * 执行发送消息 给用户
	 */
	@Override
	public void execSendMessageToUserList(){
		//子类中实现
	}
	
	/**
	 * 查询用户是否有未接收到的消息，如果有，就再次发送
	 * @param openId
	 */
	private void findUnSendMessage(String openId,UserWechatBean user){
		
		try{
			Thread.sleep(1000);
			//先判断这个用户多久没有访问过
//			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			if(user == null){
				return;
			}
			if(user.getUnionid() == null){
				//去微信服务端取数据,同步用户的unionid
			    UserWechatExtendBean newInfo = getWechatUserInfo(user.getOpenid(),user.getOriginalid());
		        if(newInfo.getSubscribe() == UserWechatExtendBean.SUBSCRIBE_YES)
		        {
				    String filterName = EmojiUtil.filterEmoji(newInfo.getNick_name());
			        newInfo.setNick_name(filterName);
			        newInfo.setId(user.getId());
			        newInfo.setBalance(null);
			        newInfo.setPoints(null);
			        //更新表数据
			        userWechatService.updateUserWechat(newInfo);
		        }
			}
			String startTime = user.getLast_access_time();
			if(startTime == null){
				return ;
			}
			//判断是否超过48小时没访问过的
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, -2);
			
			String compareTime =  DateUtil.formatDate(c.getTime());
			//相比上一次访问时间超过48小时了，才去查有没有未接收的消息
			if(compareTime.compareTo(startTime) > 0 ){
			
				String endTime = DateUtil.formatDate(new Date());
				
				List<Map<String,Object>> list = wechatDao.queryUnSendServerEvent(openId,startTime,endTime);
				
				TextBean text = new TextBean();
				text.setToUserName(openId);
				for(Map<String,Object> map : list){
					Date da = (Date)map.get("createTime_Date");
					String dateStr = DateUtil.formatDate(da);
					text.setContent("您不在的这段时间里，又有消息啦！于"+dateStr+" "+(String)map.get("Content"));
					
					text.setFromUserName(user.getOriginalid());
					text.setNotifyType((Integer)map.get("notifyType"));
					text.setFromUserOpenId((String)map.get("fromUserOpenId"));
					text.setProductId((Integer)map.get("productId"));
					text.setSbProductId((Integer)map.get("sbProductId"));
					text.setProductPrice((Integer)map.get("productPrice"));
					//发送数据
					sendMessageToUser(text);
				}
	
			}
		}catch (Exception e) {
			log.error(e, e);
		}
	}
	
	
	/**
	 * 更新微信的Access_token
	 */
	@Override
	public void updateWechatAccessToken(String originalid) {
		
		String url = getTokenUrl(originalid);
		try {
			String response = HttpUtil.get(url);
			TokenBean tokenBean = parseWecharResponse(response, WechatConstant.TYPE_TOKEN);
			
			updateToken(tokenBean,originalid);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
	}
	

	/**
	 * 更新jsapi_ticket
	 * @param bean
	 */
	@Override
	public void updateWechatJsApiTicket(String originalid) {
		
		String ticket = queryJsapiTicket(originalid);
		updateJsAPITicket(ticket,originalid);
		
	}
	
	/**
	 * 更新token
	 * @param bean
	 */
	private void updateToken(TokenBean bean,String originalid){

		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		originalInfo.setAccess_token(bean.getAccess_token());
		originalInfo.setExpires_in(bean.getExpires_in().toString());
		originalInfo.setModify_time(DateUtil.getNowDateStrSSS());
		wechatDao.updateWechatOriginalInfo(originalInfo);
	}
	
	/**
	 * 更新token
	 * @param bean
	 */
	private void updateJsAPITicket(String ticket,String originalid){

		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		originalInfo.setJsapi_ticket(ticket);
		originalInfo.setModify_time(DateUtil.getNowDateStrSSS());
		wechatDao.updateWechatOriginalInfo(originalInfo);
	}
	
	/**
	 * 更新token的URL
	 * @return
	 */
	private String getTokenUrl(String originalid) {
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);

		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String appid = originalInfo.getAppid();
		String secret = originalInfo.getSecret();
		String url = server_url + WechatConstant.TOKEN + "?grant_type=" + WechatConstant.GRANT_TYPE_CLIENT_CREDENTIAL
				+"&appid=" + appid + "&secret=" + secret;
		return url;
	}
	
	/**
	 * 发送客服接口的URL
	 * @return
	 */
	private String getCustomerSendUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		
		String url = server_url + WechatConstant.CUSTOMER_SEND +"?access_token="
				+ access_token;
		return url;
	}
	
	/**
	 * 发送木板消息接口的URL
	 * @return
	 */
	private String getTemplateSendUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		
		String url = server_url + WechatConstant.TEMPLATE_SEND +"?access_token="
				+ access_token;
		return url;
	}
	
	private <E extends BaseMessage> E parseWecharResponse(String json,int type){
		BaseMessage base = null;
		try {
			JSONObject jso = new JSONObject(json);
			
			switch (type) {
			case WechatConstant.TYPE_TOKEN:
				base = WechatUtil.parseJsonToTokenBean(jso);
				break;
			case WechatConstant.TYPE_QRCODE:
				base = WechatUtil.parseJsonToQrCodeBean(jso);
				break;
			default:
				break;
			}
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e,e);
		}
		
		return (E) base;
	}
	
	@Override
	public String validateServerSign(SignServerBean bean,String originalid){

		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String token= originalInfo.getToken();
		String[] str={token,bean.getTimestamp(),bean.getNonce()};
	    Arrays.sort(str);
	    String bigStr=str[0]+str[1]+str[2];
        String digest = new SHA1Util().getDigestOfString(bigStr.getBytes()).toLowerCase(); 
        if(digest.equals(bean.getSignature())){
	    	log.debug("签名一致，返回值："+bean.getEchostr());
	    	return bean.getEchostr();
        }else{
        	return "";
        			
        }
		
	}

	/**
	 * 更新用户的二维码ticket
	 */
	@Override
	public UserQrCodeBean updateUserTicket(String weixin_id,int senceCode,int senceId) throws IOException {

		UserWechatBean userBean = userWechatService.findUserWechatByOpenId(weixin_id);
		String ticket = "";
		UserQrCodeBean qrCode = null;
		if(userBean != null){
			String originalid = getRandomOriginalidByWeight(userBean.getOriginalid());
			String qrCodeUrl = getQrCodeUrl(originalid);
			long senceValue = WechatUtil.getScenceStr(senceCode, senceId, userBean.getId());
			String json = "{\"expire_seconds\": 2592000, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":"+senceValue+"}}}";

			String response = HttpUtil.post(qrCodeUrl,json);	
log.debug("userTicketResponse:"+response);
			QrCodeBean qrCodeBean = parseWecharResponse(response, WechatConstant.TYPE_QRCODE);
			ticket = qrCodeBean.getTicket();
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_YEAR,30);
			date = c.getTime();
			qrCode = userWechatService.updateUserTicket(senceCode,senceId,weixin_id,ticket,date,qrCodeBean.getUrl(),originalid);
		}
		return qrCode;
	}
	
	/**
	 * 随机取公众号ID
	 * @param defaultOriginalid
	 * @return
	 */
	private String getRandomOriginalidByWeight(String defaultOriginalid){
		List<WechatOriginalInfoBean> list = wechatDao.queryToOriginalInfoByFrom(defaultOriginalid);
		String originalid = defaultOriginalid;
		int sumWeight = 0 ;
		for(WechatOriginalInfoBean bean : list){
			if(bean.getWeight() != null &&  bean.getWeight() > 0){
				bean.setWeight_start(sumWeight);
				sumWeight = sumWeight + bean.getWeight();
				bean.setWeight_end(sumWeight);
			}
		}
		
		if(sumWeight > 0){
			int index = RandomUtil.nextInt(sumWeight);
			for(WechatOriginalInfoBean bean : list){
				if(bean.getWeight() != null &&  bean.getWeight() > 0 &&  index >= bean.getWeight_start() && index < bean.getWeight_end() ){
					originalid = bean.getOriginalid();
					break;
				}
			}
		}
		
		return originalid;
	}
	
	private String getQrCodeUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url+ WechatConstant.QRCODE +"?access_token="+access_token;
		return url;
	}
	
	/**
	 * 或取文章URL前缀
	 * @return
	 */
	private String getArticleUrlPre(){
		String url = configAttributeService.getValueByCode(AttributeConstant.CODE_ARTICLE_URL_PRE);
		return url;
	}
	
	//测试
	public void test(String eventKey){
		
		TextBean text = new TextBean();
		String url = getCustomerSendUrl("123");
		String content = WechatUtil.MessageToJson(text);
		text.setMsgContent(content);
		try {
			String result = HttpUtil.post(url, content);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getOauthUrl(String code,String originalid){
		String oauthUrl =configAttributeService.getValueByCode(AttributeConstant.CODE_OAUTH);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String appid = originalInfo.getAppid();
		String secret = originalInfo.getSecret();
		String url = oauthUrl+"access_token?appid="+appid+"&secret="+secret
				+"&code="+code+"&grant_type=authorization_code";
		return url;
	}
	
	@Override
	public String getOpenId(String code,String originalid) {

		String openId = null;
		
		if(StringUtil.isNull(code)){
			return openId;
		}
		
		//先查缓存，如果没有再去微信服器查询
		String cachedCode = wechatCodeMap.get(code);
		if(StringUtil.isNull(cachedCode)){

			String url =getOauthUrl(code,originalid);
			
			try {
				String result = HttpUtil.get(url);
				JSONObject jso = new JSONObject(result);
				openId = jso.getString("openid");
				
				userWechatService.addUserToSession(openId);
				
				long time = System.currentTimeMillis();
				wechatCodeMap.put(code, openId);
				codeTimeMap.put(time, code);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}
		}else{
			openId = cachedCode;
		}
		
		checkCacheCodeTimeOut();
		
		return openId;
	}
	
	/**
	 * 缓存的微信code，定时清除掉
	 */
	private void checkCacheCodeTimeOut(){
		long thisTime = System.currentTimeMillis();
		long subTime = 10 * 60 * 1000; //10分钟
		
		long minTime = thisTime - subTime;
		
		for( Object keyValue : codeTimeMap.keySet().toArray()){
			Long key = (Long)keyValue;
			if(key < minTime){
				String code = codeTimeMap.get(key);
				wechatCodeMap.remove(code);
				codeTimeMap.remove(key);
			}
		}
		log.debug("codeTimeMap:" + codeTimeMap);
		log.debug("wechatCodeMap:" + wechatCodeMap);
		log.debug("checkCacheCodeTimeOut cost:" + (System.currentTimeMillis() - thisTime));
	}

	@Override
	public String getJsapiTicket(String originalid) {
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String ticket_value = originalInfo.getJsapi_ticket();
		return ticket_value;
	}
	
	

	private String queryJsapiTicket(String originalid) {
		String url = getJsapiUrl(originalid);
		String result;
		String ticket_value="";
		try {
			result = HttpUtil.get(url);
			JSONObject jso = new JSONObject(result);
			ticket_value = jso.getString("ticket");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return ticket_value;
	}

	private String getJsapiUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url+ WechatConstant.JSAPI_TICKET_URL +"access_token="+access_token+"&type=jsapi";
		return url;
	}
	
	/**
	 * 微信获取素材总数的URL
	 * @return
	 */
	private String getWechatMaterialCountUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url+ WechatConstant.GET_MATERIALCOUNT +"access_token="+access_token ;
		return url;
	}
	/**
	 * 微信获取素材列表的URL
	 * @return
	 */
	private String getWechatMaterialListUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url+ WechatConstant.BATCHGET_MATERIAL +"access_token="+access_token ;
		return url;
	}
	
	/**
	 * 获取微信素材新增，修改删除（永久素材）的URL
	 * @param originalid
	 * @return
	 */
	private String getWechatMaterialAddNewsUrl(String originalid,String action){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String mothed = "";
		if(WechatMaterialDeliveryLogBean.ACTION_ADD.equals(action)){
			mothed = WechatConstant.ADD_NEWS;
		}else if(WechatMaterialDeliveryLogBean.ACTION_UPDATE.equals(action)){
			mothed = WechatConstant.UPDATE_NEWS;
		}else if(WechatMaterialDeliveryLogBean.ACTION_DEL.equals(action)){
			mothed = WechatConstant.DEL_NEWS;
		}else if(WechatMaterialDeliveryLogBean.ACTION_GET.equals(action)){
			mothed = WechatConstant.GET_NEWS;
		}
		String url = server_url+ mothed +"access_token="+access_token ;
		return url;
	}
	
	/**
	 * 查询素材
	 */
	@Override
	public WechatMaterialBean findWechatMaterial(int id){
		return wechatDao.findWechatMaterialById(id);
	}
	
	/**
	 * 同步微信后台的素材，入库保存
	 */
	@Override
	public void syncWechatMaterial(String originalid){
		long time = System.currentTimeMillis();
		MaterialBean materialCount = queryWechatMaterialTotalCount(originalid);
		
		if(materialCount.getNews_count() != 0){
			updateWechatMaterialByType(materialCount.getNews_count(), WechatUtil.MESSAGE_TYPE_NEWS ,originalid);
		}
		if(materialCount.getImage_count() != 0){
			updateWechatMaterialByType(materialCount.getImage_count(), WechatUtil.MESSAGE_TYPE_IMAGE ,originalid);
		}
		if(materialCount.getVideo_count() != 0){
			updateWechatMaterialByType(materialCount.getVideo_count(), WechatUtil.MESSAGE_TYPE_VIDEO ,originalid);
		}
		if(materialCount.getVoice_count() != 0){
			updateWechatMaterialByType(materialCount.getVoice_count(), WechatUtil.MESSAGE_TYPE_VOICE ,originalid);
		}
		
		log.info("syncWechatMaterial success, cost:" + (System.currentTimeMillis() - time) +"ms");
	}
	
	@Override
	public List<WechatMaterialBean> queryWechatMaterial(WechatMaterialBean bean){
		
		return wechatDao.queryWechatMaterial(bean);
	}
	
	@Override
	public WechatTemplateMsgBean queryWechatTemplateMsg(String originalid,String code){
		WechatTemplateMsgBean bean = new WechatTemplateMsgBean();
		bean.setOriginalid(originalid);
		bean.setCode(code);
		List<WechatTemplateMsgBean> list = wechatDao.queryWechatTemplateMsg(bean );
		
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List queryWechatMaterialBySucaiType(String sucai_type){
		WechatMaterialBean bean = new WechatMaterialBean();
		//批量得到得到图文素材
		if(WechatMaterialBean.MATERIAL_SUCAITYPE_NEWSALL.equals(sucai_type)){
//			bean.setSucai_type(WechatMaterialBean.MATERIAL_SUCAITYPE_NEWS);
//			bean.setPageSize(10000);
//			PageinationData pd = this.showWechatMaterialInfoForDelivery(null, bean, null, null);
			return queryMainMaterialList();
		}else{
			bean.setSucai_type(sucai_type);
			return wechatDao.queryWechatMaterial(bean);
		}
	}
	
	private List<WechatParentMaterialBean> queryMainMaterialList(){
		List<WechatParentMaterialBean> parentList = new ArrayList<WechatParentMaterialBean>();
		WechatMaterialRelationBean ralaBean = new WechatMaterialRelationBean();
		List<WechatMaterialRelationBean> relaBeanList = wechatDao.queryWechatMaterialRelationByMainMaterial(ralaBean);
		List<String> main_media_ids = new ArrayList<String>();
		for (WechatMaterialRelationBean wechatMaterialRelationBean : relaBeanList) {
			if(!main_media_ids.contains(wechatMaterialRelationBean.getMain_media_id())){
				main_media_ids.add(wechatMaterialRelationBean.getMain_media_id());
			}
		}
		List<WechatMaterialBean> materalBean = wechatDao.queryWechatMaterialByMediaList(main_media_ids);
		List<WechatMaterialBean> parentBean = wechatDao.queryParentWechatMaterialByMediaList(main_media_ids);
		for (WechatMaterialBean parentMaterial : parentBean) {
			WechatParentMaterialBean parentMaterialBean = new WechatParentMaterialBean();
			//克隆父类属性值
			parentMaterialBean.setId(parentMaterial.getId());
			parentMaterialBean.setCreate_time(parentMaterial.getCreate_time());
			parentMaterialBean.setModify_time(parentMaterial.getModify_time());
			parentMaterialBean.setSucai_media_id(parentMaterial.getSucai_media_id());
			parentMaterialBean.setSucai_type(parentMaterial.getSucai_type());
			parentMaterialBean.setOriginalid(parentMaterial.getOriginalid());
			parentMaterialBean.setOriginal_name(parentMaterial.getOriginal_name());
			List<String> deliveryOriginal = new ArrayList<String>();
			for (WechatMaterialRelationBean wechatMaterialRelationBean : relaBeanList) {
				if(!deliveryOriginal.contains(wechatMaterialRelationBean.getOriginal_name()) && parentMaterial.getSucai_media_id().equals(wechatMaterialRelationBean.getMain_media_id())){
					deliveryOriginal.add(wechatMaterialRelationBean.getOriginal_name());
				}
			}
			parentMaterialBean.setDeliveryOriginal(StringUtils.join(deliveryOriginal.toArray(),"],["));
			List<WechatMaterialBean> materialList = new ArrayList<WechatMaterialBean>();
			boolean isFirst = true;
			for (WechatMaterialBean material : materalBean) {
				//该子素材属于父素材
				if(parentMaterialBean.getSucai_media_id().equals(material.getParent_media_id())){
					if(isFirst){
						parentMaterialBean.setSucai_title(material.getSucai_title());
						parentMaterialBean.setSucai_url(material.getSucai_url());
						parentMaterialBean.setSucai_thumb_url(material.getSucai_thumb_url());
						isFirst = false;
					}
					material.setSucai_digest(null);
					material.setSucai_content(null);
					materialList.add(material);
				}
			}
			parentMaterialBean.setMaterialList(materialList);
			parentList.add(parentMaterialBean);
		}
		return parentList;
	}
	
	/**
	 * 更新微信的素材数据入库
	 * @param total
	 * @param type
	 */
	private void updateWechatMaterialByType(int total,String type,String originalid){
		
		//先删除系统中的数据，再重新添加
		wechatDao.deleteWechatMaterialByType(type,originalid);
		//分页查询数据
		for(int i = 0 ; i < total ; i = i + MaterialBean.COUNT_DEFAULT ){
				
			List<WechatMaterialBean> list = queryWechatMaterialList( type , i, MaterialBean.COUNT_DEFAULT ,originalid);
			//保存，入库
			for(WechatMaterialBean bean : list){
				bean.setOriginalid(originalid);
				wechatDao.createWechatMaterial(bean);
			}
			
		}
	}
	
	/**
	 * 查询微信素材总数
	 * @return
	 */
	private MaterialBean queryWechatMaterialTotalCount(String originalid){
		MaterialBean total = null;
		String url = getWechatMaterialCountUrl(originalid);
		try {
			String response = HttpUtil.get(url);
			
			total = WechatUtil.parseJsonToMaterialCount(response);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return total;
	}
	
	/**
	 * 分页查询微信的素材数据
	 * @param type
	 * @param offset
	 * @param count
	 * @return
	 */
	private List<WechatMaterialBean> queryWechatMaterialList(String type,Integer offset,Integer count,String originalid){
		
		List<WechatMaterialBean> list = null;
		if(offset == null) offset = 0;
		if(count == null) count = 20;
		String url = getWechatMaterialListUrl(originalid);
		
		MaterialBean param = new MaterialBean();
		param.setType(type);
		param.setOffset(offset);
		param.setCount(count);
		String request = new JSONObject(param).toString();
		
		try {
			String response = HttpUtil.post(url, request);
			
			list = WechatUtil.parseJsonToMaterialList(response,type);
			
		} catch (IOException e) {
			log.error(e, e);
		}
		
		return list;
	}
	
	/**
	 * 处理微信素材，包含新增和修改,删除
	 * @param originalid
	 * @return
	 */
	private WechatMaterialRelationBean dealWechatMaterialNewsByOriginalid(String materal_id,String originalid,String action){
		WechatMaterialRelationBean relaBean = new WechatMaterialRelationBean();
		WechatOriginalInfoBean originalBean = new WechatOriginalInfoBean();
		originalBean.setId(Integer.valueOf(originalid));
		List<WechatOriginalInfoBean> originalBeanList = wechatDao.queryWechatOriginalInfo(originalBean);
		if(originalBeanList.size() > 0){
			originalBean = originalBeanList.get(0);
		}else{
			return relaBean;
		}
		
		//获取素材的基本信息
		WechatMaterialBean parentBean = new WechatMaterialBean();
		parentBean.setSucai_media_id(materal_id);
		List<WechatMaterialBean> parentList = wechatDao.queryWechatMaterial(parentBean);
		if(parentList.size() > 0){
			parentBean = parentList.get(0);
			//查询素材的分发记录
			List<WechatMaterialRelationBean> relaBeanList = queryWechatMaterialRelationByBean(parentBean,relaBean,null);
			if(relaBeanList.size() > 0){
				for (WechatMaterialRelationBean wechatMaterialRelationBean : relaBeanList) {
					//判断是主素材
					if(parentBean.getSucai_media_id().equals(wechatMaterialRelationBean.getChildren_media_id()) && parentBean.getOriginalid().equals(originalBean.getOriginalid())){
						relaBean.setMain_media_id(parentBean.getSucai_media_id());
						relaBean.setChildren_media_id(parentBean.getSucai_media_id());
					}
					//得到需要需要分发的子素材（通过A编辑的素材Aa,第一次通过分发程序已近将素材分发给力B,C得到Ba，Ca；
					//此时在通过Aa发送分发请求是更新的则是Aa，Ba，Ca；media_id是不同的，此处就要获取这个media_id）
					if(originalBean.getOriginalid().equals(wechatMaterialRelationBean.getOriginalid())){
						relaBean = wechatMaterialRelationBean;
						break;
					}
				}
			}else{
				//主素材，往自己公众号发送（没有分发记录的默认主素材）
				if(parentBean.getOriginalid().equals(originalBean.getOriginalid())){
					relaBean.setMain_media_id(parentBean.getSucai_media_id());
					relaBean.setChildren_media_id(parentBean.getSucai_media_id());
				}
			}
			//判断该走那一步操作（新增？修改？删除？）
			String path = choicePath(originalBean.getOriginalid(),parentBean,action,relaBeanList,relaBean);
			//操作日志记录
			recordDeliveryInfo(path,originalBean.getOriginalid(),materal_id);
			//可以执行动作
			if(!path.equals(WechatMaterialDeliveryLogBean.ACTION_NO)){
				String pathUrl = getWechatMaterialAddNewsUrl(originalBean.getOriginalid(),path);
				//查询得到素材的基本信息
				WechatMaterialBean bean = new WechatMaterialBean();
				bean.setParent_media_id(parentBean.getSucai_media_id());
				List<WechatMaterialBean> materialList = wechatDao.queryWechatMaterial(bean);
				//根据动作获取相应的请求参数
				List<WechatArticlesBean> requestList = getRequestBeanForWechatDelivery(path,materialList,relaBean,originalBean);
				String response = "";
				for (WechatArticlesBean wechatArticlesBean : requestList) {
					String request = new JSONObject(wechatArticlesBean).toString();
					log.info("ACTION:"+path+";requestJSON:"+request);
					try {
						response = HttpUtil.post(pathUrl, request);
						log.info("ACTION:"+path+";responseJSON:"+response);
					} catch (IOException e) {
						log.error(e, e);
					}
				}
				if(StringUtil.isNotNull(response)){
					relaBean = dealDeliveryResponse(response,path,parentBean,relaBean,originalBean);
				}
			}
		}
		return relaBean;
	}
	
	/**
	 * 通过主素材的图片id置换子素材的图片id
	 * @param thumbMediaid
	 * @return
	 */
	public void delAllThumbMaterialByBean(String mediaid,WechatOriginalInfoBean originalBean){
		WechatMaterialBean bean = new WechatMaterialBean();
		bean.setParent_media_id(mediaid);
		//得到素材
		List<WechatMaterialBean> materialList = wechatDao.queryWechatMaterial(bean);
		if(materialList.size() > 0){
			for (WechatMaterialBean wechatMaterialBean : materialList) {
				//先将苏略图的素材删除，后面新增
				String pathUrl = getWechatMaterialAddNewsUrl(originalBean.getOriginalid(),WechatMaterialDeliveryLogBean.ACTION_DEL);
				WechatArticlesBean delBean = new WechatArticlesBean();
				delBean.setMedia_id(wechatMaterialBean.getSucai_thumb_media_id());
				String request = new JSONObject(delBean).toString();
				try {
					String response = HttpUtil.post(pathUrl, request);
					if(StringUtil.isNotNull(response) && response.contains("errcode")){
						JSONObject reponseJson = new JSONObject(response);
						//删除成功
						log.info(reponseJson.toString());
					}else{
						log.info("素材"+wechatMaterialBean.getSucai_thumb_media_id()+"删除失败！");
					}
				} catch (IOException e) {
					log.error(e, e);
				}
			}
		}
	}
	
	/**
	 * 保存分发记录
	 * @param action
	 * @param oroginalid
	 * @param mediaid
	 */
	private void recordDeliveryInfo(String action,String oroginalid,String mediaid){
		WechatMaterialDeliveryLogBean bean = new WechatMaterialDeliveryLogBean();
		CustomerBean customer = customerService.getUser();
		bean.setUser_id(customer.getId());
		bean.setAction(action);
		bean.setDelivery_time(DateUtil.getNowDateStrSSS());
		bean.setMedia_id(mediaid);
		bean.setOriginalid(oroginalid);
		wechatDao.insertwechatMaterialDeliveryLog(bean);
	}

	/**
	 * 处理分发后微信返回的结果,不同的请求类型用不同的解析方法
	 * @param response
	 */
	private WechatMaterialRelationBean dealDeliveryResponse(String response,String action,WechatMaterialBean parentBean,WechatMaterialRelationBean relaBean,WechatOriginalInfoBean originalBean){
		try {
			if(StringUtil.isNull(relaBean)){
				relaBean = new WechatMaterialRelationBean();
			}
			relaBean.setOriginalid(originalBean.getOriginalid());
			int action_type = WechatMaterialRelationBean.ACTION_TYPE_ADD;
			if(action.equals(WechatMaterialDeliveryLogBean.ACTION_ADD)){
				action_type = WechatMaterialRelationBean.ACTION_TYPE_ADD;
				JSONObject responseJson = new JSONObject(response);
				String media_id = responseJson.get("media_id").toString();
				relaBean.setMain_media_id(parentBean.getSucai_media_id());
				relaBean.setChildren_media_id(media_id);
				relaBean.setErrorcode(BaseMessage.ERROR_CODE_SUCCESS);
				relaBean.setCreate_time(DateUtil.getNowDateStrSSS());
				relaBean.setAction_type(action_type);
				CustomerBean customer = customerService.getUser();
				relaBean.setUser_id(customer.getId());
				wechatDao.saveWechatMaterialRelation(relaBean);
			}else if(action.equals(WechatMaterialDeliveryLogBean.ACTION_UPDATE) || action.equals(WechatMaterialDeliveryLogBean.ACTION_DEL)){
				if(action.equals(WechatMaterialDeliveryLogBean.ACTION_DEL)){
					action_type = WechatMaterialRelationBean.ACTION_TYPE_DEL;
					wechatDao.deleteWechatMaterialRelation(relaBean);
				}else{
					JSONObject responseJson = new JSONObject(response);
					String errcode = responseJson.get("errcode").toString();
					String errmsg = responseJson.get("errmsg").toString();
					relaBean.setErrorcode(Integer.valueOf(errcode));
					relaBean.setModify_time(DateUtil.getNowDateStrSSS());
					action_type = WechatMaterialRelationBean.ACTION_TYPE_UPDATE;
					relaBean.setAction_type(action_type);
					CustomerBean customer = customerService.getUser();
					relaBean.setUser_id(customer.getId());
					wechatDao.saveWechatMaterialRelation(relaBean);
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return relaBean;
	}
	
	
	/**
	 * //根据动作获取相应的请求参数
	 * @param action
	 * @param materialList
	 * @return
	 */
	private List<WechatArticlesBean> getRequestBeanForWechatDelivery(String action,List<WechatMaterialBean> materialList,WechatMaterialRelationBean relaBean,WechatOriginalInfoBean originalBean){
		List<WechatArticlesBean> requestBeanList = new ArrayList<WechatArticlesBean>();
		if(action.equals(WechatMaterialDeliveryLogBean.ACTION_ADD)){
			try {
				WechatArticlesBean requestBean = new WechatArticlesBean();
				List<WechatArticlesBean> articles = new ArrayList<WechatArticlesBean>();
				for (WechatMaterialBean wechatMaterialBean : materialList) {
					WechatUploadMaterialBean uploadBean = getNewMediaIdByParentMediaUrl(wechatMaterialBean.getSucai_thumb_url(), originalBean, "thumb");
					if(StringUtil.isNull(uploadBean)){
						continue;
					}
					WechatArticlesBean bean = new WechatArticlesBean();
					bean.setAuthor(wechatMaterialBean.getSucai_author());
					bean.setContent(wechatMaterialBean.getSucai_content());
					bean.setContent_source_url(wechatMaterialBean.getSucai_content_source_url());
					bean.setShow_cover_pic(wechatMaterialBean.getSucai_show_cover_pic());
					bean.setThumb_media_id(uploadBean.getMedia_id());
					bean.setTitle(wechatMaterialBean.getSucai_title());
					bean.setDigest(wechatMaterialBean.getSucai_digest());
					articles.add(bean);
				}
				requestBean.setArticles(articles);
				requestBeanList.add(requestBean);
			} catch (Exception e) {
				log.error(e, e);
			}
		}else if(action.equals(WechatMaterialDeliveryLogBean.ACTION_UPDATE)){
//			if(!relaBean.getChildren_media_id().equals(relaBean.getMain_media_id())){
//				//先将修改操作的所有缩略图素材删除，后面新增（非主素材）
//				delAllThumbMaterialByBean(relaBean.getChildren_media_id(),originalBean);
//			}
			try {
				int i = 0;
				for (WechatMaterialBean wechatMaterialBean : materialList) {
				//新增图片素材
					WechatArticlesBean requestBean = new WechatArticlesBean();
					WechatArticlesBean bean = new WechatArticlesBean();
					if(!relaBean.getChildren_media_id().equals(relaBean.getMain_media_id())){
						//非主素材才去新增缩略图素材
						WechatUploadMaterialBean uploadBean = getNewMediaIdByParentMediaUrl(wechatMaterialBean.getSucai_thumb_url(), originalBean, "thumb");
						if(StringUtil.isNull(uploadBean)){
							i++;
							continue;
						}
						bean.setThumb_media_id(uploadBean.getMedia_id());
					}else{
						bean.setThumb_media_id(wechatMaterialBean.getSucai_thumb_media_id());
						bean.setContent(wechatMaterialBean.getSucai_content());
					}
					bean.setContent(wechatMaterialBean.getSucai_content());
					requestBean.setIndex(i);
					requestBean.setMedia_id(relaBean.getChildren_media_id());
					bean.setAuthor(wechatMaterialBean.getSucai_author());
					bean.setContent_source_url(wechatMaterialBean.getSucai_content_source_url());
					bean.setShow_cover_pic(wechatMaterialBean.getSucai_show_cover_pic());
					bean.setTitle(wechatMaterialBean.getSucai_title());
					bean.setDigest(wechatMaterialBean.getSucai_digest());
					requestBean.setArticles(bean);
					requestBeanList.add(requestBean);
					i++;
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}else if(action.equals(WechatMaterialDeliveryLogBean.ACTION_DEL)){
				WechatArticlesBean requestBean = new WechatArticlesBean();
				requestBean.setMedia_id(relaBean.getChildren_media_id());
				requestBeanList.add(requestBean);
		}
		return requestBeanList;
	}
	
	/**
	 * 查询素材的分发记录
	 * @param material
	 * @param relBean
	 * @return
	 */
	@Override
	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByBean(WechatMaterialBean material,WechatMaterialRelationBean relBean,String originalid){
		//从分发关系表查询该素材的分发情况；
		relBean.setMain_media_id(material.getSucai_media_id());
		relBean.setOriginalid(originalid);
		List<WechatMaterialRelationBean> relaList = wechatDao.queryWechatMaterialRelationByMainMaterial(relBean);
		if(relaList.size() == 0){
			relBean.setMain_media_id(null);
			relBean.setChildren_media_id(material.getSucai_media_id());
			relaList = wechatDao.queryWechatMaterialRelationByMainMaterial(relBean);
			if(relaList.size() > 0){
				relBean.setMain_media_id(relaList.get(0).getMain_media_id());
				relBean.setChildren_media_id(null);
				//是的返回值是分发记录，通过main_medai_id获取的
				relaList = wechatDao.queryWechatMaterialRelationByMainMaterial(relBean);
			}
		}
		return relaList;
	}
	
	/**
	 * 查询素材的分发记录Ⅱ
	 * @param material
	 * @param relBean
	 * @return
	 */
	@Override
	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByBean(WechatMaterialRelationBean relBean){
		//从分发关系表查询该素材的分发情况；
		List<WechatMaterialRelationBean> relaList = wechatDao.queryWechatMaterialRelationByMainMaterial(relBean);
		return relaList;
	}
	
	/**
	 * 判断素材该走的方向
	 * @param Mediaid
	 * @return
	 */
	private String choicePath(String originalid,WechatMaterialBean material,String action,List<WechatMaterialRelationBean> relaList,WechatMaterialRelationBean relBean){
		//分发记录表无记录，直接返回
		if(!StringUtil.isNull(relBean)){
			
		
		if(relaList.size() > 0){
			if(action.equals(WechatMaterialDeliveryLogBean.ACTION_ADD)){
				if(relBean.getMain_media_id().equals(relBean.getChildren_media_id()) || !StringUtil.isNull(relBean.getId())){
					action = WechatMaterialDeliveryLogBean.ACTION_UPDATE;
					return action;
				}
			}
			//如果是删除操作，删除的是主素材id，并且还有子素材存才，不执行删除操作
			if(action.equals(WechatMaterialDeliveryLogBean.ACTION_DEL)){
				if(relBean.getMain_media_id().equals(relBean.getChildren_media_id()) && relaList.size() > 1){
					action = WechatMaterialDeliveryLogBean.ACTION_NO;
				}
			}
		}else{
			if(action.equals(WechatMaterialDeliveryLogBean.ACTION_DEL)){
				action = WechatMaterialDeliveryLogBean.ACTION_NO;
			}
		}
		}
		//判断该素材是不是主素材（即是通过该素材分发到其他子素材上的（ADD），同时也分发给了自己（UPDATE））
		//当前素材归属的公众号ID
		String now_originalid = material.getOriginalid();
		//公众号相同
		if(now_originalid.equals(originalid)){
			//对于自己，新增公众号只做修改操作
			if(action.equals(WechatMaterialDeliveryLogBean.ACTION_ADD)){
				action = WechatMaterialDeliveryLogBean.ACTION_UPDATE;
				relBean.setMain_media_id(material.getSucai_media_id());
				relBean.setCreate_time(DateUtil.getNowDateStrSSS());
				relBean.setChildren_media_id(material.getSucai_media_id());
			}
		}
		//分发操作		
		return action;
	}
	
	@Override
	public WechatOriginalInfoBean findWechatOriginalInfoByOriginalid(String originalid){
		WechatOriginalInfoBean bean = new WechatOriginalInfoBean();
		bean.setOriginalid(originalid);
		List<WechatOriginalInfoBean> list = queryWechatOriginalInfo(bean);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public WechatOriginalInfoBean findWechatOriginalInfoByServer(String server_domain){
		WechatOriginalInfoBean bean = new WechatOriginalInfoBean();
		bean.setServer_domain(server_domain);
		List<WechatOriginalInfoBean> list = queryWechatOriginalInfo(bean);
		if(list.size()>0){
			return list.get(0);
		}else{
			//如果没有查到，再按serverurl去查
			bean.setServer_domain(null);
			bean.setServer_url(server_domain);

			list = queryWechatOriginalInfo(bean);
			if(list.size()>0){
				return list.get(0);
			}
			
		}
		return null;
	}
	
	@Override
	public List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean){
		return wechatDao.queryWechatOriginalInfo(bean);
	}
	
	
	
	public static void main(String[] args) {
		long senceValue = WechatUtil.getScenceStr(4, 1, 2);
		String json = "{\"expire_seconds\": 2592000, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":"+senceValue+"}}}";

		String qrCodeUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" +
				"qn_c1W8APzQP6wzIN8eqYFAcw-vddMXtykjxf93rt08V7CI1VtV1OKuROwMDH7Zozc-4sudqtbfyjrAX0af2UBZsSAntuahXe0diSNftYRYXPYfAJAQIR" ;
		try {
			String response = HttpUtil.post(qrCodeUrl ,json);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	/**
	 * 素材分发前的展示
	 */
	@Override
	public PageinationData showWechatMaterialInfoForDelivery(List<WechatOriginalInfoBean> originalList, WechatMaterialBean bean,Object start_times,Object end_time) {
		//所有子素材
		List<WechatMaterialBean> showList = new ArrayList<WechatMaterialBean>();
		//所有父素材
		List<WechatMaterialBean> parentShowList = new ArrayList<WechatMaterialBean>();
		//定义返回数据集合
		List<WechatParentMaterialBean> parentList = new ArrayList<WechatParentMaterialBean>();
		
		PageinationData pd = new PageinationData();
		//处理公众号集合，使其只返回id和name
		if(!StringUtil.isNull(originalList) && originalList.size() > 0){
			WechatOriginalInfoBean originalBean = new WechatOriginalInfoBean();
			for (WechatOriginalInfoBean wechatOriginalInfoBean : originalList) {
				originalBean.setId(wechatOriginalInfoBean.getId());
				originalBean.setOriginal_name(wechatOriginalInfoBean.getOriginal_name());
				wechatOriginalInfoBean = originalBean;
			}
		}
		//查询消息集合处理，包含分页
		queryMaterialListByBean(bean,showList,parentShowList,pd, start_times, end_time);
		
		WechatMaterialBean materialBean = new WechatMaterialBean();
		WechatMaterialRelationBean relaBean = new WechatMaterialRelationBean();
		for (WechatMaterialBean parentMaterial : parentShowList) {
			WechatParentMaterialBean parentMaterialBean = new WechatParentMaterialBean();
			try {
				//克隆父类属性值
				parentMaterialBean.setId(parentMaterial.getId());
				parentMaterialBean.setCreate_time(parentMaterial.getCreate_time());
				parentMaterialBean.setModify_time(parentMaterial.getModify_time());
				parentMaterialBean.setSucai_media_id(parentMaterial.getSucai_media_id());
				parentMaterialBean.setSucai_type(parentMaterial.getSucai_type());
				parentMaterialBean.setOriginalid(parentMaterial.getOriginalid());
				parentMaterialBean.setOriginal_name(parentMaterial.getOriginal_name());
				materialBean.setSucai_media_id(parentMaterial.getSucai_media_id());
				List<WechatMaterialRelationBean> relaBeanList = queryWechatMaterialRelationByBean(materialBean,relaBean,null);
				List<String> deliveryOriginal = new ArrayList<String>();
				for (WechatMaterialRelationBean wechatMaterialRelationBean : relaBeanList) {
					if(!deliveryOriginal.contains(wechatMaterialRelationBean.getOriginal_name())){
						deliveryOriginal.add(wechatMaterialRelationBean.getOriginal_name());
					}
				}
				parentMaterialBean.setDeliveryOriginal(StringUtils.join(deliveryOriginal.toArray(),"],["));
//				AutoInvokeGetSetMethod.autoInvoke(parentMaterial,parentMaterialBean);
			} catch (Exception e) {
				log.error(e, e);
			}
			List<WechatMaterialBean> materialList = new ArrayList<WechatMaterialBean>();
			boolean isFirst = true;
			for (WechatMaterialBean material : showList) {
				//该子素材属于父素材
				if(parentMaterialBean.getSucai_media_id().equals(material.getParent_media_id())){
					if(isFirst){
						parentMaterialBean.setSucai_title(material.getSucai_title());
						parentMaterialBean.setSucai_url(material.getSucai_url());
						parentMaterialBean.setSucai_thumb_url(material.getSucai_thumb_url());
						isFirst = false;
					}
					material.setSucai_digest(null);
					material.setSucai_content(null);
					materialList.add(material);
				}
			}
			
			parentMaterialBean.setMaterialList(materialList);
			parentList.add(parentMaterialBean);
		}
		pd.setDataList(parentList);
		pd.setNowpage(bean.getNowpage() );
		try {
			pd.calculateTotalPage();
		} catch (Exception e) {
			log.error(e, e);
		}
		return pd;
	}
	
	/**
	 * 查询消息处理逻辑
	 * @param bean
	 * @param showList
	 * @param parentShowList
	 * @param pd
	 */
	private void queryMaterialListByBean(WechatMaterialBean bean,List<WechatMaterialBean> showList,List<WechatMaterialBean> parentShowList,PageinationData pd,Object start_time,Object end_time){
		if(StringUtil.isNull(bean)){
			bean = new WechatMaterialBean();
			bean.setNowpage(1);
		}
		if(bean.getNowpage() == 0){
			bean.setNowpage(1);
		}
		//查询全部
		if(StringUtil.isNotNull(bean.getOriginalid()) && bean.getOriginalid().equals("0")){
			bean.setOriginalid(null);
		}
		//查询符合条件的素材
		List<WechatMaterialBean> wechatMaterialList = wechatDao.queryDistinctWechatMaterialParentIdByBean(bean);
		// 处理素材，因为通常一个素材里面包含多个，我们需要对其进行重组
		// 素材数
		int totalCount = 0;
		List<String> media_id_list = new ArrayList<String>();
		if (!StringUtil.isNull(wechatMaterialList) && wechatMaterialList.size() > 0) {
			// 取出所有的素材mediaid
			for (WechatMaterialBean wechatMaterialBean : wechatMaterialList) {
//				if (!media_id_list.contains(wechatMaterialBean.getParent_media_id())) {
					media_id_list.add(wechatMaterialBean.getParent_media_id());
//				}
			}
		}
		if((!StringUtil.isNull(start_time) || !StringUtil.isNull(start_time)) && media_id_list.size() > 0){
			List<WechatMaterialBean> queryListOld = wechatDao.queryParentWechatMaterialByMediaList(media_id_list);
			Iterator<WechatMaterialBean> iter = queryListOld.iterator();
		    while(iter.hasNext()){
		    	WechatMaterialBean b = iter.next();
		    	if(!StringUtil.isNull(start_time)){
					if(start_time.toString().compareTo(b.getModify_time()) > 0){
						iter.remove();
						continue;
					}
				}
				if(!StringUtil.isNull(end_time)){
					if(end_time.toString().compareTo(b.getModify_time()) < 0){
						iter.remove();
						continue;
					}
				}
		    }
		    if (!StringUtil.isNull(queryListOld) && queryListOld.size() > 0) {
		    	media_id_list.clear();
				// 取出所有的素材mediaid
				for (WechatMaterialBean wechatMaterialBean : queryListOld) {
//					if (!media_id_list.contains(wechatMaterialBean.getParent_media_id())) {
						media_id_list.add(wechatMaterialBean.getSucai_media_id());
//					}
				}
			}
		}
		
		// 得到符合条件的素材总数（已去重）
		totalCount = media_id_list.size();
		List<String> queryList = new ArrayList<String>();
		// 根据素材的mediaid得到所有素材，此处做分页处理
		if (totalCount <= bean.getPageSize()) {
			queryList = media_id_list;
		} else {
			queryList = pd.fenye(media_id_list, bean.getNowpage(),bean.getPageSize()).getDataList();
		}
		if (queryList.size() > 0) {
			showList.addAll(wechatDao.queryWechatMaterialByMediaList(queryList));
			parentShowList.addAll(wechatDao.queryParentWechatMaterialByMediaList(queryList));
		}
		pd.setTotalcount(totalCount);
	}

	/**
	 * 分发微信消息
	 */
	@Override
	public List<WechatMaterialRelationBean> deliveryWechatMaterialInfo(String media_ids, String originalids,String action) {
		List<WechatMaterialRelationBean> relaBeanList = new ArrayList<WechatMaterialRelationBean>();
		//分发消息的必要参数校验
		if(StringUtil.isNotNull(originalids) && StringUtil.isNotNull(media_ids)){
			//公众号id
			List<String> originalidList = Arrays.asList(originalids.split(","));
			for (String string : originalidList) {
				if(StringUtil.isNotNull(string)){
					//公众号id
					List<String> media_idList = Arrays.asList(media_ids.split(","));
					for (String media_id : media_idList) {
						if(StringUtil.isNotNull(media_id)){
							WechatMaterialRelationBean relaBean = new WechatMaterialRelationBean();
							WechatMaterialRelationBean rela = dealWechatMaterialNewsByOriginalid(media_id,string,action);
							relaBean.setErrorcode(rela.getErrorcode());
							relaBeanList.add(relaBean);
						}
					}
				}
			}
		}
		//同步微信素材
//		List<WechatOriginalInfoBean> list = queryWechatOriginalInfo(null);
//		for(WechatOriginalInfoBean orginal : list){
//			syncWechatMaterial(orginal.getOriginalid());
//		}
		return relaBeanList;
	}

	/**
	 * 分发公众号素材
	 */
	@Override
	public void sendMessageByToUserAll(String originalIds,String mediaids) {
		//没有分发到的公众号，不发送消息，主素材号除外
		if(StringUtil.isNotNull(originalIds) && StringUtil.isNotNull(mediaids)){
			List<String> media_id_list = Arrays.asList(mediaids.split(","));
			List<String> original_id_list = Arrays.asList(originalIds.split(","));
			List<String> original_list = new ArrayList<String>();
			//得到公众号id集合
			for (String original_id : original_id_list) {
				if(StringUtil.isNotNull(original_id)){
					WechatOriginalInfoBean originalBean = new WechatOriginalInfoBean();
					originalBean.setId(Integer.valueOf(original_id));
					List<WechatOriginalInfoBean> originalBeanList = wechatDao.queryWechatOriginalInfo(originalBean);
					if(originalBeanList.size() > 0){
						originalBean = originalBeanList.get(0);
						original_list.add(originalBean.getOriginalid());
					}
				}
			}
			//根据主素材id和需要发送的公众号集合 返回需要发送的素材mediaid
			List<WechatMaterialBean> needSendMaterialList = getNeedSendMaterialList(media_id_list, original_list);
			for (WechatMaterialBean material : needSendMaterialList) {
				WechatSendAllJsonBean jsonBean = new WechatSendAllJsonBean();
				jsonBean.setFilter(true, null);
				jsonBean.setMpnews(material.getSucai_media_id());
				jsonBean.setMsgtype(WechatSendAllJsonBean.JSONBEAN_MSGTYPE_MPNEWS);
				try {
					String url = getMaterialSendToAllUrl(material.getOriginalid());
					String reauestJson = new JSONObject(jsonBean).toString();
					log.info("sendMessageByToUserAll reauestJson : " + reauestJson);
					String result = HttpUtil.post(url, reauestJson);
					log.info("sendMessageByToUserAll result : " + result);					
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e,e);
				}
				//记录服务端操作
			}
		}
	}
	
	
	/**
	 * 根据主素材id和需要发送的公众号集合 返回需要发送的素材mediaid
	 * @param media_id_list
	 * @param original_id_list
	 * @return
	 */
	public List<WechatMaterialBean> getNeedSendMaterialList(List<String> media_id_list,List<String> original_list){
		List<WechatMaterialBean> needSendMaterial = new ArrayList<WechatMaterialBean>();
		for (String media_id : media_id_list) {
			if(StringUtil.isNotNull(media_id)){
				WechatMaterialBean material = new WechatMaterialBean();
				material.setSucai_media_id(media_id);
				
				//查找素材的分发记录
				WechatMaterialRelationBean relBean = new WechatMaterialRelationBean ();
				List<WechatMaterialRelationBean> relaList = this.queryWechatMaterialRelationByBean(material, relBean,null);
				for (WechatMaterialRelationBean wechatMaterialRelationBean : relaList) {
					if(original_list.contains(wechatMaterialRelationBean.getOriginalid())){
						WechatMaterialBean bean = new WechatMaterialBean();
						bean.setSucai_media_id(wechatMaterialRelationBean.getChildren_media_id());
						bean.setOriginalid(wechatMaterialRelationBean.getOriginalid());
						needSendMaterial.add(bean);
					}
				}
				//查询得到该主素材记录
				List<WechatMaterialBean> main_material = this.queryWechatMaterial(material);
				for (WechatMaterialBean wechatMaterialBean : main_material) {
					if(original_list.contains(wechatMaterialBean.getOriginalid())){
						boolean is_insert = true;
						for (WechatMaterialBean childrenlBean : needSendMaterial) {
							if(childrenlBean.getSucai_media_id().equals(wechatMaterialBean.getSucai_media_id())){
								is_insert = false;
								break;
							}
						}
						if(is_insert){
							needSendMaterial.add(wechatMaterialBean);
						}
					}
				}
			}
		}
		return needSendMaterial;
	}
	
	/**
	 * 群发接口的URL
	 * @return
	 */
	private String getMaterialSendToAllUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url + WechatConstant.MATERIAL_SEND_ALL +"?access_token=" + access_token;
		return url;
	}
	
	/**
	 * 上传缩略图接口地址
	 * @return
	 */
	private String getUpLoadThumbMaterialUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url + WechatConstant.ADD_MATERIAL +"?access_token=" + access_token;
		return url;
	}
	
	/**
	 * 将父素材上传至子公众号的素材库里，返回该素材的mediaid和链接
	 * @param parentMediaUrl  父素材的链接
	 * @param type(image,voice,video,thumb),此处上传的时缩略图  type为thunb
	 * @throws IOException 
	 * @throws Exception 
	 */
	public WechatUploadMaterialBean getNewMediaIdByParentMediaUrl(String thumbUrl, WechatOriginalInfoBean originalBean,String type) throws IOException{
		byte[] bytes = null;
		//定义请求的配置信息
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 20).setConnectTimeout(1000 * 60).build();
		bytes = getThumbInfo(bytes,requestConfig,thumbUrl);
		// 获得utf-8编码的mbuilder
		MultipartEntityBuilder mBuilder = get_COMPATIBLE_Builder("UTF-8");
		// 设置type所以type是thumb
		mBuilder.addTextBody("type", type);
		String fileName = DateUtil.formatDatePure(new Date());
		// 这里就是我要上传到服务器的多媒体图片
		mBuilder.addBinaryBody("media",bytes ,ContentType.APPLICATION_OCTET_STREAM, fileName.substring(2, fileName.length())+".jpg");
		// 建造我们的http多媒体对象
		HttpEntity he = mBuilder.build();
		// post提交缩略图信息到公众号
		WechatUploadMaterialBean reponseBean = null;
		reponseBean = postThumbInfo(he,requestConfig,originalBean);
		return reponseBean;
	}
	
	/**
	 * 向公众号post缩略图信息
	 * @param he
	 * @param requestConfig
	 * @param originalBean
	 * @return
	 * @throws Exception 
	 */
	private WechatUploadMaterialBean postThumbInfo(HttpEntity he,RequestConfig requestConfig,WechatOriginalInfoBean originalBean) throws IOException{
		// 建立连接器
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			// 得到一个post请求的实体
			HttpPost post = getMultipartPost(originalBean.getOriginalid());
			post.setConfig(requestConfig);
			// 给请求添加参数
			post.setEntity(he);
			// 执行请求并获得结果
			CloseableHttpResponse reponse = client.execute(post);
			try {
				// 获得返回的内容
				HttpEntity entity = reponse.getEntity();
				// 输出
				String reponseStr = EntityUtils.toString(entity);
				if (StringUtil.isNotNull(reponseStr)) {
					JSONObject jsonBean = new JSONObject(reponseStr);
					if (reponseStr.contains("media_id")) {
						WechatUploadMaterialBean reponseBean = new WechatUploadMaterialBean();
						reponseBean.setErrcode(String.valueOf(BaseMessage.ERROR_CODE_SUCCESS));
						reponseBean.setErrmsg("上传成功！media_id："+ jsonBean.get("media_id").toString());
						reponseBean.setMedia_id(jsonBean.get("media_id").toString());
						reponseBean.setUrl(jsonBean.get("url").toString());
						return reponseBean;
					}
				}
				// 消耗实体
				EntityUtils.consume(entity);
			} finally {
				// 关闭返回的reponse
				reponse.close();
			}
		} finally {
			// 关闭client
			client.close();
		}
		return null;
	}
	
	/**
	 * 获取缩略图片信息
	 * @param bytes
	 * @param requestConfig
	 * @param thumbUrl
	 * @return
	 * @throws IOException
	 */
	private byte[] getThumbInfo(byte[] bytes,RequestConfig requestConfig,String thumbUrl) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			//get请求缩略图信息
			HttpGet httpGet = new HttpGet(thumbUrl); // 构造请求方式及内容
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			InputStream input = null;
			try {
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() >= 400) {
					throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode() + " thumbUrl: " + thumbUrl);
				}
				if (entity != null) {
					input = entity.getContent();
					//读出文件信息
					bytes = IOUtils.toByteArray(input);
				}
			} finally {
				if(!StringUtil.isNull(input)){
					input.close();
				}
				response.close();
			}
		} catch (Exception e1) {
			log.error(e1, e1);
		} finally {
			httpClient.close();
		}
		return bytes;
	}
	
	private MultipartEntityBuilder get_COMPATIBLE_Builder(String charSet) {
		MultipartEntityBuilder result = MultipartEntityBuilder.create();
		result.setBoundary(getBoundaryStr("7da2e536604c8")).setCharset(Charset.forName(charSet)).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		return result;
	}
	
	private String getBoundaryStr(String str) {
		return "------------" + str;
	}
	private HttpPost getMultipartPost(String originalid) {
		/* 这里设置一些post的头部信息，具体求百度吧 */
		HttpPost post = new HttpPost(getUpLoadThumbMaterialUrl(originalid));
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Accept", "*/*");
		post.addHeader("Content-Type", "multipart/form-data;boundary="+ getBoundaryStr("7da2e536604c8"));
		post.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		return post;
	}
	
}
