package com.yd.business.customer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.log.bean.LogBean;
import com.yd.business.log.service.ILogService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
/**
 * 操作员业务控制器
 * @author Anlins
 *
 */
@Controller
public class CustomerAdminController extends BaseController {

	@Autowired
	private ICustomerAdminService customerAdminService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ILogService logService;
	/*******************微信－begin*********************/
	/**
	 * 微信新增操作员
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/app/operat/add.do")
	public ModelAndView toOperatInsert(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerAdminBean bean = new CustomerAdminBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setPassword(MD5.md5(bean.getPassword()));
			bean.setCustomer_id(customerService.getUserId());
			customerAdminService.insertCustomerAdmin(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "添加失败");
		}
		return null;
	}
	
	/**
	 * 微信端新增界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/operat/insert.do")
	public ModelAndView toOperatAdd(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/wechat/operator_edit.jsp");
	}
	
	/**
	 * 微信端加载操作员列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/operat/list.do")
	public ModelAndView toOperatList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerAdminBean bean = new CustomerAdminBean();
			bean.setNickname(NumberUtil.toString(new String((NumberUtil.empty(request.getParameter("name"))?"".getBytes("iso-8859-1"):request.getParameter("name").getBytes("iso-8859-1")),"utf-8")));
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerAdminBean> list = customerAdminService.listCustomerAdmin(bean);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return new ModelAndView("/page/wechat/operator.jsp",map);
	}
	/*******************微信－end*********************/
	/**
	 * 操作员列表界面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/customerAdmin/admin/toList.do")
	public ModelAndView toList(HttpServletRequest request,HttpServletResponse response){
		return sendRedirect("/page/pc/iframe_operatmgr.html");
	}
	/**
	 * 操作员编辑界面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/customerAdmin/admin/toEdit.do")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		return sendRedirect("/page/pc/iframe_operatmgr_insert.html");
	}
	/**
	 * 操作员列表信息加载
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/customerAdmin/admin/list.do")
	public ModelAndView listCustomerAdmin(HttpServletRequest request,HttpServletResponse response){
		try {
			CustomerAdminBean bean = new CustomerAdminBean();
			bean.setCustomer_id(customerService.getUserId());
			List<CustomerAdminBean> list = customerAdminService.listCustomerAdmin(bean);
			writeJson(response, list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	/**
	 * 操作远信息新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customerAdmin/admin/insert.do")
	public ModelAndView insertCustomerAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerAdminBean bean = new CustomerAdminBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setCustomer_id(customerService.getUserId());
			bean.setCustomer_name(customerService.getUser().getName());
			bean.setPassword(MD5.md5(bean.getPassword()));
			customerAdminService.insertCustomerAdmin(bean);
			logService.createAdminLog(LogBean.TYPE_CU, LogBean.ACTION_IN, bean.toString(), LogBean.FUN_OPERAT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "添加信息失败！");
		}
		return null;
	}
	
	/**
	 * 操作员删除
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customerAdmin/admin/delete.do")
	public ModelAndView deleteCustomerAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerAdminBean bean = new CustomerAdminBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			customerAdminService.deleteCustomerAdmin(bean);
			logService.createAdminLog(LogBean.TYPE_CU, LogBean.ACTION_DE, bean.toString(), LogBean.FUN_OPERAT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "删除信息失败！");
		}
		return null;
	}
	/**
	 * 操作员修改
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customerAdmin/admin/update.do")
	public ModelAndView updateCustomerAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerAdminBean bean = new CustomerAdminBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			if(!NumberUtil.empty(request.getParameter("password")))bean.setPassword(MD5.md5(bean.getPassword()));
			customerAdminService.updateCustomerAdmin(bean);
			logService.createAdminLog(LogBean.TYPE_CU, LogBean.ACTION_UP, bean.toString(), LogBean.FUN_OPERAT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "更新信息失败！");
		}
		return null;
	}
	/**
	 * 根据id查询操作员信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/customerAdmin/admin/find.do")
	public ModelAndView findCustomerAdminById(HttpServletRequest request,HttpServletResponse response){
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			CustomerAdminBean bean = customerAdminService.findCustomerAdminById(id);
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	/**
	 * 操作员批量删除操作
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customerAdmin/admin/batDel.do")
	public ModelAndView batDelCustomerAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String[] ids = request.getParameter("ids").split(",");
			List<CustomerAdminBean> list = new ArrayList<CustomerAdminBean>();
			for(int i=0;i<ids.length;i++){
				CustomerAdminBean bean = new CustomerAdminBean();
				bean.setId(NumberUtil.toInt(ids[i]));
				list.add(bean);
			}
			customerAdminService.batDelCustomerAdmin(list);
			logService.createAdminLog(LogBean.TYPE_CU, LogBean.ACTION_DE, ids.toString(), LogBean.FUN_OPERAT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "批量删除操作失败！");
		}
		return null;
	}
	/**
	 * 批量更新状态信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customerAdmin/admin/batUpdate.do")
	public ModelAndView batUpdateCustomerAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String [] ids = request.getParameter("ids").split(",");
			Integer status = NumberUtil.toInt(request.getParameter("status"));
			for (int i = 0; i < ids.length; i++) {
				CustomerAdminBean bean = new CustomerAdminBean();
				bean.setId(NumberUtil.toInt(ids[i]));
				bean.setStatus(status);
				customerAdminService.updateCustomerAdmin(bean);
			}
			logService.createAdminLog(LogBean.TYPE_CU, LogBean.ACTION_UP, ids.toString(), LogBean.FUN_OPERAT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "更新状态信息失败！");
		}
		return null;
	}
}
