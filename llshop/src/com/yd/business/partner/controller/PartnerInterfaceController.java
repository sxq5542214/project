/**
 * 
 */
package com.yd.business.partner.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.partner.bean.PartnerInterfaceBean;
import com.yd.business.partner.service.IPartnerInterfaceService;
import com.yd.business.partner.util.PartnerUtil;

/**
 * @author ice
 *
 */
@Controller
public class PartnerInterfaceController extends BaseController {
	@Resource
	private IPartnerInterfaceService partnerInterfaceService;
	
	
	@RequestMapping("/p/intf/handlePartnerOrder.htm")
	public ModelAndView handlePartnerOrder(HttpServletRequest request,HttpServletResponse response){
		
		try{
			long time = System.currentTimeMillis();
			Map<String,String> params = getRequestParamsMap(request);
			String ip = getRemoteHost(request);
			params.put("spbill_create_ip", ip);
			log.info("handlePartnerInterface:"+params);
			
			
			//get  测试
			if(request.getMethod().equalsIgnoreCase("GET")){
				
				
//				PartnerInterfaceBean partnerBean = partnerInterfaceService.handlePartnerInterface(params, request.getMethod());
//				String responseXML = PartnerUtil.convertPartnerInterfaceBeanToXML(partnerBean);
				
				String responseXML = PartnerUtil.createFaildXML(PartnerInterfaceBean.RESULT_CODE_HTTP_METHOD_GET_ERROR, "错误的请求方式，请使用HTTP POST方式");
				writeJson(response, responseXML);
				long cost = System.currentTimeMillis() - time;
				log.info(ip+",cost: "+ cost +"ms, handlePartnerInterfaceResponse:"+responseXML);

			}else if(request.getMethod().equalsIgnoreCase("POST")){
				//post
				PartnerInterfaceBean partnerBean = partnerInterfaceService.handlePartnerInterface(params, request.getMethod());
				String responseXML = PartnerUtil.convertPartnerInterfaceBeanToXML(partnerBean);
				
				writeJson(response, responseXML);
				long cost = System.currentTimeMillis() - time;
				log.info(ip+",cost: "+ cost +"ms, handlePartnerInterfaceResponse:"+responseXML);
			}
			
			
			
		}catch (Exception e) {
			log.error(e, e);
			String responseXML = PartnerUtil.createFaildXML( PartnerInterfaceBean.RESULT_CODE_SYSTEM_INTERNAL_ERROR ,"系统内部错误");
			writeJson(response, responseXML);
		}
		
		return null;
	}
	

	
	@RequestMapping("/p/intf/simpleQueryCustomerBalance.htm")
	public ModelAndView simpleQueryCustomerBalance(HttpServletRequest request,HttpServletResponse response){
		
		try{
			long time = System.currentTimeMillis();
			Map<String,String> params = getRequestParamsMap(request);
			String ip = getRemoteHost(request);
//			params.put("spbill_create_ip", ip);
			log.info("queryCustomerBalance:"+params);
			
			int customer_id = Integer.parseInt(params.get("customer_id"));
			String partner_code = params.get("partner_code");
			
			PartnerInterfaceBean result = partnerInterfaceService.queryCustomerBalance(customer_id, partner_code);

			String responseXML = PartnerUtil.convertPartnerInterfaceBeanToXML(result);
			writeJson(response, responseXML);
			long cost = System.currentTimeMillis() - time;
			log.info(ip+",cost: "+ cost +"ms, simpleQueryCustomerBalance:"+responseXML);
			
		}catch (Exception e) {
			log.error(e, e);
			String responseXML = PartnerUtil.createFaildXML( PartnerInterfaceBean.RESULT_CODE_SYSTEM_INTERNAL_ERROR ,"系统内部错误");
			writeJson(response, responseXML);
		}
		
		return null;
	}
	
	

	@RequestMapping("/p/intf/handlePartnerAsyncOrder.htm")
	public ModelAndView handlePartnerAsyncOrder(HttpServletRequest request,HttpServletResponse response){
		
		try{
			long time = System.currentTimeMillis();
			Map<String,String> params = getRequestParamsMap(request);
			String ip = getRemoteHost(request);
			params.put("spbill_create_ip", ip);
			log.info("handlePartnerAsyncOrder:"+params);
			
			
			//get  测试
			if(request.getMethod().equalsIgnoreCase("GET")){
				
				
//				PartnerInterfaceBean partnerBean = partnerInterfaceService.handlePartnerInterface(params, request.getMethod());
//				String responseXML = PartnerUtil.convertPartnerInterfaceBeanToXML(partnerBean);
				
				String responseXML = PartnerUtil.createFaildXML(PartnerInterfaceBean.RESULT_CODE_HTTP_METHOD_GET_ERROR, "错误的请求方式，请使用HTTP POST方式");
				writeJson(response, responseXML);
				long cost = System.currentTimeMillis() - time;
				log.info(ip+",cost: "+ cost +"ms, handlePartnerInterfaceResponse:"+responseXML);

			}else if(request.getMethod().equalsIgnoreCase("POST")){
				//post
				PartnerInterfaceBean partnerBean = partnerInterfaceService.handlePartnerInterface(params, request.getMethod());
				String responseXML = PartnerUtil.convertPartnerInterfaceBeanToXML(partnerBean);
				
				writeJson(response, responseXML);
				long cost = System.currentTimeMillis() - time;
				log.info(ip+",cost: "+ cost +"ms, handlePartnerInterfaceResponse:"+responseXML);
			}
			
			
			
		}catch (Exception e) {
			log.error(e, e);
			String responseXML = PartnerUtil.createFaildXML( PartnerInterfaceBean.RESULT_CODE_SYSTEM_INTERNAL_ERROR ,"系统内部错误");
			writeJson(response, responseXML);
		}
		
		return null;
	}
	

	@RequestMapping("/p/intf/queryPartnerOrder.htm")
	public ModelAndView queryPartnerOrder(HttpServletRequest request,HttpServletResponse response){
		
		try{
			long time = System.currentTimeMillis();
			Map<String,String> params = getRequestParamsMap(request);
			String ip = getRemoteHost(request);
			log.info("queryPartnerOrder:"+params);
			
			int customer_id = Integer.parseInt(params.get("customer_id"));
			String partner_code = params.get("partner_code");
			String partner_out_trade_no = params.get("partner_out_trade_no");
			
			PartnerOrderProductBean order = partnerInterfaceService.queryPartnerOrder(customer_id, partner_code,partner_out_trade_no);

			String responseXML = PartnerUtil.convertPartnerInterfaceBeanToXML(order);
			writeJson(response, responseXML);
			long cost = System.currentTimeMillis() - time;
			log.info(ip+",cost: "+ cost +"ms, queryPartnerOrder:"+responseXML);
			
		}catch (Exception e) {
			log.error(e, e);
			String responseXML = PartnerUtil.createFaildXML( PartnerInterfaceBean.RESULT_CODE_SYSTEM_INTERNAL_ERROR ,"系统内部错误");
			writeJson(response, responseXML);
		}
		
		return null;
	}
}
