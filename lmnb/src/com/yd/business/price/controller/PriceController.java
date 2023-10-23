package com.yd.business.price.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.service.IPriceService;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPricedetailModel;
import com.yd.util.AutoInvokeGetSetMethod;

@Controller
public class PriceController extends BaseController {
	@Resource
	private IPriceService priceService;
	@Resource
	private IOperatorService operatorService;

	@RequestMapping("admin/price/ajaxQueryPriceByCompany.do")
	public ModelAndView ajaxQueryPriceByCompany(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			result = priceService.queryPriceListByCompany(operator.getSystemid());

		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}
	@RequestMapping("admin/price/ajaxQueryPriceDetail.do")
	public ModelAndView ajaxQueryPriceDetail(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result =  new IOTWebDataBean();
		try {
//			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);

			int id = Integer.parseInt(request.getParameter("priceid"));
			LmPricedetailModel pd = priceService.findPriceDetailByPriceId(id);
			
			result.setData(pd);
			
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	@RequestMapping("admin/price/ajaxAddOrUpdatePriceByCompany.do")
	public ModelAndView ajaxAddOrUpdatePriceByCompany(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean operator = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
//			if(operator == null) 
//				operator = operatorService.findOperatorById(7l);
			
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
