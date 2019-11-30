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
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.bean.ShopOrderRemindBean;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.order.service.impl.ShopOrderServiceImpl;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserCartService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatService;
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
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
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
			String openid = request.getParameter("openid");
			String order_id = request.getParameter("order_id");
			
			ShopOrderInfoBean order = shopOrderService.findShopOrderInfoById(Integer.parseInt(order_id));
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user.getId().intValue() == order.getUser_id().intValue()){
				shopOrderService.updateShopOrderStatusToDelete(order.getOrder_code());
			}
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	@RequestMapping("**/order/shop/remindShopOrder.do")
	public ModelAndView remindShopOrder(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			String order_id = request.getParameter("order_id");
			String remindStr = request.getParameter("remind");
			
			ShopOrderInfoBean order = shopOrderService.findShopOrderInfoById(Integer.parseInt(order_id));
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user.getId().intValue() == order.getUser_id().intValue()){
				//催单
				ShopOrderRemindBean remind = shopOrderService.createShopOrderRemind(remindStr, user, order);
				
				msgCenterActionService.saveAndHandleUserAction(openid,  MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_REMIND, null, remind);
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

			ShopOrderInfoBean order;
			String order_code = request.getParameter("order_code");
			if(StringUtil.isNotNull(order_code)){
				order = shopOrderService.findShopOrderInfoByCode(order_code);
			}else{
				String order_id = request.getParameter("order_id");
				order = shopOrderService.findShopOrderInfoById(Integer.parseInt(order_id));
			}
			
			String express_mode = request.getParameter("express_mode");
			String express_order_code = request.getParameter("express_order_code");
			String express_price = request.getParameter("express_price");
			Integer price = null;
			if(StringUtil.isNotNull(express_price)){
				price = Integer.parseInt(express_price);
			}
			
			shopOrderService.updateShopOrderExpressInfo(order, express_mode, express_order_code,price);
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		return null;
	}
	
	@RequestMapping("**/order/shop/updateShopOrderExpressByWechatUser.do")
	public ModelAndView updateShopOrderExpressByWechatUser(HttpServletRequest request,HttpServletResponse response){
		try {

			ShopOrderInfoBean order;
			String order_code = request.getParameter("order_code");
			order = shopOrderService.findShopOrderInfoByCode(order_code);
			String openid = request.getParameter("openid");
			
			// 检测当前用户是否有权限进行此项操作
			List<UserWechatBean> list = userWechatService.queryWechatUserActionAgree("**/order/shop/updateShopOrderExpressByWechatUser.do", openid);
			if(list.size() == 0){
				writeJson(response, "您没有权限进行此项操作，请联系管理员添加权限");
				return null;
			}
			
			String express_mode = request.getParameter("express_mode");
			String express_order_code = request.getParameter("express_order_code");
			String express_price = request.getParameter("express_price");
			Integer price = null;
			if(StringUtil.isNotNull(express_price)){
				price = Integer.parseInt(express_price);
			}
			
			shopOrderService.updateShopOrderExpressInfo(order, express_mode, express_order_code,price);
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		return null;
	}

	@RequestMapping("**/order/shop/toShopOrderDeliverPage.do")
	public ModelAndView toShopOrderDeliverPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			
			if(openid == null){
				String code = request.getParameter("code");
				openid = wechatService.getOpenidByWechatCode(code, request);
			}
			
			//查询所有待发货的订单
			ShopOrderInfoBean bean = new ShopOrderInfoBean();
			bean.setStatus(ShopOrderInfoBean.STATUS_PAYSUCCESS);
			bean.setOrderby(" order by id asc ");
			List<ShopOrderInfoBean> list = shopOrderService.queryShopOrderInfo(bean);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("openid", openid);
			
			return new ModelAndView("/page/pc/order/h5/order_delver.jsp",map);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("**/order/shop/toAdminShopOrderListPage.do")
	public ModelAndView toAdminShopOrderListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			
			if(openid == null){
				String code = request.getParameter("code");
				openid = wechatService.getOpenidByWechatCode(code, request);
			}
			
			String contactPhone = StringUtil.convertNull(request.getParameter("contact_phone"));
			String contactName = StringUtil.convertNull(request.getParameter("contact_name"));
			
			
			//查询所有支付过的订单
			ShopOrderInfoBean bean = new ShopOrderInfoBean();
			bean.setContact_name(contactName);
			bean.setContact_phone(contactPhone);
			
//			String condition = StringUtil.isNull(contactName) && StringUtil.isNull(contactPhone) ? " where " : " and ";
			
			bean.setOrderby(" and status >= 3 order by id desc  limit 50");
			List<ShopOrderInfoBean> list = shopOrderService.queryShopOrderInfo(bean);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("openid", openid);
			map.put("contactPhone", contactPhone);
			map.put("contactName", contactName);
			
			return new ModelAndView("/page/pc/order/h5/order_list.jsp",map);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/order/shop/queryShopOrderProductList.do")
	public ModelAndView queryShopOrderProductList(HttpServletRequest request,HttpServletResponse response){
		try {
			//查询所有待发货的订单
			String orderCode = request.getParameter("orderCode");
			ShopOrderProductBean bean = new ShopOrderProductBean();
			bean.setOrder_code(orderCode);
			List<ShopOrderProductBean> list = shopOrderService.queryShopOrderProduct(bean );

			writeJson(response, list);
			return null;
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
}
