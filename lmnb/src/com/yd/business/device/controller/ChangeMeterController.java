package com.yd.business.device.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.device.bean.ChangeMeterExtBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;

@Controller
public class ChangeMeterController extends BaseController {
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IDeviceKindService deviceKindService;
	@Resource
	private IChangeMeterService changeMeterService;


	@RequestMapping("admin/device/changemeter/ajaxQueryUserChangeMeterList.do")
	public ModelAndView ajaxQueryUserChangeMeterList(HttpServletRequest request,HttpServletResponse response){

		try {
			String cm_type = request.getParameter("cm_type");
			String u_name = request.getParameter("u_name");
			String u_cardno = request.getParameter("u_cardno");
			String u_phone = request.getParameter("u_phone");
			
			ChangeMeterExtBean cme = new ChangeMeterExtBean();
			if(StringUtil.isNotNull(cm_type)) {
				cme.setCm_type(Integer.parseInt(cm_type));
			}
			if(StringUtil.isNotNull(u_name)) {
				cme.setUser_name(u_name);
			}
			if(StringUtil.isNotNull(u_cardno)) {
				cme.setUser_cardno(Integer.parseInt(u_cardno));
			}
			if(StringUtil.isNotNull(u_phone)) {
				cme.setUser_phone(u_phone);
			}
			cme.setOrderby(" order by cm_id desc");
			List<ChangeMeterExtBean> list =changeMeterService.queryChangeMeterByExt(cme);
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	@RequestMapping("admin/device/changemeter/ajaxChangeMeter.do")
	public ModelAndView ajaxChangeMeter(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result ;
		try {
			String changeMeterCost = request.getParameter("changeMeterCost");
			String changeMeterCode = request.getParameter("changeMeterCode");
			String oldReadNum = request.getParameter("oldReadNum");
			String changeReadNum = request.getParameter("changeReadNum");
			String changeFixUser = request.getParameter("changeFixUser");
			String remark1 = request.getParameter("remark1");
			
			LmMeterModel meter = new LmMeterModel();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), meter);
			BigDecimal cost = new BigDecimal(changeMeterCost);
			BigDecimal oldNum = new BigDecimal(oldReadNum);
			BigDecimal changeNum = new BigDecimal(changeReadNum);
			
			LmOperatorModel op = getCurrentLoginOperator();
			
			result = changeMeterService.changeMeter(meter.getCode(), changeMeterCode, cost, oldNum, changeNum, changeFixUser, remark1,op.getId());
			
			System.out.println(meter);
			
			writeJson(response, result );
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
