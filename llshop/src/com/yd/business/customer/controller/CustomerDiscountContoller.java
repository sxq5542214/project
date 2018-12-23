package com.yd.business.customer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.customer.bean.CustomerDiscountGroupBean;
import com.yd.business.customer.service.ICustomerDiscountGroupService;
import com.yd.business.customer.service.ICustomerDiscountService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.SupplierDiscountRelationBean;
import com.yd.business.supplier.service.ISupplierDiscountRelationService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.NumberUtil;
@Controller
public class CustomerDiscountContoller extends BaseController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ICustomerDiscountService customerDiscountService;
	@Autowired
	private ICustomerDiscountGroupService customerDiscountGroupService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private ISupplierService supplierService;
	@Autowired
	private ISupplierDiscountRelationService supplierDiscountRelationService;
	@RequestMapping("/admin/discount.do")
	public ModelAndView toDiscount(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", customerService.getUser());
		return new ModelAndView("/page/pc/iframe_discountmgr.jsp", model);
	}
	@RequestMapping("/admin/discountEdt.do")
	public ModelAndView toDiscountEdt(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", customerService.getUser());
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			CustomerDiscountGroupBean groupBean = customerDiscountGroupService.findCustDisGroupById(id);//获取分组信息
			CustomerDiscountBean bean = new CustomerDiscountBean();
			bean.setGroup_id(id);
			List<CustomerDiscountBean> list = customerDiscountService.listCustomerDiscount(bean);
			//处理list结果
			Map<String, List<CustomerDiscountBean>> result = new HashMap<String, List<CustomerDiscountBean>>();
			for (int i = 0; i < list.size(); i++) {
				CustomerDiscountBean cdb = list.get(i);
				if(result.size()>0){
					Iterator<String> it = result.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						if(key.equals(cdb.getProduct_brand_name())){
							List<CustomerDiscountBean> ls = result.get(key);
							ls.add(cdb);
							result.put(key, ls);
						}
					}
				}else{
					List<CustomerDiscountBean> l = new ArrayList<CustomerDiscountBean>();
					l.add(cdb);
					result.put(cdb.getProduct_brand_name(), l);
				}
			}
			model.put("group", groupBean);
			model.put("list", result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return new ModelAndView("/page/pc/iframe_discountmgr_edit.jsp",model);
	}
	/**
	 * 获取商户规则分组的详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/listDisGroupInfo.do")
	public ModelAndView listCustDisGroupInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerDiscountGroupBean bean = new CustomerDiscountGroupBean();
			bean.setCustomer_id(NumberUtil.toInt(request.getParameter("custid")));
			List<CustomerDiscountGroupBean> list = customerDiscountGroupService.listCustDisGroupInfo(bean);
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
	 * 获取所有的商品品牌信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/listProductTypeBrand.do")
	public ModelAndView listProductTypeBrand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			ProductTypeBean bean = new ProductTypeBean();
//			bean.setType(ProductTypeBean.TYPE_BRA);//只获取品牌列表
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			List<ProductTypeBean> list = productTypeService.listProductType(bean);
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
	 * 获取当前用户的折扣规则列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/listDisGroup.do")
	public ModelAndView listCustomerDiscountGroup(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerDiscountGroupBean bean = new CustomerDiscountGroupBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerDiscountGroupBean> list = customerDiscountGroupService.listCustomerDiscountGroup(bean);
			writeJson(response, list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}
	
	@RequestMapping("/discount/admin/listDis.do")
	public ModelAndView listCustomerDiscount(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerDiscountBean bean = new CustomerDiscountBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerDiscountBean> list = customerDiscountService.listCustomerDiscount(bean);
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
	 * 删除折扣规则
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/discount/admin/delete.do")
	public ModelAndView deleteCustDisGroup(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			if(id>0){
				SupplierDiscountRelationBean sdrb = new SupplierDiscountRelationBean();
				sdrb.setParent_discount_group_id(id);
				List<SupplierDiscountRelationBean> list = supplierDiscountRelationService.listSupDisRela(sdrb);
				if(list==null||list.size()==0){
					CustomerDiscountGroupBean bean = new CustomerDiscountGroupBean();
					CustomerDiscountBean cdBean = new CustomerDiscountBean();
					bean.setId(id);
					cdBean.setGroup_id(id);
					customerDiscountService.deleteCustomerDiscount(cdBean);
					customerDiscountGroupService.deleteCustomerDiscountGroup(bean);
				}else writeJson(response, "该折扣规则已经授权给商户，不允许删除！");
			}else writeJson(response, "折扣规则ID不能为空");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "折扣规则删除失败！");
		}
		return null;
	}
	
	/**
	 * 批量删除规则
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/batchDelete.do")
	public ModelAndView batchDeleteCustDisGroup(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String[] ids = request.getParameter("ids").split(",");
			List<CustomerDiscountBean> disList = new ArrayList<CustomerDiscountBean>();
			List<CustomerDiscountGroupBean> groupList = new ArrayList<CustomerDiscountGroupBean>();
			boolean isExists = false;
			for (int i = 0; i < ids.length; i++) {
				CustomerDiscountBean bean = new CustomerDiscountBean();
				CustomerDiscountGroupBean groupBean = new CustomerDiscountGroupBean();
				bean.setGroup_id(NumberUtil.toInt(ids[i]));
				groupBean.setId(NumberUtil.toInt(ids[i]));
				disList.add(bean);
				groupList.add(groupBean);
				
				SupplierDiscountRelationBean sdrb = new SupplierDiscountRelationBean();
				sdrb.setParent_discount_group_id(NumberUtil.toInt(ids[i]));
				List<SupplierDiscountRelationBean> list = supplierDiscountRelationService.listSupDisRela(sdrb);
				if(list!=null&&list.size()>0) isExists = true;
			}
			if(!isExists){
				customerDiscountService.batchDeleteCustomerDiscount(disList);
				customerDiscountGroupService.batchDeleteCustomerDiscountGroup(groupList);
			}else writeJson(response, "存在已经授权的折扣规则，请核查后再删除！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "批量删除折扣规则失败");
		}
		return null;
	}
	/**
	 * 新增折扣规则
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/insert.do")
	public ModelAndView insertCustDisGroup(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			JSONObject jso = new JSONObject(request.getParameter("params"));
			CustomerDiscountGroupBean bean = new CustomerDiscountGroupBean();
			bean.setName(NumberUtil.toString(jso.get("name")));
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerDiscountGroupBean> list = customerDiscountGroupService.listCustomerDiscountGroup(bean);
			if(list!=null&&list.size()>0) writeJson(response, "已有相同名称的折扣规则，请重新修改!");
			else{
				bean.setRemark(NumberUtil.toString(jso.get("remark")));
				bean.setCustomer_name(customerService.getUser().getName());
				customerDiscountGroupService.insertCustomerDiscountGroup(bean);
				int id = bean.getId();
				JSONArray arr = jso.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					JSONObject o = arr.getJSONObject(i);
					CustomerDiscountBean disBean = new CustomerDiscountBean();
					disBean.setGroup_id(id);
					disBean.setCustomer_id(customerService.getUserId());
					disBean.setName(bean.getName());
					disBean.setProduct_brand(NumberUtil.toInt(o.get("brand")));
					disBean.setDiscount(NumberUtil.toInt(o.get("discount")));
					disBean.setMin_price(NumberUtil.toInt(o.get("minprice")));
					disBean.setMax_price(NumberUtil.toInt(o.get("maxprice")));
					customerDiscountService.insertCustomerDiscount(disBean);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "新增折扣规则失败！");
		}
		return null;
	}
	
	/**
	 * 商户授权
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/power.do")
	public ModelAndView power(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String params = request.getParameter("params");
			String result = supplierService.insetSupPower(params);
			writeJson(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}
	/**
	 * 修改授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/discount/admin/update.do")
	public ModelAndView updateDisGroup(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = "";
		try {
			JSONObject jso = new JSONObject(request.getParameter("params"));
			CustomerDiscountGroupBean bean = new CustomerDiscountGroupBean();
			bean.setId(NumberUtil.toInt(jso.get("id")));
			//这里需要判断一下是否已经被授权给商户了
			SupplierDiscountRelationBean sdrb = new SupplierDiscountRelationBean();
			sdrb.setParent_discount_group_id(NumberUtil.toInt(jso.get("id")));
			List<SupplierDiscountRelationBean> list = supplierDiscountRelationService.listSupDisRela(sdrb);
			if(list!=null&&list.size()>0) result = "该折扣规则已经授权给商户，不允许修改！";
			else{
				bean.setName(NumberUtil.toString(jso.get("name")));
				bean.setRemark(NumberUtil.toString(jso.get("remark")));
				customerDiscountGroupService.updateCustomerDiscountGroup(bean);
				
				JSONArray arr = jso.getJSONArray("list");
				for (int i = 0; i < arr.length(); i++) {
					JSONObject o = arr.getJSONObject(i);
					int type = NumberUtil.toInt(o.get("type"));
					CustomerDiscountBean cdb = new CustomerDiscountBean();
					cdb.setCustomer_id(customerService.getUserId());
					cdb.setGroup_id(bean.getId());
					cdb.setProduct_brand(o.getInt("productbrand"));
					cdb.setName(bean.getName());
					cdb.setMin_price(NumberUtil.toInt(o.get("minprice")));
					cdb.setMax_price(NumberUtil.toInt(o.get("maxprice")));
					cdb.setDiscount(NumberUtil.toInt(o.get("discount")));
					if(type==0){//新增
						customerDiscountService.insertCustomerDiscount(cdb);
					}else if(type==1){//修改
						Integer cdbid = NumberUtil.toInt(o.get("id"));
						cdb.setId(cdbid);
						
						if(customerService.getUserId()!=1){
							CustomerDiscountBean cdbBean = customerDiscountService.findCustomerDiscountById(cdbid);
							if(cdbBean.getDiscount()<cdbBean.getDiscount()){
								customerDiscountService.updateCustomerDiscount(cdb);
							}else {
								result = "设置的折扣不允许小于父客户设置的值！";
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			result = "修改失败！";
		}finally{
			writeJson(response, result);
		}
		return null;
	}
}
