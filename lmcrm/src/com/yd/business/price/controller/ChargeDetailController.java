/**
 * 
 */
package com.yd.business.price.controller;

import java.util.ArrayList;
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

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PrintBean;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
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
	
	

	@RequestMapping("**/admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do")
	public ModelAndView ajaxUpdateChargeDetailBrushFlagToSuccess(HttpServletRequest request,HttpServletResponse response) {
		try {
			String u_cardno = request.getParameter("u_cardno");
			String useDate = request.getParameter("useDate");
			
			UserInfoBean user = userInfoService.findUserByCardNo(Integer.parseInt(u_cardno));
			
			ChargeDetailBean last = chargeDetailService.findLastChargeDetailByUser(user.getU_no());
			if(last.getCd_brushflag().intValue() == ChargeDetailBean.BRUSHFLAG_NO) {
				Date brushDate = DateUtil.parseDateOnlyDate(useDate);
				chargeDetailService.updateChargeDetailBrushFlagToSuccess(last.getCd_id(),brushDate);
			}

		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
		
	}
	
	

	/**
	 *  界面查询用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/price/chargeDetail/ajaxQueryChargeListByUser.do")
	public ModelAndView ajaxQueryChargeListByUser(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean operator = getCurrentLoginOperator();
			String u_phone = request.getParameter("u_phone");
			String u_name = request.getParameter("u_name");
			String u_paperwork = request.getParameter("u_paperwork");
			String u_buildingid = request.getParameter("u_buildingid");
			String u_areaid = request.getParameter("u_areaid");
			String u_cardno = request.getParameter("u_cardno");
			
			UserInfoBean bean = new UserInfoBean();
			bean.setU_operatorid(operator.getO_id());
			bean.setU_phone(u_phone);
			bean.setU_name(u_name);
			bean.setU_paperwork(u_paperwork);
			if(StringUtil.isNotNull(u_buildingid)) {
				bean.setU_buildingid(Long.parseLong(u_buildingid));
			}else if(StringUtil.isNotNull(u_areaid)) {
				bean.setAreaid(Long.parseLong(u_areaid));
			}else if(StringUtil.isNotNull(u_cardno)) {
				bean.setU_cardno(Integer.parseInt(u_cardno));
			}
			List<ChargeDetailBean> list = new ArrayList<ChargeDetailBean>();
			List<UserInfoBean> userList = userInfoService.queryUserInfo(bean);
			
			for(UserInfoBean user : userList) {
				List<ChargeDetailBean> tempList = chargeDetailService.queryChargeListByUserId(user.getU_id());
				list.addAll(tempList);
			} 
			
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
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
			
			System.out.println(jso.toString() );
			writeJson(response, map);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
		
	}
	
	
}
