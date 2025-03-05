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
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.client.bean.QingSongInterfaceBean.DeviceDto;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.price.service.IPriceService;
import com.yd.business.user.service.IUserInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.iotbusiness.mapper.model.LlDictionaryModel;
import com.yd.iotbusiness.mapper.model.LlDictionaryModelExample;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

@Controller
public class DeviceInfoController extends BaseController {
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IDeviceKindService deviceKindService;
	@Resource
	private IChargeDetailService chargeDetailService;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IUserWechatService userWechatService;
	

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
	
	@RequestMapping("admin/device/ajaxAddOrUpdateMeter.do")
	public ModelAndView ajaxAddOrUpdateMeter(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			LmMeterModel bean = new LmMeterModel();
			
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setSystemid(operator.getSystemid());
			result = deviceInfoService.addOrUpdateMeter(bean);
			
			String openfeeStr = request.getParameter("openfee");
			if(StringUtil.isNotNull(openfeeStr)) {
				// 是否有开户费，有则记录
				Integer openfee = Integer.parseInt(openfeeStr);
				
				//更新用户表中的开户费
				Integer userid = bean.getUserid();
				LmUserModel user = userInfoService.findUserById(userid);
				user.setOpenfee(new BigDecimal(openfee));
				userInfoService.addOrUpdateUser(user);
				
				//创建充值表中的开户费
				chargeDetailService.createOpenFeePayment(bean.getCode(),openfee,"开户费："+openfeeStr);
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
	


	@RequestMapping("admin/device/ajaxCheckDeviceStation.do")
	public ModelAndView ajaxCheckDeviceStation(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			String meterid = request.getParameter("meterid");
			
			DeviceDto dto = deviceInfoService.checkDeviceStation(Integer.parseInt(meterid));
			result = new IOTWebDataBean();
			result.setMessage("表具校验通过！");
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	
	

	@RequestMapping("admin/device/ajaxQueryMeterList.do")
	public ModelAndView ajaxQueryMeterList(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			String userid = request.getParameter("userid");
			
			LmMeterModel bean = new LmMeterModel();
			bean.setUserid(Integer.parseInt(userid));

			bean.setSystemid(operator.getSystemid());
			result = deviceInfoService.queryMeterList(bean);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	@RequestMapping("device/ajaxQueryMeterListByOpenid.do")
	public ModelAndView ajaxQueryMeterListByOpenid(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			
			String openid = request.getParameter("openid");
			
			LmUserModel user = userWechatService.findLmUserByOpenId(openid);
			
			LmMeterModel meterModel = new LmMeterModel();
			meterModel.setUserid(user.getId());
			meterModel.setChanged(MeterModelExtendsBean.CHANGED_FALSE);
			result = deviceInfoService.queryMeterList(meterModel );
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}
	
	@RequestMapping("admin/device/ajaxQueryMeterAndUserList.do")
	public ModelAndView ajaxQueryMeterAndUserList(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			MeterModelExtendsBean bean = new MeterModelExtendsBean();
			
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setSystemid(operator.getSystemid());
			
			result = deviceInfoService.queryMeterAndUserList(bean);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	@RequestMapping("admin/device/ajaxOpenValveByCode.do")
	public ModelAndView ajaxOpenValveByCode(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			String meterCode = request.getParameter("code");
			
			result = deviceInfoService.openOrCloseMeter(meterCode, operator, true,"界面操作开阀:"+operator.getRealname());
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	@RequestMapping("admin/device/ajaxCloseValveByCode.do")
	public ModelAndView ajaxCloseValveByCode(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			String meterCode = request.getParameter("code");
			
			result = deviceInfoService.openOrCloseMeter(meterCode, operator, false,"界面操作关阀:"+operator.getRealname());
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}


	@RequestMapping("admin/device/ajaxQueryDayMeterReadingCount.do")
	public ModelAndView ajaxQueryDayMeterReadingCount(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			String daystr = DateUtil.getNowOlnyDateStr();
			result = deviceInfoService.queryDayMeterReadingCount(daystr, operator.getSystemid());
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	@RequestMapping("admin/device/ajaxQueryOpenedMeterCount.do")
	public ModelAndView ajaxQueryOpenedMeterCount(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			result = deviceInfoService.queryOpenedMeterCount(operator.getSystemid());
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	/**
	 * 查询首页的今日抄表数近2月每日明细数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/device/ajaxQueryDayMeterReadingCountListDataByDashboard.do")
	public ModelAndView ajaxQueryDayMeterReadingCountListDataByDashboard(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			result = deviceInfoService.queryDayMeterReadingCountListData(null,operator.getSystemid(),operator.getId());
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}

	/**
	 * 查询首页的今日开户数近2月每日明细数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/device/ajaxQueryOpenedMeterCountListDataByDashboard.do")
	public ModelAndView ajaxQueryOpenedMeterCountListDataByDashboard(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result;
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			result = deviceInfoService.queryDayOpendedMeterCountListData(null,operator.getSystemid(),operator.getId());
			
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
