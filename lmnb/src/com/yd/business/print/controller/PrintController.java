package com.yd.business.print.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.service.IPriceService;
import com.yd.business.print.service.IPrintService;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;

@Controller
public class PrintController extends BaseController {
	@Resource
	private IPrintService printService;
	@Resource
	private IOperatorService operatorService;

	@RequestMapping("admin/print/ajaxQueryPrintTemplateList.do")
	public ModelAndView ajaxQueryPrintTemplateList(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);

			if(StringUtil.isNotNull(request.getParameter("id"))) {
				int id = Integer.parseInt(request.getParameter("id"));
				result = printService.queryPrintTemplateById(id);
			}else {
				result = printService.queryPrintTemplateList(operator.getSystemid());
			}

		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	@RequestMapping("admin/print/ajaxUpdatePrintTemplateStyle.do")
	public ModelAndView ajaxUpdatePrintTemplateStyle(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String style = request.getParameter("style");
			int id = Integer.parseInt(request.getParameter("id"));
			result = printService.updatePrintTemplateStyle(id, style,operator.getRealname()+"修改" );

		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	
}
