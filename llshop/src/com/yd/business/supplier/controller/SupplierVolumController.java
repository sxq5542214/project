/**
 * 
 */
package com.yd.business.supplier.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.supplier.bean.SupplierVolumBean;
import com.yd.business.supplier.service.ISupplierVolumService;
import com.yd.util.AutoInvokeGetSetMethod;

/**
 * 商户的优惠卷管理
 * @author ice
 *
 */
@Controller
public class SupplierVolumController extends BaseController {
	@Resource
	private ISupplierVolumService supplierVolumService;
	
	@RequestMapping("/**/volum/querySupplierVolum.do")
	public ModelAndView querySupplierVolum(HttpServletRequest request,HttpServletResponse response){
		
//		Integer pageSize = getPageSizeByRequest(request);
//		Integer nowPage = getNowPageByRequest(request);
//
//		if(pageSize == null){pageSize = 50;}
//		if(nowPage == null){nowPage = 1;}
		SupplierVolumBean bean = new SupplierVolumBean();
		try {
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			PageinationData pd = supplierVolumService.querySupplierVolum(bean);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageData", pd);
			return new ModelAndView("/page/supplier/volum/volumManager.jsp", map );
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("/**/volum/querySupplierVolumByAjax.do")
	public ModelAndView querySupplierVolumByAjax(HttpServletRequest request,HttpServletResponse response){
		
		SupplierVolumBean bean = new SupplierVolumBean();
		try {
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			PageinationData pd = supplierVolumService.querySupplierVolum(bean);
			
			writeJson(response, pd);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("/**/volum/createMoneyVolum.do")
	public ModelAndView createMoneyVolum(HttpServletRequest request,HttpServletResponse response){
		try {
			SupplierVolumBean bean = new SupplierVolumBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			supplierVolumService.createMoneyVolum(bean);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bean", bean);
			return new ModelAndView("/page/supplier/volum/createSuccess.jsp", map );
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("/**/volum/createExchangeVolum.do")
	public ModelAndView createExchangeVolum(HttpServletRequest request,HttpServletResponse response){
		try {
			SupplierVolumBean bean = new SupplierVolumBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			supplierVolumService.createExchangeVolum(bean);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bean", bean);
			return new ModelAndView("/page/supplier/volum/createSuccess.jsp", map );
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	
}
