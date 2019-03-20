/**
 * 
 */
package com.yd.business.order.crons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.order.bean.OrderLogConditionBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * 未付款的定单回捞定时任务
 * @author ice
 *
 */
@Component("orderNotPayReFoundCrons")
public class OrderNotPayReFoundCrons extends BaseCrons {
	@Resource
	private IWechatService wechatService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
//		//未支付的回捞任务,已放在消息中心里提醒
//		notifyNotpayUser();
		//用户发送短信提醒领取红包
		PaiedUserTakeRed();
	}
	
	
	private void notifyNotpayUser() {
		log.info("orderNotPayReFoundCrons  start.....");
		//取20分钟之前的
		long time = 20 * 60 * 1000;
		String minTime = DateUtil.formatDate(System.currentTimeMillis() - time);
		List<OrderProductLogBean> list = orderProductLogService.queryOrderProductLogByNoPay(minTime );
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();				//申明一个map
//		for(OrderProductLogBean bean : list){
		for(int i = list.size()-1;i>=0;i--){
			OrderProductLogBean bean = null;
			bean = list.get(i);
			try{
				if(map.get(bean.getUser_id()) == null){		//做个if判断,如果list中userid和map中userid不相等发短信,相反继续循环
					UserWechatBean user = userWechatService.findUserWechatById(bean.getUser_id());
					map.put(bean.getUser_id(), bean.getUser_id());				//把user_id放到对应的map中
					String openid = user.getOpenid();
					String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_NOPAY_REFOUND);
					content = content.replaceAll("#openid#", openid);
					content = content.replaceAll("#nick_name#", user.getNick_name());
					content = content.replaceAll("#product_name#", bean.getProduct_name());
					content = content.replaceAll("#money#", String.valueOf(bean.getCost_balance() + bean.getCost_money()));
					content = content.replaceAll("#order_code#", bean.getOrder_code());
					
					TextBean text = new TextBean();
					text.setToUserName(openid);
					text.setContent(content);
					text.setFromUserName(user.getOriginalid());
					wechatService.sendMessageToUser(text);
					
					bean.setRemark(StringUtil.convertNull(bean.getRemark()) + "消息已发送");
					orderProductLogService.createOrUpdateOrderProductLog(bean);
				}else{										//其余用户只改变remark的值说明已经发送过了,但是实际上不给用户发消息
					bean.setRemark(StringUtil.convertNull(bean.getRemark()) + "消息已发送");
					orderProductLogService.createOrUpdateOrderProductLog(bean);
				}
			}catch (Exception e) {
				log.error(e, e);
			}
		}
		log.info("orderNotPayReFoundCrons  end.....");
		
	}


	private void PaiedUserTakeRed(){
		log.info("orderPaiedUserTakeRedCrons  start......");
		//获取已经付款但是没有领取红包用户的信息
		List<OrderLogConditionBean> listreduser = orderProductLogService.queryLucykIsNullUser();
		//获取短信内容
		for(OrderProductLogBean bean : listreduser){
			try{
				UserWechatBean user = userWechatService.findUserWechatById(bean.getUser_id());
				WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
				String openid = user.getOpenid();
				//获取短信内容
				String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_PAIED_REMIND_TAKE_RED);
				content = content.replaceAll("#order_code#", bean.getOrder_code());
				content = content.replaceAll("#server_domain#", original.getServer_domain());
				content = content.replaceAll("#server_url#", original.getServer_url());
				TextBean text = new TextBean();
				text.setToUserName(openid);
				text.setFromUserName(user.getOriginalid());
				text.setContent(content);
				wechatService.sendMessageToUser(text);
				bean.setRemark(StringUtil.convertNull(bean.getRemark()) + "--您身边的在线加油站");
				orderProductLogService.createOrUpdateOrderProductLog(bean);
			}catch (Exception e) {
				log.error(e, e);
			}
		}
		log.info("orderPaiedUserTakeRedCrons  end.....");
	}
	
	
}