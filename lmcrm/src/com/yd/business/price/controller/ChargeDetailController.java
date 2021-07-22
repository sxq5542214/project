/**
 * 
 */
package com.yd.business.price.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.ChargeDetailBean;
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
			String no = request.getParameter("u_no");
			String useDate = request.getParameter("useDate");
			ChargeDetailBean last = chargeDetailService.findLastChargeDetailByUser(Long.parseLong(no));
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
			String u_no = request.getParameter("u_no");
			
			UserInfoBean bean = new UserInfoBean();
			bean.setU_operatorid(operator.getO_id());
			bean.setU_phone(u_phone);
			bean.setU_name(u_name);
			bean.setU_paperwork(u_paperwork);
			if(StringUtil.isNotNull(u_buildingid)) {
				bean.setU_buildingid(Long.parseLong(u_buildingid));
			}else if(StringUtil.isNotNull(u_areaid)) {
				bean.setAreaid(Long.parseLong(u_areaid));
			}else if(StringUtil.isNotNull(u_no)) {
				bean.setU_no(Long.parseLong(u_no));
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
	
}
