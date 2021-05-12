/**
 * 
 */
package com.yd.business.price.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.price.service.IChargeDetailService;

/**
 * @author ice
 *
 */
@Controller
public class ChargeDetailController extends BaseController {
	
	@Autowired
	private IChargeDetailService chargeDetailService;
	
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
	
}
