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
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
/**
 * 
 * @author Anlins
 *
 */
@Controller
public class SupplierController extends BaseController {

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
	 * 充值
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/recharge.do")
	public ModelAndView toRecharge(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SupplierBean bean = new SupplierBean();
			bean.setParent_customer_id(customerService.getUserId());
			List<SupplierBean> list = supplierService.listSupplier(bean);
			map.put("user", customerService.getUser());
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败！");
		}
		return new ModelAndView("/page/pc/iframe_recharge.jsp",map);
	}
	/**
	 * 授权记录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/toPowerLog.do")
	public ModelAndView toPowerLog(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SupplierPowerLogBean bean = new SupplierPowerLogBean();
			bean.setCustomer_id(customerService.getUserId());
			List<SupplierPowerLogBean> list = supplierPowerLogService.listSupplierPowerLog(bean);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败！");
		}
		return new ModelAndView("/page/pc/iframe_product_power_log.jsp",map);
	}
	/**
	 * 获取商品可以核减的商户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/toQuerySupplierByMinus.do")
	public ModelAndView toQuerySupplierByMinus(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			int productid = NumberUtil.toInt(request.getParameter("productid"));
			int store_num = NumberUtil.toInt(request.getParameter("store_num"));
			List<SupplierBean> list = supplierService.querySupplierByMinus(customerService.getUserId(), productid, store_num);
			writeJson(response, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败！");
		}
		return null;
	}
	/**
	 * 商品指派
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/toDesignSupplierProduct.do")
	public ModelAndView designSupplierProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String params = request.getParameter("params");
			String result = supplierService.designSupplierProduct(params);
			writeJson(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "商品指派失败！");
		}
		return null;
	}
	/**
	 * 跳转至我的商户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/mysup.do")
	public ModelAndView toMySupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SupplierBean> list = listSupplierByCustomerid();
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败！");
		}
		return new ModelAndView("/page/pc/iframe_supmgr_my.jsp",map);
	}
	private List<SupplierBean> listSupplierByCustomerid(){
		SupplierBean bean = new SupplierBean();
		bean.setCustomer_id(customerService.getUserId());
		List<SupplierBean> list = supplierService.listSupplier(bean);
		return list;
	}
	/**
	 * 跳转至商品指派
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/productdesign.do")
	public ModelAndView toProductDesign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ProductTypeBean> list = productTypeService.listProductTypeByCustomerId(customerService.getUserId());
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载失败！");
		}
		return new ModelAndView("/page/pc/iframe_design.jsp",map);
	}
	/**
	 * 商品授权提交
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/powerSupProduct.do")
	public ModelAndView powerSupProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String params = request.getParameter("params");
			String result = supplierService.powerSupProduct(params);
			writeJson(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "商品授权失败！");
		}
		return null;
	}
	/**
	 * 跳转至商品授权
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/propower.do")
	public ModelAndView toProductPower(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SupplierBean> list = listSupplierByParentCustomerId();
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "授权取消失败！");
		}
		return new ModelAndView("/page/pc/iframe_product_power.jsp",map);
	}
	/**
	 * 获取当前客户的子商户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/toSupplierByParentCustomerid.do")
	public ModelAndView toSupplierByParentCustomerid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			List<SupplierBean> list = listSupplierByParentCustomerId();
			writeJson(response, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "信息获取失败！");
		}
		return null;
	};
	private List<SupplierBean> listSupplierByParentCustomerId(){
		SupplierBean bean = new SupplierBean();
		bean.setParent_customer_id(customerService.getUserId());
		List<SupplierBean> list = supplierService.listSupplier(bean);
		return list;
	}
	/**
	 * 取消商户授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/supplier/admin/cancelPower.do")
	public ModelAndView cancelSupplierPower(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			Integer supplierid = NumberUtil.toInt(request.getParameter("supplierid"));
			String result = supplierService.cancelPower(id,supplierid);
			writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "授权取消失败！");
		}
		return null;
	}
	/**
	 * 根据id查询商户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/find.do")
	public ModelAndView findSupplierById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierBean bean = supplierService.findSupplierById(NumberUtil.toInt(request.getParameter("id")));
			writeJson(response, bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}
	/**
	 * 商户列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/list.do")
	public ModelAndView listSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierBean bean = new SupplierBean();
			bean.setParent_customer_id(customerService.getUserId());
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			List<SupplierBean> list = supplierService.listSupplier(bean);
			writeJson(response, list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}
	/**
	 * 新增商户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/insert.do")
	public ModelAndView insertSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SupplierBean bean = new SupplierBean();
		try {
			String disGroupIds = request.getParameter("disgroupid");
			String currentDate = NumberUtil.toString(new Date());
			int iscreate = NumberUtil.toInt(request.getParameter("iscreate"));
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setParent_customer_id(customerService.getUserId());
			bean.setParent_name(customerService.getUser().getName());
			bean.setCreate_time(currentDate);
			bean.setModify_time(currentDate);
			bean.setStatus(SupplierBean.STATUS_Y);
			writeJson(response, insertSupplier(bean,disGroupIds,CustomerBean.TYPE_IMP,iscreate));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "商户新增失败！");
		}
		return null;
	}
	/**
	 * 插入商户
	 * @param bean
	 * @return
	 */
	public String insertSupplier(SupplierBean bean,String disGroupIds,int type,int iscreate){
		CustomerBean custBean = new CustomerBean();
		String currentDate = NumberUtil.toString(new Date());
		CustomerBean customer = customerService.findCustomerByPhone(bean.getContacts_phone());//这里判断当前客户是否已经生成，如果没有生成则新增加一个
		if(customer==null){
			custBean.setName(bean.getName());
			custBean.setContacts_phone(bean.getContacts_phone());
			custBean.setContacts_name(bean.getContacts_name());
			custBean.setAddress(bean.getAddress());
			custBean.setCreate_time(currentDate);
			custBean.setModify_time(currentDate);
			custBean.setUsername(bean.getContacts_phone());
			custBean.setPassword(MD5.md5(bean.getContacts_phone()));
			custBean.setStatus(bean.getStatus());
			custBean.setIscreate(iscreate);
			custBean.setBalance(0);
			custBean.setPoints(0);
			custBean.setGet_cash_min(1);
			custBean.setCredit(0);
			custBean.setRemark(bean.getRemark());
			custBean.setPay_cycle(CustomerBean.PAY_CYCLE_MONTH);
			custBean.setType(type);
			customerService.insertCustomer(custBean);//新增商户信息
			customer = customerService.findCustomerByPhone(bean.getContacts_phone());
			custBean.setId(customer.getId());
		}else custBean.setId(customer.getId());
		bean.setCustomer_id(custBean.getId());
		String result = supplierService.insertSupplier(bean,disGroupIds);
		return result;
	}
	/**
	 * 商户信息修改(基本信息修改)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/update.do")
	public ModelAndView updateSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierBean bean = new SupplierBean();
			bean.setModify_time(NumberUtil.toString(new Date()));
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			supplierService.updateSupplier(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "商户信息修改失败");
		}
		return null;
	}
	/**
	 * 删除商户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/delete.do")
	public ModelAndView deleteSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierBean bean = new SupplierBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			supplierService.deleteSupplier(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "商户信息删除失败");
		}
		return null;
	}
	/**
	 * 批量删除商户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/batchDel.do")
	public ModelAndView batchDeleteSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String [] ids = request.getParameter("ids").split(",");
			List<SupplierBean> list = new ArrayList<SupplierBean>();
			for (int i = 0; i < ids.length; i++) {
				SupplierBean bean = new SupplierBean();
				bean.setId(NumberUtil.toInt(ids[i]));
				list.add(bean);
			}
			supplierService.batchDeleteSupplier(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "批量删除商户信息失败！");
		}
		return null;
	}
	/**
	 * 商户信息导入
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/import.do")
	public ModelAndView importSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = "";
		try {
			String disGroupIds = request.getParameter("disgroupid");
			String params = request.getParameter("params");
			JSONObject jso = new JSONObject(params);
			JSONArray arr = jso.getJSONArray("Data");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				int iscreate = NumberUtil.toString(o.get("是否可以创建子商户")).equals("是")?1:0;
				map.put("name", o.get("商户名称"));
				map.put("status", NumberUtil.toString(o.get("状态")).equals("正常")?1:0);
				map.put("type", NumberUtil.toString(o.get("商户分类")).equals("种类1")?0:1);
				//map.put("issale", NumberUtil.toString(o.get("是否可以销售")).equals("是")?1:0);
				map.put("iscreate", iscreate);
				map.put("address", o.get("商户地址"));
				map.put("balance", o.get("账户保证金"));
				map.put("discount", o.get("折扣额度"));
				map.put("contacts_name", o.get("联系人"));
				map.put("contacts_phone", o.get("联系方式"));
				map.put("remark", o.get("备注"));
				SupplierBean bean = new SupplierBean();
				AutoInvokeGetSetMethod.autoInvoke(map, bean);
				bean.setParent_customer_id(customerService.getUserId());
				bean.setParent_name(customerService.getUser().getName());
				bean.setCreate_time(NumberUtil.toString(new Date()));
				bean.setModify_time(NumberUtil.toString(new Date()));
				result = insertSupplier(bean,disGroupIds,CustomerBean.TYPE_IMP,iscreate);
				if(!NumberUtil.empty(result)) break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		writeJson(response, result);
		return null;
	}
	
	/**
	 * 批量修改状态信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/batchUpdate.do")
	public ModelAndView batchUpdateSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String[] ids = request.getParameter("ids").split(",");
			Integer status = NumberUtil.toInt(request.getParameter("status"));
			Integer issale = NumberUtil.toInt(request.getParameter("issale"));
			List<SupplierBean> list = new ArrayList<SupplierBean>();
			for (int i = 0; i < ids.length; i++) {
				SupplierBean bean = new SupplierBean();
				bean.setId(NumberUtil.toInt(ids[i]));
				bean.setStatus(status);
				bean.setIssale(issale);
				list.add(bean);
				supplierService.updateSupplier(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "批量操作失败！");
		}
		return null;
	}
	
	/**
	 * 获取已经授权的商户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/admin/powerList.do")
	public ModelAndView listSupPower(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			List<SupplierBean> list = supplierService.listSupplierByPower(customerService.getUserId());
			writeJson(response, list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}
}
