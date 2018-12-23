/**
 * 
 */
package com.yd.business.order.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.AreaData;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.dao.IOrderDao;
import com.yd.business.isp.service.IAccessISPOrderInterfaceService;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.partner.bean.PartnerInterfaceBean;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.bean.SupplierProductAttachBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.sms.service.ISmsService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.service.ISupplierCardSecretService;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("orderService")
public class OrderServiceImpl extends BaseService implements IOrderService {
	@Resource
	private IOrderDao orderDao;
	@Resource
	private IProductService productService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private ICustomerAdminService customerAdminService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IAccessISPOrderInterfaceService accessISPOrderInterfaceService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private ISupplierCardSecretService supplierCardSecretService;
	@Resource
	private IProductTypeService productTypeService;
	@Resource
	private IChannelProductService channelProductService;
	@Resource
	private IAreaDataService areaDataService;
	@Resource
	private IChannelService channelService;
	@Resource
	private ISmsService smsService;
	@Resource
	private IWechatPayService wechatPayService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	
	//存放进行中的定单号，线程同步的，避免同一时刻多次重复定购
	private static ConcurrentHashMap<String,Object> runningCacheMap = new ConcurrentHashMap<String, Object>();
	
//	private static Map<String, AreaData> adMap = new HashMap<String, AreaData>();
	
//	@PostConstruct
//	private void initADMap(){
//		
//		List<AreaData> list = orderDao.queryAreaData(null);
//		
//		for(AreaData ad : list){
//			adMap.put(ad.getCode(), ad);
//		}
//		log.debug("Map<String, AreaData> adMap  init success! size:"+adMap.size());
//	}
	
	/**
	 * 根据手机的前几位取到区域数据
	 * @param phone
	 * @return
	 */
	@Override
	public AreaData getAreaDataByPhone(String phone){
		AreaData ad = null;
		
		if(StringUtil.isNotNull(phone) && phone.length() == 11 ){
			String pre8 = phone.substring(0, 8);
			ad = areaDataService.findAreaData(pre8);
			if(ad == null){
				String pre7 = phone.substring(0, 7);
				ad = areaDataService.findAreaData(pre7);
			}
			if(ad == null){
				String pre3 = phone.substring(0,3);
				ad = areaDataService.findAreaData(pre3);
			}
		}
		if(ad == null){
			log.error(" phone not found! "+ phone);
		}
		return ad;
	}
	
	/**
	 * 处理用户的订购商品逻辑
	 */
	@Override
	public OrderProductLogBean orderProductByUser(String out_trade_code,String param){

		//订购日志信息
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
//		ChannelBean channel = null;
//		if(orderLog != null ){
//			channel = channelService.findChannelById(orderLog.getChannel_id());
//		}
		if(runningCacheMap.get(out_trade_code) == null) { //只有不在runningCacheMap  中的定单号才去定购
			runningCacheMap.put(out_trade_code, "running");
			//只有是同步的，才需要在支付成功后，再次调用时，去订购
			if(orderLog == null ||  orderLog.getStatus() == OrderProductLogBean.STATUS_PAYSUCCESS
			|| orderLog.getStatus() == OrderProductLogBean.STATUS_NEED_AGAIN_ORDER ){
				UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(out_trade_code);
				
				if(orderLog == null){
					orderLog = new OrderProductLogBean();
					orderLog.setOrder_account(consumeInfo.getPhone());
					orderLog.setCreate_time(DateUtil.getNowDateStrSSS());
					orderLog.setOrder_code(out_trade_code);
					orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
					orderLog.setUser_id(consumeInfo.getUser_id());
					orderLog.setSupplier_product_id(consumeInfo.getSupplier_product_id());
					orderLog.setEvent_type(consumeInfo.getEvent_type());
				}
				orderLog.setStatus(OrderProductLogBean.STATUS_ORDERING);
				//更新订购日志表，正在订购中
				orderProductLogService.createOrUpdateOrderProductLog(orderLog);
				
				//支付已经成功
				if(consumeInfo.getStatus() == UserConsumeInfoBean.STATUS_SUCCESS){
					
					SupplierProductBean sp = supplierProductService.findSupplierProductById(consumeInfo.getSupplier_product_id());
					UserWechatBean user = userWechatService.findUserWechatById(consumeInfo.getUser_id());
					
					//根据订单号查询是否该用户是否有可以使用的优惠卷
					SupplierCouponRecordBean couponRecordBean  = supplierCouponService.findCouponRecordByOrderCode(out_trade_code); //根据订单号到优惠卷记录表中

					int price = (int) (sp.getProduct_price() * sp.getDiscount()/100d - orderLog.getCost_points()  );
					int userBalance = user.getBalance() + user.getPoints();
					
					//如果该用户优惠卷的记录信息不为空
					if(!StringUtil.isNull(couponRecordBean)){								//如果在优惠卷记录表查询出来的为空	
						SupplierCouponConfigBean couponConfigBean = new SupplierCouponConfigBean();
						couponConfigBean.setId(couponRecordBean.getCoupon_id());
						//查询用户使用优惠卷的信息
						couponConfigBean =  supplierCouponService.findCouponConfigInfo(couponConfigBean);

						//如果使用的是抵扣卷
						if(couponConfigBean.getCoupon_discount() == SupplierCouponConfigBean.COUPON_DISCOUNT_ZERO){
							price = price - couponConfigBean.getCoupon_offsetmoney() ;
							if(price < SupplierCouponConfigBean.PRICE_IS_ZERO){
								price = SupplierCouponConfigBean.PRICE_IS_ZERO;
							}
						}else{
							price = price * couponConfigBean.getCoupon_discount()/SupplierCouponConfigBean.COUPON_DISCOUNT_DIVIDE_ONE_HUNDRED;
							if(price < SupplierCouponConfigBean.PRICE_IS_ZERO){
								price = SupplierCouponConfigBean.PRICE_IS_ZERO;
							}
						}
					}
					
					
					
					//用户的钱+积分够付，才能订购(或者是无需校验金额的订单，打包商品的订单)
					if(userBalance >= price || orderLog.getEvent_type() == OrderProductLogBean.EVENT_TYPE_USER_ORDER_EFF){
						
//						ispBean.setOrder_code(out_trade_code);
						if(orderLog.getEvent_type() != OrderProductLogBean.EVENT_TYPE_USER_ORDER_EFF){
							//扣减用户的余额和积分
							int costBalance = user.getBalance() - price ;
							
							user.setBalance( costBalance );
							user.setPoints(user.getPoints() - orderLog.getCost_points());
						}
						
						
						//访问订购商品的具体接口
//						ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(out_trade_code, consumeInfo.getPhone(), sp.getProduct_id());
						ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(sp.getCustomer_id(), out_trade_code, consumeInfo.getPhone(), param, sp.getProduct_id());
					
						
						if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
							//判断是否有附加商品，如果有的话，一起订购
							List<SupplierProductAttachBean> attachs = supplierProductService.querySupplierAttachProductBySpid(sp.getId());
							for(int i = 0;i < attachs.size() ; i++){
								SupplierProductAttachBean attach = attachs.get(i);
								if(attach.getAttach_supplier_product_id() != null){
									SupplierProductBean attachSp = supplierProductService.findSupplierProductById(attach.getAttach_supplier_product_id());
									if(attachSp != null){
//										accessISPOrderInterfaceService.accessISPOrderInterface(out_trade_code+"G"+i, consumeInfo.getPhone(), attachSp.getProduct_id());
										channelProductService.orderProductByChannel(attachSp.getCustomer_id(), out_trade_code+"G"+i, consumeInfo.getPhone(), param, attachSp.getProduct_id());
									}
								}
							}
						}
						
//						orderLog.setCost_points(temp);
//						orderLog.setCost_money(0);
//						orderLog.setCost_balance(costBalance);
//						orderLog.setCost_price(price);
						orderLog.setSupplier_id(sp.getSupplier_id());
						orderLog.setProduct_name(sp.getProduct_name());
						orderLog.setProduct_price(sp.getProduct_price());

						orderLog.setChannel_id(ispBean.getChannel_id());
						if( (ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS && ispBean.getChannel_type() == ChannelBean.TYPE_SYNC )
								|| (ispBean.getStatus() == ISPInterfaceBean.STATUS_WAIT && ispBean.getChannel_type() == ChannelBean.TYPE_ASYNC )){
							switch (ispBean.getChannel_type()) {
							//同步的通道
							case ChannelBean.TYPE_SYNC:
								orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
								orderLog.setRemark("充值成功");
								
								
								handlerOrderProductByUserResult(user, orderLog, sp, orderLog.getStatus());
								
//								下面已不用，封装到上面的方法里了
//								user.setLast_order_time(DateUtil.getNowDateStr());
//								//生成扣减积分记录
//								userCommissionPointsService.createUserPointLog(user.getId(), -orderLog.getCost_points(), "订购商品【"+sp.getProduct_name()+"】支付积分");
//								//订购成功，扣减用户信息
//								userWechatService.update(user);
//								//订购成功，自己和上级添加积分
//								userWechatService.updateUserBalanceByOrderProduct(sp.getProduct_id(), user);
//								//订购成功，给用户发消息
//								sendMessageByOrderSuccess(user, out_trade_code ,orderLog);
								break;
							
							//异步的通道
							case ChannelBean.TYPE_ASYNC:

								orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
								orderLog.setRemark("下单成功，待充值");
								
								//更新用户信息，主要是得把钱给扣了
								userWechatService.update(user);

								//保存并处理用户动作
								msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_ASYNC , null, orderLog);
								
//								//下单成功，给用户发消息
//								sendMessageByOrderASyncSuccess(user, out_trade_code, orderLog);
								break;
							default:
								break;
							}
							
						}else{
							if(!orderLog.getStatus().equals(OrderProductLogBean.STATUS_NEED_AGAIN_ORDER)){			//如果不是再次订购给状态更改为-1
								orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
							}
							orderLog.setReason(ispBean.getResMsg());
							orderLog.setRemark(ispBean.getResMsg()+",由于运营商系统原因，造成本次流量充值失败。我们已将您本次支付的金额自动存入您的现金账户，您可以选择提现或再次消费。");
							boolean MatchingSuccessed = matchingErrorOrder(ispBean.getResMsg(),out_trade_code);
							//匹配到错误,判断是否还需要再次订购
							if(MatchingSuccessed){				
								//如果不需要再次订购,，直接给用户发消息，然后结束
								orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
								handlerOrderProductByUserResult(user, orderLog, sp, orderLog.getStatus());

							}else{//如果需要再次订购
								if(orderLog.getStatus() == OrderProductLogBean.STATUS_NEED_AGAIN_ORDER){				//判断是否是第二次再次订购
									orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
									handlerOrderProductByUserResult(user, orderLog, sp, orderLog.getStatus());
								}else{																					//一旦订购失败,初始化给状态改为-6
									orderLog.setStatus(OrderProductLogBean.STATUS_NEED_AGAIN_ORDER);
								}
							}
						}
						
						//订购成功，添加积分，这里的已废弃不用了
//						ProductBean product = productService.findProductById(sp.getProduct_id());
//						int givePoints = product.getGive_points();
//						user.setPoints(user.getPoints() + givePoints);
//						userCommissionPointsService.createUserPointLog(user.getId(), givePoints, "订购商品【"+sp.getProduct_name()+"】赠送积分");
						
					}else{
						
						orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
						orderLog.setRemark("充值失败，账户总额不足以支付！");
						
					}
					
				}else{
					orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
					orderLog.setRemark("原订单支付未完成，充值失败！");
				}
				
				//创建订购日志
				orderProductLogService.createOrUpdateOrderProductLog(orderLog);
			}
			//移除缓存
			runningCacheMap.remove(out_trade_code);
		}
		log.debug("orderProductByUser,runningCacheMap:" + runningCacheMap);
		return orderLog;
		
	}
	
	/**
	 * 处理用户订购结果
	 * @param user
	 * @param orderLog
	 * @param sp
	 * @param status
	 */
	private void handlerOrderProductByUserResult(UserWechatBean user, OrderProductLogBean orderLog, SupplierProductBean sp,int status){
		
		if(status == OrderProductLogBean.STATUS_SUCCESS)
		{
			//根据订单号,把优惠卷消除,如果根据订单号在优惠卷记录表中查不到任何信息则不更新
			supplierCouponService.updateStatusBecomeUserByOrderCode(orderLog.getOrder_code(),orderLog.getProduct_name());
			
			user.setLast_order_time(DateUtil.getNowDateStr());
			//生成扣减积分记录
			userCommissionPointsService.createUserPointLog(user.getId(), -orderLog.getCost_points(), "充值【"+sp.getProduct_name()+"】支付积分");
			//订购成功，扣减用户信息
			userWechatService.update(user);
			//订购成功，自己和上级添加积分
			userWechatService.updateUserBalanceByOrderProduct(sp.getProduct_id(), user);
			
//			//订购成功，给用户发微信消息
			sendMessageByOrderSuccess(user, orderLog.getOrder_code() ,orderLog);
//			//订购成功，给用户发送短信
//			smsService.sendSuccessMsgMyPhoneNumAndContent(orderLog.getOrder_account(), user.getNick_name(), orderLog.getProduct_name(), sp.getCustomer_id());
		}else{
			//订购失败，判断用户有没有领过红包，有领过红包将红包金额剔除
			takeOffBalanceByOrderFailed(orderLog,user);
			

			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_FAILD , null, orderLog);
			
//			//订购失败，给用户发消息
//			sendMessageByOrderFaild(user, orderLog.getOrder_code() ,orderLog);
//			//订购失败，给用户发送短信
//			smsService.sendFaildMsgByOrder(orderLog.getOrder_account(), orderLog.getProduct_name() , orderLog.getRemark(), sp.getCustomer_id());

		}
	}
	/**
	 * 处理用户订购活动的结果
	 * @param user
	 * @param orderLog
	 * @param sp
	 * @param status
	 */
	private void handlerOrderProductByActivityResult(UserWechatBean user, OrderProductLogBean orderLog, int status){
		
		if(status == OrderProductLogBean.STATUS_SUCCESS)
		{

			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS , null, orderLog);
			
//			//订购成功，给用户发微信消息
//			sendMessageByOrderSuccess(user, orderLog.getOrder_code() ,orderLog);
//			//订购成功，给用户发送短信
//			smsService.sendSuccessMsgMyPhoneNumAndContent(orderLog.getOrder_account(), user.getNick_name(), orderLog.getProduct_name(), null);

		}else{
			

			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_FAILD , null, orderLog);
			
//			//订购失败，给用户发消息
//			sendMessageByOrderFaild(user, orderLog.getOrder_code() ,orderLog);
			
		}
	}
	
	/**
	 * 处理商户订购余额支付订购的结果
	 * @param user
	 * @param orderLog
	 * @param status
	 */
	private void handlerOrderProductBySupplierBalanceResult(SupplierProductBean sp, OrderProductLogBean orderLog, int status){


		SupplierBean supplier = supplierService.findSupplierById(sp.getSupplier_id());
		if(status == OrderProductLogBean.STATUS_SUCCESS)
		{
			int price = sp.getProduct_price() ;
			supplier.setBalance( supplier.getBalance().intValue() - price );
			supplierService.updateSupplier(supplier);
		}else{
			
		}
	}
	
	/**
	 * 处理卡密订购的结果
	 * @param cardSecret
	 * @param orderLog
	 * @param status
	 */
	private void handlerOrderProductBySupplierCardSecretResult(SupplierCardSecretBean cardSecret , OrderProductLogBean orderLog, int status){
		
		if(status == OrderProductLogBean.STATUS_SUCCESS)
		{
			//成功减库存
			cardSecret.setStatus(SupplierCardSecretBean.STATUS_USED);
			cardSecret.setPhone(orderLog.getOrder_account());
			cardSecret.setOrder_code(orderLog.getOrder_code());
		}else{
			
		}
	}
	
	
	/**
	 * 给定购成功的用户发消息
	 * @param user
	 * @param order_code
	 */
	private void sendMessageByOrderSuccess(UserWechatBean user,String order_code,OrderProductLogBean orderLog){
		
//		//先给自己发
//		String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_SUCCESS);
//		content = content.replaceAll("#product_name#", orderLog.getProduct_name());
//		content = content.replaceAll("#money#", String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
//		TextBean text = new TextBean();
//		text.setToUserName(user.getOpenid());
//		text.setContent(content);
//		text.setNotifyType(BaseMessage.NOTIFYTYPE_ORDER_SUCCESS);
//		wechatService.sendMessageToUser(text);
		orderLog.setNick_name(user.getNick_name());
		orderLog.setMoney( String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
		
		//保存并处理用户动作
		msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS , null, orderLog);
		
		
		List<UserWechatBean> listUser = null;
		UserWechatBean condition = new UserWechatBean();
		//再发给父用户
		if(user.getParentid() != null){//查询父用户
			condition.setId(user.getParentid());
			listUser = userWechatService.list(condition);
		}
		if(listUser != null && listUser.size() > 0){
//			content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_SUCCESS_PARENT);
//			text = new TextBean();
//			for(UserWechatBean u : listUser){
//				
//				String str = content.replaceAll("#nick_name#", user.getNick_name());
//				str = str.replaceAll("#product_name#", orderLog.getProduct_name());
//				str = str.replaceAll("#money#", String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
//				text.setToUserName(u.getOpenid());
//				text.setContent(str);
//				text.setNotifyType(BaseMessage.NOTIFYTYPE_PARENT);
//				wechatService.sendMessageToUser(text);
//			}
			
			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS_NOTIFY_PARENT , null, orderLog);
			
		}
		
		//再给子用户发
		condition.setId(null);
		condition.setParentid(user.getId());
		listUser = userWechatService.list(condition);
		if(listUser != null && listUser.size() > 0){
//			content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_SUCCESS_CHILD);
//			text = new TextBean();
//			for(UserWechatBean u : listUser){
//
//				String str = content.replaceAll("#nick_name#", user.getNick_name());
//				str = str.replaceAll("#product_name#", orderLog.getProduct_name());
//				str = str.replaceAll("#money#", String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
//				text.setToUserName(u.getOpenid());
//				text.setContent(str);
//				text.setNotifyType(BaseMessage.NOTIFYTYPE_CHILDREN);
//				wechatService.sendMessageToUser(text);
//			}
			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS_NOTIFY_CHILD , null, orderLog);
			
		}
		
		
		
	}
	

	/**
	 * 给下单成功的用户发消息
	 * @param user
	 * @param order_code
	 */
	private void sendMessageByOrderASyncSuccess(UserWechatBean user,String order_code,OrderProductLogBean orderLog){
		
		//给自己发
		String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_ASYNC_SUCCESS);
		content = content.replaceAll("#product_name#", orderLog.getProduct_name());
		TextBean text = new TextBean();
		text.setToUserName(user.getOpenid());
		text.setContent(content);
		text.setNotifyType(BaseMessage.NOTIFYTYPE_ORDER_SUCCESS);
		text.setFromUserName(user.getOriginalid());
		wechatService.sendMessageToUser(text);
		//更新用户表，扣除余额
		userWechatService.update(user);
	}
	/**
	 * 给定购失败的用户发消息
	 * @param user
	 * @param order_code
	 */
	private void sendMessageByOrderFaild(UserWechatBean user,String order_code,OrderProductLogBean orderLog){
		
		//先给自己发
		String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_FAILD);
		content = content.replaceAll("#product_name#", orderLog.getProduct_name());
		TextBean text = new TextBean();
		text.setToUserName(user.getOpenid());
		text.setContent(content);
		text.setFromUserName(user.getOriginalid());
		text.setNotifyType(BaseMessage.NOTIFYTYPE_ORDER_FAILD);
		wechatService.sendMessageToUser(text);
		
	}
	
	/**
	 * 通过活动来订购商品
	 * @param activityUserRelation
	 * @return
	 */
	@Override
	public OrderProductLogBean orderProductByActivity(ActivityUserRelationBean activityUserRelation){
		
		String out_no = activityUserRelation.getOrder_code();
		String phone = activityUserRelation.getPhone();
		SupplierProductBean sp = supplierProductService.findSupplierProductById(activityUserRelation.getProduct_id());
		//订购日志信息
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(out_no);
		
		//访问订购商品的具体接口
//		ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(out_no, phone, sp.getProduct_id());
		ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(sp.getCustomer_id(), out_no, phone, null, sp.getProduct_id());

		UserWechatBean user = userWechatService.findUserWechatById(activityUserRelation.getUser_id());

		orderLog.setChannel_id(ispBean.getChannel_id());
		if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
			switch (ispBean.getChannel_type()) {
			//同步的通道
			case ChannelBean.TYPE_SYNC:
				orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
				orderLog.setRemark("充值成功");
				
				handlerOrderProductByActivityResult(user, orderLog, orderLog.getStatus());
				break;
			
			//异步的通道
			case ChannelBean.TYPE_ASYNC:

				orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
				orderLog.setRemark("下单成功，待充值");
				

				//保存并处理用户动作
				msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_ASYNC , null, orderLog);
				
//				//下单成功，给用户发消息
//				sendMessageByOrderASyncSuccess(user, out_no, orderLog);
				break;
			default:
				break;
			}
			
		}else{
			orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
			orderLog.setReason(ispBean.getResMsg());
			orderLog.setRemark(ispBean.getResMsg()+",由于运营商系统原因，造成本次流量充值失败。我们已将您本次支付的金额自动存入您的现金账户，您可以选择提现或再次消费。");
			//订购失败，给用户发消息
			handlerOrderProductByActivityResult(user, orderLog, orderLog.getStatus());
		
		}
		//创建订购日志
		orderProductLogService.createOrUpdateOrderProductLog(orderLog);
		
		return orderLog;
	}
	
	
	/**
	 * 商户侧订购商品，使用库存
	 */
	@Override
	public OrderProductLogBean orderProductBySupplierStoreNum(String adminid,String spid,String phone){
		
		CustomerAdminBean admin = customerAdminService.findCustomerAdminById(Integer.parseInt(adminid));
		SupplierProductBean sp = supplierProductService.findSupplierProductById(Integer.parseInt(spid));
		OrderProductLogBean orderLog = null;
		
		//校验admin的客户ID 与  商品的客户ID是相同的
		if(sp.getCustomer_id().intValue() == admin.getCustomer_id()){

			orderLog = new OrderProductLogBean();
			
			orderLog.setOrder_account(phone);
			orderLog.setCreate_time(DateUtil.getNowDateStr());

			//商户订单号
			String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_STORE, admin.getCustomer_id());
			orderLog.setOrder_code(out_no);
			orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
			orderLog.setAdmin_id(admin.getId());
			orderLog.setSupplier_product_id(Integer.parseInt(spid));
			orderLog.setSupplier_id(sp.getSupplier_id());
			orderLog.setProduct_name(sp.getProduct_name());
			orderLog.setProduct_price(sp.getProduct_price());
			orderLog.setEvent_type(UserConsumeInfoBean.EVENT_TYPE_SUPPLIER_STORENUM);

			//有库存
			if(sp.getStore_num() > 0){
			
				//访问订购商品的具体接口
//				ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(out_no, phone, sp.getProduct_id());
				ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(sp.getCustomer_id(), out_no, phone, null, sp.getProduct_id());

				orderLog.setChannel_id(ispBean.getChannel_id());
				if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
					
					switch (ispBean.getChannel_type()) {
					//同步的通道
					case ChannelBean.TYPE_SYNC:

						orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
						orderLog.setRemark("充值成功");
						
						//成功减库存
						sp.setStore_num(sp.getStore_num() - 1);
						supplierProductService.updateSupplierProduct(sp);
						
						break;
					
					//异步的通道
					case ChannelBean.TYPE_ASYNC:

						orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
						orderLog.setRemark("下单成功，待充值");

						break;
					default:
						break;
					}
					
				}else{
					orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
					orderLog.setRemark(ispBean.getResMsg());
				}
			
			}else{
				orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
				orderLog.setRemark("剩余库存不足，无法订购此商品");
			}
			
			//创建订购日志
			orderProductLogService.createOrUpdateOrderProductLog(orderLog);
		}
		
		return orderLog;
	}

	

	/**
	 * 商户侧订购商品，使用卡密
	 */
	@Override
	public OrderProductLogBean orderProductBySupplierCardSecret(SupplierCardSecretBean cardSecret,String phone){
		
		ProductBean product = productService.findProductById(cardSecret.getProduct_id());
		OrderProductLogBean orderLog = new OrderProductLogBean();
		
		if(runningCacheMap.get(cardSecret.getSecret_key()) == null) { //只有不在runningCacheMap  中的定单号才去定购
			runningCacheMap.put(cardSecret.getSecret_key(), "running");
		
			AreaData ad = getAreaDataByPhone(phone);
			if(ad != null){
				
				//查询品牌是否一致
				ProductTypeBean pt = productTypeService.findProductType(ad.getBrand(), ProductTypeBean.TYPE_BRA);
				if(product.getProduct_brand().intValue() == pt.getId()){
					
					orderLog.setOrder_account(phone);
					orderLog.setCreate_time(DateUtil.getNowDateStrSSS());
		
					//商户订单号
					String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_CARDSECRET, cardSecret.getCustomer_id());
					orderLog.setOrder_code(out_no);
					orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
					orderLog.setAdmin_id(null);
					orderLog.setSupplier_product_id(cardSecret.getSupplier_product_id());
					orderLog.setSupplier_id(cardSecret.getSupplier_id());
					orderLog.setProduct_name(cardSecret.getProduct_name());
					orderLog.setProduct_price(product.getProduct_price());
					orderLog.setCost_balance(0);
					orderLog.setCost_money(0);
					orderLog.setCost_points(0);
					orderLog.setCost_price(0);
					orderLog.setEvent_type(UserConsumeInfoBean.EVENT_TYPE_SUPPLIER_CARDSECRET);
					
					//访问订购商品的具体接口
//					ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(out_no, phone, cardSecret.getProduct_id());
					ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(cardSecret.getCustomer_id(), out_no, phone, null, cardSecret.getProduct_id());
					orderLog.setChannel_id(ispBean.getChannel_id());
					
					if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){

						switch (ispBean.getChannel_type()) {
						//同步的通道
						case ChannelBean.TYPE_SYNC:

							orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
							orderLog.setRemark("充值成功");
							
							handlerOrderProductBySupplierCardSecretResult(cardSecret, orderLog, orderLog.getStatus());
//							成功减库存
//							cardSecret.setStatus(SupplierCardSecretBean.STATUS_USED);
//							cardSecret.setPhone(phone);
//							cardSecret.setOrder_code(out_no);
							break;
						
						//异步的通道
						case ChannelBean.TYPE_ASYNC:

							orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
							orderLog.setRemark("下单成功，待充值");

							break;
						default:
							break;
						}
					}else{
						orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
						orderLog.setRemark(ispBean.getResMsg());
					}
					//创建订购日志
					orderProductLogService.createOrUpdateOrderProductLog(orderLog);
				
				}else{
					orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
					orderLog.setRemark("手机号与充值的品牌不符！");
				}
				
			}else{
				orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
				orderLog.setRemark("该号码不支持订购！");
			}
			
			runningCacheMap.remove(cardSecret.getSecret_key()); //从订购的缓存中去除
		}else{
			orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
			orderLog.setRemark("正在充值中，请不要重复提交！");
		}
		
		return orderLog;
	}

	

//	/**
//	 * 订购商品，根据订购日志
//	 */
//	@Override
//	public OrderProductLogBean orderProductByOrderProductLog(OrderProductLogBean orderLog){
//		
//		String phone = orderLog.getOrder_account();
//		AreaData ad = getAreaDataByPhone(phone );
//		if(ad != null ){
//				if(orderLog.getStatus() == OrderProductLogBean.STATUS_PAYSUCCESS )
//				{
//					//商户订单号
//					SupplierProductBean sp = supplierProductService.findSupplierProductById(orderLog.getSupplier_product_id());
//					//访问订购商品的具体接口
//					ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(orderLog.getOrder_code(), phone, sp.getProduct_id());
//					
//					if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
//						orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
//						orderLog.setRemark("订购成功");
//						
//					}else{
//						orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
//						orderLog.setRemark(ispBean.getResMsg());
//					}
//					//创建订购日志
//					orderProductLogService.createOrUpdateOrderProductLog(orderLog);
//				}
//			
//		}else{
//			orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
//			orderLog.setRemark("该号码不支持订购！");
//		}
//		
//		return orderLog;
//	}

	
	
	/**
	 * 商户订购商品，使用支付余额
	 */
	@Override
	public OrderProductLogBean orderProductBySupplierBalance(String out_trade_code,Integer adminId) {
		//订购日志信息
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
		
		if(orderLog == null ){
			orderLog = new OrderProductLogBean();
			UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(out_trade_code);
			
			orderLog.setOrder_account(consumeInfo.getPhone());
			orderLog.setCreate_time(DateUtil.getNowDateStr());
			orderLog.setOrder_code(out_trade_code);
			orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
			orderLog.setUser_id(consumeInfo.getUser_id());
			orderLog.setAdmin_id(adminId);
			orderLog.setSupplier_product_id(consumeInfo.getSupplier_product_id());
			orderLog.setEvent_type(consumeInfo.getEvent_type());

			SupplierProductBean sp = supplierProductService.findSupplierProductById(consumeInfo.getSupplier_product_id());
			SupplierBean supplier = supplierService.findSupplierById(sp.getSupplier_id());
			int price = sp.getProduct_price() ;
			int userBalance = supplier.getBalance() ;
			
			//支付已经成功,或者余额大于需要支付的价格
			if(consumeInfo.getStatus() == UserConsumeInfoBean.STATUS_SUCCESS || (consumeInfo.getStatus() == UserConsumeInfoBean.STATUS_WAIT && userBalance >= price)){
				
				orderLog.setSupplier_id(sp.getSupplier_id());
				orderLog.setProduct_name(sp.getProduct_name());
				orderLog.setProduct_price(sp.getProduct_price());
				orderLog.setCost_money(0 );
				orderLog.setCost_balance(price);
				orderLog.setCost_price(price );
				
				//用户的钱够付，才能订购
				if(userBalance >= price){
					
					//访问订购商品的具体接口
//					ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(out_trade_code, consumeInfo.getPhone(), sp.getProduct_id());
//					ispBean.setOrder_code(out_trade_code);
					ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(sp.getCustomer_id(), out_trade_code, consumeInfo.getPhone(), null, sp.getProduct_id());
					orderLog.setChannel_id(ispBean.getChannel_id());
					
					if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){

						switch (ispBean.getChannel_type()) {
						//同步的通道
						case ChannelBean.TYPE_SYNC:
							
							orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
							orderLog.setRemark("充值成功");
							
							handlerOrderProductBySupplierBalanceResult(sp, orderLog, orderLog.getStatus());
							
							break;
						
						//异步的通道
						case ChannelBean.TYPE_ASYNC:

							orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
							orderLog.setRemark("下单成功，待充值");

							break;
						default:
							break;
						}
						
					}else{
						orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
						orderLog.setRemark(ispBean.getResMsg());
						
					}
					
				}else{
					orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
					orderLog.setRemark("充值失败，账户总额不足以支付！");
				}
			}else{
				orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
				orderLog.setRemark("原订单支付未完成，充值失败！");
			}
			//创建订购日志
			orderProductLogService.createOrUpdateOrderProductLog(orderLog);
		}
		
		return orderLog;
	}

	@Override
	public String createUnifiedOrder(Integer adminid,String phone,Integer spid,String interfaceType,Integer event_type){
		
		CustomerAdminBean admin = customerAdminService.findCustomerAdminById(adminid);
		SupplierProductBean sp = supplierProductService.findSupplierProductById(spid);
		//商户订单号
		String out_no = userConsumeInfoService.createOutTradeNo(interfaceType, admin.getCustomer_id());
		//保存充值记录
		userConsumeInfoService.createConsumeInfo(phone,sp.getProduct_price(), sp.getId(), admin.getId(), null, out_no,interfaceType,event_type);
		
		return out_no;
	}
	
	@Override
	public void saveOrUpdatePartnerOrderProduct(PartnerOrderProductBean bean){
		
		if(bean.getId() == null)
		{
			orderDao.savePartnerOrderProduct(bean);
		}else{
			orderDao.updatePartnerOrderProduct(bean);
		}
	}
	
	@Override
	public PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partnerOrderCode){
		return orderDao.findPartnerOrderProductByPartnerOrderCode(partnerOrderCode);
	}
	
	@Override
	public List<OrderProductEffBean> queryOrderProductEffByStatus(int status) {
		
		String date = DateUtil.getNowDateStr();
		
		return orderDao.queryOrderProductEffByStatus(date, status);
	}
	
	@Override
	public List<OrderProductEffBean> queryOrderProductEff(OrderProductEffBean bean){
		return orderDao.queryOrderProductEff(bean);
	}
	
	@Override
	public void createOrderProductEff(OrderProductEffBean bean){
		
		orderDao.createOrderProductEff(bean);
	}
	
	@Override
	public OrderProductEffBean createOrderProductEffByOrderProductLog(OrderProductLogBean orderLog,int month_offset){

		OrderProductEffBean eff = createOrderProductEff(orderLog,month_offset);
		
		//给用户发消息，提示预定成功
		UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
		

		//保存并处理用户动作
		msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_EFF , null, eff);
		
//		TextBean text = new TextBean();
//		text.setToUserName(user.getOpenid());
//		String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_EFF_SUCCESS);
//		content = content.replaceAll("#eff_time#", DateUtil.formatDateOnlyDate(c.getTime()));
//		content = content.replaceAll("#product_name#", orderLog.getProduct_name());
//		text.setContent(content);
//		wechatService.sendMessageToUser(text);
		user.setBalance(user.getBalance() - orderLog.getCost_balance() - orderLog.getCost_money());
		user.setPoints(user.getPoints() - orderLog.getCost_points());
		//更新用户最后一次订购时间
		user.setLast_order_time(DateUtil.getNowDateStr());
		userWechatService.update(user);
		//根据订单号,把优惠卷消除,如果根据订单号在优惠卷记录表中查不到任何信息则不更新
		supplierCouponService.updateStatusBecomeUserByOrderCode(orderLog.getOrder_code(),orderLog.getProduct_name());
		
		
		return eff;
	}
	
	/**
	 * 生成预约订单
	 * @param orderLog
	 * @param month_offset
	 * @return
	 */
	private OrderProductEffBean createOrderProductEff(OrderProductLogBean orderLog,int month_offset){
		SupplierProductBean sp = supplierProductService.findSupplierProductById(orderLog.getSupplier_product_id());
		//生成预定订单
		OrderProductEffBean eff = new OrderProductEffBean();
		eff.setCreate_time(DateUtil.getNowDateStr());
		eff.setCustomer_id(sp.getCustomer_id());
		eff.setEff_id(orderLog.getId());

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 1);
		c.set(Calendar.MINUTE, 1);
		c.add(Calendar.MONTH, month_offset);
		
		eff.setEff_time(DateUtil.formatDate(c.getTime()));
		eff.setOrder_account(orderLog.getOrder_account());
		eff.setOrder_code(orderLog.getOrder_code());
		eff.setProduct_id(sp.getProduct_id());
		eff.setStatus(OrderProductEffBean.STATUS_WAIT);
		eff.setSupplier_id(sp.getSupplier_id());
		eff.setSupplier_product_id(orderLog.getSupplier_product_id());
		eff.setType(OrderProductEffBean.TYPE_USER_EFF);
		eff.setProduct_name(orderLog.getProduct_name());
		this.createOrderProductEff(eff);
		
		orderLog.setRemark("已成功生成预约定单！生效时间："+DateUtil.formatDateOnlyDate(c.getTime()));
		orderProductLogService.createOrUpdateOrderProductLog(orderLog);
		return eff;
	}
	
	@Override
	public void updateOrderProductEff(OrderProductEffBean bean){
		
		orderDao.updateOrderProductEff(bean);
		
	}
	
	/**
	 * 处理订购订单的回调
	 * @param orderCode
	 * @return
	 */
	@Override
	public OrderProductLogBean handlerOrderProductCallBack(String orderCode,int status,String remark){
		
		//不同的订单号，处理逻辑不同
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(orderCode);
		if(orderLog == null){
			orderLog = new OrderProductLogBean();
			orderLog.setStatus(PartnerInterfaceBean.STATUS_FAILD);
			orderLog.setRemark("订单号未找到！" + orderCode);
			return orderLog;
		}
		
		if(orderLog.getStatus() == OrderProductLogBean.STATUS_PAYSUCCESS || orderLog.getStatus() == OrderProductLogBean.STATUS_ORDERING
				|| orderLog.getStatus() == OrderProductLogBean.STATUS_NEED_AGAIN_ORDER){

			orderLog.setStatus(status);
			orderLog.setRemark(remark);
			//通过微信支付和余额支付订购的
			if(orderCode.startsWith(IUserConsumeInfoService.OUTTRADE_TYPE_WXPAY) || orderCode.startsWith(IUserConsumeInfoService.OUTTRADE_TYPE_USERBALANCE) ){
				UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
				SupplierProductBean sp = supplierProductService.findSupplierProductById(orderLog.getSupplier_product_id());
				
				
				handlerOrderProductByUserResult(user, orderLog, sp, status);
				
				if(status != OrderProductLogBean.STATUS_SUCCESS){
					//订购失败，退还用户余额
					user.setBalance(user.getBalance() + orderLog.getCost_balance() + orderLog.getCost_money());
					user.setPoints(user.getPoints() + orderLog.getCost_points());
					userWechatService.update(user);
					//检查通道情况
					channelProductService.checkChannelAlive(orderLog.getChannel_id(), sp.getProduct_id());
				}
			}
			
			//通过摇一摇活动的
			if(orderCode.startsWith(IUserConsumeInfoService.OUTTRADE_TYPE_SHAKE_GIFT)){
				UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
				//订购成功，给用户发消息
				handlerOrderProductByActivityResult(user, orderLog, orderLog.getStatus());
			}
			
			//通过卡密的
			if(orderCode.startsWith(IUserConsumeInfoService.OUTTRADE_TYPE_CARDSECRET)){
				
				handlerOrderProductBySupplierCardSecretResult(new SupplierCardSecretBean(), orderLog, status);
			}
			
			//通过库存的
			if(orderCode.startsWith(IUserConsumeInfoService.OUTTRADE_TYPE_STORE)){
				SupplierProductBean sp = supplierProductService.findSupplierProductById(orderLog.getSupplier_product_id());

				//成功减库存
				sp.setStore_num(sp.getStore_num() - 1);
				supplierProductService.updateSupplierProduct(sp);
			}
			
			//创建订购日志
			orderProductLogService.createOrUpdateOrderProductLog(orderLog);
			

			//异步订购成功后，扣减通道余额
			if(orderLog.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
				SupplierProductBean sp = supplierProductService.findSupplierProductById(orderLog.getSupplier_product_id());
				channelProductService.minusChannelBalance(orderLog.getChannel_id(), sp.getProduct_id(),orderCode);
			}
		}
		
		
		return orderLog;
	}

	/**
	 * 更新可预约订单的生效时间,扫描用户的预约表,可更新预约订单的预约时间
	 */
	@Override
	public OrderProductEffBean updateOrderProductEffDate(int id, OrderProductLogBean orderLog,int month_offset) {
		OrderProductEffBean eff = this.queryOrderProductEffById(id);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 1);
		c.set(Calendar.MINUTE, 1);
		c.add(Calendar.MONTH, month_offset);
		eff.setEff_time(DateUtil.formatDate(c.getTime()));
		eff.setExecute_time(DateUtil.formatDate(c.getTime()));
		eff.setRemark("已成功生成预约定单！生效时间："+DateUtil.formatDateOnlyDate(c.getTime()));
		eff.setProduct_name(orderLog.getProduct_name());
		//是待生效的才更新
		if(eff.getStatus() == OrderProductEffBean.STATUS_WAIT){
			this.updateOrderProductEff(eff);
			orderLog.setRemark("已成功生成预约定单！生效时间："+DateUtil.formatDateOnlyDate(c.getTime()));
			orderProductLogService.createOrUpdateOrderProductLog(orderLog);
//			//给用户发消息，提示预定成功
			UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
//			TextBean text = new TextBean();
//			text.setToUserName(user.getOpenid());
//			String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_EFF_SUCCESS);
//			content = content.replaceAll("#eff_time#", DateUtil.formatDateOnlyDate(c.getTime()));
//			content = content.replaceAll("#product_name#", orderLog.getProduct_name());
//			text.setContent(content);
//			wechatService.sendMessageToUser(text);
			

			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_EFF , null, eff);
			
			//更新用户最后一次订购时间
			user.setLast_order_time(DateUtil.getNowDateStr());
			userWechatService.update(user);
		}
		return eff;
	}

	/**
	 * 直接生效订单，（用户操作可预约订单里，选择直接生效）
	 */
	@Override
	public OrderProductEffBean dealEffOrderProduct(OrderProductEffBean effOrder,OrderProductLogBean orderLog) {
		//还原用户订购时的余额和积分，然后去订购
		//复用 OrderEffCrons 65行
		UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
		user.setBalance(user.getBalance() + orderLog.getCost_balance() + orderLog.getCost_money());
		user.setPoints(user.getPoints() + orderLog.getCost_points());
		userWechatService.update(user);
		//调用外部服务订购商品
		orderLog = this.effUserOrder(orderLog.getId());
		//生效订单，修改预约订单状态
		if(orderLog != null && orderLog.getStatus() == OrderProductLogBean.STATUS_SUCCESS){
			effOrder.setExecute_time(DateUtil.getNowDateStr());
			effOrder.setStatus(OrderProductEffBean.STATUS_SUCCESS);
			effOrder.setRemark(orderLog.getRemark());
		}else{
			effOrder.setExecute_time(DateUtil.getNowDateStr());
			effOrder.setStatus(OrderProductEffBean.STATUS_FAILD);
			effOrder.setRemark(orderLog.getRemark());
			
		}
		this.updateOrderProductEff(effOrder);
		try {
			if(StringUtil.isNotNull(effOrder.getExecute_time())){
				effOrder.setExecute_time(DateUtil.formatDateOnlyDate(DateUtil.parseDate(effOrder.getExecute_time())));
			}
			if(StringUtil.isNotNull(effOrder.getEff_time())){
				effOrder.setEff_time(DateUtil.formatDateOnlyDate(DateUtil.parseDate(effOrder.getEff_time())));
			}
		} catch (ParseException e) {
			log.error(e, e);
		}
		return effOrder;
	}

	@Override
	public OrderProductEffBean queryOrderProductEffById(long id) {
		return orderDao.queryOrderProductEffById(id);
	}

	@Override
	public List<OrderProductEffShowPageBean> queryEffOrderProductLogByUserId(int userId, int status) {
		OrderProductLogBean bean = new OrderProductLogBean();
		bean.setUser_id(userId);
		bean.setStatus(status);
		List<OrderProductEffShowPageBean> list = new ArrayList<OrderProductEffShowPageBean>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", userId);
		param.put("type", OrderProductEffShowPageBean.TYPE_USER_EFF);//预约订单类型
		if(status == OrderProductEffShowPageBean.STATUS_WAIT){
			param.put("status", status);
			list = orderDao.queryEffOrderProductLog(param);
		}else{
			param.put("status", OrderProductEffShowPageBean.STATUS_FAILD+","+OrderProductEffShowPageBean.STATUS_SUCCESS);
			list = orderDao.queryEffOrderProductLog(param);
		}
		try{
			for (OrderProductEffShowPageBean map : list) {
				if(!StringUtil.isNull(map.getExecute_time())){
					map.setExecute_time(DateUtil.formatDateOnlyDate(DateUtil.parseDate(map.getExecute_time())));
				}
				if(!StringUtil.isNull(map.getEff_time())){
					map.setEff_time(DateUtil.formatDateOnlyDate(DateUtil.parseDate(map.getEff_time())));
				}
				
			}
		}catch(Exception e){
			log.error(e, e);
		}
		return list;
	}
	
	/**
	 * 产品订购方法
	 * @param id
	 * @return
	 */
	public OrderProductLogBean effUserOrder(int id){
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogById(id);
		//还原用户订购时的余额和积分，然后去订购
		UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
		user.setBalance(user.getBalance() + orderLog.getCost_balance() + orderLog.getCost_money());
		user.setPoints(user.getPoints() + orderLog.getCost_points());
		userWechatService.update(user);
		orderLog = this.orderProductByUser(orderLog.getOrder_code(), null);
		return orderLog;
	}

	@Override
	public Map<String, Object> modifyEffOrderProduct(int nextNum, int id) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//获取预约订单
		OrderProductEffBean effOrder = this.queryOrderProductEffById(id);
		//获取订单
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogById(effOrder.getEff_id());
		//可预约订单
		if(effOrder.getStatus() == OrderProductEffBean.STATUS_WAIT){
			//获取客户信息
			UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(effOrder.getOrder_code());
			//需要预约生效时间
			if(nextNum != 0){
				OrderProductEffBean bean = this.updateOrderProductEffDate(id,orderLog, nextNum);
				consumeInfo.setEff_num(nextNum);
				userConsumeInfoService.updateUserConsumeInfo(consumeInfo);
				try{
					if(StringUtil.isNotNull(bean.getExecute_time())){
						bean.setExecute_time(DateUtil.formatDateOnlyDate(DateUtil.parseDate(bean.getExecute_time())));
					}
					if(StringUtil.isNotNull(bean.getEff_time())){
						bean.setEff_time(DateUtil.formatDateOnlyDate(DateUtil.parseDate(bean.getEff_time())));
					}
				}catch(Exception e){
					log.error(e,e);
				}
				returnMap.put("RESULT", true);
				returnMap.put("OBJ", bean);
			}
			//直接生效
			else{
				//预订订单直接生效
				effOrder = this.dealEffOrderProduct(effOrder,orderLog);
				returnMap.put("RESULT", true);
				returnMap.put("OBJ", effOrder);
			}
		}else{
			Map<String,String> mm = new HashMap<String,String>();
			mm.put("empty", "failed");
			returnMap.put("RESULT", false);
			returnMap.put("OBJ", mm);
		}
		return returnMap;
	}

	/**
	 * 订购失败，将用户所获得的红包金额从余额里扣除
	 * @param orderLog
	 * @param user
	 */
	private void takeOffBalanceByOrderFailed(OrderProductLogBean orderLog,UserWechatBean user){
		//红包是否发送
		if(!StringUtil.isNull(orderLog.getIs_sended()) && (orderLog.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_SUCCESS_HONBAO || orderLog.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_SUCCESS_BALANCE)){
			if(!StringUtil.isNull(orderLog.getLucky_money())){
				//将红包的钱扣除
				int money =  user.getBalance() - orderLog.getLucky_money();
				user.setBalance(money);
				userWechatService.update(user);
				
			}
		}
		//更新订单表的是否发送红包(发送失败)
		orderLog.setIs_sended(OrderProductLogBean.IS_SENDED_FAILD);
		orderProductLogService.createOrUpdateOrderProductLog(orderLog);
	}


	
	/**
	 * 调用接口返回结果模糊匹配,匹配链接超时余额不足的情况，
	 */
		private   boolean matchingErrorOrder(String text,String out_trade_code){	
    	ConfigCruxBean beanIn = new ConfigCruxBean();
    	beanIn.setStatus(ConfigCruxBean.STATUS_USE);
    	beanIn.setType(ConfigCruxBean.TYPE_ORDER_ERROR);
    	List<ConfigCruxBean> list = configCruxService.queryConfigCruxInfo(beanIn);
		for(ConfigCruxBean beanOut : list){
	        Pattern pattern = Pattern.compile("("+beanOut.getKey()+")");
	        Matcher matcher = pattern.matcher(text);
	        if(matcher.find()){
	            return true;
	        }
		}
		return false;
		
}

	@Override
	public void createPackageOrderProduct(OrderProductLogBean orderLog,UserConsumeInfoBean consumeInfo) {
		//获得用户订购的商品，判断是否需要生成预约订单
		SupplierProductBean spBean = supplierProductService.findSupplierProductBySpid(orderLog.getSupplier_product_id());
		//该商品是打包商品，需要生成预约订单
		String orderLogCode = orderLog.getOrder_code();
//		int cost_points = orderLog.getCost_points();
//		int cost_balance = orderLog.getCost_balance();
		if(!StringUtil.isNull(spBean.getPackage_num()) && spBean.getPackage_num() > SupplierProductBean.PACKAGE_NUM_DEFAULT){
			for(int i=1;i<spBean.getPackage_num().intValue();i++){
				//生成预约订单的单号
				String effOrderCode = orderLogCode + "_" + i;
				OrderProductLogBean oldOrder = orderProductLogService.findOrderProductLogByCode(effOrderCode);
				//判断是否已近生成预约订单
				if(StringUtil.isNull(oldOrder)){
					orderLog.setOrder_code(effOrderCode);
					//设置ID为null，新建order
					orderLog.setId(null);
					//将金额设置0，所有
					orderLog.setCost_points(0);
					orderLog.setCost_balance(0);
					orderLog.setCost_money(0);
					orderLog.setCost_price(0);
					//不能将订单的金额设置为0；后面会影响订购
					orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
					//设置红包金额为0
					orderLog.setLucky_money(0);
					//该订单不发送红包
					orderLog.setIs_sended(OrderProductLogBean.IS_SENDED_NO);
					orderLog.setEvent_type(OrderProductLogBean.EVENT_TYPE_USER_ORDER_EFF);
					//第一个订单为预约订单
					int eff_num = i;
					//预约时间的处理
					if(consumeInfo.getEff_num() != null && consumeInfo.getEff_num() >0){
						eff_num = consumeInfo.getEff_num()+i;
					}
					//创建订单
					orderProductLogService.createOrUpdateOrderProductLog(orderLog);
					//创建预约订单
//					createOrderProductEffByOrderProductLog(orderLog,eff_num);
					createOrderProductEff(orderLog,eff_num);
					UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
					//更新用户最后一次订购时间
					user.setLast_order_time(DateUtil.getNowDateStr());
					userWechatService.update(user);
					//创建付款信息
					userConsumeInfoService.createConsumeInfo(consumeInfo.getPhone(), 0, consumeInfo.getSupplier_product_id(), consumeInfo.getUser_id(), consumeInfo.getTransaction_id(), orderLog.getOrder_code(), consumeInfo.getInterface_type(), eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER_EFF);
					userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, effOrderCode);
				}
			}
		}
	}
		
}
