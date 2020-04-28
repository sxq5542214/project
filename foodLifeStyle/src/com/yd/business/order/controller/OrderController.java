/**
 * 
 */
package com.yd.business.order.controller;

import java.util.Calendar;
import java.util.Date;
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
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserCartService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class OrderController extends BaseController {
	@Resource
	private IOrderService orderService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IUserWechatService userWechatService;
	

	public static final String PAGE_USER_ORDER_PRODUCT_RESULT = "/page/user/orderResult.jsp";
	public static final String PAGE_SUPPLIER_ORDER_PRODUCT_RESULT = "/page/supplier/orderResult.jsp";
	public static final String PAGE_SUPPLIER_ORDER_PRODUCT_RESULT_STORENUM = "/page/supplier/orderResult_storeNum.jsp";

	/**
	 * 用户订购商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("order/userOrderProduct.do")
	public ModelAndView userOrderProduct(HttpServletRequest request,HttpServletResponse response){
		
		try{
			Map<String, Object> model = new HashMap<String, Object>();
			String out_trade_code = request.getParameter("out_trade_code");
			
			if(out_trade_code != null){
				OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
				UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(out_trade_code);
				UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
				//判断是否是预定的订单
				if(consumeInfo.getEff_num() != null && consumeInfo.getEff_num() >0 
						){
					if(consumeInfo.getStatus() == UserConsumeInfoBean.STATUS_SUCCESS )
					{
						OrderProductEffBean condition = new OrderProductEffBean();
						condition.setType(OrderProductEffBean.TYPE_USER_EFF);
						condition.setEff_id(orderLog.getId());
						//有预定记录的，就是预定单，不去再次生成了
						List<OrderProductEffBean> effs = orderService.queryOrderProductEff(condition);
						if(effs.size() == 0){
							orderService.createOrderProductEffByOrderProductLog(orderLog,consumeInfo.getEff_num());
						}
					}else{
						orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
						orderLog.setRemark("待支付状态更新后自动下单");
						model.put("user", user);
						model.put("orderLog", orderLog);
						return new ModelAndView(PAGE_USER_ORDER_PRODUCT_RESULT, model);
					}
				}else{
					//没有预约，直接订购
					orderLog = orderService.orderProductByUser(out_trade_code, null);
				}
				//处理打包商品订单，其余的商品同一走预订流程，生成预订订单
				orderService.createPackageOrderProduct(orderLog,consumeInfo);
				//重新获取对象
				orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
				//获取用户
				OrderProductLogBean neworderLog = orderProductLogService.dealOrderProductLuckyMoney(orderLog);
				
				model.put("user", user);
//				model.put("luckymoney", neworderLog.getLucky_money());
				model.put("orderLog", neworderLog);
				return new ModelAndView(PAGE_USER_ORDER_PRODUCT_RESULT, model);
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}

	/**
	 * 商户订购商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("order/supplierOrderProductByStoreNum.do")
	public ModelAndView supplierOrderProductByStoreNum(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String adminid = request.getParameter("adminid");
			String spid = request.getParameter("spid");
			String phone = request.getParameter("phone");
			
			OrderProductLogBean orderLog = orderService.orderProductBySupplierStoreNum(adminid, spid, phone);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderLog", orderLog);
			return new ModelAndView(PAGE_SUPPLIER_ORDER_PRODUCT_RESULT_STORENUM, model);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	

	/**
	 * 商户订购商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("order/supplierOrderProductByBalance.do")
	public ModelAndView supplierOrderProductByBalance(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String out_trade_code = request.getParameter("out_trade_code");
			String adminid = request.getParameter("adminid");
			
			OrderProductLogBean orderLog = orderService.orderProductBySupplierBalance(out_trade_code,Integer.parseInt(adminid));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderLog", orderLog);
			return new ModelAndView(PAGE_SUPPLIER_ORDER_PRODUCT_RESULT, model);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	

	/**
	 * 获取统一下单接口的预支付交易单号
	 * @return
	 */
	@RequestMapping("**/order/createUnifiedOrder.do")
	public ModelAndView createUnifiedOrder(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String adminid = request.getParameter("adminid");
			String product_id = request.getParameter("product_id");
			String phone = request.getParameter("phone");
			String type = request.getParameter("type");
			Integer spid = Integer.parseInt(product_id);
			Integer event_type = UserConsumeInfoBean.EVENT_TYPE_SUPPLIER_ORDER;
			
			String out_no = orderService.createUnifiedOrder(Integer.parseInt(adminid), phone, spid,type,event_type);
			
			writeJson(response, out_no);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/order/changeUserOrderEffDate.do")
	/**
	 * 用户处理自己的可预约订单，变更订单生效时间
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView changeUserEffOrderDate(HttpServletRequest request,HttpServletResponse response){
		try{
			int nextNum = 0;
			if(!StringUtil.isNull(request.getParameter("choiceDate"))){
				//获取生效时间
				nextNum = Integer.valueOf(request.getParameter("choiceDate"));
				//预约订单ID
				int id = Integer.valueOf(request.getParameter("id"));
				Map<String,Object> resultMap = orderService.modifyEffOrderProduct(nextNum,id);
				boolean flag = (Boolean)resultMap.get("RESULT");
				if(flag){
					writeJson(response,(OrderProductEffBean)resultMap.get("OBJ"));
				}else{
					writeJson(response,(Map<String,String>)resultMap.get("OBJ"));
				}
			}
		}catch(Exception e){
			log.error(e, e);
			Map<String,String> mm = new HashMap<String,String>();
			mm.put("empty", "failed");
			writeJson(response,mm);
		}
	    return null;
	}
	
	/**
	 * 购买流量，完成交易后分享，即可获得一定金额的微信红包
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/order/userSharedByOrderProduct.do")
	public ModelAndView userSharedByOrderProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			String orderId = request.getParameter("orderId");
			if(StringUtil.isNotNull(openid)){
				String share_type = request.getParameter("share_type");
				String ip = getRemoteHost(request);
				OrderProductLogBean pLogBean = orderProductLogService.dealSharedOrderAndSendLuckMoney(orderId,openid,share_type,ip);
		        writeJson(response,pLogBean);
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
}
