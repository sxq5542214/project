package com.yd.business.order.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.order.bean.PartnerApplyOrderBean;
import com.yd.business.order.bean.PartnerApplyOrderProductBean;
import com.yd.business.order.service.IPartnerApplyOrderProductService;
import com.yd.business.order.service.IPartnerApplyOrderService;
import com.yd.business.product.bean.PartnerProductBean;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IPartnerProductService;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.IProductTypeService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.FileUploadUtil;
import com.yd.util.NumberUtil;
import com.yd.util.UUID;
@Controller
public class PartnerOrderController extends BaseController {
	@Resource
	private IPartnerApplyOrderService partnerApplyOrderService;
	@Resource
	IPartnerApplyOrderProductService partnerApplyOrderProductService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private IProductService productService;
	@Resource
	private IProductTypeService productTypeService;
	@Resource
	private IPartnerProductService partnerProductService;
	// 保存文件的目录
	private static String PATH_FOLDER = "/";

	@Override
	protected void initServletContext(ServletContext servletContext) {
		// 保存文件的目录
		PATH_FOLDER = servletContext.getRealPath("/images");
		// TODO Auto-generated method stub
		super.initServletContext(servletContext);
	}
	/**
	 * 重新申请api
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/aginapi.do")
	public ModelAndView toAginApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			customerService.getUser().setAuditstatus(null);
			map.put("user", customerService.getUser());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据错误！");
		}
		return new ModelAndView("/page/pc/iframe_api.jsp",map);
	}
	/**
	 * 申请界面打开
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/api.do")
	public ModelAndView toApi(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			CustomerBean bean = customerService.findCustomerById(customerService.getUserId());
			customerService.getUser().setAuditstatus(bean.getAuditstatus());
			map.put("user", bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据错误！");
		}
		return new ModelAndView("/page/pc/iframe_api.jsp",map);
	}
	/**
	 * API申请
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/api/admin/apply.do", method = RequestMethod.POST)
	public ModelAndView apiApply(HttpServletRequest req,HttpServletResponse response) throws IOException{
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
		String img_url = getImgName();
		String result = "";
		try {
			String corporate = request.getParameter("corporate");
			String cardno = request.getParameter("cardno");
			String corporatephone = request.getParameter("corporatephone");
			String companyno = request.getParameter("companyno");
			String companyexpire = request.getParameter("companyexpire");
			String companyaddress = request.getParameter("companyaddress");
			String companycover = request.getParameter("companycover");
			
			if(NumberUtil.empty(corporate)){
				result = "公司法人信息不能为空";
			}else if(NumberUtil.empty(cardno)){
				result = "公司法人身份证号码不能为空";
			}else if(NumberUtil.empty(corporatephone)){
				result = "公司法人联系号码不能为空";
			}else if(NumberUtil.empty(companyno)){
				result = "公司注册号不能为空";
			}else if(NumberUtil.empty(companyexpire)){
				result = "公司有效期不能为空";
			}else if(NumberUtil.empty(companyaddress)){
				result = "公司地址不能为空";
			}else if(NumberUtil.empty(companycover)){
				result = "公司业务范围不能为空";
			}else{
				String path_cf = getImgPath("thumb_img", "cf_"+img_url);
				String path_cl = getImgPath("thumb_img", "cl_"+img_url);
				String path_cpl = getImgPath("thumb_img", "cpl_"+img_url);
				
				FileUploadUtil.imgFileUpload(request, response,
						appendPath(PATH_FOLDER, "thumb_img") + "/" + "cf_"+img_url,
						appendPath(PATH_FOLDER, "thumb_img"),"cardfirst");
				FileUploadUtil.imgFileUpload(request, response,
						appendPath(PATH_FOLDER, "thumb_img") + "/" + "cl_"+img_url,
						appendPath(PATH_FOLDER, "thumb_img"),"cardlast");
				FileUploadUtil.imgFileUpload(request, response,
						appendPath(PATH_FOLDER, "thumb_img") + "/" + "cpl_"+img_url,
						appendPath(PATH_FOLDER, "thumb_img"),"companylic");
				
				CustomerBean bean = new CustomerBean();
				bean.setId(customerService.getUserId());
				bean.setAuditstatus(CustomerBean.AUDITSTATUS_WSH);
				bean.setCompany_legal_idcard_positive(path_cf);
				bean.setCompany_legal_idcard_back(path_cl);
				bean.setCompany_business_license(path_cpl);
				bean.setCompany_legal_name(corporate);
				bean.setCompany_legal_idcard(cardno);
				bean.setCompany_legal_phone(corporatephone);
				bean.setCompany_registcode(companyno);
				bean.setCompany_dff_date(companyexpire);
				bean.setCompany_address(companyaddress);
				bean.setCompany_scope(companycover);
				bean.setModify_time(DateUtil.getNowDateStr());
				customerService.updateCustomer(bean);
				result = "申请提交成功！";
			}
			
			writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据提交错误，请联系平台");
		}
		return null;
	}
	/**
	 * api申请列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/applylist.do")
	public ModelAndView toApiApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PartnerApplyOrderBean bean = new PartnerApplyOrderBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setApply_customer_id(customerService.getUserId());
			List<PartnerApplyOrderBean> list = partnerApplyOrderService.listPartnerApplyOrder(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_apilist.jsp",map);
	}
	/**
	 * api 商品申请
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/proApply.do")
	public ModelAndView toProApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, List<ProductBean>> m = new HashMap<String, List<ProductBean>>();
			List<ProductTypeBean> typeList = productTypeService.listProductType(null);
			for (int i = 0; i < typeList.size(); i++) {
				ProductBean bean = new ProductBean();
				bean.setStatus(ProductBean.STATUS_UP);
				bean.setProduct_brand(typeList.get(i).getId());
				List<ProductBean> list = productService.listMealPro(bean);
				if(list!=null&&list.size()>0) m.put(typeList.get(i).getName(), list);
			}
			map.put("list", m);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_apiproduct.jsp",map);
	}
	/**
	 * api 商品申请提交
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/admin/commitApiProApply.do")
	public ModelAndView commitProApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			JSONArray array = new JSONArray(request.getParameter("params"));
			if(array.length()>0){
				//第一步生成工单
				PartnerApplyOrderBean orderBean = new PartnerApplyOrderBean();
				orderBean.setCode("APA"+UUID.code());
				orderBean.setName(UUID.name(customerService.getUser().getName()));
				orderBean.setCreate_time(DateUtil.getNowDateStr());
				orderBean.setStatus(PartnerApplyOrderBean.STATUS_WSH);
				orderBean.setApply_customer_id(customerService.getUserId());
				orderBean.setApply_customer_name(customerService.getUser().getName());
				partnerApplyOrderService.insertPartnerApplyOrder(orderBean);
				int orderid = orderBean.getId();
				for (int i = 0; i < array.length(); i++) {
					JSONObject jso = array.getJSONObject(i);
					PartnerApplyOrderProductBean bean = new PartnerApplyOrderProductBean();
					bean.setOrderid(orderid);
					bean.setProduct_id(NumberUtil.toInt(jso.get("id")));
					bean.setProduct_name(NumberUtil.toString(jso.get("name")));
					bean.setStatus(PartnerApplyOrderProductBean.STATUS_W);
					bean.setComments(NumberUtil.toString(jso.get("comments")));
					partnerApplyOrderProductService.insertPartnerApplyOrderProduct(bean);
				}
			}else writeJson(response, "请选择一个商品后才可提交！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}
	/**
	 * 审核商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/admin/auditApiProApply.do")
	public ModelAndView auditProApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			
			PartnerApplyOrderProductBean bean = new PartnerApplyOrderProductBean();
			bean.setStatus(NumberUtil.toInt(request.getParameter("status")));
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			bean.setComments(NumberUtil.toString(request.getParameter("comments")));
			partnerApplyOrderProductService.updatePartnerApplyOrderProduct(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}
	/**
	 * 审核工单
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/admin/auditApiOrderApply.do")
	public ModelAndView auditOrderApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			int id = NumberUtil.toInt(request.getParameter("id"));
			PartnerApplyOrderBean bean = new PartnerApplyOrderBean();
			bean.setId(id);
			bean.setAudit_time(DateUtil.getNowDateStr());
			bean.setStatus(NumberUtil.toInt(request.getParameter("status")));
			PartnerApplyOrderProductBean b = new PartnerApplyOrderProductBean();
			b.setStatus(PartnerApplyOrderProductBean.STATUS_W);
			b.setOrderid(id);
			List<PartnerApplyOrderProductBean> list = partnerApplyOrderProductService.listPartnerApplyOrderProduct(b);
			if(list!=null&&list.size()>0){
				String result = "";
				for (int i = 0; i < list.size(); i++) {
					result += list.get(i).getProduct_name()+",";
				}
				writeJson(response, result+"这些商品未审核，请审核后在提交！");
			}else {
				PartnerApplyOrderProductBean bn = new PartnerApplyOrderProductBean();
				bn.setOrderid(id);
				bn.setStatus(PartnerApplyOrderProductBean.STATUS_Y);
				List<PartnerApplyOrderProductBean> listPro = partnerApplyOrderProductService.listPartnerApplyOrderProduct(bn);
				for (int i = 0; i < listPro.size(); i++) {
					PartnerApplyOrderProductBean be = listPro.get(i);
					ProductBean proBean = productService.findProductById(be.getProduct_id());
					PartnerProductBean parBean = new PartnerProductBean();
					parBean.setCustomer_id(customerService.getUserId());
					parBean.setPartner_id(customerService.getUserId());
					parBean.setProduct_id(proBean.getId());
					parBean.setProduct_code(proBean.getCode());
					parBean.setProduct_name(proBean.getName());
					parBean.setProduct_price(proBean.getProduct_price());
					parBean.setProduct_type(proBean.getProduct_type());
					parBean.setProduct_type_name("");
					parBean.setProduct_brand(proBean.getProduct_brand());
					parBean.setProduct_brand_name(proBean.getProduct_brand_name());
					parBean.setStatus(proBean.getStatus());
					parBean.setCreate_time(DateUtil.getNowDateStr());
					parBean.setEff_date(DateUtil.getNowDateStr());
					parBean.setDff_date(DateUtil.plusDate(DateUtil.getNowDateStr(), 90));
					partnerProductService.insertPartnerProduct(parBean);
				}
				partnerApplyOrderService.updatePartnerApplyOrder(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}
	
	/**
	 * 加载审核商品界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/auditProductList.do")
	public ModelAndView auditProductList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PartnerApplyOrderBean bean = new PartnerApplyOrderBean();
			bean.setStatus(PartnerApplyOrderBean.STATUS_WSH);
			List<PartnerApplyOrderBean> list = partnerApplyOrderService.listPartnerApplyOrder(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_apiaudit_pro.jsp",map);
	}
	/**
	 * 加载审核商品界面-详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/admin/auditProductInfo.do")
	public ModelAndView auditProductInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			PartnerApplyOrderProductBean bean = new PartnerApplyOrderProductBean();
			bean.setOrderid(id);
			List<PartnerApplyOrderProductBean> list = partnerApplyOrderProductService.listPartnerApplyOrderProduct(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_apiaudit_proinfo.jsp",map);
	}
}
