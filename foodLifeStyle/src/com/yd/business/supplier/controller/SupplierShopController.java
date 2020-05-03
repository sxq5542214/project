package com.yd.business.supplier.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.order.bean.ShopOrderEffInfoBean;
import com.yd.business.order.bean.ShopOrderEffProductBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierProductCategoryBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.CookieUtil;
import com.yd.util.DateUtil;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author ice
 *
 */
@Controller
public class SupplierShopController extends BaseController {
	public static final String PAGE_SUPPLIERSHOPMANAGER_CATEGORY = "/page/supplier/shop/manager/category.jsp";
	public static final String PAGE_SUPPLIERSHOP_SHOPCATEGORY = "/page/supplier/shop/shopCategory.jsp";
	public static final String PAGE_SUPPLIER_SHOP_ORDER_EFF = "/page/supplier/shop/orderEffInfo.jsp";
	public static final String PAGE_SUPPLIERSHOPMANAGER_INDEX = "/page/supplier/shop/manager/index.jsp";


	@Autowired
	private ISupplierService supplierService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ISupplierProductService supplierProductService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private ISupplierPowerLogService supplierPowerLogService;
	@Autowired
	private IUserWechatService userWechatService;

	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IShopOrderService shopOrderService;
	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toMySupplierShopManagerPage.html")
	public ModelAndView toMySupplierShopManagerPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String openid = request.getParameter("openid");
			List<SupplierBean> list = supplierService.querySupplierByOpenid(openid);
			SupplierBean supplier = null;
			String modelView = null;
			switch (list.size()) {
			case 0:
				//没有注册过,到注册界面
				modelView = "redirect:/wechat/user/toDistributeControll.do?openid="+openid+"&conName=wx.supplier.toSupplierSignupPage";
				break;
			case 1:
				//注册过，只有一个，进入管理界面
				supplier = list.get(0);
				modelView = "redirect:/wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+supplier.getId();
				break;
			default:
				//注册过多个
				supplier = list.get(0);
				modelView = "redirect:/wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+supplier.getId();
				// 要改
				break;
			}
			
			return new ModelAndView(modelView);
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}

	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toSupplierShopPage.html")
	public ModelAndView toSupplierShopPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sid = request.getParameter("sid");
		String openid = getCurrentOpenid();
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}
			
			SupplierBean supplier = supplierService.findSupplierById(supplier_id);
			List<SupplierProductCategoryBean> productCategoryList = supplierProductService.querySupplierProductCategoryBySupplierId(supplier_id,SupplierProductCategoryBean.STATUS_YES);
			
			SupplierProductBean condition = new SupplierProductBean();
			condition.setSupplier_id(supplier_id);
			condition.setStatus(SupplierProductBean.STATUS_UP);
			condition.setDelete_flag(SupplierProductBean.DELETE_FLAG_NO);
			condition.setNow_time(DateUtil.getNowDateStr());
			condition.setOrderby(" order by pc.seq , product_category_id,sp.seq  asc ");
			List<SupplierProductBean> productList = supplierProductService.listSupplierProduct(condition );
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productCategoryList", productCategoryList);
			model.put("productList", productList);
			model.put("openid", openid);
			model.put("supplier", supplier);
			model.put("user", user);


			
			return new ModelAndView(PAGE_SUPPLIERSHOP_SHOPCATEGORY, model);			
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toManagerCategoryPage.html")
	public ModelAndView toManagerCategoryPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sid = request.getParameter("sid");
		String openid = request.getParameter("openid");
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}
			
			//判断openid 和 商户ID是否一致，不一致可能是被改了商户id
			SupplierBean supplier = supplierService.findSupplier(supplier_id,openid);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(supplier != null) {
				List<SupplierProductCategoryBean> productCategoryList = supplierProductService.querySupplierProductCategoryBySupplierId(supplier_id,SupplierProductCategoryBean.STATUS_YES);
				
				SupplierProductBean condition = new SupplierProductBean();
				condition.setSupplier_id(supplier_id);
				condition.setDelete_flag(SupplierProductBean.DELETE_FLAG_NO);
//				condition.setStatus(SupplierProductBean.STATUS_UP);
				condition.setNow_time(DateUtil.getNowDateStr());
				condition.setOrderby(" order by pc.seq , product_category_id,sp.seq  asc ");
				List<SupplierProductBean> productList = supplierProductService.listSupplierProduct(condition );
				
//					UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				Map<String, Object> model = new HashMap<String, Object>();
//					model.put("user", user);
				model.put("productCategoryList", productCategoryList);
				model.put("productList", productList);
				model.put("openid", openid);
				model.put("supplier", supplier);
				model.put("user", user);

				return new ModelAndView(PAGE_SUPPLIERSHOPMANAGER_CATEGORY, model);	
			}else {
				writeJson(response, "您没有权限访问他人的店铺信息");
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toShopManagerIndexPage.html")
	public ModelAndView toShopManagerIndexPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sid = request.getParameter("sid");
		String openid = request.getParameter("openid");
		if(StringUtil.isNull(openid)) {
			openid = getCurrentOpenid();
		}
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}
			
			//判断openid 和 商户ID是否一致，不一致可能是被改了商户id
			SupplierBean supplier = supplierService.findSupplier(supplier_id,openid);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(supplier != null) {
				

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("supplier", supplier);
				return new ModelAndView(PAGE_SUPPLIERSHOPMANAGER_INDEX, model );	
			}else {
				writeJson(response, "您没有权限访问他人的店铺信息");
			}
		}catch (Exception e) {
			log.error(e,e);
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
				ShopOrderInfoBean newOrder = shopOrderService.createOrderLogByUserCartList(openid,productInfos,time,eff_date);
				order = new ShopOrderEffInfoBean(newOrder);
			}else{//没有定单号、没有传参数据,则根据cookie里的数据创建订单
				String data = CookieUtil.getValueByCookie(request, "productInfo");
				ShopOrderInfoBean newOrder =  shopOrderService.createOrderLogByUserCartList(openid,data,time,eff_date);
				order = new ShopOrderEffInfoBean(newOrder);
			}
			
			// 找之前已经选择过的优惠卷
			List<SupplierCouponRecordBean> couponList = supplierCouponService.queryCouponRecordByOrderCode(order.getOrder_code(),order.getUser_id());
			if(couponList.size() == 0 && order.getStatus() == ShopOrderInfoBean.STATUS_WAIT)
			{	//如果没有，则找目前可用的优惠卷
				couponList = supplierCouponService.queryUserCanUseCouponByOrderCode(order.getUser_id(), order.getOrder_code());
			}
			UserWechatBean user = userWechatService.findUserWechatById(order.getUser_id());
//			int expressBottomPrice = configAttributeService.getIntValueByCode(AttributeConstant.CODE_SHOP_ORDER_NEED_EXPRESS_BOTTOM_PRICE);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("user", user);
			model.put("couponList", couponList);
//			model.put("expressBottomPrice", expressBottomPrice);
			
			return new ModelAndView(PAGE_SUPPLIER_SHOP_ORDER_EFF,model );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**order/shop/updateOrderEffDate.html")
	public ModelAndView updateOrderEffDate(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String orderCode = request.getParameter("orderCode");
			String effDate = request.getParameter("effDate");
			String remark = request.getParameter("remark");
			String contactName = request.getParameter("contact_name");
			String contactPhone = request.getParameter("contact_phone");
			
			boolean flag = shopOrderService.updateShopOrderToEff(orderCode, effDate,openid,remark,contactName,contactPhone);
			
			if(flag) {
				writeJson(response, "success");
			}else {
				writeJson(response, "error");
			}

		} catch (Exception e) {
			log.error(e,e);
		}
		
		return null;
	}
	

	@RequestMapping("**order/shop/cancelOrderEffDate.html")
	public ModelAndView cancelOrderEffDate(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String orderCode = request.getParameter("orderCode");
			String remark = request.getParameter("remark");
			
			shopOrderService.updateOrderStatusToCancel(orderCode, openid, remark);
			
			writeJson(response, "success");

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	/**
	 * 查询近期订单数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/shop/ajaxShopEffOrderLatelyData.html")
	public ModelAndView ajaxShopEffOrderLatelyData(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String orderCode = request.getParameter("orderCode");
			String remark = request.getParameter("remark");
			
			shopOrderService.updateOrderStatusToCancel(orderCode, openid, remark);
			
			writeJson(response, "success");

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
}
