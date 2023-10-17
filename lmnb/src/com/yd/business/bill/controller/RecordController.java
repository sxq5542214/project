package com.yd.business.bill.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.bill.service.IRecordService;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;
import com.yd.util.AutoInvokeGetSetMethod;

@Controller
public class RecordController extends BaseController {
	@Resource
	private IRecordService recordService;

	
	@RequestMapping("admin/bill/ajaxQueryRecordList.do")
	public ModelAndView ajaxQueryRecordList(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			LmRecordModel bean = new LmRecordModel();
			
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setSystemid(operator.getSystemid());
			
			result = recordService.queryRecordList(bean);
			
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
