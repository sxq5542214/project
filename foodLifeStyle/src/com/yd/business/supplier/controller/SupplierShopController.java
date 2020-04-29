package com.yd.business.supplier.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.AutoInvokeGetSetMethod;
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
	public static final String PAGE_SUPPLIERSHOP_CATEGORY = "/page/supplier/shop/category.jsp";

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

	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/supplier/shop/toMySupplierShopPage.html")
	public ModelAndView toMySupplierShopPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String openid = request.getParameter("openid");
			
			
			
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
			List<ProductTypeBean> productTypeList = productTypeService.listProductTypeByCustomerId(supplier.getCustomer_id());
			
			
			SupplierProductBean condition = new SupplierProductBean();
			condition.setCustomer_id(CustomerBean.ID_PLATFROM);
			condition.setStatus(SupplierProductBean.STATUS_UP);
			condition.setNow_time(DateUtil.getNowDateStr());
			condition.setHome_flag(SupplierProductBean.HOME_FLAG_YES);
			condition.setOrderby(" order by product_type,seq  asc ");
			List<SupplierProductBean> productList = supplierProductService.listSupplierProduct(condition );
			
			
//				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			Map<String, Object> model = new HashMap<String, Object>();
			model = new HashMap<String, Object>();
//				model.put("user", user);
			model.put("productTypeList", productTypeList);
			model.put("productList", productList);
			model.put("openid", openid);

			
			return new ModelAndView(PAGE_SUPPLIERSHOP_CATEGORY, model);			
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
}
