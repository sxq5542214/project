package com.yd.business.bill.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.bill.bean.BillModelExtendBean;
import com.yd.business.bill.service.IBillService;
import com.yd.business.device.bean.ChangeMeterExtBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPaymentModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.util.StringUtil;

@Controller
public class BillController extends BaseController {

	@Resource
	private IBillService billService;
	@Autowired
	private IUserWechatService userWechatService;

	@RequestMapping("admin/bill/ajaxQueryBillList.do")
	public ModelAndView ajaxQueryBillList(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result ;
		try {
			
			LmBillModel model = new LmBillModel();
			LmOperatorModel op = getCurrentLoginOperator();
			model.setSystemid(op.getSystemid());
			
			
			model.setUserid(Integer.parseInt(request.getParameter("userid")));
			result = billService.queryBillList(model);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	

	@RequestMapping("bill/ajaxQueryBillListByOpenid.do")
	public ModelAndView ajaxQueryBillListByOpenid(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result ;
		try {
			

			String openid = request.getParameter("openid");
			LmUserModel user = userWechatService.findLmUserByOpenId(openid);
			
			LmBillModel model = new LmBillModel();
			model.setUserid(user.getId());
			model.setSystemid(user.getSystemid());
			result = billService.queryBillList(model);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	@RequestMapping("admin/bill/ajaxQueryBillWaterList.do")
	public ModelAndView ajaxQueryBillWaterList(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result ;
		try {
			
			BillModelExtendBean model = new BillModelExtendBean();
			LmOperatorModel op = getCurrentLoginOperator();
			model.setSystemid(op.getSystemid());
			
			
			Integer meterid = Integer.parseInt(request.getParameter("meterid"));
			model.setMeterid(meterid);
			model.setOrderby(" billmonth desc ");
			result = billService.queryBillWaterList(model);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	
}
