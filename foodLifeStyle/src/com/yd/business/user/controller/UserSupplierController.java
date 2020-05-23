package com.yd.business.user.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.order.bean.ShopOrderEffInfoBean;
import com.yd.business.order.bean.ShopOrderEffProductBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IAddressService;
import com.yd.business.other.service.IAdvertisingService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.SupplierBalanceLogBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierPackageService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierStoreService;
import com.yd.business.supplier.service.ISupplierTopicService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserAddressService;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserSignService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.CookieUtil;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
@Controller
public class UserSupplierController extends BaseController {
	public static final String PAGE_USERSUPPLIERPRODUCT = "/page/user/supplierProductShop/index.jsp";
	public static final String PAGE_USERSUPPLIERCATEGORY = "/page/user/supplierProductShop/category.jsp";
	public static final String PAGE_USER_SHOP_ORDER = "/page/shop/order/orderInfo.jsp";
	public static final String PAGE_USER_SHOP_LOCAL_ORDER = "/page/shop/order/localOrderInfo.jsp";
	public static final String PAGE_USER_PLATFORM_INDEXPAGE = "/page/user/platform/index.jsp";
	public static final String PAGE_USER_SUPPLIERSHOP_PACKAGEPRODUCTLIST = "/page/user/supplierProductShop/userSupplierPackageProductList.jsp";
	public static final String PAGE_SUPPLIER_SHOP_ORDER_EFF = "/page/supplier/shop/orderEffInfo.jsp";
	public static final String PAGE_SUPPLIER_SHOP_PAY_PRICE_PLATFORM = "/page/supplier/shop/payPricePlatForm.jsp";
	public static final String PAGE_SUPPLIER_SHOP_PAY_PRICE_PERSONAL = "/page/supplier/shop/payPricePersonal.jsp";
	public static final String PAGE_USER_SUPPLIERSHOP_STOREBALANCECARDLIST = "/page/user/supplierProductShop/userSupplierStoreBalanceCardList.jsp";
	
	
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
	private ISupplierUserService supplierUserService; 
	@Resource
	private ISupplierService supplierService; 
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
	@Resource
	private IProductTypeService productTypeService;
	@Resource
	private IAdvertisingService advertisingService;
	@Resource
	private ISupplierPackageService supplierPackageService;
	@Resource
	private ISupplierStoreService supplierStoreService;

	
	@Deprecated
	@RequestMapping("**/user/supplier/queryPlatformSupplierProduct.do")
	public ModelAndView queryPlatformSupplierProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			String spid = request.getParameter("spid");
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				//跳转至关注公众号界面
				writeJson(response, "<script>alert(\"请先关注公众号!如已关注，请重新打开\");</script>");
				return null;
			}
			
			List<SupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
			List<AdvertisingBean> advertList = advertisingService.queryAdvertisingInfo(AdvertisingBean.CODE_SUPPLIERINDEXPAGE);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("supplierProductList", list);
			model.put("advertList", advertList);

			
			return new ModelAndView(PAGE_USERSUPPLIERPRODUCT, model);			//返回购买页面
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	@RequestMapping("**/user/supplier/toPlatformSupplierListPage.html")
	public ModelAndView toPlatformSupplierListPage(HttpServletRequest request,HttpServletResponse response){
		try{
//			String spid = request.getParameter("spid");
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				//跳转至关注公众号界面
				writeJson(response, "<script>alert(\"请先关注公众号!如已关注，请重新打开\");</script>");
				return null;
			}
			
			List<SupplierBean> listMyVisit= supplierUserService.queryUserVisitSupplierListByOpenid(openid);
			
			if(listMyVisit.size() == 1) {
				// 用户只访问过一个，则直接进入商户页
				
				return new ModelAndView("/supplier/shop/toSupplierShopPage.html?sid="+ listMyVisit.get(0).getId() +"&fromOpenid="+openid);
				
			}else {

				List<AdvertisingBean> advertList = advertisingService.queryAdvertisingInfo(AdvertisingBean.CODE_PLATFORMINDEXPAGE);
				
				Map<String, Object> model = new HashMap<String, Object>();
				model = new HashMap<String, Object>();
				model.put("user", user);
				model.put("listMyVisit", listMyVisit);
				model.put("advertList", advertList);
				
				return new ModelAndView(PAGE_USER_PLATFORM_INDEXPAGE, model);		// 用户访问的主页面
			}
				
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	/**
	 *  打开商城分类界面
	 */
	@Deprecated
	@RequestMapping("**/user/supplier/toSupplierProductCategoryPage.do")
	public ModelAndView toSupplierProductCategoryPage(String sid,String openid){
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}
			
			SupplierBean supplier = supplierService.findSupplierById(supplier_id);
			List<ProductTypeBean> productTypeList = productTypeService.listProductTypeByCustomerId(supplier.getCustomer_id());
			
			
			SupplierProductBean condition = new SupplierProductBean();
			condition.setCustomer_id(CustomerBean.ID_PLATFROM);
			condition.setStatus(SupplierProductBean.STATUS_UP);
			condition.setNow_time(DateUtil.getNowDateStr());
			condition.setHome_flag(SupplierProductBean.HOME_FLAG_YES);
			condition.setOrderby(" order by product_type,seq  asc ");
			List<SupplierProductBean> productList = supplierProductService.listSupplierProduct(condition );
			
			
//			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			Map<String, Object> model = new HashMap<String, Object>();
			model = new HashMap<String, Object>();
//			model.put("user", user);
			model.put("productTypeList", productTypeList);
			model.put("productList", productList);
			model.put("openid", openid);

			
			return new ModelAndView(PAGE_USERSUPPLIERCATEGORY, model);			
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	

	/**
	 * 用户支付
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/user/supplier/toPayPricePage.html")
	public ModelAndView toPayPricePage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String sid = request.getParameter("sid");
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			SupplierBean supplier = supplierService.findSupplierById(Integer.parseInt(sid));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("supplier", supplier);
			if( (NumberUtil.convertNull(supplier.getPay_where()) == SupplierBean.PAY_WHERE_PERSONAL) && StringUtil.isNotNull(supplier.getPersonal_pay_img())) {
				
				return new ModelAndView(PAGE_SUPPLIER_SHOP_PAY_PRICE_PERSONAL,model );
				
			}else {
				model.put("user", user);
				return new ModelAndView(PAGE_SUPPLIER_SHOP_PAY_PRICE_PLATFORM,model );
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
	@RequestMapping("**/user/supplier/toSupplierShopUserOrderPage.html")
	public ModelAndView toSupplierShopUserOrderPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String openid = request.getParameter("openid");
			String order_code = request.getParameter("order_code");
			String timeString = request.getParameter("time");
			String productInfos = request.getParameter("productInfos");
			String isRemote = request.getParameter("isRemote");
			String sid = request.getParameter("sid");
			Integer supplier_id = Integer.parseInt(sid);
			Long time = null;
			if(StringUtil.isNotNull(timeString)){
				time = Long.parseLong(timeString);
			}
			
			int type = SupplierBalanceLogBean.TYPE_USER_SHOPORDER_OFFLINE;
			SupplierBean supplier = supplierService.findSupplierById(supplier_id);
			if(StringUtil.isNotNull(isRemote) || supplier.getIssale() == SupplierBean.ISSALE_REMOTE ) {
				type = SupplierBalanceLogBean.TYPE_USER_SHOPORDER_ONLINE;
			}
			
			ShopOrderInfoBean order ;
			if(StringUtil.isNotNull(order_code)){ //已有定单号
				order = shopOrderService.findShopOrderInfoByCode(order_code);

				ShopOrderProductBean condition = new ShopOrderProductBean();
				condition.setOrder_code(order_code);
				List<ShopOrderProductBean> productList = shopOrderService.queryShopOrderProduct(condition );
				order.setProductList(productList);
				
			}else if(StringUtil.isNotNull(productInfos)){ // 根据传参过来的数据创建订单
				order = shopOrderService.createOrderLogByUserCartList(supplier_id,openid,productInfos,time,type);
			}else{//没有定单号、没有传参数据,则根据cookie里的数据创建订单
				String data = CookieUtil.getValueByCookie(request, UserCartController.COOKIE_KEY_PRODUCTINFO);
				order = shopOrderService.createOrderLogByUserCartList(supplier_id,openid,data,time,type);
			}
			
			// 找之前已经选择过的优惠卷
			List<SupplierCouponRecordBean> couponList = supplierCouponService.queryCouponRecordByOrderCode(order.getOrder_code(),order.getUser_id());
			if(couponList.size() == 0 && order.getStatus() == ShopOrderInfoBean.STATUS_WAIT)
			{	//如果没有，则找目前可用的优惠卷
				couponList = supplierCouponService.queryUserCanUseCouponByOrderCode(order.getUser_id(), order.getOrder_code());
			}
			
			// 找折扣卡/储值卡 
			List<SupplierStoreBalanceCardRecordBean> cardList = supplierStoreService.queryUserCanuseStoreBalanceCardRecord( openid, order.getSupplier_id());
			
			SupplierUserBean user = supplierUserService.findSupplierUser(order.getUser_id(), supplier.getId());
			int expressBottomPrice = configAttributeService.getIntValueByCode(AttributeConstant.CODE_SHOP_ORDER_NEED_EXPRESS_BOTTOM_PRICE);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("user", user);
			model.put("couponList", couponList);
			model.put("cardList", cardList);
			model.put("expressBottomPrice", expressBottomPrice);
			model.put("supplier", supplier);
			
			if(StringUtil.isNotNull(isRemote) || supplier.getIssale() == SupplierBean.ISSALE_REMOTE ) {
				return new ModelAndView(PAGE_USER_SHOP_ORDER,model );
			}else {
				return new ModelAndView(PAGE_USER_SHOP_LOCAL_ORDER,model );
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
	@RequestMapping("**/user/supplier/toSupplierShopUserEffOrderPage.html")
	public ModelAndView toSupplierShopUserEffOrderPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String openid = request.getParameter("openid");
			String order_code = request.getParameter("order_code");
			String timeString = request.getParameter("time");
			String eff_date = request.getParameter("eff_date");
			String productInfos = request.getParameter("productInfos");
			String sid = request.getParameter("sid");
			Integer supplier_id = Integer.parseInt(sid);
			int type = SupplierBalanceLogBean.TYPE_USER_SHOPORDER_EFF ;
			Long time = null;
			if(StringUtil.isNotNull(timeString)){
				time = Long.parseLong(timeString);
			}
			ShopOrderEffInfoBean order ;
			if(StringUtil.isNotNull(order_code)){ //已有定单号
				order = shopOrderService.findShopOrderEffInfoByCode(order_code);

				ShopOrderEffProductBean condition = new ShopOrderEffProductBean();
				condition.setOrder_code(order_code);
				List<ShopOrderEffProductBean> productList = shopOrderService.queryShopOrderEffProduct(condition );
				order.setProductList(productList);
				
			}else if(StringUtil.isNotNull(productInfos)){ // 根据传参过来的数据创建订单
				ShopOrderInfoBean newOrder = shopOrderService.createOrderLogByUserCartList(supplier_id,openid,productInfos,time,eff_date,type);
				order = new ShopOrderEffInfoBean(newOrder);
			}else{//没有定单号、没有传参数据,则根据cookie里的数据创建订单
				String data = CookieUtil.getValueByCookie(request, UserCartController.COOKIE_KEY_PRODUCTINFO);
				ShopOrderInfoBean newOrder =  shopOrderService.createOrderLogByUserCartList(supplier_id,openid,data,time,eff_date,type);
				order = new ShopOrderEffInfoBean(newOrder);
			}
			
			// 找之前已经选择过的优惠券
			List<SupplierCouponRecordBean> couponList = supplierCouponService.queryCouponRecordByOrderCode(order.getOrder_code(),order.getUser_id());
			if(couponList.size() == 0 && order.getStatus() == ShopOrderInfoBean.STATUS_WAIT)
			{	//如果没有，则找目前可用的优惠券
				couponList = supplierCouponService.queryUserCanUseCouponByOrderCode(order.getUser_id(), order.getOrder_code());
			}
			UserWechatBean user = userWechatService.findUserWechatById(order.getUser_id());
//			int expressBottomPrice = configAttributeService.getIntValueByCode(AttributeConstant.CODE_SHOP_ORDER_NEED_EXPRESS_BOTTOM_PRICE);
			SupplierBean supplier = supplierService.findSupplierById(supplier_id);
			
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("user", user);
			model.put("couponList", couponList);
			model.put("supplier", supplier);
//			model.put("expressBottomPrice", expressBottomPrice);
			
			return new ModelAndView(PAGE_SUPPLIER_SHOP_ORDER_EFF,model );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}


	/**
	 * 用户的商户产品套餐详情界面
	 * @return
	 */
	@RequestMapping("/user/supplier/toSupplierPackageProductListPage.html")
	public ModelAndView toSupplierPackageProductListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String openid = request.getParameter("openid");
			String sid = request.getParameter("sid");
			if(StringUtil.isNull(openid)) {
				openid = getCurrentOpenid();
			}
			
			SupplierPackageProductRecordBean bean = new SupplierPackageProductRecordBean();
			bean.setOpenid(openid);
			bean.setSupplier_id(Integer.parseInt(sid));
			List<SupplierPackageProductRecordBean> list = supplierPackageService.querySupplierPackageProductRecord(bean );
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			
			return new ModelAndView(PAGE_USER_SUPPLIERSHOP_PACKAGEPRODUCTLIST,model );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	


	/**
	 * 用户的商户储值卡/折扣卡详情界面
	 * @return
	 */
	@RequestMapping("/user/supplier/toSupplierStoreBalanceCardListPage.html")
	public ModelAndView toSupplierStoreBalanceCardListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String openid = request.getParameter("openid");
			String sid = request.getParameter("sid");
			String id = request.getParameter("id");
			Integer record_id = null; 
			if(StringUtil.isNull(openid)) {
				openid = getCurrentOpenid();
			}
			if(StringUtil.isNotNull(id)) {
				record_id = Integer.parseInt(id);
			}
			
			
			SupplierStoreBalanceCardRecordBean bean = new SupplierStoreBalanceCardRecordBean();
			bean.setOpenid(openid);
			bean.setSupplier_id(Integer.parseInt(sid));
			bean.setId(record_id);
			bean.setOrderby("order by id desc");
			List<SupplierStoreBalanceCardRecordBean> list = supplierStoreService.queryStoreBalanceCardRecord(bean);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			
			return new ModelAndView(PAGE_USER_SUPPLIERSHOP_STOREBALANCECARDLIST,model );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	
}
