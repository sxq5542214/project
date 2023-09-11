package com.yd.business.client.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.client.bean.QingSongInterfaceBean;
import com.yd.business.client.bean.QingSongInterfaceBean.MeterCMD;
import com.yd.business.client.service.IIOTInterfaceService;
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
import com.yd.util.AutoInvokeGetSetMethod;

@Controller
public class IOTInterfaceController extends BaseController {
	@Resource
	private IIOTInterfaceService iotInterfaceService;


	@RequestMapping("client/nbApi/buildStation")
	public ModelAndView qingsongBuildStation(HttpServletRequest request,HttpServletResponse response){

		try {
			String stationcode = request.getParameter("stationcode");
			
			iotInterfaceService.saveQingSongStationCode(stationcode);
			
			writeJson(response, "OK" );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("client/nbApi/postMeterCmd")
	public ModelAndView qingsongPostMeterCmd(HttpServletRequest request,HttpServletResponse response){

		try {
			String stationcode = request.getParameter("stationcode");
			
			iotInterfaceService.saveQingSongStationCode(stationcode);
			
			writeJson(response, "OK" );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("client/nbApi/postMeterReading")
	public ModelAndView qingsongPostMeterReading(HttpServletRequest request,HttpServletResponse response){

		try {
			String jsonStr = request.getParameter("json");
			
			int result = iotInterfaceService.handlerQingSongPostMeterReading(jsonStr);
			
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, -1 );
		}
		return null;
	}
}
