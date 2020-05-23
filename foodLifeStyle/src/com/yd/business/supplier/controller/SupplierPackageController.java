package com.yd.business.supplier.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.yd.basic.framework.context.WebContext;
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
import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierProductCategoryBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierPackageService;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.util.WechatConstant;
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
public class SupplierPackageController extends BaseController {
	public static final String PAGE_SUPPLIERPACKAGE_MANAGER = "/page/supplier/shop/manager/package/packageList.jsp";
	public static final String PAGE_SUPPLIERPACKAGE_ADDPACKAGE = "/page/supplier/shop/manager/package/addOrUpdatePackage.jsp";
	public static final String PAGE_SUPPLIERPACKAGE_ASSIGNUSER = "/page/supplier/shop/manager/package/packageAssignUser.jsp";
	public static final String PAGE_SUPPLIERPACKAGE_USERPACKAGEMANAGER = "/page/supplier/shop/manager/package/userPackageManager.jsp";
	public static final String PAGE_SUPPLIERPACKAGE_USERPACKAGEUPDATE = "/page/supplier/shop/manager/package/userPackageUpdate.jsp";


	@Autowired
	private ISupplierService supplierService;
	@Autowired
	private ISupplierPackageService supplierPackageService;
	@Autowired
	private ISupplierProductService supplierProductService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private ISupplierPowerLogService supplierPowerLogService;
	@Autowired
	private IUserWechatService userWechatService;
	@Resource
	private ISupplierUserService supplierUserService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IShopOrderService shopOrderService;
	
	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/package/toSupplierPackageManagerPage.html")
	public ModelAndView toSupplierPackageManagerPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			SupplierBean supplier = getCurrentSupplier();
			
			SupplierPackageBean bean = new SupplierPackageBean();
			bean.setSupplier_id(supplier.getId());
			bean.setOrderby(" order by create_time desc");
			
			List<SupplierPackageBean> listPackage = supplierPackageService.querySupplierPackage(bean );
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("listPackage", listPackage);
			return new ModelAndView(PAGE_SUPPLIERPACKAGE_MANAGER, map );

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/package/toAddSupplierPackagPage.html")
	public ModelAndView toAddSupplierPackagPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String id = request.getParameter("id");
			List<SupplierPackageProductBean> ppList = Collections.EMPTY_LIST;
			
			SupplierPackageBean pack = null;
			if(StringUtil.isNotNull(id)) {
				pack = supplierPackageService.findSupplierPackageById(Integer.parseInt(id), supplier.getId());
				
				SupplierPackageProductBean pp = new SupplierPackageProductBean();
				pp.setSupplier_package_id(pack.getId());
				ppList = supplierPackageService.querySupplierPackageProduct(pp );
			}
			
			SupplierProductBean prod = new SupplierProductBean();
			prod.setSupplier_id(supplier.getId());
			prod.setDelete_flag(SupplierProductBean.DELETE_FLAG_NO);
			prod.setOrderby(" order by product_category_id asc");
			List<SupplierProductBean> listProduct = supplierProductService.listSupplierProduct(prod );
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("listProduct", listProduct);
			map.put("pack", pack);
			map.put("ppList", ppList);
			
			return new ModelAndView(PAGE_SUPPLIERPACKAGE_ADDPACKAGE,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	
	/**
	 * 	添加或修改产品套餐
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/package/createOrUpdateSupplierPackage.html")
	public ModelAndView createOrUpdateSupplierPackage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String id = request.getParameter("id");
			String[] numList = request.getParameterValues("num");
			String[] productList = request.getParameterValues("products");
			String product_price = request.getParameter("product_price");
			String product_real_price = request.getParameter("product_real_price");
			String prime_cost_price = request.getParameter("prime_cost_price");
			
			SupplierPackageBean pack = new SupplierPackageBean();
			
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), pack);
			pack.setSupplier_id(supplier.getId());
			pack.setProduct_price(NumberUtil.multiply100(product_price));
			pack.setProduct_real_price(NumberUtil.multiply100(product_real_price));
			pack.setPrime_cost_price(NumberUtil.multiply100(prime_cost_price));
			if(StringUtil.isNotNull(id)) {
				supplierPackageService.updatePackageAndProducts(pack, productList, numList);
			}else {
				supplierPackageService.createSupplierPackageAndProducts(pack, productList, numList);
			}
			
			return new ModelAndView("redirect:/supplier/package/toSupplierPackageManagerPage.html");
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}


	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/package/toPackageAssignUserPage.html")
	public ModelAndView toPackageAssignUserPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String packageid = request.getParameter("packageid");
			
			SupplierPackageBean pack = null;
			List<SupplierUserBean> userList = Collections.EMPTY_LIST;
			if(StringUtil.isNotNull(packageid)) {
				pack = supplierPackageService.findSupplierPackageById(Integer.parseInt(packageid), supplier.getId());
				
				SupplierUserBean userBean = new SupplierUserBean();
				userBean.setSupplier_id(supplier.getId());
				userBean.setOrderby(" order by last_access_time desc");
				userList = supplierUserService.querySupplierUser(userBean );
				
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pack", pack);
			map.put("userList", userList);
			
			return new ModelAndView(PAGE_SUPPLIERPACKAGE_ASSIGNUSER,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	
	


	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/package/packageAssignUser.html")
	public ModelAndView packageAssignUser(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String packageid = request.getParameter("supplier_package_id");
			String user_id = request.getParameter("user_id");
			String nick_name = request.getParameter("nick_name");
			String phone = request.getParameter("phone");
			
			
			supplierPackageService.createSupplierPackageProductRecord(Integer.parseInt(user_id),Integer.parseInt(packageid),supplier.getId());
			
			//更新用户昵称和手机号
			SupplierUserBean spUser = supplierUserService.findSupplierUser(Integer.parseInt(user_id), supplier.getId());
			if(StringUtil.isNotNull(phone)){
				spUser.setPhone(phone);
			}
			if(StringUtil.isNotNull(nick_name)){
				spUser.setNick_name(nick_name);
			}
			supplierUserService.updateSupplierUser(spUser);
			
			return new ModelAndView("redirect:/supplier/package/toUserPackageManagerPage.html?openid=" + spUser.getOpenid());
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	

	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/package/toUserPackageManagerPage.html")
	public ModelAndView toUserPackageManagerPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String openid = request.getParameter("openid");
			
			SupplierUserBean spUser = supplierUserService.findSupplierUser(openid, supplier.getId());
			
			SupplierPackageProductRecordBean condition = new SupplierPackageProductRecordBean();
			condition.setSupplier_id(supplier.getId());
			condition.setOpenid(openid);
			condition.setOrderby(" and num != 0  order by id");
			List<SupplierPackageProductRecordBean> spprList = supplierPackageService.querySupplierPackageProductRecord(condition );
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("spprList", spprList);
			map.put("spUser", spUser);
			
			return new ModelAndView(PAGE_SUPPLIERPACKAGE_USERPACKAGEUPDATE,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	
	@RequestMapping("supplier/package/ajax/updatePackageNum.html")
	public ModelAndView updatePackageNum(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String recordId = request.getParameter("recordId");
			String num = request.getParameter("num");
			SupplierBean supplier = getCurrentSupplier();
			
			boolean flag = supplierPackageService.updatePackageProductRecordNum(Integer.parseInt(recordId),Integer.parseInt(num),supplier);
			
			if(flag) {
				writeJson(response, "success");
			}
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
}
