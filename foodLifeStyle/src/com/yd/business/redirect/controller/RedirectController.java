package com.yd.business.redirect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.service.ISystemManagerService;
@Controller
public class RedirectController extends BaseController {
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ISystemManagerService systemManagerService;

	@RequestMapping("/admin/index.do")
	public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerBean customer = customerService.getUser();
		Map<String, List<SystemMenuBean>> menuMap = systemManagerService.queryMenuByCustomer(customer.getId());

		map.put("user", customer);
		map.put("menuMap", menuMap);
		return new ModelAndView("/page/pc/index.jsp",map);
	}
	@RequestMapping("/admin/person.do")
	public ModelAndView toPerson(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", customerService.getUser());
		return new ModelAndView("/page/pc/person.jsp",map);
	}
	@RequestMapping("/admin/apply.do")
	public ModelAndView toApply(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_apply.jsp");
	}
	@RequestMapping("/admin/discountIns.do")
	public ModelAndView toDiscountIns(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_discountmgr_insert.jsp");
	}
	@RequestMapping("/admin/ifmIdx.do")
	public ModelAndView toIfrmaeIndex(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_index.jsp");
	}
	@RequestMapping("/admin/applyAdt.do")
	public ModelAndView toIframeApplyAudit(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_myapplyauditmgr.jsp");
	}
	@RequestMapping("/admin/applymgr.do")
	public ModelAndView toApplyMgr(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_myapplymgr.jsp");
	}
	@RequestMapping("/admin/operatIns.do")
	public ModelAndView toOperatIns(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_operatmgr_insert.jsp");
	}
	@RequestMapping("/admin/operat.do")
	public ModelAndView toOperat(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_operatmgr.jsp");
	}
	@RequestMapping("/admin/power.do")
	public ModelAndView toPower(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_powermgr.jsp");
	}
	@RequestMapping("/admin/supIns.do")
	public ModelAndView toSupIns(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", customerService.getUser());
		return new ModelAndView("/page/pc/iframe_supmgr_insert.jsp",map);
	}
	@RequestMapping("/admin/sup.do")
	public ModelAndView toSup(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", customerService.getUser());
		return new ModelAndView("/page/pc/iframe_supmgr.jsp",map);
	}
	@RequestMapping("/admin/suppower.do")
	public ModelAndView toSupPower(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_suppower.jsp");
	}
	@RequestMapping("/admin/system.do")
	public ModelAndView toSystem(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_system.jsp");
	}
	@RequestMapping("/admin/supImp.do")
	public ModelAndView toSupImp(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/supplier_import.jsp");
	}
	@RequestMapping("/admin/powerinfo.do")
	public ModelAndView toPowerInfo(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("/page/pc/iframe_power_info.jsp");
	}
	
}
