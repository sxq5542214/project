package com.yd.business.customer.controller;

import java.io.IOException;
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
import com.yd.business.customer.bean.CustomerApplyProductBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerApplyProductService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.NumberUtil;
@Controller
public class CustomerApplyProductController extends BaseController {

	@Autowired
	private ICustomerApplyProductService customerApplyProductService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ISupplierProductService supplierProductService;
	@Autowired
	private ISupplierService supplierService;
	
	/************************微信－begin***************************/
	/**
	 * 微信端申领工单发起
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/apply/apply.do")
	public ModelAndView toApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String result = apply(request, customerService.getUserId());
			if(NumberUtil.empty(result)) writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}
	/**
	 * 微信端申领-选择商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/apply/myapplypro.do")
	public ModelAndView toMyApplyPro(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer parcustid = NumberUtil.toInt(request.getParameter("parcustid"));
			List<SupplierProductBean> list = supplierProductService.queryProBySupParCustomerId(parcustid, customerService.getUserId());
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败");
		}
		return new ModelAndView("/page/wechat/apply_edit_product.jsp",map);
	}
	/**
	 * 微信端申领-选择客户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/apply/myapply.do")
	public ModelAndView toMyApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//先获取当前登录客户有多少父客户
			SupplierBean bean = new SupplierBean();
			bean.setCustomer_id(customerService.getUserId());
			List<SupplierBean> supList = supplierService.listSupplier(bean);
			if(supList!=null&&supList.size()>0){
				List<CustomerBean> list = customerService.listCustomerBySupplier(supList);
				map.put("list", list);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败");
		}
		return new ModelAndView("/page/wechat/apply_edit.jsp",map);
	}
	/**
	 * 我的申领列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/apply/myapplylist.do")
	public ModelAndView toMyApplyList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerApplyProductBean bean = new CustomerApplyProductBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setStatus(CustomerApplyProductBean.APPLY_STATUS_YSQ);
			bean.setSupplier_name(NumberUtil.toString(new String((NumberUtil.empty(request.getParameter("name"))?"".getBytes("iso-8859-1"):request.getParameter("name").getBytes("iso-8859-1")),"utf-8")));
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerApplyProductBean> list = customerApplyProductService.listCustomerApplyProduct(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/wechat/apply.jsp",map);
	}
	
	/**
	 * 微信端审核结果提交
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/apply/auditcommit.do")
	public ModelAndView toApplyAuditCommit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			Integer status = NumberUtil.toInt(request.getParameter("status"));
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			Integer supplier_id = NumberUtil.toInt(request.getParameter("supid"));//需要核减的商户id
			Integer apply_num = NumberUtil.toInt(request.getParameter("num"));//审核同意数量
			String result = customerApplyProductService.applyAudit(id, status, supplier_id,apply_num);
			if(!NumberUtil.empty(result)) writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "申领工单审核失败！");
		}
		return null;
	}
	/**
	 * 微信端申领审核
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/apply/audit.do")
	public ModelAndView toApplyAudit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			CustomerApplyProductBean bean = customerApplyProductService.findCustApplyProductById(id);
			if(bean!=null){
				List<SupplierBean> list = supplierService.listMinusSupplier(customerService.getUserId(), bean.getProduct_id());
				map.put("apply", bean);
				map.put("list", list);
			}else writeJson(response, "申领工单不存在！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/wechat/audit_apply.jsp",map);
	}
	/**
	 * 微信端加载审核列表
	 * @param request
	 * @param response
	 * @return
	 * @throws  
	 */
	@RequestMapping("/app/apply/auditlist.do")
	public ModelAndView toApplyAuditList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerApplyProductBean bean = new CustomerApplyProductBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setStatus(CustomerApplyProductBean.APPLY_STATUS_YSQ);
			bean.setSupplier_name(NumberUtil.toString(new String((NumberUtil.empty(request.getParameter("name"))?"".getBytes("iso-8859-1"):request.getParameter("name").getBytes("iso-8859-1")),"utf-8")));
			bean.setApply_customer_id(customerService.getUserId());
			List<CustomerApplyProductBean> list = customerApplyProductService.listCustomerApplyProduct(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/wechat/audit.jsp",map);
	}
	/************************微信－end***************************/
	/**
	 * 加载需要核减的商户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/supprocustlist.do")
	public ModelAndView querySupplierByProCustId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			CustomerApplyProductBean bean = customerApplyProductService.findCustApplyProductById(id);
			if(bean!=null){
				List<SupplierBean> list = supplierService.listMinusSupplier(customerService.getUserId(), bean.getProduct_id());
				writeJson(response, list);
			}else writeJson(response, "申领工单不存在！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败");
		}
		return null;
	}
	/**
	 * 加载客户下所有商户的商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/applyCustPro.do")
	public ModelAndView queryCustomerProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			Integer parcustid = NumberUtil.toInt(request.getParameter("parcustid"));
			List<SupplierProductBean> list = supplierProductService.queryProBySupParCustomerId(parcustid, customerService.getUserId());
			writeJson(response, list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败");
		}
		return null;
	}
	/**
	 * 加载当前登录用户的父客户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/applyCustList.do")
	public ModelAndView queryCustomerList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			//先获取当前登录客户有多少父客户
			SupplierBean bean = new SupplierBean();
			bean.setCustomer_id(customerService.getUserId());
			List<SupplierBean> supList = supplierService.listSupplier(bean);
			if(supList!=null&&supList.size()>0){
				List<CustomerBean> list = customerService.listCustomerBySupplier(supList);
				writeJson(response, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败");
		}
		return null;
	}
	/**
	 * 我的申领列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/mylist.do")
	public ModelAndView queryMyApplyList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerApplyProductBean bean = new CustomerApplyProductBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerApplyProductBean> list = customerApplyProductService.listCustomerApplyProduct(bean);
			writeJson(response, list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败");
		}
		return null;
	}
	/**
	 * 我的申领审核列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/myauditlist.do")
	public ModelAndView queryMyAuditApplyList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerApplyProductBean bean = new CustomerApplyProductBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setStatus(CustomerApplyProductBean.APPLY_STATUS_YSQ);
			bean.setSupplier_name(request.getParameter("supplier_name"));
			bean.setApply_customer_id(customerService.getUserId());
			List<CustomerApplyProductBean> list = customerApplyProductService.listCustomerApplyProduct(bean);
			writeJson(response, list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败！");
		}
		return null;
	}
	/**
	 * 申领商品工单发起
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/apply.do")
	public ModelAndView applyProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String result = apply(request, customerService.getUserId());
			if(NumberUtil.empty(result)) writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "申领商品工单发起失败！");
		}
		return null;
	}
	private String apply(HttpServletRequest request,Integer userid){
		String result = "";
		Integer proid = NumberUtil.toInt(request.getParameter("proid"));
		Integer id = NumberUtil.toInt(request.getParameter("id"));
		Integer custid = NumberUtil.toInt(request.getParameter("custid"));
		SupplierProductBean supProBean = supplierProductService.findSupplierProductById(id);//重新查询一下是那个商户下的商品，防止被更改
		//确认是那个商户发起的申请
		SupplierBean supBean = new SupplierBean();
		supBean.setCustomer_id(userid);
		supBean.setParent_customer_id(custid);
		
		List<SupplierBean> list = supplierService.listSupplier(supBean);
		if(list!=null&&list.size()>0){
			CustomerApplyProductBean bean = new CustomerApplyProductBean();
			bean.setCustomer_id(userid);
			bean.setApply_customer_id(custid);
			bean.setProduct_id(proid);
			bean.setApply_supplier_id(list.get(0).getId());
			bean.setSupplier_name(list.get(0).getName());
			bean.setProduct_name(supProBean.getProduct_name());
			bean.setApply_num(NumberUtil.toInt(request.getParameter("applynum")));
			bean.setStatus(CustomerApplyProductBean.APPLY_STATUS_YSQ);
			bean.setCreate_time(NumberUtil.toString(new Date()));
			bean.setRemark(request.getParameter("remark"));
			bean.setModify_time(NumberUtil.toString(new Date()));
			bean.setSupplier_store_num(supProBean.getStore_num());
			customerApplyProductService.insertCustomerApplyProduct(bean);
		}else result = "无法确认需要申请的商户信息，请确认信息是否准确！";
		return result;
	}
	/**
	 * 申领商品审核
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/audit.do")
	public ModelAndView applyAuditProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			Integer status = NumberUtil.toInt(request.getParameter("status"));
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			Integer supplier_id = NumberUtil.toInt(request.getParameter("supid"));//需要核减的商户id
			Integer apply_num = NumberUtil.toInt(request.getParameter("num"));//审核同意数量
			String result = customerApplyProductService.applyAudit(id, status, supplier_id,apply_num);
			if(!NumberUtil.empty(result)) writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "申领工单审核失败！");
		}
		return null;
	}
	/**
	 * 取消申领
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/cancelApply.do")
	public ModelAndView cancelApplyProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerApplyProductBean bean = new CustomerApplyProductBean();
			bean.setStatus(CustomerApplyProductBean.APPLY_STATUS_CA);
			bean.setModify_time(NumberUtil.toString(new Date()));
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			customerApplyProductService.updateCustomerApplyProduct(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "申领取消失败！");
		}
		return null;
	}
	/**
	 * 购买商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/apply/admin/meal.do")
	public ModelAndView mealProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String params = NumberUtil.toString(request.getParameter("params"));
			String result = "";
			if(NumberUtil.empty(params)) result = "操作请求数据错误！";
			else{
				JSONObject jso = new JSONObject(params);
				Integer money = NumberUtil.toInt(jso.get("money"));
				JSONArray arr = jso.getJSONArray("data");
				if(arr!=null&&arr.length()>0&&money>0) result = customerApplyProductService.mealProduct(money, arr);
				else result = "未选择可售商品，请选择后再提交！";
			}
			writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "商品购买失败！");
		}
		return null;
	}
}
