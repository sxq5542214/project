package com.yd.business.bill.controller;

import java.util.List;

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
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.iotbusiness.mapper.model.LlDictionaryModel;
import com.yd.iotbusiness.mapper.model.LlDictionaryModelExample;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;

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
