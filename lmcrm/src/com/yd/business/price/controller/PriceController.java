package com.yd.business.price.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.service.IPriceService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;

@Controller
public class PriceController extends BaseController {
	@Resource
	private IPriceService priceService;
	@Resource
	private IOperatorService operatorService;

	@RequestMapping("admin/price/ajaxQueryPriceByCompany.do")
	public ModelAndView ajaxQueryPriceByCompany(HttpServletRequest request,HttpServletResponse response){
		
		OperatorBean operator = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
		if(operator == null) 
			operator = operatorService.findOperatorById(7l);
		
		String p_enabled = request.getParameter("p_enabled");
		String p_name = request.getParameter("p_name");
		String p_ladder = request.getParameter("p_ladder");
		System.out.println();
		PriceBean bean = new PriceBean();
		bean.setP_companyid(operator.getO_companyid());
		if(StringUtil.isNotNull(p_enabled)) {
			bean.setP_enabled(Integer.parseInt(p_enabled));
		}
			bean.setP_name(p_name);
		if(StringUtil.isNotNull(p_ladder)) {
			bean.setP_ladder(Integer.parseInt(p_ladder));
		}
		
		List<PriceBean> list = priceService.queryALLPriceList(bean);

//		System.out.println(p_enabled +"  ,   " + p_name  +"   ,  " + p_ladder +"     ");
		writeJson(response, list);
		
		return null;
	}
	

	@RequestMapping("admin/price/ajaxAddOrUpdatePriceByCompany.do")
	public ModelAndView ajaxAddOrUpdatePriceByCompany(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean operator = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			if(operator == null) 
				operator = operatorService.findOperatorById(7l);
			
			String p_enabled = request.getParameter("p_enabled");
			String p_name = request.getParameter("p_name");
			String p_ladder = request.getParameter("p_ladder");
			
			System.out.println();
			PriceBean bean = new PriceBean();
			
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);

			bean.setP_companyid(operator.getO_companyid());
			
			int i = priceService.addOrUpdatePrice(bean);
			
			writeJson(response, i);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
		
		
		return null;
	}
	
}
