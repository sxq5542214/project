package com.yd.business.user.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.msgcenter.bean.MsgCenterUserSubscribeBean;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.controller.OrderController;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.bean.AddressBean;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IAddressService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierTopicService;
import com.yd.business.user.bean.UserAddressBean;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserInfoCenterPageBean;
import com.yd.business.user.bean.UserSignBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserAddressService;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserSignService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatSendRedPackLogBean;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
@Controller
public class UserController extends BaseController {

	@Resource
	private ISupplierProductService supplierProductService;
	@Autowired
	private IUserWechatService userWechatService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IOrderService orderService;
	@Resource
	private IUserSignService userSignService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IWechatPayService wechatPayService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IAreaDataService areaDataService;
	@Resource
	private ISupplierTopicService supplierTopicService; 
	@Resource
	private IMsgCenterArticleService msgCenterArticleService;
	@Resource
	private IAddressService addressService;
	@Resource
	private IUserAddressService userAddressService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IShopOrderService shopOrderService;
	
	public static final String PAGE_ORDERPRODUCT = "/page/user/activity/signActivity/orderProduct/orderProduct.jsp";
	public static final String PAGE_ORDERPRODUCTLOG = "/page/user/orderProductLog.jsp";
	public static final String PAGE_OWNERORDERLOG = "/page/user/ownerOrderLog.jsp";
	public static final String PAGE_ORDERCONFIRM = "/page/user/orderConfirm.jsp";
	public static final String PAGE_SIGNHISTORY = "/page/user/signHistory.jsp";
	public static final String PAGE_USERINFOCENTER = "/page/user/infoCenter.jsp";
	public static final String PAGE_USERCOMMISSIONPOINTS = "/page/user/userCommissionPointsLog.jsp";
	public static final String PAGE_USERFRIENDLEVELONE = "/page/user/userFriendLevelOne.jsp";
	public static final String PAGE_USERBALANCELOG = "/page/user/userBalanceLog.jsp";
	public static final String PAGE_USERSIGN = "/page/user/userSign.jsp";
	public static final String PAGE_USERSIGNANDTOPIC = "/page/user/userSignAndTopic.jsp";
	
	public static final String PAGE_FIRST_ORDERPRODUCT = "/page/user/first-orderProduct.jsp";
	public static final String PAGE_FIRST_ORDERCONFIRM = "/page/user/first-orderConfirm.jsp";
	public static final String PAGE_GETBALANCECASH = "/page/user/getBalanceCashPage.jsp";

	public static final String PAGE_LIULIANG1GACTIVITY = "/page/activity/liuliang1GActivity.jsp";
	public static final String PAGE_ACTIVITY_VOLTECALL = "/page/activity/volteCallActivity.jsp";
	public static final String PAGE_ACTIVITY_QIANG1G = "/page/activity/qiang1GActivity.jsp";
	public static final String PAGE_ACTIVITY_SCRATCHACTIVITY = "/page/user/activity/scratch/scratchActivity.jsp";
	public static final String PAGE_ACTIVITY_FREE_ORDER= "/page/activity/freeOrderActivity.jsp";
	public static final String PAGE_ACTIVITY_SIGN_ORDERPRODUCT = "/page/user/activity/signActivity/orderProduct/orderProduct.jsp";
	//买一赠一活动页面
	public static final String PAGE_BUYGIVEONE_ORDERPRODUCT = "/page/user/activity/buyGiveOne/orderProduct.jsp";
	
	public static final String PAGE_ACTIVYTY_GETLIST = "activity/user/getSignActivityList.do";
	
	public static final String PAGE_USER_ADD_ADDRESS = "/page/shop/userAddAddress.jsp";
	public static final String PAGE_USER_ADDRESS_LIST = "/page/shop/userAddressList.jsp";
	public static final String PAGE_USER_SHOP_ORDER = "/page/shop/order/orderInfo.jsp";
	public static final String PAGE_USER_SHOP_ORDER_LIST = "/page/shop/order/orderList.jsp";
	
	/**
	 * 检查手机号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/user/checkPhone.do")
	public ModelAndView checkPhone(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String phone = request.getParameter("phone");
			AreaDataBean ad = areaDataService.getAreaDataByPhone(phone);
			writeJson(response, ad);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	@RequestMapping("**/user/queryPlatformProduct.do")
	public ModelAndView queryPlatformProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			
//			List<CustomerSupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
			
			UserWechatBean user = userWechatService.findUserWechatById(5000);
			Map<String, Object> model = new HashMap<String, Object>();
			model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("customer_id", 1);
			model.put("openid", "123");
			//调用数据库查询是否有历史的号码,获得直接在页面展示
			OrderProductLogBean beanout = null;
			beanout=orderProductLogService.findOrderProductLogByUseridDesc(5000);
			if(beanout!=null){
			if(!"null".equals(beanout.getOrder_account())){
				model.put("order_account",beanout.getOrder_account());
			}
			}
//			List<OrderProductLogBean> logList = orderProductLogService.queryOrderProductLogByUserId(user.getId(),OrderProductLogBean.STATUS_SUCCESS);
//			if(StringUtil.isNull(user.getLast_order_time())){
//				return new ModelAndView(UserController.PAGE_FIRST_ORDERPRODUCT, model);
//			}
//			return new ModelAndView(PAGE_BUYGIVEONE_ORDERPRODUCT, model);	//返回特价活动界面
//			return new ModelAndView(PAGE_FIRST_ORDERPRODUCT, model);	//返回第一次购买页面
			return new ModelAndView(PAGE_ORDERPRODUCT, model);			//返回第二次购买页面
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	
	
	@RequestMapping("**/user/toOrderConfirm.do")
	public ModelAndView toOrderConfirm(String phone,String spid,String openid){
		try{
			Map<String, Object> model = new HashMap<String, Object>();
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(Integer.parseInt(spid));
			
			//查询自己拥有的优惠卷
			SupplierCouponRecordBean couponRuleBean = new SupplierCouponRecordBean();
			couponRuleBean.setUserid(user.getId());
			Integer couponRuleCount = supplierCouponService.queryUserCouponCount(null,user.getId(),null); 	
			
			model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("supplierProduct", sp);
			model.put("phone", phone);
			model.put("couponRuleCount", couponRuleCount);
			return new ModelAndView(PAGE_ORDERCONFIRM, model);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("**/user/toFirstOrderConfirm.do")
	public ModelAndView toFirstOrderConfirm(String phone,String spid,String openid){
		try{
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(Integer.parseInt(spid));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("supplierProduct", sp);
			model.put("phone", phone);
			
			if(StringUtil.isNull(user.getLast_order_time())){
				return new ModelAndView(PAGE_FIRST_ORDERCONFIRM, model);
			}else{
				return new ModelAndView(PAGE_ORDERCONFIRM, model);
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	@RequestMapping("**/user/toOrderConfirmByOrderLog.do")
	public ModelAndView toOrderConfirmByOrderLog(HttpServletRequest request,HttpServletResponse response){
		try{
			
			String order_code = request.getParameter("order_code");
			OrderProductLogBean orderProductLog = orderProductLogService.findOrderProductLogByCode(order_code);
			
			
			UserWechatBean user = userWechatService.findUserWechatById(orderProductLog.getUser_id());
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(orderProductLog.getSupplier_product_id());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("supplierProduct", sp);
			model.put("phone", orderProductLog.getOrder_account());
			if(orderProductLog.getStatus() == OrderProductLogBean.STATUS_SUCCESS){
				model.put("orderLog", orderProductLog);
				//如果已经订购成功，直接返回订购结果的界面
				return new ModelAndView(OrderController.PAGE_USER_ORDER_PRODUCT_RESULT, model);
			}
			
			if(StringUtil.isNull(user.getLast_order_time()) 
					&& StringUtil.isNotNull(user.getShare_time())
					&& orderProductLog.getSupplier_id() == SupplierBean.PLATFROM_SUPPLIER_ID){
				return new ModelAndView(PAGE_FIRST_ORDERCONFIRM, model);
			}else{
				return new ModelAndView(PAGE_ORDERCONFIRM, model);
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("**/user/queryFriendLevelOne.do")
	public ModelAndView queryFriendLevelOne(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			
			UserWechatBean bean = new UserWechatBean();
			bean.setParentid(user.getId());
			List<UserWechatBean> friends =userWechatService.list(bean );
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("friends", friends);
			model.put("openid",user.getOpenid());
			return new ModelAndView(PAGE_USERFRIENDLEVELONE,model);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}

	@RequestMapping("**/user/toUserInfoCenter.do")
	public ModelAndView toUserInfoCenter(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			UserInfoCenterPageBean userInfoPage = userWechatService.queryActivityFriendLevelCount(user.getId());
			userInfoPage.setUserWechat(user);
			
			boolean isShop = configAttributeService.getBooleanValueByCode(AttributeConstant.CODE_SYSTEM_IS_SHOP_ORDER);

//			if(user != null && user.getParentid() != null) 这是判断好坏人的
			if(user != null )
			{
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userInfoPage", userInfoPage);
				model.put("isShop", isShop);
				return new ModelAndView(UserController.PAGE_USERINFOCENTER, model);
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("**/user/queryCommissionPoint.do")
	public ModelAndView queryCommissionPoint(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			
			
			if(user != null)
			{
				List list = userCommissionPointsService.queryUserCommissionPoints(user.getId());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model = new HashMap<String, Object>();
				model.put("list", list);
				model.put("openid",user.getOpenid());
				return new ModelAndView(PAGE_USERCOMMISSIONPOINTS, model);
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	
	@RequestMapping("**/user/queryOrderProductLog.do")
	public ModelAndView queryOrderProductLog(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String userId = request.getParameter("userId");
			if(userId == null){
				String openid = request.getParameter("openid");
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				if(user != null && openid != null)
				{
					userId = user.getId().toString();
				}
			}
			UserWechatBean user = userWechatService.findUserWechatById(Integer.parseInt(userId));

			List<OrderProductLogBean> list = orderProductLogService.queryOrderProductLogByUserId(Integer.parseInt(userId));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("openid",user.getOpenid());
			model.put("list", list);
			return new ModelAndView(PAGE_ORDERPRODUCTLOG, model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/user/toMySignHisInfo.do")
	public ModelAndView toMySignHisInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			
			String userId = request.getParameter("userId");
			List<UserSignBean> list = userSignService.queryUserSignHistory(Integer.parseInt(userId));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			
			return new ModelAndView(PAGE_SIGNHISTORY, model);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("**/user/createUnifiedOrder.do")
	public ModelAndView createUnifiedOrder(HttpServletRequest request,HttpServletResponse response){
		try{
			String coupon_record_id = request.getParameter("coupon_record_id");	//优惠卷id
			String openid = request.getParameter("openid");
			String price = request.getParameter("price");
			String pointsStr = request.getParameter("points");
			String balanceStr = request.getParameter("cost_balance");
			String eff_numStr = request.getParameter("eff_num");
			Integer points = StringUtil.isNull(pointsStr) ? 0:Integer.parseInt(pointsStr);
			Integer costBalance = StringUtil.isNull(balanceStr) ? 0:Integer.parseInt(balanceStr);
			Integer eff_num = StringUtil.isNull(eff_numStr) ? 0:Integer.parseInt(eff_numStr);
			String phone = request.getParameter("phone");
			Integer product_id = Integer.parseInt(request.getParameter("product_id"));

			int rmb = (int) (Double.parseDouble(price)  * 100);
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				throw new RuntimeException(" createUnifiedOrder user is null!");
			}
			
			
			
			
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(product_id);
			
//			int costBalance = (int) (sp.getProduct_price() * sp.getDiscount()/100d - points);
			
			//商户订单号
			String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_USERBALANCE, user.getId());
			UserConsumeInfoBean consume = new UserConsumeInfoBean();

			//如果优惠卷规记录id不等于空
			if(!StringUtil.isNull(coupon_record_id) ){
				SupplierCouponRecordBean couponRecordBean = new SupplierCouponRecordBean();
				couponRecordBean.setUserid(user.getId());
				couponRecordBean.setId(Integer.parseInt(coupon_record_id));
				couponRecordBean  = supplierCouponService.findCouponRecord(couponRecordBean);
				if(couponRecordBean == null){
						throw new RuntimeException(" createUnifiedOrder couponRecord is null!");
				}
				
				
			//根据优惠卷记录表中的id,在优惠卷优惠卷记录表中添加订单号
				supplierCouponService.updateOrderCodeCouponRecordById(Integer.parseInt(coupon_record_id),out_no);
				consume = userConsumeInfoService.createConsumeInfo(phone,costBalance, product_id, user.getId(), null, out_no,UserConsumeInfoBean.INTERFACETYPE_COUPONPAY,eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_COUPON);
			}
			else{
				//保存充值记录
				consume = userConsumeInfoService.createConsumeInfo(phone,costBalance, product_id, user.getId(), null, out_no,UserConsumeInfoBean.INTERFACETYPE_USERBALANCE,eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER);
			}

			OrderProductLogBean productLog = orderProductLogService.createOrderProductLogByUserConsumeInfo(consume, points, 0,costBalance, null);
			
			//由于是余额支付，所以直接更新定单状态为已支付
			orderProductLogService.updateOrderProductLogStatusToPaySuccess(productLog.getOrder_code());
			userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, consume.getOut_trade_code());
			writeJson(response, productLog.getOrder_code());
			
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		
		return null;
		
	}
	

	@RequestMapping("**/user/getBalanceCash.do")
	public ModelAndView getBalanceCash(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			int getCash = Integer.parseInt(request.getParameter("getCash"));
			String ip = getRemoteHost(request);
			
			WechatSendRedPackLogBean sendLog = wechatPayService.getLastTimeSendPackLog(openid);
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, 1);
			String mustLastTime = DateUtil.formatDate(c.getTime());
			//最近一次提现是1分钟之前才可以再次提现，不然微信红包不支持
			if(sendLog != null &&  mustLastTime.compareTo(sendLog.getSend_time()) <= 0 ){
				writeJson(response, "waitMinute");
				return null;
			}
			
			boolean flag = wechatPayService.payBonusLimit200(openid, getCash, ip);
//			boolean flag = true;
			if(flag){
				writeJson(response, "success");
			}else{
				writeJson(response, "false");
			}
			
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
		
	}
	
	@RequestMapping("**/user/queryBalanceLog.do")
	public ModelAndView queryBalanceLog(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			
			UserConsumeInfoBean bean = new UserConsumeInfoBean();
			bean.setUser_id(user.getId());
			bean.setStatus(UserConsumeInfoBean.STATUS_SUCCESS);
			List<UserConsumeInfoBean> logs = userConsumeInfoService.queryUserConsumeInfo(bean);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("logs", logs);
			model.put("openid",user.getOpenid());
			return new ModelAndView(PAGE_USERBALANCELOG,model);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("**/user/userSharedByScratch.do")
	public ModelAndView userSharedByScratch(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			
			if(StringUtil.isNotNull(openid)){
				String share_type = request.getParameter("share_type");
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				
				user.setShare_time(DateUtil.getNowDateStrSSS());
				user.setShare_type(Integer.parseInt(share_type));
				userWechatService.update(user);
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	/**
	 * 处理用户的分享
	 * @param request 
	 */
	@RequestMapping("**/user/handleUserShare.do")
	public ModelAndView handleUserShare(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			String share_from = request.getParameter("share_from");
			String share_time_ms = request.getParameter("share_time_ms");
			String param = request.getParameter("param");
			if(StringUtil.isNotNull(openid)){
				String share_type = request.getParameter("share_type");
				
				userWechatService.handlerUserShare(openid, share_from, Integer.parseInt(share_type),share_time_ms,param);
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}

	@RequestMapping("**/user/queryEffOwnerOrderLog.do")
	/**
	 * 获取用户的所有订单，分可预约和已生效订单
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryOwnerEffOrderLog(HttpServletRequest request,HttpServletResponse response){
		try{
//			String userId = request.getParameter("userId");
//			if(userId == null){
				String openid = request.getParameter("openid");
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				if(user != null && openid != null)
				{
					String userId = user.getId().toString();
					//获取订单列表
					List<OrderProductEffShowPageBean> waitList = orderService.queryEffOrderProductLogByUserId(Integer.parseInt(userId), OrderProductEffBean.STATUS_WAIT);
					List<OrderProductEffShowPageBean> successList = orderService.queryEffOrderProductLogByUserId(Integer.parseInt(userId), OrderProductEffBean.STATUS_SUCCESS);
					
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("openid",user.getOpenid());
					model.put("waitList", waitList);
					model.put("successList", successList);
					return new ModelAndView(PAGE_OWNERORDERLOG, model);
				}
//			}
//			UserWechatBean user = userWechatService.findUserWechatById(Integer.parseInt(userId));
			
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/user/sendUserMessageWithin48Hour.do")
	public ModelAndView sendUserMessageWithin48Hour(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
			taskExecutor.execute(new BaseRunable() {
				
				@Override
				public void runMethod() {
					List<UserWechatBean> list =	userWechatService.queryUserListWithin48Hour();
					String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ACCESS_WITHIN_48HOUR);
					TextBean text = new TextBean();
					int size = list.size();
					for(int i = 0 ; i < size; i++){
						UserWechatBean bean = list.get(i);
						String str = content.replaceAll("#openid#", bean.getOpenid());
						
						text.setToUserName(bean.getOpenid());
						text.setContent(str);

						text.setFromUserName(bean.getOriginalid());
						wechatService.sendMessageToUser(text);
						
						System.out.println("sendUserMessageWithin48Hour:"+i+"/"+size);
					}
				}
			});
			
			
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	

	@RequestMapping("**/user/toUserSignPage.do")
	public ModelAndView toUserSignPage(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String openid = request.getParameter("openid");		//原先写法

			//判断本日有没有签到
			UserSignBean sign= userSignService.whetherSign(openid);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("openid", openid);
			model.put("sign", sign);
			model.put("user", user);
			return new ModelAndView(UserController.PAGE_USERSIGN, model);
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}


	/**
	 * 用户签到与话题的界面
	 */
	@RequestMapping("**/user/toUserSignAndTopicPage.do")
	public ModelAndView toUserSignAndTopicPage(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String openid = request.getParameter("openid");		//原先写法

			//判断本日有没有签到
			UserSignBean sign= userSignService.whetherSign(openid);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			WechatOriginalInfoBean originalinfo = BaseContext.getWechatOriginalInfo(user.getOriginalid());

			SupplierTopicBean condition = new SupplierTopicBean();
			condition.setStatus(SupplierEventBean.STATUS_ENABLE);
			condition.setOrderby(" order by read_num ");
			condition.setEnd_time(DateUtil.getNowDateStr());
			List<SupplierTopicBean> nowTopicList = supplierTopicService.queryBeforEndTimeSupplierTopic(condition );
			
			MsgCenterUserSubscribeBean bean = new MsgCenterUserSubscribeBean();
			bean.setCode(MsgCenterUserSubscribeBean.CODE_USERSIGNANDTOPIC);
			bean.setType(MsgCenterUserSubscribeBean.TYPE_MSGCENTER);
			bean.setStatus(MsgCenterUserSubscribeBean.STATUS_DISABLE);
			bean.setOpenid(openid);
			List<MsgCenterUserSubscribeBean> list = msgCenterArticleService.queryUserSubscribeInfo(bean );
			boolean flag = list.size() == 0 ;

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("openid", openid);
			model.put("sign", sign);
			model.put("user", user);
			model.put("originalinfo", originalinfo);
			model.put("nowTopicList", nowTopicList);
			model.put("isUserSubscribe", flag );
			return new ModelAndView(UserController.PAGE_USERSIGNANDTOPIC, model);
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}

	
	
	//用户签到功能
	@RequestMapping("**/user/todaySign.do")
	public ModelAndView todaySign(HttpServletRequest request,HttpServletResponse response){
		
		try {
				String openid = request.getParameter("openid");		
				UserSignBean sign =	userSignService.userSignByOpenid(openid);
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				sign.setSence_id(user.getPoints());										//获取当前积分
				if(user != null){
					writeJson(response, sign);
				}
			 
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	
	/**
	 * 查询用户历史订购号码最多十个
	 * @param request 
	 */
	@RequestMapping("**/user/UserHistoryPhoneLastTen.do")
	public ModelAndView UserHistoryPhoneLastTen(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user != null && openid != null){
				int userId = user.getId();
				List<OrderProductLogBean>  userphone = orderProductLogService.queryOrderLogLastTen(userId);
				if(userphone == null || userphone.size() != 0)
				{
					writeJson(response, userphone);
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("**/user/toUserAddressListPage.do")
	public ModelAndView toUserAddressListPage(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String userid = request.getParameter("user_id");
			String openid = request.getParameter("openid");
			String order_code = request.getParameter("order_code");
			UserWechatBean user;
			UserAddressBean bean = new UserAddressBean();
			if(StringUtil.isNull(userid)){
				user = userWechatService.findUserWechatByOpenId(openid);
			}else{
				user = userWechatService.findUserWechatById(Integer.parseInt(userid));
			}
			
			bean.setUser_id(user.getId());
			List<UserAddressBean> list = userAddressService.queryUserAddress(bean );
			
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("list", list);
			model.put("user", user);
			model.put("order_code", order_code);
			
			
			return new ModelAndView(PAGE_USER_ADDRESS_LIST, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("**/user/toUserAddAddressPage.do")
	public ModelAndView toUserAddAddressPage(HttpServletRequest request,HttpServletResponse response){
		try {
			

			String user_id = request.getParameter("user_id");
			UserWechatBean user ;
			if(StringUtil.isNull(user_id)){
				String openid = request.getParameter("openid");
				user = userWechatService.findUserWechatByOpenId(openid);
			}else{
				user = userWechatService.findUserWechatById(Integer.parseInt(user_id));
			}
			String order_code = request.getParameter("order_code");
			
			AddressBean bean = new AddressBean();
			bean.setLevel(AddressBean.LEVEL_PROVINCE);
			List<AddressBean> list = addressService.queryAddress(bean );
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			model.put("user", user);
			model.put("order_code", order_code);
			
			return new ModelAndView(PAGE_USER_ADD_ADDRESS, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/user/deleteUserAddress.do")
	public ModelAndView deleteUserAddress(HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserAddressBean bean = new UserAddressBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), bean);
			
			List<UserAddressBean> list = userAddressService.queryUserAddress(bean);
			
			if(list.size() > 0){
				userAddressService.deleteUserAddress(bean.getId());
			}
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("**/user/addAddress.do")
	public ModelAndView addAddress(HttpServletRequest request,HttpServletResponse response){
		try {

			String order_code = request.getParameter("order_code");
			UserAddressBean bean = new UserAddressBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), bean);
			bean.setAddress_id(Integer.parseInt(bean.getDistrict()));
			
			userAddressService.createUserAddress(bean);
			
			if(StringUtil.isNotNull(order_code)){
				
				return new ModelAndView("redirect:user/toUserAddressListPage.do?user_id="+bean.getUser_id() +"&order_code="+order_code );
			}
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/user/addAddressByWechatData.do")
	public ModelAndView addAddressByWechatData(HttpServletRequest request,HttpServletResponse response){
		try {

//			String orderCode = request.getParameter("order_code");
			String openid = request.getParameter("openid");
			
			
			
			UserAddressBean bean = new UserAddressBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), bean);
			bean.setUser_name("用户使用微信地址");
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user.getId().intValue() == bean.getUser_id().intValue() ){
				
				AddressBean address = new AddressBean();
				address.setFull_name("%"+bean.getProvince()+"%" +bean.getCity() +"%"+bean.getDistrict()+"%");
				
				List<AddressBean> listAddr = addressService.queryAddress(address);
				if(listAddr.size() == 0){
					log.error("error! addAddressByWechatData  address:"+bean.getProvince()+"%" +bean.getCity() +"%"+bean.getDistrict() + " notfound please check data!");
					return null;
				}else{
					bean.setAddress_id(listAddr.get(0).getId());
					userAddressService.createUserAddress(bean);
					
					bean.setOrderby(" order by id desc");
					bean = userAddressService.queryUserAddress(bean).get(0);
					
					writeJson(response, ""+ bean.getId());
				}
				
			}
			
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	/**
	 * 跳转到用户定单界面
	 * @return
	 */
	@RequestMapping("**/user/toUserShopOrderPage.do")
	@Deprecated
	public ModelAndView toUserShopOrderPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String data = request.getParameter("data");
			String openid = request.getParameter("openid");
			String order_code = request.getParameter("order_code");

			ShopOrderInfoBean order ;
			if(StringUtil.isNotNull(order_code)){ //已有定单号
				order = shopOrderService.findShopOrderInfoByCode(order_code);

				ShopOrderProductBean condition = new ShopOrderProductBean();
				condition.setOrder_code(order_code);
				List<ShopOrderProductBean> productList = shopOrderService.queryShopOrderProduct(condition );
				order.setProductList(productList);
				
			}else{ //没有定单号
				order = shopOrderService.createOrderLogByUserCartList(openid,data,null);
			}
			
			UserWechatBean user = userWechatService.findUserWechatById(order.getUser_id());
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("user", user);
			
			return new ModelAndView(PAGE_USER_SHOP_ORDER,model );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 * 跳转到用户定单列表界面
	 * @return
	 */
	@RequestMapping("**/user/toUserShopOrderListPage.do")
	public ModelAndView toUserShopOrderListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String user_id = request.getParameter("user_id");
			String openid = request.getParameter("openid");
			UserWechatBean user ;
			if(user_id == null){
				user = userWechatService.findUserWechatByOpenId(openid);
			}else{
				user = userWechatService.findUserWechatById(Integer.parseInt(user_id));
			}
			
			
			ShopOrderInfoBean bean = new ShopOrderInfoBean();
			bean.setUser_id(user.getId());
			bean.setNotInStatus(""+ShopOrderInfoBean.STATUS_USER_DELETE);
			bean.setOrderby("order by id desc");
			List<ShopOrderInfoBean> orderList = shopOrderService.queryShopOrderInfo(bean );
			
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderList", orderList);
			model.put("user", user);
			
			return new ModelAndView(PAGE_USER_SHOP_ORDER_LIST,model );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	/**
	 * 签到管理管理,查询功能
	 */
	@RequestMapping("/admin/user/userController/queryAdmimUserSign.do")
	public ModelAndView queryAdmimUserSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			UserSignBean bean = new UserSignBean();
			String user_id =  request.getParameter("query_usersign_user_id");
			String sign_date =  request.getParameter("query_crons_sign_date");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(user_id)){
				bean.setUser_id(Integer.parseInt(user_id));
			}
			if(!StringUtil.isNull(sign_date)){
				bean.setSign_date(sign_date);
			}
			PageinationData pd =  userSignService.queryUserSingPage(bean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/userSign/iframe_user_sign.jsp", map);
	}
	
	
	
	/**
	 * 签到删除 	根据id删除ll_user_sign表信息
	 */
	@RequestMapping("/admin/user/userController/deleteUserSign.do")
	public ModelAndView deleteUserSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			UserSignBean bean = new UserSignBean();
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
				bean.setId(Integer.parseInt(id));
				userSignService.deteleUserSign(bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}
	
	
	
	/**
	 *  增加和编辑签到表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/user/userController/editUserSign.do")
	public ModelAndView editUserSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		UserSignBean bean =  new UserSignBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			bean.setSign_month(request.getParameter("sign_month"));
			bean.setSign_date(request.getParameter("sign_date"));
			bean.setUser_id(Integer.parseInt(request.getParameter("user_id")));
			bean.setSence_id(Integer.parseInt(request.getParameter("sence_id")));
			bean.setSign_count(Integer.parseInt(request.getParameter("sign_count")));
			bean.setSerial_sign_count(Integer.parseInt(request.getParameter("serial_sign_count")));
			bean.setLast_sign_date(request.getParameter("last_sign_date"));
			bean.setLast_points(Integer.parseInt(request.getParameter("last_points")));
			bean.setPoints_month(request.getParameter("points_month"));
			userSignService.editUserSign(bean);
			if(StringUtil.isNull(bean.getRemark())){
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
}
