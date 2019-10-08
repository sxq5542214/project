/**
 * 
 */
package com.yd.business.wechat.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatPayInfoBean;
import com.yd.business.wechat.bean.WechatPayResultBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.HttpUtil;
import com.yd.util.MD5Util;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class WechatController extends BaseController {
	
	@Resource
	protected IWechatService wechatService;
	@Resource
	protected IUserWechatService userWechatService;
	@Resource
	protected IConfigAttributeService configAttributeService;
	@Resource
	protected IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IOrderService orderService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IShopOrderService shopOrderService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
//	@Deprecated
//	@RequestMapping("/wechat/handle.do")
//	public String handle(HttpServletRequest request,HttpServletResponse response){
//		setResponseCharSet(response);
//		log.debug("wechat/handleSupplier.do  method:" + request.getMethod());
//		if(request.getMethod().equals(METHOD_GET)){
//			//微信验证服务接口
//			return checkServerSign(request,response);
//			
//		}else{
//			//微信接口调用入口
//			InputStream inputStream = null;
//			String resultXml = null;
//			try {
//				
//				// 从request中取得输入流  
//				inputStream = request.getInputStream();
//		        // 读取输入流  
//		        SAXReader reader = new SAXReader();  
//		        Document document = reader.read(inputStream);  
//				log.debug("request doc==============\n"+document.asXML());
//				
//				//得到返回值，如果有，立刻返回消息
//				BaseMessage result = wechatService.handlerWechatMessage(document);
//		        if(result != null){
//		        	resultXml = WechatUtil.MessageToXml(result);
//		        	log.debug("response doc==============\n"+resultXml);
//		        	if(resultXml != null){
//						endResponse(response.getWriter(), resultXml);
//					}
//		        }
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error(e,e);
//			}finally{
//				if(inputStream != null)
//				{
//					try {
//						inputStream.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//						log.error(e,e);
//					}
//			        inputStream = null;
//				}
//			}
//			
//			return null;
//		}
//	}
//	

	
	
	
	
//	/**
//	 * 微信JS-SDK签名.
//	 * @param json
//	 * @return
//	 * @throws Exception 
//	 * @throws JSONException
//	 */
//	@RequestMapping("/wechat/checkJsSign.do")
//	public ModelAndView getJsSign(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String json = request.getParameter("json");
//		JSONObject jso = null;
//		try {
//			jso = new JSONObject(json);
//		} catch (JSONException e) {
//			e.printStackTrace();
//			log.error(e,e);
//		}
//		String url = jso.optString("url");
//		String jsapi_ticket = null;
//		jsapi_ticket = wechatService.getJsapiTicket();
//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		Map<String, String> ret = Sign.sign(jsapi_ticket, url);
//		
//		String appid = configAttributeService.getValueByCode(AttributeConstant.CODE_APPID);
//		ret.put("appid", appid);
//		list.add(ret);
//		writeJson(response, list);
//		return null;
//	}
	
	
	

	/**
	 * 获取统一下单接口的预支付交易单号
	 * @return
	 */
	@RequestMapping("**/wechat/createUnifiedOrder.do")
	public ModelAndView createUnifiedOrder(HttpServletRequest request,HttpServletResponse response){
		
		String coupon_record_id = request.getParameter("coupon_record_id");			//优惠卷id
		String openid = request.getParameter("openid");								//openid 	123
		String price = request.getParameter("price");								//用户需要支付的价格		14.20
		String pointsStr = request.getParameter("points");							//该产品可以抵用的积分		100
		String balanceStr = request.getParameter("cost_balance");					//可以使用的余额			100
		String eff_numStr = request.getParameter("eff_num");						//预订月份				0
		Integer eff_num = StringUtil.isNull(eff_numStr) ? 0:Integer.parseInt(eff_numStr);		//给eff_numStr字段         String类型转换成Integer类型
		Integer points = StringUtil.isNull(pointsStr) ? 0:Integer.parseInt(pointsStr);			//给pointsStrr字段          String类型转换成Integer类型
		Integer cost_balance = StringUtil.isNull(balanceStr) ? 0:Integer.parseInt(balanceStr);	//给eff_numStr字段          String类型转换成Integer类型
		String phone = request.getParameter("phone");											//充值的电话号码			18755171111
		Integer product_id = Integer.parseInt(request.getParameter("product_id"));				//产品id				43
		
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);					//通过openid到user表中查找记录
		if(user == null){																		//做一个验证,确定此openid能够找到对应的用户
			throw new RuntimeException(" createUnifiedOrder user is null!");
		}
		
		
		//如果优惠卷规记录id不等于空
		if(!StringUtil.isNull(coupon_record_id) ){
			SupplierCouponRecordBean couponRecordBean = new SupplierCouponRecordBean();
			couponRecordBean.setUserid(user.getId());
			couponRecordBean.setId(Integer.parseInt(coupon_record_id));
			couponRecordBean  = supplierCouponService.findCouponRecord(couponRecordBean);
			if(couponRecordBean == null){
					throw new RuntimeException(" createUnifiedOrder couponRecord is null!");
			}
		}
		
		//根据用户表中originalid 在ll_wechat_original_info表中信息 ,主要用于查询来自哪个公众号
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
		
		//wx26a55db19faf530f
		String appidStr = originalInfo.getAppid();
		String appid = "appid=" + appidStr; 
		//商户号
		String mch_id = "mch_id=" +originalInfo.getMch_id();
		//随机码
		String nonce_str = "nonce_str=" + UUID.randomUUID().toString().replaceAll("-", "");
		//附加数据，微信会原样返回,
		String attach = "attach="+ "test";
		//商品详情
		String body = "body=支付商品";
		//商户订单号
		String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_WXPAY, user.getId());
		String out_trade_no = "out_trade_no="+out_no;
		//需要支付的总金额,以分为单位
		int rmb = (int) (Double.parseDouble(price)  * 100);
		String total_fee = "total_fee="+ rmb;
		//客户IP
		String spbill_create_ip = request.getRemoteAddr();
		if(spbill_create_ip == null){ spbill_create_ip = "115.28.43.16"; }
		spbill_create_ip = "spbill_create_ip="+spbill_create_ip;
		
		//回调URL
		String notify_url = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_NOTIFY_URL);
		notify_url = "notify_url="+ notify_url ; //+ "wechat/serverNotify.html";
		
		//交易类型
		String trade_type = "trade_type=JSAPI";
		//用户ID //oiRcFuKHjk9_V8-eWwHA1W4x1XWc
		openid = "openid=" + user.getOpenid();
		//设备信息，公众号使用WEB
		String device_info = "device_info=WEB";
		//指定支付方式，不可使用信用卡
		String limit_pay = "limit_pay=no_credit";
		String key = "key=" + originalInfo.getPay_wechat_sign_key();

		Object[] params={appid,mch_id,nonce_str,attach,body,out_trade_no,device_info,
				total_fee,spbill_create_ip,notify_url,trade_type,openid,limit_pay};
		String tempStr = concatParam(params);
		//签名,需要把key放在最后
		tempStr += "&"+key;
		String sign = "sign="+MD5Util.encode16(tempStr,"UTF-8").toUpperCase();
		
		Object[] newParam = ArrayUtils.addAll(params, new String[]{sign});
		String xml = convertToXML(newParam);
		
		String callUrl = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_UNIFIED_URL);
		long time = System.currentTimeMillis();
		try {
			//调用微信的预支付接口
			String responseStr = HttpUtil.post(callUrl, xml);
			WechatPayResultBean resultBean = parseWechatPayResult(responseStr);
			System.out.println("调用微信的预支付接口 cost:"+ (System.currentTimeMillis() - time));
			if(resultBean.getPrepay_id() != null){
				String interface_type = UserConsumeInfoBean.INTERFACETYPE_WEICHAT;
				if(cost_balance != null && cost_balance >0){
					interface_type = UserConsumeInfoBean.INTERFACETYPE_WEICHATANDBALANCE;
				}
			
				UserConsumeInfoBean consume = new UserConsumeInfoBean();
				//如果优惠卷记录id为不为空
				if(!StringUtil.isNull(coupon_record_id) ){
					//根据优惠卷记录表中的id,在优惠卷优惠卷记录表中添加订单号
					supplierCouponService.updateOrderCodeCouponRecordById(Integer.parseInt(coupon_record_id),out_no);
					interface_type =  UserConsumeInfoBean.INTERFACETYPE_COUPONPAY;
					//保存充值记录     在ll_user_consume_info表中加入充值记录
					consume = userConsumeInfoService.createConsumeInfo(phone,rmb, product_id, user.getId(), resultBean.getPrepay_id(), out_no,interface_type,eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_COUPON);
				}else{
					//保存充值记录     在ll_user_consume_info表中加入充值记录
					consume = userConsumeInfoService.createConsumeInfo(phone,rmb, product_id, user.getId(), resultBean.getPrepay_id(), out_no,interface_type,eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER);

				}
				
				//在ll_order_product_log表中增加订购记录表
				orderProductLogService.createOrderProductLogByUserConsumeInfo(consume, points, rmb,cost_balance, null);
				
				//返回界面需要支付的信息
				WechatPayInfoBean data = createPayInfo(appidStr,resultBean.getPrepay_id(),key);
				data.setOutTradeNo(out_no);
				writeJson(response, data);
				return null;
			}else{
				log.error("调用微信的预支付接口失败！ requseXML:"+xml+" respXML:"+ responseStr);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		writeJson(response, "false");
		
		return null;
	}
	

	/**
	 * 获取统一下单接口的预支付交易单号
	 * @return
	 */
	@RequestMapping("**/wechat/createUnifiedOrderByShop.do")
	public ModelAndView createUnifiedOrderByShop(HttpServletRequest request,HttpServletResponse response){
		
		String coupon_record_id = request.getParameter("coupon_record_id");			//优惠卷id
		String openid = request.getParameter("openid");								//openid 	123
		String cost_money = request.getParameter("cost_money");								//用户需要支付的价格		14.20
		String points = request.getParameter("points");							//该产品可以抵用的积分		100
		String balanceStr = request.getParameter("cost_balance");					//可以使用的余额			100
		Integer cost_balance = StringUtil.isNull(balanceStr) ? 0:Integer.parseInt(balanceStr);	//给eff_numStr字段          String类型转换成Integer类型
		String phone = request.getParameter("phone");											//充值的电话号码			18755171111
		String order_code = request.getParameter("order_code");				//定单号
		
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);					//通过openid到user表中查找记录
		if(user == null){																		//做一个验证,确定此openid能够找到对应的用户
			log.error(" createUnifiedOrderByShop user is null! ,openid:"+ openid );
			return null;
		}
		
		
		//如果优惠卷规记录id不等于空
		if(!StringUtil.isNull(coupon_record_id) ){
			SupplierCouponRecordBean couponRecordBean = new SupplierCouponRecordBean();
			couponRecordBean.setUserid(user.getId());
			couponRecordBean.setStatus(SupplierCouponRecordBean.STATUS_CANUSE);
			couponRecordBean.setId(Integer.parseInt(coupon_record_id));
			couponRecordBean  = supplierCouponService.findCouponRecord(couponRecordBean);
			//根据优惠卷信息 更新订单数据，主要是价格侧的变动
			shopOrderService.updateShopOrderByCoupon(order_code, couponRecordBean);
			if(couponRecordBean == null){
				log.error(" createUnifiedOrderByShop couponRecord is null ,userid:"+ user.getId() +" recordid:" + coupon_record_id);
			}
		}
		
		//根据用户表中originalid 在ll_wechat_original_info表中信息 ,主要用于查询来自哪个公众号
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
		
		//wx26a55db19faf530f
		String appidStr = originalInfo.getAppid();
		String appid = "appid=" + appidStr; 
		//商户号
		String mch_id = "mch_id=" +originalInfo.getMch_id();
		//随机码
		String nonce_str = "nonce_str=" + UUID.randomUUID().toString().replaceAll("-", "");
		//附加数据，微信会原样返回,
		String attach = "attach="+ "test";
		//商品详情
		String body = "body=支付商品";
		//商户订单号
//		String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_WXPAY, user.getId());
		String out_trade_no = "out_trade_no="+ order_code;
		//需要支付的总金额,以分为单位
		int rmb = (int) (Double.parseDouble(cost_money)  * 100);
		String total_fee = "total_fee="+ rmb;
		//客户IP
		String spbill_create_ip = request.getRemoteAddr();
		if(spbill_create_ip == null){ spbill_create_ip = "115.28.43.16"; }
		spbill_create_ip = "spbill_create_ip="+spbill_create_ip;
		
		//回调URL
		String notify_url = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_NOTIFY_URL);
		notify_url = "notify_url="+ notify_url ; //+ "wechat/serverNotify.html";
		
		//交易类型
		String trade_type = "trade_type=JSAPI";
		//用户ID //oiRcFuKHjk9_V8-eWwHA1W4x1XWc
		openid = "openid=" + user.getOpenid();
		//设备信息，公众号使用WEB
		String device_info = "device_info=WEB";
		//指定支付方式，不可使用信用卡
		String limit_pay = "limit_pay=no_credit";
		String key = "key=" + originalInfo.getPay_wechat_sign_key();

		Object[] params={appid,mch_id,nonce_str,attach,body,out_trade_no,device_info,
				total_fee,spbill_create_ip,notify_url,trade_type,openid,limit_pay};
		String tempStr = concatParam(params);
		//签名,需要把key放在最后
		tempStr += "&"+key;
		String sign = "sign="+MD5Util.encode16(tempStr,"UTF-8").toUpperCase();
		
		Object[] newParam = ArrayUtils.addAll(params, new String[]{sign});
		String xml = convertToXML(newParam);
		
		String callUrl = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_UNIFIED_URL);
		long time = System.currentTimeMillis();
		try {
			//调用微信的预支付接口
			String responseStr = HttpUtil.post(callUrl, xml);
			WechatPayResultBean resultBean = parseWechatPayResult(responseStr);
			System.out.println("调用微信的预支付接口 cost:"+ (System.currentTimeMillis() - time));
			if(resultBean.getPrepay_id() != null){
				String interface_type = UserConsumeInfoBean.INTERFACETYPE_WEICHAT;
				if(cost_balance != null && cost_balance >0){
					interface_type = UserConsumeInfoBean.INTERFACETYPE_WEICHATANDBALANCE;
				}
				
				UserConsumeInfoBean consume ;
				//如果优惠卷记录id为不为空
				if(!StringUtil.isNull(coupon_record_id) ){
					//根据优惠卷记录表中的id,在优惠卷优惠卷记录表中添加订单号
					supplierCouponService.updateOrderCodeCouponRecordById(Integer.parseInt(coupon_record_id),order_code);
				}
				//保存充值记录     在ll_user_consume_info表中加入充值记录
				consume = userConsumeInfoService.createConsumeInfo(phone,rmb, (Integer)null, user.getId(), resultBean.getPrepay_id(), order_code,interface_type,0,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER_SHOP);
				
				
				//返回界面需要支付的信息
				WechatPayInfoBean data = createPayInfo(appidStr,resultBean.getPrepay_id(),key);
				data.setTransactionId(resultBean.getPrepay_id());
				data.setOutTradeNo(order_code);
				writeJson(response, data);
				return null;
			}else{
				log.error("调用微信的预支付接口失败！ requseXML:"+xml+" \n respXML:"+ responseStr);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		writeJson(response, "false");
		
		return null;
	}
	
	

	/**
	 * 根据定单号删除充值记录，主要是用于界面上用户取消支付的情况
	 * @return
	 */
	@RequestMapping("**/wechat/deleteUnifiedOrder.do")
	public ModelAndView deleteUnifiedOrder(HttpServletRequest request,HttpServletResponse response){
		
		String outTradeNo = request.getParameter("outTradeNo");
		String openid = request.getParameter("openid");
		
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(outTradeNo);
		//保存并处理用户动作
		msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_CANCEL , null, orderLog);
		
		try{
			userConsumeInfoService.deleteConsumeInfo(outTradeNo);
			orderProductLogService.updateOrderProductLogStatusToCancel(outTradeNo);
			log.debug("删除定单："+outTradeNo);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}

	/**
	 * 根据定单号删除充值记录，主要是用于界面上用户取消支付的情况
	 * @return
	 */
	@RequestMapping("**/wechat/deleteUnifiedOrderByShop.do")
	public ModelAndView deleteUnifiedOrderByShop(HttpServletRequest request,HttpServletResponse response){

		String outTradeNo = request.getParameter("outTradeNo");
		String transactionId = request.getParameter("transactionId");
		String openid = request.getParameter("openid");
		
		ShopOrderInfoBean order = shopOrderService.findShopOrderInfoByCode(outTradeNo);
		//保存并处理用户动作
		msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_CANCEL , null, order);
		
		try{
			userConsumeInfoService.deleteConsumeInfoByTransactionId(transactionId);
			
			order.setStatus(ShopOrderInfoBean.STATUS_CANCEL);
			shopOrderService.updateShopOrderInfo(order);
			log.debug("删除定单："+outTradeNo);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	

	/**
	 * 响应微信发送的支付结果，目前仅仅是C端会用微信支付
	 */
	@RequestMapping(value = {"/wechat/serverNotify.html","/wechat/serverNotify.do"})
	public ModelAndView serverNotify(HttpServletRequest request,HttpServletResponse response){
		// 从request中取得输入流  
		ServletInputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
	        // 读取输入流  
	        SAXReader reader = new SAXReader();  
	        Document document = reader.read(inputStream);  
			log.info("wechat serverNotify doc==============\n"+document.asXML());
			
			final WechatPayResultBean result = parseWechatPayResult(document.asXML());
			if(result.getResult_code().equalsIgnoreCase("SUCCESS") && 								//通过返回状态码返回的结果是成功,说明此用户已经支付成功
					result.getReturn_code().equalsIgnoreCase("SUCCESS") ){
				validateNotifySign(result);
				
				boolean isShop = configAttributeService.getBooleanValueByCode(AttributeConstant.CODE_SYSTEM_IS_SHOP_ORDER);
				if(isShop){
					notifyShopOrderProduct(result);
				}else{
					notifyOrderProduct(result);
				}
				
			}
			
			writeJson(response,"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}finally{
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}
		}
		return null;
	}
	
	/**
	 * 普通定购成功通知
	 * @param result
	 */
	private void notifyOrderProduct(final WechatPayResultBean result){

		//更新用户余额
		userWechatService.updateUserBalance(result.getOut_trade_no(),result.getAttach());
		//更新充值记录表和订购表(通过订单号更新ll_user_consume_info表中的状态)
		userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, result.getOut_trade_no());
		//通过订单号在订购表中更改状态为更新成功状态
		orderProductLogService.updateOrderProductLogStatusToPaySuccess(result.getOut_trade_no());
		
		UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(result.getOut_trade_no());
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(result.getOut_trade_no());
		//随机生成用户红包
		orderProductLogService.dealOrderProductLuckyMoney(orderLog);
		//判断是否是预定的订单
		if(consumeInfo.getEff_num() != null && consumeInfo.getEff_num() >0){
			
			OrderProductEffBean condition = new OrderProductEffBean();
			condition.setType(OrderProductEffBean.TYPE_USER_EFF);
			condition.setEff_id(orderLog.getId());
			//有预定记录的，就是预定单，不去再次生成了
			List<OrderProductEffBean> effs = orderService.queryOrderProductEff(condition);
			if(effs.size() == 0){
				orderService.createOrderProductEffByOrderProductLog(orderLog,consumeInfo.getEff_num());
			}
		}else{
			//启动线程去进行订购
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try{
						orderService.orderProductByUser(result.getOut_trade_no(), result.getAttach());
					}catch (Exception e) {
						log.error(e, e);
					}
				}
			});
		}
		
//		Consumetable consumeTable = consumetableService.findByOutTradeNo(result.getOut_trade_no());
//		if(consumeTable != null && consumeTable.getMoney() <0){
//			//微信是以分为单位的，所以需要除100 或 乘0.01
//			double recMoney = Double.valueOf(result.getTotal_fee()) ;
//			consumeTable.setMoney(recMoney);
//			consumeTable.setInterfaceType(Consumetable.INTERFACETYPE_WEICHAT_AFTER);
//			
//			User user = userService.findUserByOpenid(result.getOpenid());
//			Double temp = user.getBalance() + recMoney;
//			user.setBalance(temp);
//			userService.add(user);
//			
//			//资金池的修改 已不用，在mycartaction中用户用户余额支付才修改，因为此处微信通知，不知道用户买了哪几个商品
//			consumetableService.add(consumeTable);
//		}
	}
	
	

	/**
	 * 商城定购成功通知
	 * @param result
	 */
	private void notifyShopOrderProduct(final WechatPayResultBean result){

		//更新用户余额
//		userWechatService.updateUserBalance(result.getOut_trade_no(),result.getAttach());
		//更新充值记录表和订购表(通过订单号更新ll_user_consume_info表中的状态)
		userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, result.getOut_trade_no());
		//通过订单号在订购表中更改状态为更新支付成功状态
		shopOrderService.updateShopOrderPaySuccess(Integer.parseInt(result.getCash_fee()), result.getOut_trade_no());
		
				
		
		
		
//		UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(result.getOut_trade_no());
//		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(result.getOut_trade_no());
//		//随机生成用户红包
//		orderProductLogService.dealOrderProductLuckyMoney(orderLog);
//		//判断是否是预定的订单
//		if(consumeInfo.getEff_num() != null && consumeInfo.getEff_num() >0){
//			
//			OrderProductEffBean condition = new OrderProductEffBean();
//			condition.setType(OrderProductEffBean.TYPE_USER_EFF);
//			condition.setEff_id(orderLog.getId());
//			//有预定记录的，就是预定单，不去再次生成了
//			List<OrderProductEffBean> effs = orderService.queryOrderProductEff(condition);
//			if(effs.size() == 0){
//				orderService.createOrderProductEffByOrderProductLog(orderLog,consumeInfo.getEff_num());
//			}
//		}else{
//			//启动线程去进行订购
//			taskExecutor.execute(new Runnable() {
//				@Override
//				public void run() {
//					try{
//						orderService.orderProductByUser(result.getOut_trade_no(), result.getAttach());
//					}catch (Exception e) {
//						log.error(e, e);
//					}
//				}
//			});
//		}
		
//		Consumetable consumeTable = consumetableService.findByOutTradeNo(result.getOut_trade_no());
//		if(consumeTable != null && consumeTable.getMoney() <0){
//			//微信是以分为单位的，所以需要除100 或 乘0.01
//			double recMoney = Double.valueOf(result.getTotal_fee()) ;
//			consumeTable.setMoney(recMoney);
//			consumeTable.setInterfaceType(Consumetable.INTERFACETYPE_WEICHAT_AFTER);
//			
//			User user = userService.findUserByOpenid(result.getOpenid());
//			Double temp = user.getBalance() + recMoney;
//			user.setBalance(temp);
//			userService.add(user);
//			
//			//资金池的修改 已不用，在mycartaction中用户用户余额支付才修改，因为此处微信通知，不知道用户买了哪几个商品
//			consumetableService.add(consumeTable);
//		}
	}
	
	/**
	 * 验证支付通知的签名
	 * @param result
	 * @return
	 */
	private String validateNotifySign(WechatPayResultBean result){
		
		List<String> list = result.getParams();
		String appid = result.getAppid();
		WechatOriginalInfoBean info = new WechatOriginalInfoBean();
		info.setAppid(appid);
		info = wechatOriginalInfoService.queryWechatOriginalInfo(info).get(0);
		
		String key = "key=" + info.getPay_wechat_sign_key();
//		Object[] strParams =  list.toArray();
//		Arrays.sort(strParams);
		
		String tempStr = concatParam(list.toArray());
		//签名,需要把key放在最后
		tempStr += "&"+key;
		String sign = MD5Util.encode16(tempStr,"UTF-8").toUpperCase();
		
		if( !result.getSign().equals(sign)){
//			return "签名不一致！";
			log.error("validateNotifySign 签名不一致,wechatSign:"+result.getSign()+" ,serverSign:"+sign);
			throw new RuntimeException("签名不一致！");
		}
		
		return null;
	}
	
	
	
	protected static String concatParam(Object... params){
		
		Arrays.sort(params);

		StringBuffer str = new StringBuffer();
		
		for(Object s : params){
			str.append("&" + s);
		}
		
		return str.substring(1);
	}
	

	private static String convertToXML(Object... params){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element root = doc.addElement("xml");
		
		for(Object p : params){
			System.out.println(p);
			String pa =String.valueOf(p);
			int index = pa.indexOf("=");
			String key = pa.substring(0,index);
			String value = pa.substring(index+1, pa.length());
			
			Element ele = root.addElement(key);
//			ele.addCDATA(value);
			ele.setText(value);
		}
		
		return doc.asXML();
	}
	
	
	/**
	 * 微信支付的返回解析
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	private static WechatPayResultBean parseWechatPayResult(String xml) throws DocumentException{
		WechatPayResultBean result = new WechatPayResultBean();
		Document doc = DocumentHelper.parseText(xml);
//		List<String> params = new ArrayList<String>();
//		result.setParams(params);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e: list){

			if(e.getName().equalsIgnoreCase("return_code")){
				result.setReturn_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("return_msg")){
				result.setReturn_msg(e.getText());
			}
			if(e.getName().equalsIgnoreCase("appid")){
				result.setAppid(e.getText());
			}
			if(e.getName().equalsIgnoreCase("mch_id")){
				result.setMch_id(e.getText());
			}
			if(e.getName().equalsIgnoreCase("device_info")){
				result.setDevice_info(e.getText());
			}
			if(e.getName().equalsIgnoreCase("nonce_str")){
				result.setNonce_str(e.getText());
			}
			if(e.getName().equalsIgnoreCase("sign")){
				result.setSign(e.getText());
			}
			if(e.getName().equalsIgnoreCase("result_code")){
				result.setResult_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("err_code")){
				result.setErr_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("err_code_des")){
				result.setErr_code_des(e.getText());
			}
			if(e.getName().equalsIgnoreCase("trade_type")){
				result.setTrade_type(e.getText());
			}
			if(e.getName().equalsIgnoreCase("prepay_id")){
				result.setPrepay_id(e.getText());
			}
			if(e.getName().equalsIgnoreCase("code_url")){
				result.setCode_url(e.getText());
			}
			//支付通知扩展字段
			if(e.getName().equalsIgnoreCase("attach")){
				result.setAttach(e.getText());
			}if(e.getName().equalsIgnoreCase("bank_type")){
				result.setBank_type(e.getText());
			}if(e.getName().equalsIgnoreCase("cash_fee")){
				result.setCash_fee(e.getText());
			}if(e.getName().equalsIgnoreCase("fee_type")){
				result.setFee_type(e.getText());
			}if(e.getName().equalsIgnoreCase("is_subscribe")){
				result.setIs_subscribe(e.getText());
			}if(e.getName().equalsIgnoreCase("openid")){
				result.setOpenid(e.getText());
			}if(e.getName().equalsIgnoreCase("out_trade_no")){
				result.setOut_trade_no(e.getText());
			}if(e.getName().equalsIgnoreCase("time_end")){
				result.setTime_end(e.getText());
			}if(e.getName().equalsIgnoreCase("total_fee")){
				result.setTotal_fee(e.getText());
			}if(e.getName().equalsIgnoreCase("transaction_id")){
				result.setTransaction_id(e.getText());
			}
		}
		return result;
	}
	
	
	/**
	 * 创建支付信息
	 * @param appId
	 * @param prepay_id
	 * @param signKey
	 * @return
	 */
	protected WechatPayInfoBean createPayInfo(String appId,String prepay_id,String signKey){
		WechatPayInfoBean bean = new WechatPayInfoBean();
		bean.setAppId(appId);
		bean.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
		bean.setPackages("prepay_id="+prepay_id);
		bean.setSignType("MD5");
		bean.setTimeStamp(""+(System.currentTimeMillis()/1000));
		
		String temp = "appId="+appId+"&nonceStr="+bean.getNonceStr()+"&package="+bean.getPackages()
				+ "&signType="+bean.getSignType()+"&timeStamp="+bean.getTimeStamp() + "&"+ signKey;
		String paySign = MD5Util.encode16(temp,"UTF-8").toUpperCase();
		bean.setPaySign(paySign);
		
		return bean;
	}
	
	@RequestMapping("**/wechat/test.do")
	public ModelAndView test(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
//			wechatService.syncWechatMaterial();
			
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
}
