package com.yd.business.card.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.card.bean.CardInfoBean;
import com.yd.business.card.service.ICardInfoService;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.util.StringUtil;

@Controller
public class CardInfoController extends BaseController {
	
	@Autowired
	private ICardInfoService cardInfoService;

	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IChangeMeterService changeMeterService;
	

	/**
	 *  界面进行用户开户操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/card/ajaxOpenAccountCard.do")
	public ModelAndView ajaxOpenAccountCard(HttpServletRequest request,HttpServletResponse response){
		CardInfoBean bean  = new CardInfoBean();
		try {
			String dkid = request.getParameter("deviceKindId");
			String uid = request.getParameter("userId");
			String chargeMoney = request.getParameter("chargeMoney");
			BigDecimal money = new BigDecimal(chargeMoney);
			
			bean  = cardInfoService.generateCardInfoByOpenAccount(Long.parseLong(uid), Long.parseLong(dkid), money.intValue() );
			
		} catch (Exception e) {
			log.error(e, e);
			bean.setQueryStatus(BaseBean.QUERYSTATUS_ERROR);
			bean.setQueryResult(e.getMessage());
		}
		writeJson(response, bean );
		return null;
	}
	
	

	/**
	 *  界面进行用户开户操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/card/ajaxChargeMoneyCard.do")
	public ModelAndView ajaxChargeMoneyCard(HttpServletRequest request,HttpServletResponse response){
		CardInfoBean bean  = new CardInfoBean();
		try {
			String u_no = request.getParameter("u_no");
			String chargeMoney = request.getParameter("chargeMoney");
			BigDecimal money = new BigDecimal(chargeMoney);
			
			
			UserInfoBean user = userInfoService.findUserByNo(Long.parseLong(u_no));
			bean  = cardInfoService.generateCardInfoByChargeMoney( user.getU_id(), null , money.intValue() );
					
		} catch (Exception e) {
			log.error(e, e);
			bean.setQueryStatus(BaseBean.QUERYSTATUS_ERROR);
			bean.setQueryResult(e.getMessage());
		}
		writeJson(response, bean );
		return null;
	}
	
	

	/**
	 *  界面进行用户修改充值记录操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/card/ajaxUpdateLastChargeMoneyCard.do")
	public ModelAndView ajaxUpdateLastChargeMoneyCard(HttpServletRequest request,HttpServletResponse response){
		CardInfoBean bean  = new CardInfoBean();
		try {
			String u_no = request.getParameter("u_no");
			String updateChargeMoney = request.getParameter("updateChargeMoney");
			BigDecimal money = new BigDecimal(updateChargeMoney);
			
			
			UserInfoBean user = userInfoService.findUserByNo(Long.parseLong(u_no));
			bean  = cardInfoService.generateCardInfoByUpdateLastChargeMoney(user.getU_id(), null , money.intValue() );
					
		} catch (Exception e) {
			log.error(e, e);
			bean.setQueryStatus(BaseBean.QUERYSTATUS_ERROR);
			bean.setQueryResult(e.getMessage());
		}
		writeJson(response, bean );
		return null;
	}

	/**
	 *  界面进行用户补卡操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/card/ajaxRepairCard.do")
	public ModelAndView ajaxRepairCard(HttpServletRequest request,HttpServletResponse response){
		CardInfoBean bean  = new CardInfoBean();
		try {
			String u_no = request.getParameter("u_no");
			String brushFlag = request.getParameter("brushFlag");
			String repairCardMoney = request.getParameter("repairCardMoney");
			BigDecimal money = new BigDecimal(repairCardMoney);
			
			
			UserInfoBean user = userInfoService.findUserByNo(Long.parseLong(u_no));
			boolean flag = "1".equals(brushFlag) ? true:false;
			bean  = cardInfoService.generateCardInfoByRepairCard(user.getU_id(), null , money.intValue() ,flag);
					
		} catch (Exception e) {
			log.error(e, e);
			bean.setQueryStatus(BaseBean.QUERYSTATUS_ERROR);
			bean.setQueryResult(e.getMessage());
		}
		writeJson(response, bean );
		return null;
	}

	/**
	 *  界面进行换表维护操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/card/ajaxChangeMeterCard.do")
	public ModelAndView ajaxChangeMeterCard(HttpServletRequest request,HttpServletResponse response){
		CardInfoBean bean  = new CardInfoBean();
		try {
			String u_no = request.getParameter("u_no");
			String changeMeterMoney = request.getParameter("changeMeterMoney");
			String cm_oldmetercode = request.getParameter("cm_oldmetercode");
			String cm_type = request.getParameter("cm_type");
			String cm_remark = request.getParameter("cm_remark");
			String cm_newmetercode = request.getParameter("cm_newmetercode");
			String cm_newmeterno = request.getParameter("cm_newmeterno");
			Long device_kind = StringUtil.isNotNull(request.getParameter("device_kind"))? Long.parseLong(request.getParameter("device_kind")):-1  ;
			
			BigDecimal money = new BigDecimal(changeMeterMoney);
			
			OperatorBean op = getCurrentLoginOperator();
			// 写入换表维护表
			changeMeterService.createChangeMeter(Long.parseLong(u_no), new BigDecimal(cm_oldmetercode),new BigDecimal(cm_newmetercode),Long.parseLong(cm_newmeterno), Integer.parseInt(cm_type), op.getO_id(), cm_remark,device_kind);
			
			UserInfoBean user = userInfoService.findUserByNo(Long.parseLong(u_no));
			bean  = cardInfoService.generateCardInfoByChangeMeter(user.getU_id(), null , money.intValue()  );
					
		} catch (Exception e) {
			log.error(e, e);
			bean.setQueryStatus(BaseBean.QUERYSTATUS_ERROR);
			bean.setQueryResult(e.getMessage());
		}
		writeJson(response, bean );
		return null;
	}
	
	
	
	
}