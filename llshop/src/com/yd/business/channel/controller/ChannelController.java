/**
 * 
 */
package com.yd.business.channel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelCustomerBean;
import com.yd.business.channel.bean.ChannelProductBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.isp.bean.ISPInterfaceBean;

/**
 * @author ice
 *
 */
@Controller
public class ChannelController extends BaseController {

	public final String PAGE_CHANNEL_LIST = "/page/pc/channel/iframe_channel_list.jsp";
	public final String PAGE_CHANNEL_CUSTOMER_PRODUCT_LIST = "/page/pc/channel/channel_customer_product_list.jsp";
	
	
	@Resource
	private IChannelService channelService;
	@Resource
	private IChannelProductService channelProductService;
	@Resource
	private ICustomerService customerService;
	
	@RequestMapping("channel/orderProductByChannel.do")
	public ModelAndView orderProductByChannel(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
			int channel_id = getIntValueByRequest("channel_id", request);
			String order_code = request.getParameter("order_code");
			String phone = request.getParameter("phone");
			String param = request.getParameter("param");
			int product_id = getIntValueByRequest("product_id", request);
			String server_name = request.getParameter("server_name");
			
			log.debug("channelServer begin: "+ getRequestParamsMap(request));
			
			ISPInterfaceBean isp = channelProductService.orderProductByChannel(channel_id, order_code, phone, param, product_id, server_name);
			
			writeJson(response, isp);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}

	
	@RequestMapping("**/admin/channel/toChannelListPage.do")
	public ModelAndView toChannelListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			List<ChannelBean> list = channelService.queryChannel(null);
			
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("list", list);
			
			return new ModelAndView(PAGE_CHANNEL_LIST, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	
	@RequestMapping("**/admin/channel/addChannelBalance.do")
	public ModelAndView updateChannelBalance(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String channel_id = request.getParameter("channel_id");
			String balance = request.getParameter("add_balance");
			String remark = request.getParameter("remark");
			
			channelService.updateChannelCalcBalance(Integer.parseInt(channel_id), Integer.parseInt(balance), remark);
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		return null;
	}
	
	@RequestMapping("**/admin/channel/updateChannelProductStatus.do")
	public ModelAndView updateChannelProductStatus(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			
			ChannelProductBean bean = channelProductService.findChannelProductById(Integer.parseInt(id));
			bean.setStatus(Integer.parseInt(status));
			channelProductService.updateChannelProduct(bean);
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		return null;
	}
	
	@RequestMapping("**/admin/channel/toChannelCustomerProductPage.do")
	public ModelAndView toChannelCustomerProductPage(HttpServletRequest request, HttpServletResponse response){
		try {
			String customer_id = request.getParameter("customer_id");
			
			CustomerBean customer = customerService.findCustomerById(Integer.parseInt(customer_id));
			ChannelBean bean = new ChannelBean();
			bean.setStatus(ChannelBean.STATUS_ENABLE);
			List<ChannelBean> channelList = channelService.queryChannel(bean );
			ChannelCustomerBean cb = new ChannelCustomerBean();
			cb.setCustomer_id(customer.getId());
			List<ChannelCustomerBean> channelCustomerList = channelProductService.queryChannelCustomer(cb  );
			ChannelProductBean cpb = new ChannelProductBean();
			cpb.setCustomer_id(customer.getId());
			List<ChannelProductBean> channelProductList = channelProductService.queryChannelProduct(cpb );
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customer", customer);
			model.put("channelCustomerList", channelCustomerList);
			model.put("channelList", channelList);
			model.put("channelProductList", channelProductList);
			
			return new ModelAndView(PAGE_CHANNEL_CUSTOMER_PRODUCT_LIST, model);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/channel/updateChannelCustomerChannel.do")
	public ModelAndView updateChannelCustomerChannel(HttpServletRequest request,HttpServletResponse response){
		try {

			String id = request.getParameter("id");
			String channel_id = request.getParameter("channel_id");
			String channel_name = request.getParameter("channel_name");
			
			
			ChannelCustomerBean bean = new ChannelCustomerBean();
			bean.setId(Integer.parseInt(id));
			bean.setChannel_id(Integer.parseInt(channel_id));
			bean.setChannel_name(channel_name);
			channelProductService.updateChannelCustomer(bean );
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		return null;
	}

	
	@RequestMapping("**/admin/channel/updateCustomerProductChannel.do")
	public ModelAndView updateCustomerProductChannel(HttpServletRequest request,HttpServletResponse response){
		try {

			String id = request.getParameter("id");
			String channel_id = request.getParameter("channel_id");
			String channel_name = request.getParameter("channel_name");
			
			
			ChannelProductBean bean = new ChannelProductBean();
			bean.setId(Integer.parseInt(id));
			bean.setChannel_id(Integer.parseInt(channel_id));
			bean.setChannel_name(channel_name);
			channelProductService.updateChannelProduct(bean );
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		return null;
	}
	
	
}
