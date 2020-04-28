/**
 * 
 */
package com.yd.business.alipay.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.alipay.bean.AliPayLogBean;
import com.yd.business.alipay.service.IAlipayService;
import com.yd.business.alipay.service.impl.AlipayServiceImpl;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.bean.CustomerConsumeInfoBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.customer.service.ICustomerConsumeInfoService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.service.IUserConsumeInfoService;

/**
 * @author ice
 *
 */
@Controller
public class AlipayController extends BaseController {
	
	@Resource
	private IAlipayService alipayService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private ICustomerConsumeInfoService customerConsumeInfoService;
	
	
	@RequestMapping("/alipay/wapPayServerNotify.do")
	public ModelAndView wapPayServerNotify(HttpServletRequest request,HttpServletResponse response){
		
		try{
			Map<String, String[]> map = request.getParameterMap();
			log.info("aliPayServerNotify "+ request.getMethod()+":"+map);
			
			//get是页面过来的,跳转到订购结果界面
			if(request.getMethod().equalsIgnoreCase("GET")){
				
				String no = request.getParameter("out_trade_no");
				
				CustomerConsumeInfoBean cconsume = customerConsumeInfoService.findCustomerConsumeInfo(no);
				
				if(cconsume != null){
					//先查客户充值的订单
					return new ModelAndView("/page/pc/iframe_recharge.jsp");
					
				}else{
					//再查订购的订单
					UserConsumeInfoBean consume = userConsumeInfoService.findUserConsumeInfo(no);
					Integer adminid = consume.getUser_id();
					
					//跳转到订购结果界面
					return new ModelAndView("order/supplierOrderProductByBalance.do?out_trade_code="+no+"&adminid="+adminid);
				}
			}else if(request.getMethod().equalsIgnoreCase("POST")){
				// post是服务过来的
				AliPayLogBean logBean = alipayService.handleServerNotify(map, request.getMethod());
				
				//反馈给支付宝状态消息
				writeJson(response, logBean.getStatus());
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}
	
	
	/**
	 * 获取统一下单接口的预支付交易单号,订购商品的充值
	 * @return
	 */
	@RequestMapping("**/alipay/createOrderProductUnifiedOrder.do")
	public ModelAndView createOrderProductUnifiedOrder(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String adminid = request.getParameter("adminid");
			String product_id = request.getParameter("product_id");
			String phone = request.getParameter("phone");
			Integer spid = Integer.parseInt(product_id);
			
			String out_no = alipayService.createOrderProductUnifiedOrder(Integer.parseInt(adminid), phone, spid);
			
			writeJson(response, out_no);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 获取统一下单接口的预支付交易单号,客户自主充值余额
	 * @return
	 */
	@RequestMapping("**/alipay/createCustomerRechargeUnifiedOrder.do")
	public ModelAndView createCustomerRechargeUnifiedOrder(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String customer_id = request.getParameter("customer_id");
			String money = request.getParameter("money");
			int realMoney = Integer.parseInt(money) * 100;
			String out_no = alipayService.createCustomerRechargeUnifiedOrder(Integer.parseInt(customer_id), realMoney);
			
//			writeJson(response, out_no);
			
			String result = alipayService.toAliPayPage(out_no,AlipayServiceImpl.TYPE_CUSTOMER);
			
			writeJson(response, result);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("/alipay/toAliPayPage.do")
	public ModelAndView toAliPayPage(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String out_no = request.getParameter("out_no");
			
			String result = alipayService.toAliPayPage(out_no,AlipayServiceImpl.TYPE_USER);
			
			writeJson(response, result);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
