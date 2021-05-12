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
import com.yd.business.user.bean.UserInfoBean;

@Controller
public class CardInfoController extends BaseController {
	
	@Autowired
	private ICardInfoService cardInfoService;


	/**
	 *  界面查询未开户用户列表
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
	
	
	
	
}
