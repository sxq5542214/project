/**
 * 
 */
package com.yd.business.order.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author zxz
 *
 */
@Controller
public class OrderProductLogController extends BaseController {
	
	@Autowired
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IConfigCruxService configCruxService;
	
	
	/**
	 * 定购日志管理,查询功能
	 */
	@RequestMapping("/admin/order/orderProductLogController/queryAdmimOrderProductLog.do")
	public ModelAndView queryAdmimOrderProductLog(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			OrderProductLogBean bean = new OrderProductLogBean();
			String user_id =  request.getParameter("query_orderproductlog_user_id");
			String order_code =  request.getParameter("query_orderproductlog_order_code");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(user_id)){
				bean.setUser_id(Integer.parseInt(user_id));
			}
			if(!StringUtil.isNull(order_code)){
				bean.setOrder_code(order_code);
			}
			PageinationData pd =  orderProductLogService.queryOrderProductLogPage(bean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/orderProductLog/iframe_order_product_log.jsp", map);
	}
	
	
	
	/**
	 * 定购日志管理删除 	根据id删除ll_order_product_log表信息
	 */
	@RequestMapping("/admin/order/orderProductLogController/deleteOrderProductLog.do")
	public ModelAndView deleteOrderProductLog(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			OrderProductLogBean bean = new OrderProductLogBean();
			bean.setId(Integer.parseInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				orderProductLogService.deleteOrderProductLog (bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}

	
	/**
	 *  增加和编辑字典值表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/order/orderProductLogController/editOrderProductLog.do")
	public ModelAndView editOrderProductLog(HttpServletRequest request,HttpServletResponse response) throws IOException{
		OrderProductLogBean bean =  new OrderProductLogBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			bean.setSupplier_id(Integer.parseInt(request.getParameter("supplier_id")));
			bean.setSupplier_name(request.getParameter("supplier_name"));				//String类型产品名称
			bean.setSupplier_product_id(Integer.parseInt(request.getParameter("supplier_product_id")));
			bean.setProduct_name(request.getParameter("product_name"));					//String类型产品名称
			bean.setProduct_price(Integer.parseInt(request.getParameter("product_price")));
			bean.setOrder_code(request.getParameter("order_code")) ;					//String类型订单号		
			bean.setOrder_account(request.getParameter("order_account")) ;				//String类型订单号码
			bean.setCost_price(Integer.parseInt(request.getParameter("cost_price")));
			bean.setCost_points(Integer.parseInt(request.getParameter("cost_points")));
			bean.setCost_money(Integer.parseInt(request.getParameter("cost_money")));
			bean.setCost_balance(Integer.parseInt(request.getParameter("cost_balance")));
			bean.setUser_id(Integer.parseInt(request.getParameter("user_id")));
			bean.setStatus(Integer.parseInt(request.getParameter("status")));
			bean.setRemark(request.getParameter("remark"));								//String类型备注说明		
			String channel_id = request.getParameter("channel_id");
			if(!StringUtil.isNull(channel_id)){
				bean.setChannel_id(Integer.parseInt(channel_id));
			}
			bean.setEvent_type(Integer.parseInt(request.getParameter("event_type")));
			String lucky_money = request.getParameter("lucky_money");
			if(!StringUtil.isNull(lucky_money)){
				bean.setLucky_money(Integer.parseInt(lucky_money));
			}
			String is_sended = request.getParameter("is_sended");
			if(!StringUtil.isNull(is_sended)){
				bean.setIs_sended(Integer.parseInt(is_sended));
			}
			orderProductLogService.editOrderProductLog(bean);
			bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
}
