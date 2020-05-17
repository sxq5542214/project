/**
 * 
 */
package com.yd.business.order.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.dao.IOrderDao;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.sms.service.ISmsService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.service.ISupplierCardSecretService;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierUserService;
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
	private IAreaDataService areaDataService;
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
	@Resource
	private ISupplierUserService supplierUserService;
	
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
	public AreaDataBean getAreaDataByPhone(String phone){
		AreaDataBean ad = null;
		
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
			supplierCouponService.updateCouponRecordStatusBecomeUsedByOrderCode(orderLog.getOrder_code(),orderLog.getProduct_name());
			
			user.setLast_order_time(DateUtil.getNowDateStr());
			//生成扣减积分记录
			userCommissionPointsService.createUserPointLog(sp.getSupplier_id(), user.getId(), -orderLog.getCost_points(), "充值【"+sp.getProduct_name()+"】支付积分");
			
//			//订购成功，扣减用户信息
//			userWechatService.update(user);
//			//订购成功，自己和上级添加积分
//			userWechatService.updateUserBalanceByOrderProduct(sp.getProduct_id(), user);
			
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
	
	

	@Override
	public String createUnifiedOrder(Integer adminid,String phone,Integer spid,String interfaceType,Integer event_type){
		
		CustomerAdminBean admin = customerAdminService.findCustomerAdminById(adminid);
		SupplierProductBean sp = supplierProductService.findSupplierProductById(spid);
		//商户订单号
		String out_no = userConsumeInfoService.createOutTradeNo(interfaceType, admin.getCustomer_id());
		//保存充值记录
		userConsumeInfoService.createConsumeInfo(phone,sp.getProduct_price(),sp.getSupplier_id(), sp.getId(), admin.getId(), null, out_no,interfaceType,event_type);
		
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
		supplierCouponService.updateCouponRecordStatusBecomeUsedByOrderCode(orderLog.getOrder_code(),orderLog.getProduct_name());
		
		
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
					userConsumeInfoService.createConsumeInfo(consumeInfo.getPhone(), 0, consumeInfo.getSupplier_id(), consumeInfo.getSupplier_product_id(), consumeInfo.getUser_id(), consumeInfo.getTransaction_id(), orderLog.getOrder_code(), consumeInfo.getInterface_type(), eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER_EFF);
					userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, effOrderCode);
				}
			}
		}
	}
		
}
