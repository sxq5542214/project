/**
 * 
 */
package com.yd.business.msg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.msg.bean.SmsTxSendInfoLogBean;
import com.yd.business.msg.bean.SmsTxSendPhoneLogBean;
import com.yd.business.msg.service.ISMSService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.other.bean.ConfigAttributeBean;

/**
 * @author ice
 *
 */
@Controller
public class SmsController extends BaseController {
	@Autowired
	private ISMSService smsService;
	
	
	@RequestMapping("admin/sms/querySmsTemplateList.do")
	public ModelAndView querySmsTemplateList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<ConfigAttributeBean> list = smsService.querySmsTemplateList();
			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	@RequestMapping("admin/sms/querySmsSendInfoList.do")
	public ModelAndView querySmsSendInfoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
//			OperatorBean op = getCurrentLoginOperator();
//			List<SmsTxSendInfoLogBean> list = smsService.querySmsSendInfoList(op.getO_companyid());
//			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("admin/sms/querySmsSendPhoneList.do")
	public ModelAndView querySmsSendPhoneList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String requestid = request.getParameter("request_id");
			List<SmsTxSendPhoneLogBean> list = smsService.querySmsSendPhoneList(requestid);
			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("admin/sms/sendSms.do")
	public ModelAndView sendSms(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String templateId = request.getParameter("templateId");
			String smsName = request.getParameter("smsName");
			String addressIds = request.getParameter("addressIds");
			String[] params = request.getParameterValues("params[]");
			String content= request.getParameter("content");;
			String[] phones = null ;
//			OperatorBean op = getCurrentLoginOperator();
//			
//			String result = smsService.sendTXsms(addressIds, params, templateId, content, smsName, op);
//			
//			writeJson(response, result);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "error");
		}
		return null;
	}
	
	
	
}
