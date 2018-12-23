/**
 * 
 */
package com.yd.business.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.FileUploadUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 * 
 */
@Controller
public class ProductController extends BaseController {
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

	public static final String PRODUCT_UPDATE_PAGE = "/page/pc/product/product_edit.jsp";
	public static final String PRODUCT_DESC_SHOW_PAGE = "/page/shop/product_show.jsp";
	
	
	/**
	 * 获取可售商品列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/product.do")
	public ModelAndView toProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, List<ProductBean>> m = new HashMap<String, List<ProductBean>>();
			List<ProductTypeBean> typeList = productTypeService.listProductType(null);
			for (int i = 0; i < typeList.size(); i++) {
				ProductBean bean = new ProductBean();
				bean.setStatus(ProductBean.STATUS_UP);
				bean.setProduct_brand(typeList.get(i).getId());
				List<ProductBean> list = productService.listMealPro(bean);
				if (list != null && list.size() > 0)
					m.put(typeList.get(i).getName(), list);
			}
			map.put("list", m);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e, e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_mailgood.jsp", map);
	}

	/***************************************************** Product *******************************************************/
	/**
	 * 加载商品列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/product/admin/productlist.do")
	public ModelAndView toProductListByType(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String[] ids = request.getParameter("ids").split(",");
			if (ids != null && ids.length > 0) {
				List<Integer> map = new ArrayList<Integer>();
				for (int i = 0; i < ids.length; i++) {
					map.add(NumberUtil.toInt(ids[i]));
				}
				Map<String, List<Integer>> params = new HashMap<String, List<Integer>>();
				params.put("ids", map);
				List<ProductBean> list = productService.listProductByIds(params);
				writeJson(response, list);
			} else
				writeJson(response, "请选择商品品牌！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}

	/**
	 * 跳转至商品管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/productmgr.do")
	public ModelAndView toProductMgr(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SupplierProductBean bean = new SupplierProductBean();
			bean.setCustomer_id(customerService.getUserId());
			List<SupplierProductBean> list = supplierProductService.listSupplierProduct(bean);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			writeJson(response, "授权取消失败！");
		}
		return new ModelAndView("/page/pc/iframe_productmgr.jsp", map);
	}

	/**
	 * 基础商品列表界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/product/toProductListPage.do")
	public ModelAndView toProductListPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ProductBean> list = productService.listProduct(null);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			writeJson(response, "基础获取商品列表失败！");
		}
		return new ModelAndView("/page/pc/product/product_list.jsp", map);
	}

	/**
	 * 删除基础商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/product/deleteProduct.do")
	public ModelAndView deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {

			String id = request.getParameter("id");

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			writeJson(response, 0);
		}
		writeJson(response, 1);
		return null;
	}

	/**
	 * 跳转至基础商品修改界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/product/toUpdateProductPage.do")
	public ModelAndView toUpdateProductPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ProductBean bean = null;
			String id = request.getParameter("id");
			if (StringUtil.isNull(id)) {
				bean = new ProductBean();
			} else {
				bean = productService.findProductById(Integer.parseInt(id));
			}
			ProductTypeBean type = new ProductTypeBean();
			type.setType(ProductTypeBean.TYPE_BRA);
			List<ProductTypeBean> brandList = productTypeService.listProductType(type);

			type.setType(ProductTypeBean.TYPE_CLS);
			List<ProductTypeBean> typeList = productTypeService.listProductType(type);

			model.put("bean", bean);
			model.put("brandList", brandList);
			model.put("typeList", typeList);
			return new ModelAndView(PRODUCT_UPDATE_PAGE, model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return null;
	}

	/**
	 * 修改商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/product/updateProduct.do")
	public ModelAndView updateProduct(MultipartHttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {

			Map<String, String> param = getRequestParamsMap(request);
			ProductBean bean = new ProductBean();
			AutoInvokeGetSetMethod.autoInvoke(param, bean);

			String serverUrl = BaseContext.getServerUrl();

			String imageName = getImgName();
			String path = appendPath(PATH_FOLDER, "thumb_img") + "\\" + imageName;
			boolean hasUpload = FileUploadUtil.isExists(request);
			if (hasUpload) {
				bean.setHead_img(serverUrl + getImgPath("thumb_img", imageName));
			}

			productService.createOrUpdateProduct(bean);
			
			if(hasUpload){
				FileUploadUtil.imgFileUpload(request, response, path, appendPath(PATH_FOLDER, "thumb_img"));
			}
			return new ModelAndView("redirect:/admin/product/toProductListPage.do");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("/product/showProductDescById.do")
	public ModelAndView showProductDescById(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String id = request.getParameter("id");
			
			ProductBean product = productService.findProductById(Integer.parseInt(id));
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("bean", product);
			return new ModelAndView(PRODUCT_DESC_SHOW_PAGE, param);
			
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

}
