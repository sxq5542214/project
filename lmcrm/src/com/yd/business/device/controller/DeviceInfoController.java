package com.yd.business.device.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;

@Controller
public class DeviceInfoController extends BaseController {
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IDeviceKindService deviceKindService;

	@RequestMapping("admin/device/toDeviceInfoPage.do")
	public ModelAndView toDeviceInfoPage(HttpServletRequest request,HttpServletResponse response){
		
		
		
		return null;
	}

	@RequestMapping("admin/device/ajaxQueryEnableDeviceKind.do")
	public ModelAndView ajaxQueryEnableDeviceKind(HttpServletRequest request,HttpServletResponse response){

		try {

			
			DeviceKindBean bean = new DeviceKindBean();
			bean.setDk_enabled(DeviceKindBean.ENABLED_TRUE);
			List<DeviceKindBean> list = deviceKindService.queryDeviceKind(bean);
			
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
