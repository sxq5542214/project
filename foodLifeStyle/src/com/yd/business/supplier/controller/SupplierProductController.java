/**
 * 
 */
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

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.image.bean.ImageBean;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierProductCategoryBean;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.ImageUtils;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class SupplierProductController extends BaseController {
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private ICustomerAdminService customerAdminService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private IProductService productService;
	@Resource
	private IProductTypeService productTypeService;
	@Resource
	private ICustomerService customerService;
	
	private String PAGE_SUPPLIERORDERCONFIRM = "/page/supplier/orderConfirm.jsp";
	private String SUPPLIER_PRODUCT_UPDATE_PAGE = "/page/pc/product/supplier_product_edit.jsp";
	private String PAGE_SUPPLIER_SHOP_MANAGER_ADDORUPDATECATEGORY = "/page/supplier/shop/manager/addOrUpdateCategory.jsp";
	private String PAGE_SUPPLIER_SHOP_MANAGER_ADDORUPDATEPRODUCT = "/page/supplier/shop/manager/addOrUpdateProduct.jsp";
	/**
	 * 根据客户id查询客户所有的品牌信息
	 * @throws IOException 
	 */
	@RequestMapping("/supplierProduct/admin/queryProductTypeByCustid.do")
	public ModelAndView queryProductTypeByCustomerId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			List<ProductTypeBean> list = productTypeService.listProductTypeByCustomerId(customerService.getUserId());
			writeJson(response, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}
	
	/**
	 * 客户端查询商户商品
	 */
	@RequestMapping("/supplierProduct/querySupplierProduct.do")
	public ModelAndView querySupplierProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			int customerid = 3;
			List<CustomerSupplierProductBean> list= supplierProductService.queryCustomerSupplierProduct(customerid);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("adminid", 1);
			map.put("customerid", customerid);
			
			return new ModelAndView("/page/supplier/orderProduct.jsp", map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}

	/**
	 * 创建或修改商品分类
	 */
	@RequestMapping("/supplierProduct/createOrUpdateProductCategory.html")
	public ModelAndView createOrUpdateProductCategory(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid =request.getParameter("openid");
			String sid =request.getParameter("sid");
			String id =request.getParameter("id");
			String name =request.getParameter("name");
			String seq =request.getParameter("seq");
			
			SupplierProductCategoryBean bean = new SupplierProductCategoryBean();
			bean.setName(name);
			bean.setSeq(Integer.parseInt(seq));
			bean.setSupplier_id(Integer.parseInt(sid));
			if(StringUtil.isNotNull(id)) {
				bean.setId(Integer.parseInt(id));
				supplierProductService.updateSupplierProductCategory(bean);
			}else {
				supplierProductService.createSupplierProductCategory(bean);
			}
			return new ModelAndView("redirect:/wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+sid);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}

	/**
	 * 创建或修改商品分类
	 */
	@RequestMapping("/supplierProduct/createOrUpdateProduct.html")
	public ModelAndView createOrUpdateProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid =request.getParameter("openid");
			String sid =request.getParameter("sid");
			String id = request.getParameter("id");
			String product_category_id = request.getParameter("product_category_id");
			String product_name = request.getParameter("product_name");
			String product_title = request.getParameter("product_title");
			String product_price = request.getParameter("product_price");
			String product_real_price = request.getParameter("product_real_price");
			String prime_cost_price = request.getParameter("prime_cost_price");
			String seq = request.getParameter("seq");
			String status = request.getParameter("status");
			
			//压缩并写入图片到磁盘
			int width = 100;
			int height = 100;
			String tempDir = ImageBean.THUMB_IMG_DIR + sid + "/";
			String targetDir = request.getServletContext().getRealPath("/")+ "/" + tempDir;
			String targetFileSrc = ImageUtils.uploadFileByRequest(request, targetDir, width, height);
			String accessFileURL = null;
			if(targetFileSrc != null) { //有上传文件才执行
				String fileName = targetFileSrc.substring(targetFileSrc.lastIndexOf("/")+1);
				accessFileURL = tempDir + fileName;
			}
			
			SupplierProductCategoryBean category = supplierProductService.findSupplierProductCategoryById(Integer.parseInt(product_category_id));
			SupplierBean supplier = supplierService.findSupplierById(Integer.parseInt(sid));
			SupplierProductBean bean = new SupplierProductBean();
			
			bean.setStatus(Integer.parseInt(status));
			bean.setSeq(Integer.parseInt(seq));
			bean.setProduct_name(product_name);
			bean.setProduct_title(product_title);
			bean.setProduct_price(Integer.parseInt(product_price));
			if(StringUtil.isNotNull(product_real_price)) {
				bean.setProduct_real_price(Integer.parseInt(product_real_price));
			}
			if(StringUtil.isNotNull(prime_cost_price)) {
				bean.setPrime_cost_price(Integer.parseInt(prime_cost_price));
			}else {
				bean.setPrime_cost_price(-1);
			}
			bean.setProduct_category_id(category.getId());
			bean.setProduct_category_name(category.getName());
			bean.setSupplier_id(supplier.getId());
			bean.setSupplier_name(supplier.getName());
			bean.setCustomer_id(supplier.getCustomer_id());
			bean.setDelete_flag(SupplierProductBean.DELETE_FLAG_NO);
			bean.setProduct_img(accessFileURL);
			
			if(StringUtil.isNotNull(id)) {
				bean.setId(Integer.parseInt(id));
				supplierProductService.updateSupplierProduct(bean);
			}else {
				bean.setStore_num(99999);
				bean.setDiscount(100);
				bean.setProduct_offset_points(0);
				bean.setMin_luckymoney(0);
				bean.setMax_luckymoney(0);
				bean.setEff_time(DateUtil.getNowDateStr());
				bean.setDff_time("2099-12-31");
				supplierProductService.insertSupplierProduct(bean);
			}
			return new ModelAndView("redirect:/wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+sid);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	/**
	 * 客户端查询商户商品
	 */
	@RequestMapping("/wx/supplierProduct/deleteProductCategory.html")
	public ModelAndView deleteProductCategory(HttpServletRequest request,HttpServletResponse response){
		try{
			String id =request.getParameter("id");
			String openid =request.getParameter("openid");
			String sid =request.getParameter("sid");
			if(StringUtil.isNotNull(id)) {
				SupplierProductCategoryBean bean = supplierProductService.findSupplierProductCategoryById(Integer.parseInt(id));
				bean.setStatus(SupplierProductCategoryBean.STATUS_NO);
				supplierProductService.updateSupplierProductCategory(bean);
			}
			return new ModelAndView("/wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+sid);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 新增或修改商品分类
	 */
	@RequestMapping("/supplierProduct/toCreateOrUpdateProductCategoryPage.html")
	public ModelAndView toCreateOrUpdateProductCategoryPage(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid =request.getParameter("openid");
			String sid =request.getParameter("sid");
			int supplier_id = Integer.parseInt(sid);
			List<SupplierProductCategoryBean> productCategoryList = supplierProductService.querySupplierProductCategoryBySupplierId(supplier_id ,SupplierProductCategoryBean.STATUS_YES);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("productCategoryList", productCategoryList);
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_ADDORUPDATECATEGORY ,map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 新增或修改商品信息
	 */
	@RequestMapping("/supplierProduct/toCreateOrUpdateProductPage.html")
	public ModelAndView toCreateOrUpdateProductPage(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid =request.getParameter("openid");
			String sid =request.getParameter("sid");
			String id =request.getParameter("id");
			int supplier_id = Integer.parseInt(sid);
			
			SupplierProductBean bean = null;
			if(StringUtil.isNotNull(id)) {
				bean = supplierProductService.findSupplierProductById(Integer.parseInt(id));
			}
			List<SupplierProductCategoryBean> productCategoryList = supplierProductService.querySupplierProductCategoryBySupplierId(supplier_id ,SupplierProductCategoryBean.STATUS_YES);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("productCategoryList", productCategoryList);
			map.put("bean", bean);
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_ADDORUPDATEPRODUCT ,map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}

	@RequestMapping("**/supplierProduct/queryProductByPhone.do")
	public ModelAndView queryProductByPhone(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String customer_id = request.getParameter("customer_id");
			String phone = request.getParameter("phone");
			List<CustomerSupplierProductBean> list = supplierProductService.queryCustomerProductByPhone(Integer.parseInt(customer_id), phone);
			
			if(list == null || list.size() != 0)
			{
				writeJson(response, list);
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	

	@RequestMapping("**/supplierProduct/toOrderConfirm.do")
	public ModelAndView toOrderConfirm(String phone,String spid,String adminid){
		try{
			
			CustomerAdminBean admin = customerAdminService.findCustomerAdminById(Integer.parseInt(adminid));
			
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(Integer.parseInt(spid));
			SupplierBean supplier = supplierService.findSupplierById(sp.getSupplier_id());
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("admin", admin);
			model.put("supplierProduct", sp);
			model.put("phone", phone);
			model.put("supplier", supplier);
			
			return new ModelAndView(PAGE_SUPPLIERORDERCONFIRM, model);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	

	/**
	 * 商户商品列表界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/supplierProduct/toSupplierProductListPage.do")
	public ModelAndView toSupplierProductListPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerBean customer = getCurrentLoginUser();
			
			SupplierProductBean bean = new SupplierProductBean();
			bean.setCustomer_id( customer.getId());
			
			List<SupplierProductBean> list = supplierProductService.listSupplierProduct(bean );
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			writeJson(response, "基础可售商品列表失败！");
		}
		return new ModelAndView("/page/pc/product/supplier_product_list.jsp", map);
	}
	
	

	/**
	 * 跳转至基础商品修改界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/supplierProduct/toUpdateSupplierProductPage.do")
	public ModelAndView toUpdateSupplierProductPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			SupplierProductBean bean = null;
			String id = request.getParameter("id");
			if (StringUtil.isNull(id)) {
				bean = new SupplierProductBean();
				bean.setDiscount(100);
				String date = DateUtil.getNowDateStr();
				bean.setEff_time(date);
				bean.setDff_time(date);
				bean.setMin_luckymoney(0);
				bean.setMax_luckymoney(0);
				bean.setProduct_offset_points(0);
				bean.setStore_num(0);
				bean.setPrime_cost_price(0);
			} else {
				bean = supplierProductService.findSupplierProductById(Integer.parseInt(id));
			}
			
			List<ProductBean> productList = productService.listProduct(null);
			
			
			CustomerBean user = getCurrentLoginUser();
			SupplierBean supplierBean = new SupplierBean();
			supplierBean.setCustomer_id(user.getId());
			List<SupplierBean> supplierList = supplierService.listSupplier(supplierBean );

			model.put("bean", bean);
			model.put("productList", productList);
			model.put("supplierList", supplierList);
			return new ModelAndView(SUPPLIER_PRODUCT_UPDATE_PAGE , model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("/admin/supplierProduct/updateSupplierProduct.do")
	public ModelAndView updateSupplierProduct(HttpServletRequest request,HttpServletResponse response){
		try {
			
			Map<String,String> param = getRequestParamsMap(request);
			SupplierProductBean bean = new SupplierProductBean();
			AutoInvokeGetSetMethod.autoInvoke(param, bean);
			
			CustomerBean user = getCurrentLoginUser();
			
			bean.setCustomer_id(user.getId());
			supplierProductService.createOrUpdateSupplierProduct(bean);
			
			return toSupplierProductListPage(request, response);
		} catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}
	
	

	/**
	 * 客户端查询商户商品
	 */
	@RequestMapping("/supplierProduct/toSupplierProductShopList.do")
	public ModelAndView toSupplierProductShopList(HttpServletRequest request,HttpServletResponse response){
		try{
			SupplierProductBean bean = new SupplierProductBean();
			
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), bean);
			bean.setCustomer_id(CustomerBean.ID_PLATFROM);
			
			List<SupplierProductBean> list= supplierProductService.listSupplierProduct(bean );
			
//			ProductTypeBean type = new ProductTypeBean();
//			type.setType(ProductTypeBean.TYPE_CLS);
			List<ProductTypeBean> typeList = productTypeService.listProductType(null );
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("typeList", typeList);
			
			return new ModelAndView("/page/shop/list/product_list.jsp", map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	/**
	 * 客户端查询商户商品
	 */
	@RequestMapping("/product/supplierProduct/toSupplierProductShopInfo.do")
	public ModelAndView toSupplierProductShopInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			String id = request.getParameter("id");
			String openid = request.getParameter("openid");
			if(id == null){
				writeJson(response , "<script>alert('界面加载出错！请重新打开界面')</script>");
				return null;
			}
			SupplierProductBean bean = supplierProductService.findSupplierProductById(Integer.parseInt(id));
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("bean", bean);
			map.put("openid", openid);
			
			return new ModelAndView("/page/shop/product/product_info.jsp", map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	
	
}
