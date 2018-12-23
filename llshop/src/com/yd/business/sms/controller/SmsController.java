package com.yd.business.sms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.ApiException;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.sms.bean.SmsBean;
import com.yd.business.sms.bean.SmsCustomerBean;
import com.yd.business.sms.bean.SmsSignBean;
import com.yd.business.sms.service.ISmsCustomerService;
import com.yd.business.sms.service.ISmsService;
import com.yd.business.sms.service.ISmsSignService;
import com.yd.util.NumberUtil;
/**
 * 短信测试
 * @author Zhang
 *
 */
@Controller
public class SmsController extends BaseController {

	@Resource
	private ISmsService smsService;
	@Resource
	private ISmsSignService smsSignService;
	@Resource
	private ISmsCustomerService smsCustomerService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	
	@RequestMapping("/admin/sendsms.do")
	public ModelAndView sendMsg(HttpServletRequest request,HttpServletResponse response){
		try {
			String smsSign = NumberUtil.toString(request.getParameter("sign_name"));
			String rec_num = NumberUtil.toString(request.getParameter("rec_num"));
			String temp_id = NumberUtil.toString(request.getParameter("temp_id"));
			String params = NumberUtil.toString(request.getParameter("params"));
			smsService.sendAliMsg("normal", smsSign, rec_num, temp_id,params);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 打开消息模板新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/toInsertSmsPage.do")
	public ModelAndView toInsertSmsPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return new ModelAndView("/page/pc/iframe_sms_insert.jsp");
	}
	/**
	 * 打开消息签名新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/toInsertSmsSignPage.do")
	public ModelAndView toInsertSmsSignPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return new ModelAndView("/page/pc/iframe_smssign_insert.jsp");
	}
	/**
	 * 打开客户消息模板新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/toInsertSmsCustPage.do")
	public ModelAndView toInsertSmsCustPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SmsBean> listSms = smsService.listSms(new SmsBean());
			List<SmsSignBean> listSign = smsSignService.listSmsSign(new SmsSignBean());
			List<CustomerBean> listCust = customerService.listCustomer(new CustomerBean());
			map.put("listSms", listSms);
			map.put("listSign", listSign);
			map.put("listCust", listCust);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_sms_customer_insert.jsp",map);
	}
	/**
	 * 消息模板新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/insertSms.do")
	public ModelAndView insertSms(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsBean bean = new SmsBean();
			bean.setOrder_code(NumberUtil.toString(request.getParameter("order_code")));
			bean.setTemplate_name(NumberUtil.toString(request.getParameter("template_name")));
			bean.setTemplate_id(NumberUtil.toString(request.getParameter("template_id")));
			bean.setAdd_time(NumberUtil.toString(request.getParameter("add_time")));
			bean.setStatus(SmsBean.STATUS_Y);
			bean.setContent(NumberUtil.toString(request.getParameter("content")));
			smsService.insertSms(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/admin/listSms.do");
	}
	/**
	 * 消息模板删除
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/deleteSms.do")
	public ModelAndView deleteSms(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsBean bean = new SmsBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			smsService.deleteSms(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/admin/listSms.do");
	}
	/**
	 * 消息模板列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/listSms.do")
	public ModelAndView listSms(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsBean bean = new SmsBean();
			if(!NumberUtil.empty(request.getParameter("status")))bean.setStatus(NumberUtil.toInt(request.getParameter("status")));
			bean.setTemplate_id(NumberUtil.toString(request.getParameter("template_id")));
			List<SmsBean> list = smsService.listSms(bean);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_sms.jsp", map);
	}
	/**
	 * 消息模板单个查询
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/findSms.do")
	public ModelAndView findSms(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsBean bean = smsService.findById(NumberUtil.toInt(request.getParameter("id")));
			map.put("sms", bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_sms.jsp", map);
	}
	/**
	 * 消息签名新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/insertSmsSign.do")
	public ModelAndView insertSmsSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsSignBean bean = new SmsSignBean();
			//order_code,sign_name,apply_time,apply_commen,sign_type,status
			bean.setOrder_code(NumberUtil.toString(request.getParameter("order_code")));
			bean.setSign_name(NumberUtil.toString(request.getParameter("sign_name")));
			bean.setApply_time(NumberUtil.toString(request.getParameter("apply_time")));
			bean.setApply_commen(NumberUtil.toString(request.getParameter("apply_commen")));
			bean.setSign_type(NumberUtil.toString(request.getParameter("sign_type")));
			bean.setStatus(SmsSignBean.STATUS_Y);
			smsSignService.insertSmsSign(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_smssign.jsp");
	}
	/**
	 * 消息签名删除
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/deleteSmsSign.do")
	public ModelAndView deleteSmsSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsSignBean bean = new SmsSignBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			smsSignService.deleteSmsSign(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_smssign.jsp");
	}
	/**
	 * 消息签名列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/listSmsSign.do")
	public ModelAndView listSmsSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsSignBean bean = new SmsSignBean();
			if(!NumberUtil.empty(request.getParameter("status")))bean.setStatus(NumberUtil.toInt(request.getParameter("status")));
			List<SmsSignBean> list = smsSignService.listSmsSign(bean);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_smssign.jsp",map);
	}
	/**
	 * 消息签名单个查询
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/findSmsSign.do")
	public ModelAndView findSmsSign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsSignBean bean = smsSignService.findByid(NumberUtil.toInt(request.getParameter("id")));
			map.put("smsSign", bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_smssign_insert.jsp", map);
	}
	/**
	 * 发送消息测试
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/sendSmsTest.do")
	public ModelAndView sendSmsTest(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsBean bean = new SmsBean();
			bean.setStatus(SmsBean.STATUS_Y);
			
			SmsSignBean signBean = new SmsSignBean();
			signBean.setStatus(SmsSignBean.STATUS_Y);
			List<SmsBean> list = smsService.listSms(bean);
			List<SmsSignBean> listSign = smsSignService.listSmsSign(signBean);
			map.put("list", list);
			map.put("signList", listSign);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_smstest.jsp", map);
	}
	/**
	 * 客户短信模板管理
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/listSmsCust.do")
	public ModelAndView listSmsCust(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsCustomerBean bean = new SmsCustomerBean();
			List<SmsCustomerBean> list = smsCustomerService.listSmsCust(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_sms_customer.jsp",map);
	}
	/**
	 * 客户短信模板新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/insertSmsCust.do")
	public ModelAndView insertSmsCust(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsCustomerBean bean = new SmsCustomerBean();
			bean.setCustomerid(NumberUtil.toInt(request.getParameter("custid")));
			bean.setSmsType(NumberUtil.toString(request.getParameter("smstype")));
			bean.setSmsFreeSignName(NumberUtil.toString(request.getParameter("smsFreeSignName")));
			bean.setSmsTemplateCode(NumberUtil.toString(request.getParameter("smsTemplateCode")));
			CustomerBean cust = customerService.findCustomerById(NumberUtil.toInt(request.getParameter("custid")));
			bean.setCustomername(cust.getName());
			smsCustomerService.insertSmsCust(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/admin/listSmsCust.do");
	}
	/**
	 * 修改客户短信模板
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/updateSmsCust.do")
	public ModelAndView updateSmsCust(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsCustomerBean bean = new SmsCustomerBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			bean.setSmsType(NumberUtil.toString(request.getParameter("smstype")));
			bean.setSmsFreeSignName(NumberUtil.toString(request.getParameter("smsFreeSignName")));
			bean.setSmsTemplateCode(NumberUtil.toString(request.getParameter("smsTemplateCode")));
			smsCustomerService.updateSmsCust(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/admin/listSmsCust.do");
	}
	/**
	 * 删除客户短信模板
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/deleteSmsCust.do")
	public ModelAndView deleteSmsCust(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SmsCustomerBean bean = new SmsCustomerBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			smsCustomerService.deleteSmsCust(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/admin/listSmsCust.do");
	}
	/**
	 * 根据ID查询客户短信模板信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/findSmsCustById.do")
	public ModelAndView findSmsCustById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SmsCustomerBean bean = smsCustomerService.querySmsCustomerById(NumberUtil.toInt(request.getParameter("id")));
			map.put("bean", bean);
			List<SmsBean> listSms = smsService.listSms(new SmsBean());
			List<SmsSignBean> listSign = smsSignService.listSmsSign(new SmsSignBean());
			List<CustomerBean> listCust = customerService.listCustomer(new CustomerBean());
			map.put("listSms", listSms);
			map.put("listSign", listSign);
			map.put("listCust", listCust);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/iframe_sms_customer_insert.jsp",map);
	}
	
	@RequestMapping("/sms/execWaitSendSMS.do")
	public ModelAndView execWaitSendSMS(HttpServletRequest request,HttpServletResponse response){
		try{
			
			taskExecutor.execute(new BaseRunable() {
				
				@Override
				public void runMethod() {
					smsService.queryWaitAndSend();
				}
			});
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("sms/ajax/querySMSCustomer.do")
	public ModelAndView querySMSCustomer(HttpServletRequest request,HttpServletResponse response){
		try {
			
			SmsCustomerBean bean = new SmsCustomerBean();
			List<SmsCustomerBean> list = smsCustomerService.listSmsCust(bean );
			
			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
}
