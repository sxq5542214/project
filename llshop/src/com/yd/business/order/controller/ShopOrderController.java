/**
 * 
 */
package com.yd.business.order.controller;

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
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.user.service.IUserCartService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class ShopOrderController extends BaseController {
	
	@Resource
	private IShopOrderService shopOrderService;
	@Resource
	private IUserCartService userCartService;
	@Resource
	private IDictionaryService dictionaryService;
	
	@RequestMapping("**/order/shop/setupOrderAddress.do")
	public ModelAndView setupOrderAddress(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String userAddrId = request.getParameter("userAddrId");
			String order_code = request.getParameter("order_code");
			
			shopOrderService.setupOrderAddress(order_code, Integer.parseInt(userAddrId));
			
//			return new ModelAndView("redirect:/user/toUserShopOrderPage.do?order_code="+order_code);
			return new ModelAndView("redirect:/user/supplier/toSupplierShopUserOrderPage.do?order_code="+order_code);
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/order/shop/deleteShopOrder.do")
	public ModelAndView deleteShopOrder(HttpServletRequest request,HttpServletResponse response){
		try {
			String user_id = request.getParameter("user_id");
			String order_id = request.getParameter("order_id");
			
			ShopOrderInfoBean order = shopOrderService.findShopOrderInfoById(Integer.parseInt(order_id));
			
			if(Integer.parseInt(user_id) == order.getUser_id().intValue()){
				shopOrderService.updateShopOrderStatusToDelete(order.getOrder_code());
			}
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/order/shop/toShopOrderListMgr.do")
	public ModelAndView toShopOrderListMgr(HttpServletRequest request,HttpServletResponse response){
		try {
			int paramLength = request.getParameterMap().size();
			
			ShopOrderInfoBean condition = new ShopOrderInfoBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), condition);
			if(paramLength == 0){
				condition.setStatus(ShopOrderInfoBean.STATUS_PAYSUCCESS);
				condition.setOrderby(" order by id asc");
			}
			
			List<ShopOrderInfoBean> orderList = shopOrderService.queryShopOrderInfo(condition );
			List<DictionaryBean> dictList = dictionaryService.getZidianBycache("ShopOrderInfoBean", "status", true);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderList", orderList);
			model.put("condition", condition);
			model.put("dictList", dictList);
			
			return new ModelAndView("/page/pc/order/iframe_shop_order_list.jsp",model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/order/shop/updateShopOrderExpress.do")
	public ModelAndView updateShopOrderExpress(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String order_id = request.getParameter("order_id");
			String express_mode = request.getParameter("express_mode");
			String express_order_code = request.getParameter("express_order_code");
			String express_price = request.getParameter("express_price");
			Integer price = null;
			if(StringUtil.isNotNull(express_price)){
				price = Integer.parseInt(express_price);
			}
			
			shopOrderService.updateShopOrderExpressInfo(Integer.parseInt(order_id), express_mode, express_order_code,price);
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
