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
import com.yd.business.device.dao.IChangeMeterDao;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;

@Controller
public class ChangeMeterController extends BaseController {
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IDeviceKindService deviceKindService;
	@Resource
	private IChangeMeterService changeMeterService;

	
	
}
