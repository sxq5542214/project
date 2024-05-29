/**
 * 
 */
package com.yd.business.price.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.conn.Wire;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PrintBean;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPaymentModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class ChargeDetailController extends BaseController {
	
	@Autowired
	private IChargeDetailService chargeDetailService;
	@Autowired
	private IUserInfoService userInfoService;
	

	@RequestMapping("**/admin/chargeDetail/ajaxUpdateChargeDetailStatusToSuccess.do")
	public ModelAndView ajaxUpdateChargeDetailStatusToSuccess(HttpServletRequest request,HttpServletResponse response) {
		try {
			String cdid = request.getParameter("cdid");
			chargeDetailService.updateChargeDetailToSuccess(cdid);

		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
		
	}
	
	

//	@RequestMapping("**/admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do")
//	public ModelAndView ajaxUpdateChargeDetailBrushFlagToSuccess(HttpServletRequest request,HttpServletResponse response) {
//		try {
//			String u_cardno = request.getParameter("u_cardno");
//			String useDate = request.getParameter("useDate");
//			String u_id = request.getParameter("u_id");
//			UserInfoBean user = null;
//
//			UserInfoBean bean = new UserInfoBean();
//			if(StringUtil.isNotNull(u_id)) {
//				user = userInfoService.findUserById(Long.parseLong(u_id));
//			}else {
//				bean.setU_cardno(Integer.parseInt(u_cardno));
//				
//				List<UserInfoBean> userList = userInfoService.queryUserInfo(bean);
//				if(userList.size() != 1 ) {
//					bean.setDataList(userList);
//					bean.setResultCode(UserInfoBean.RESULTCODE_FAILD);
//					bean.setResultDesc("用户卡号"+ u_cardno +"有重复！");
//					writeJson(response, bean);
//					return null;
//				}else {
//					user = userList.get(0);
//				}
//			
//			}
//			ChargeDetailBean last = chargeDetailService.findLastChargeDetailByUserId(user.getU_id());
//			if(last.getCd_brushflag().intValue() == ChargeDetailBean.BRUSHFLAG_NO) {
//				Date brushDate = DateUtil.parseDateOnlyDate(useDate);
//				chargeDetailService.updateChargeDetailBrushFlagToSuccess(last.getCd_id(),brushDate);
//			}
//
//		} catch (Exception e) {
//			log.error(e, e);
//		}
//		return null;
//		
//	}
	
	

	/**
	 *  界面查询用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/charge/ajaxQueryChargeList.do")
	public ModelAndView ajaxQueryChargeList(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result ;
		try {
			
			LmPaymentModel model = new LmPaymentModel();
			LmOperatorModel op = getCurrentLoginOperator();
			model.setSystemid(op.getSystemid());
			model.setMetercode(request.getParameter("meterCode"));
			
			model.setUserid(Integer.parseInt(request.getParameter("userid")));
			model.setPaystate(ChargeDetailBean.PAY_STATUS_SUCCESS);
			result = chargeDetailService.queryChargeList(model);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}

	/**
	 *  界面查询用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/charge/ajaxChargeBalance.do")
	public ModelAndView ajaxChargeBalance(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result = new IOTWebDataBean();
		try {
			
			LmPaymentModel model = new LmPaymentModel();
			LmOperatorModel op = getCurrentLoginOperator();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), model);
			model.setSystemid(op.getSystemid());
			model.setOperatorid(op.getId());
			model.setOperatorname(op.getRealname());
			model.setPaystate((byte) 2); //WEB界面充值记为已支付
			int num = chargeDetailService.handleChargeBalance(model);
			result.setData(model);
			
		} catch (Exception e) {
			log.error(e, e);
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	/**
	 * 	查询打印数据
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryChargeDetailByPrint.do")
	public ModelAndView ajaxQueryChargeDetailByPrint(HttpServletRequest request,HttpServletResponse response) {
		try {
			String cdid = request.getParameter("cdid");
			
			System.out.println(cdid);
			
			PrintBean print = chargeDetailService.generatePrintBean(Long.parseLong(cdid));
			
			Map<String,PrintBean[]> map = new HashMap<String, PrintBean[]>();
			map.put("Master", new PrintBean[] {print});

			JSONObject jso = new JSONObject(map);
//			String str = "{\"Master\": [{\"txtUserNo1\": 123,\"txtUserName1\": \"3333\" ,\"txtPriceKind1\":111,\"txtPrice11\":222,\"txtUserAddress1\":\"XX地址\",\"txtReadingDate1\":\"2021\" ,\"txtStartDate\":\"11\",\"txtEndDate\":\"11\",\"txtStartAmount1\":\"11\",\"txtChargeAmount11\":\"11\",\"txtChargeMoney11\":\"11\",\"txtEndAmount1\":\"11\",\"txtPaidMoney\":\"11\",\"txtBalance\":\"11\",\"txtOperator12\":\"11\",\"txtOperator11\":\"11\",\"txtChargeDate1\":\"11\",\"txtTotalCharge1\":\"11\",\"txtBigMoney1\":\"11\",\"txtOtherMoney11\":\"11\",\"txtOtherMoney12\":\"11\",\"txtChargeOrder1\":\"11\"} ]}";
			
			// 查询后，设置打印状态为已打印
			chargeDetailService.updateChargeDetailPrintStatus(Long.parseLong(cdid), ChargeDetailBean.PRINT_STATUS_YES);
			
			System.out.println(jso.toString() );
			writeJson(response, map);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
		
	}
	
	

	/**
	 * 	查询当月缴费总额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryMonthChargeAmoutSumByDashboard.do")
	public ModelAndView ajaxQueryMonthChargeAmoutSumByDashboard(HttpServletRequest request,HttpServletResponse response) {

		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			
			Integer opid = null;
			String operatorid = request.getParameter("operatorid");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
			String billMonth = DateUtil.formatMonth(new Date());
			result = chargeDetailService.queryMonthChargeAmoutSum( billMonth, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	/**
	 * 	查询当日缴费总额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryDayChargeAmoutSumByDashboard.do")
	public ModelAndView ajaxQueryDayChargeAmoutSumByDashboard(HttpServletRequest request,HttpServletResponse response) {

		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			
			Integer opid = null;
			String operatorid = request.getParameter("operatorid");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
			String today = DateUtil.formatDateOnlyDate(new Date());
			result = chargeDetailService.queryDayChargeAmoutSum( today, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	/**
	 * 	查询当日缴费总额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryDayChargeAmoutMeterCountByDashboard.do")
	public ModelAndView ajaxQueryDayChargeAmoutMeterCountByDashboard(HttpServletRequest request,HttpServletResponse response) {

		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			
			Integer opid = null;
			String operatorid = request.getParameter("operatorid");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
			String daystr = DateUtil.getNowOlnyDateStr();
			result = chargeDetailService.queryDayBuyAmountMeterCount( daystr, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	/**
	 * 	查询当月缴费总额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryMonthChargeAmoutMeterCountByDashboard.do")
	public ModelAndView ajaxQueryMonthChargeAmoutMeterCountByDashboard(HttpServletRequest request,HttpServletResponse response) {

		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			
			Integer opid = null;
			String operatorid = request.getParameter("operatorid");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
			String billmoth = DateUtil.formatMonth(new Date());
			result = chargeDetailService.queryMonthBuyAmountMeterCount( billmoth, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	
	/**
	 * 	查询当月缴费总额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryDayBuyAmountSumListOfMonthByDashboard.do")
	public ModelAndView ajaxQueryDayBuyAmountSumListOfMonthByDashboard(HttpServletRequest request,HttpServletResponse response) {
	
		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();

			Integer opid = null;
			Integer monthNum = 0;
			String operatorid = request.getParameter("operatorid");
			String month = request.getParameter("month");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
			if(StringUtil.isNotNull(month)) {
				monthNum = Integer.parseInt(month);
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, monthNum);
			
			String billmoth = DateUtil.formatMonth(c.getTime());
			result = chargeDetailService.queryDayBuyAmountSumListOfMonth( billmoth, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}


	
	/**
	 * 	查询近2月每日缴费金额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryDayBuyAmountListDataByDashboard.do")
	public ModelAndView ajaxQueryDayBuyAmountListDataByDashboard(HttpServletRequest request,HttpServletResponse response) {
	
		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();

			Integer opid = null;
//			Integer monthNum = 0;
			String operatorid = request.getParameter("operatorid");
//			String month = request.getParameter("month");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
//			if(StringUtil.isNotNull(month)) {
//				monthNum = Integer.parseInt(month);
//			}
//			Calendar c = Calendar.getInstance();
//			c.add(Calendar.MONTH, monthNum);
			
			String billmoth = "";  //DateUtil.formatMonth(c.getTime());
			result = chargeDetailService.queryDayBuyAmountListData( billmoth, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}

	/**
	 * 	查询当月及上月的每日缴费额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryDayBuyAmountListDataByAdminDashboard.do")
	public ModelAndView ajaxQueryDayBuyAmountListDataByAdminDashboard(HttpServletRequest request,HttpServletResponse response) {
	
		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();

			Integer opid = null;
//			Integer monthNum = 0;
			String operatorid = request.getParameter("operatorid");
//			String month = request.getParameter("month");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
//			if(StringUtil.isNotNull(month)) {
//				monthNum = Integer.parseInt(month);
//			}
//			Calendar c = Calendar.getInstance();
//			c.add(Calendar.MONTH, monthNum);
			
			String billmoth = "";  //DateUtil.formatMonth(c.getTime());
			result = chargeDetailService.queryDayBuyAmountListData( billmoth, op.getSystemid(), opid);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	
	/**
	 * 	查询当月及上月的每日缴费额
	 */
	@RequestMapping("**/admin/chargeDetail/ajaxQueryDayBuyCountListDataByDashboard.do")
	public ModelAndView ajaxQueryDayBuyCountListDataByDashboard(HttpServletRequest request,HttpServletResponse response) {
	
		IOTWebDataBean result ;
		try {
			LmOperatorModel op = getCurrentLoginOperator();

			Integer opid = null;
//			Integer monthNum = 0;
			String operatorid = request.getParameter("operatorid");
//			String month = request.getParameter("month");
			if(StringUtil.isNotNull(operatorid)) {
				opid = op.getId();
			}
//			if(StringUtil.isNotNull(month)) {
//				monthNum = Integer.parseInt(month);
//			}
//			Calendar c = Calendar.getInstance();
//			c.add(Calendar.MONTH, monthNum);
			
			String billmoth = "";  //DateUtil.formatMonth(c.getTime());
			result = chargeDetailService.queryDayBuyCountListData( billmoth, op.getSystemid(), opid);
			
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
