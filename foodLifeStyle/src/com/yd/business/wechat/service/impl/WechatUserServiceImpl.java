/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserSignBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.service.IUserSignService;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.EventBean;
import com.yd.business.wechat.bean.TemplateMessage;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatWaitSendBean;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.business.wechat.util.WechatUtil;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * 处理用户侧的微信逻辑
 * @author ice
 *
 */
@Service("wechatUserService")
public class WechatUserServiceImpl extends WechatServiceImpl {
	// 网页手动授权
	public static final String WEB_AUTH_CODE_SNSAPI_USERINFO = "snsapi_userinfo";
	// 网页静默授权
	public static final String WEB_AUTH_CODE_SNSAPI_BASE = "snsapi_base";
	
	@Resource
	private IUserSignService userSignService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private ISupplierEventService supplierEventService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private ISupplierUserService supplierUserService;
	
	@Override
	protected BaseMessage handleUserSubscribeMessage(EventBean eventBean) throws Exception {
		String eventKey = eventBean.getEventKey();
		System.out.println("eventKey----AAAAAAAAAAAAAAAAAAA-------"+eventKey);
		final String weixin_id = eventBean.getFromUserName(); 
		String parentId = null;
//		String senceCode = null;
		String senceValue =  null;
		Integer senceType = null;
		Integer senceId = null;
		
		if(StringUtil.isNotNull(eventKey) && eventKey.indexOf("_") >=0){
			// 普通关注发过来的eventKey竟然是一串不知道什么东西，加了个qrscene判断，如：
			//<EventKey><![CDATA[last_trade_no_1009570192201603154003927516]]></EventKey>
			String prefix = eventKey.substring(0,eventKey.indexOf("_")); 
			String senceString = eventKey.substring(eventKey.indexOf("_")+1,eventKey.length());
			
			//扫二维码的
			if("qrscene".equalsIgnoreCase(prefix) && senceString.length() >= 3){
				senceValue = senceString;
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
		if(uBean == null ){
			createWechatUser(weixin_id, parentId,senceType,senceId,eventBean.getToUserName());
		}else{
			uBean.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
			userWechatService.update(uBean);
		}
		//创建用户好友关系
		userWechatService.createUserWechatFriend(NumberUtil.toInt(parentId) ,weixin_id );
		
		//处理场景事件
		handleSenceCode(senceValue,weixin_id,parentId);

		TextBean result = null;
//		result = new TextBean();
//		result.setToUserName(eventBean.getFromUserName());
//		result.setFromUserName(eventBean.getToUserName());
//		result.setCreateTime(DateUtil.java2phpDate(System.currentTimeMillis()));
//		result.setMsgType(WechatUtil.MESSAGE_TYPE_TEXT);
//		String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_SUBSCRIBE_WELCOM);
//		result.setContent(content);

		eventBean.setProductId(senceType);
		eventBean.setProductId(senceId);
		msgCenterActionService.saveAndHandleUserAction(eventBean.getFromUserName(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_SUBSCRIBE, senceType +","+ senceId, eventBean);
		
		
//		taskExecutor.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					//休眠3秒再发送消息
//					Thread.sleep(3*1000);
//					//发送 积分如何变现金的图文消息给用户
//					ArticleBean article = new ArticleBean();
//					article.setToUserName(weixin_id);
//					article.setTitle("我一定会让你爱上我！");
//					article.setDescription("遇见我，你一定会爱上我！");
//					article.setUrl("http://mp.weixin.qq.com/s?__biz=MzI3NjMzMzYxOQ==&mid=100000059&idx=1&sn=524f6e43c01162d1a9fa3c7641694f09#rd");
//					article.setPicurl("http://mmbiz.qpic.cn/mmbiz_png/Wx0gSgnYgTaibWMS6KL3oOham3jmHGq6Xj1syKqDyUy8h7h3odJhny2xIYEP6ayW3V6kWIXvZJaeCvtn6dw0Vkw/0?wx_fmt=png");
//					sendMessageToUser(article);
//				} catch (InterruptedException e) {
//					log.error(e, e);
//				}
//				
//			}
//		});
		
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
		
		
		msgCenterActionService.saveAndHandleUserAction(bean.getFromUserName(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_CLICK, eventKey, bean);
		
		
		// 签到菜单被点击
		if(WechatConstant.MENU_KEY_USERSIGN.equals(eventKey)){
			
			UserSignBean sign = userSignService.userSignByOpenid(bean.getFromUserName());
			sign.setRemark("今日获得积分："+String.valueOf(sign.getLast_points()/100d +"元"));
			
			msgCenterActionService.saveAndHandleUserAction(bean.getFromUserName(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_MSG_USER_SIGN, null, sign);
			
		}
//		//点击具体的某个营销活动菜单
//		if(WechatConstant.MENU_KEY_SUPPLIEREVENT.equals(eventKey)){
//			
//			
//		}
//		//点击具体的某个菜单
//		if(WechatConstant.MENU_KEY_WECHAT.equals(eventKey)){
//			
//
//			String value = configAttributeService.getValueByCode(eventKeyStr);
//			
//		}
		
		return result;
	}
	
	
	
	/**
	 * 处理不同场景下的用户关注信息
	 * @param senceCode
	 * @param weixin_id
	 * @param parentId
	 */
	@Override
	protected void handleSenceCode(String senceValue, String weixin_id, String parentId) {
		if(senceValue == null) return;
//		String[] senceTypes = senceStr.split(SENCECODE_SPLIT_STR_2);
		int senceCode = WechatUtil.getSenceType(senceValue);
		int idValue = WechatUtil.getSenceId(senceValue);
		
		switch (senceCode) {
		case WechatConstant.TICKET_SENCE_CODE_UNDEFINE:
			
			break;
		case WechatConstant.TICKET_SENCE_CODE_LOTTERY:
			
			break;
			
		case WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT://商户活动
//			UserWechatBean user = userWechatService.findUserWechatByOpenId(weixin_id);
			//商户活动关注的,创建活动code
			if(parentId != null){
				
				//如果两个用户的unionid一样，那就不给它号了
//				UserWechatBean parent = userWechatService.findUserWechatById(Integer.parseInt(parentId));
				//不是自己
//				if(StringUtil.isNotNull(user.getUnionid()) && !user.getUnionid().equals(parent.getUnionid())){
					//查一下这个父用户下，有没有相同unionid的子用户
//					UserWechatConditionBean condition = new UserWechatConditionBean();
//					condition.setParentid(parent.getId());
//					condition.setUnionid(user.getUnionid());
//					List<UserWechatBean> list = userWechatService.queryUser(condition );
//					if(list.size() == 0){
						supplierEventService.createEventCode(idValue, Integer.parseInt(parentId), weixin_id);
//					}
//				}
			}
//			else if(!AreaData.PROVINCE_AH.equals(user.getProvince())){ 
//				//没有父用户的，并且省份不是安徽的直接送给自己一个幸运号
//				supplierEventService.createEventCode(idValue, user.getId(), weixin_id);
//			}
			break;
		case WechatConstant.TICKET_SENCE_CODE_SUPPLIERSHOPEFF:
			supplierUserService.createOrUpdateSupplierUser(weixin_id, idValue);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("idValue", idValue);
			map.put("senceCode", senceCode);
			map.put("parentId", parentId);
			
			msgCenterActionService.saveAndHandleUserAction(weixin_id, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_SUBSCRIBE_SHOP_EFF, null, map);

			break;
		case WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS:
			
			break;
		default:
			break;
		}
		
	}
	

	/**
	 * 执行发送消息 给用户
	 */
	@Override
	public void execSendMessageToUserList(){
		
		WechatWaitSendBean condition = new WechatWaitSendBean();
		condition.setStatus(WechatWaitSendBean.STATUS_WAIT);
		condition.setNow_time(DateUtil.getNowDateStr());
		
		//查询所有待发送
		List<WechatWaitSendBean> list = wechatDao.queryWaitSendMessage(condition );
		
		for(WechatWaitSendBean bean : list){

			UserWechatBean user = userWechatService.findUserWechatByOpenId(bean.getOpenid());
			WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
			//参数替换
			if(bean.getText_msg() != null){
				String text = bean.getText_msg() ;
				text = text.replaceAll("#server_domain#", original.getServer_domain());
				text = text.replaceAll("#server_url#", original.getServer_url());
				bean.setText_msg(text);
			}
			if(bean.getNews_url() != null){
				String news_url = bean.getNews_url();
				news_url = news_url.replaceAll("#server_domain#", original.getServer_domain());
				news_url = news_url.replaceAll("#server_url#", original.getServer_url());
				bean.setNews_url(news_url);
			}
			
			
			BaseMessage base = WechatUtil.convertWaitSendToBaseMessage(bean);
			//用于记录eca_wechat_send_message  与  eca_wechat_server_event 的关系
//			base.setMsgId(base.getId());
			
			base.setFromUserName(user.getOriginalid());
			sendMessageToUser(base);

			bean.setStatus(WechatWaitSendBean.STATUS_SEND);
			bean.setModify_time(DateUtil.getNowDateStr());
			bean.setSend_time(DateUtil.getNowDateStr());
			wechatDao.updateWaitSendMessage(bean);
		}
		
	}
	

	
}
