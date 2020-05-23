/**
 * 
 */
package com.yd.business.order.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.ShopOrderEffInfoBean;
import com.yd.business.order.bean.ShopOrderEffProductBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.bean.ShopOrderRemindBean;
import com.yd.business.order.dao.IShopOrderDao;
import com.yd.business.order.service.IOrderService;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.supplier.bean.SupplierBalanceLogBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierStoreService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.supplier.service.impl.SupplierServiceImpl;
import com.yd.business.supplier.service.impl.SupplierUserServiceImpl;
import com.yd.business.user.bean.UserAddressBean;
import com.yd.business.user.bean.UserCartBean;
import com.yd.business.user.bean.UserCartBean.CartInfo;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserAddressService;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.service.impl.UserConsumeInfoServiceImpl;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("shopOrderService")
public class ShopOrderServiceImpl extends BaseService implements IShopOrderService {
	
	@Resource
	private IShopOrderDao shopOrderDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IOrderService orderService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IUserAddressService userAddressService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private ISupplierUserService supplierUserService;
	@Resource
	private ISupplierStoreService supplierStoreService;
	

	@Override
	public void createShopOrderInfo(ShopOrderInfoBean bean){
		shopOrderDao.createShopOrderInfo(bean);
	}
	@Override
	public void createShopOrderEffInfo(ShopOrderEffInfoBean bean){
		shopOrderDao.createShopOrderEffInfo(bean);
	}

	@Override
	public void updateShopOrderInfo(ShopOrderInfoBean bean){
		bean.setModify_time(DateUtil.getNowDateStr());
		shopOrderDao.updateShopOrderInfo(bean);
	}
	@Override
	public void updateShopOrderEffInfo(ShopOrderEffInfoBean bean){
		bean.setModify_time(DateUtil.getNowDateStr());
		shopOrderDao.updateShopOrderEffInfo(bean);
	}
	
	
	
	@Override
	public boolean updateShopOrderToEff(String orderCode,String effDate,String openid,String remark,String concatName,String concatPhone) throws Exception {
		ShopOrderEffInfoBean effBean = findShopOrderEffInfoByCode(orderCode);
		ShopOrderInfoBean order = findShopOrderInfoByCode(orderCode);
		order.setStatus(ShopOrderInfoBean.STATUS_ORDERING);
		order.setExpress_date(effDate);
		order.setRemark(remark);
		order.setEvent_type(ShopOrderEffInfoBean.EVENT_TYPE_USER_ORDER_EFF);
		order.setContact_name(concatName);
		order.setContact_phone(concatPhone);
		if(effBean == null) {
			//创建
			effBean = new ShopOrderEffInfoBean(order);
			effBean.setId(null);
			effBean.setOld_order_id(order.getId());
			//创建预约订单
			createShopOrderEffInfo(effBean);
			
			ShopOrderEffInfoBean effOrder = findShopOrderEffInfoByCode(orderCode);
			
			//创建预约订单商品
			ShopOrderProductBean bean = new ShopOrderEffProductBean();
			bean.setOrder_code(orderCode);
			List<ShopOrderProductBean> prodList = queryShopOrderProduct(bean );
			for(ShopOrderProductBean prod : prodList) {
				ShopOrderEffProductBean effProd = new ShopOrderEffProductBean(prod);
				effProd.setId(null);
				effProd.setOrder_info_id(effOrder.getId());
				
				createShopOrderEffProduct(effProd );
			}
			//消息中心发送事件
			msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_SUBSCRIBE_SHOP_EFF , "create", effOrder);
		}else {
			//修改
			effBean.setEff_date(effDate);
			effBean.setExpress_date(effDate);
			effBean.setRemark(remark);
			effBean.setStatus(ShopOrderInfoBean.STATUS_ORDERING);
			effBean.setContact_name(concatName);
			effBean.setContact_phone(concatPhone);
			//修改预订表的状态
			updateShopOrderEffInfo(effBean);
			//消息中心发送事件
			msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_EFF, "update", effBean);
		}
		//修改基础订单
		updateShopOrderInfo(order);
		return true;
	}

	@Override
	public List<ShopOrderInfoBean> queryShopOrderInfo(ShopOrderInfoBean bean){
		
		// 历史一个月以上的订单，状态修改为已成交
		updateOrderToFinishBy30DayAgo();
		
		
		return shopOrderDao.queryShopOrderInfo(bean);
	}
	
	@Override
	public List<ShopOrderEffInfoBean> queryShopOrderEffInfo(ShopOrderEffInfoBean bean){
		
		// 历史一个月以上的订单，状态修改为已成交
//		updateOrderToFinishBy30DayAgo();
		
		
		return shopOrderDao.queryShopOrderEffInfo(bean);
	}
	
	private int updateOrderToFinishBy30DayAgo() {
		return shopOrderDao.updateOrderToFinishBy30DayAgo();
	}
	@Override
	public int updateRabbishOrderStatus(Integer userId) {
		if(userId != null) {
			return shopOrderDao.updateRabbishOrderStatus(userId);
		}
		return -1;
	}
	
	@Override
	public void updateShopOrderExpressInfo(ShopOrderInfoBean order,String mode,String code,Integer price){
		
		if(price != null){
			order.setExpress_price(price);
		}
		if(StringUtil.isNotNull(mode)){
			order.setExpress_mode(mode);
		}
		if(StringUtil.isNotNull(code)){
			order.setExpress_order_code(code);
			order.setExpress_date(DateUtil.getNowDateStr());
			order.setStatus(ShopOrderInfoBean.STATUS_ALREADY_DELIVERY);
		}
		
		updateShopOrderInfo(order);
		// 调用消息中心通知用户发货
		msgCenterActionService.saveAndHandleUserAction( UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_DELIVERY, "订单发货", order);
	}

	@Override
	public ShopOrderInfoBean findShopOrderInfoByCode(String order_code) {
		ShopOrderInfoBean bean = new ShopOrderInfoBean();
		bean.setOrder_code(order_code);
		List<ShopOrderInfoBean> list = queryShopOrderInfo(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public ShopOrderEffInfoBean findShopOrderEffInfoByCode(String order_code) {
		ShopOrderEffInfoBean bean = new ShopOrderEffInfoBean();
		bean.setOrder_code(order_code);
		List<ShopOrderEffInfoBean> list = queryShopOrderEffInfo(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ShopOrderInfoBean findShopOrderInfoById(int id) {
		ShopOrderInfoBean bean = new ShopOrderInfoBean();
		bean.setId(id);
		List<ShopOrderInfoBean> list = queryShopOrderInfo(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	

	@Override
	public void createShopOrderProduct(ShopOrderProductBean bean){
		shopOrderDao.createShopOrderProduct(bean);
	}
	@Override
	public void createShopOrderEffProduct(ShopOrderEffProductBean bean){
		shopOrderDao.createShopOrderEffProduct(bean);
	}
	
	/**
	 * 根据商户商品ID创建 订单商品
	 * @param spid
	 * @param num
	 * @param sopType
	 * @param offset_points
	 * @param sp_total_price
	 * @param orderCode
	 */
	public ShopOrderEffProductBean createShopOrderProductBySpid(int spid,int num,int sopType, int offset_points, int sp_total_price,String orderCode){
		boolean isEff = orderCode.indexOf(UserConsumeInfoServiceImpl.OUTTRADE_TYPE_SHOPEFF) >= 0;
		SupplierProductBean sp = supplierProductService.findSupplierProductById(spid);
		ShopOrderInfoBean order = isEff ? findShopOrderEffInfoByCode(orderCode) : findShopOrderInfoByCode(orderCode);
		UserWechatBean user = userWechatService.findUserWechatById(order.getUser_id());
		
		ShopOrderEffProductBean spb = new ShopOrderEffProductBean();
		spb.setHead_img(sp.getProduct_img());
		spb.setOrder_info_id(order.getId());
		spb.setOrder_code(order.getOrder_code());
		spb.setUser_id(user.getId());
		spb.setNick_name(user.getNick_name());
		spb.setSupplier_id(sp.getSupplier_id());
		spb.setSupplier_name(sp.getSupplier_name());
		spb.setSupplier_product_id(sp.getId());
		spb.setSupplier_product_name(sp.getProduct_name());
		spb.setOriginal_price(sp.getProduct_price());
		spb.setDiscount(sp.getDiscount());
		spb.setNum(num);
		spb.setCost_points(offset_points);
		spb.setReal_price(sp_total_price);
		spb.setType(sopType);
		spb.setCreate_time(DateUtil.getNowDateStrSSS());
		spb.setPrime_cost_price(sp.getPrime_cost_price() * num);
		
		if(isEff) {
			createShopOrderEffProduct(spb);
		}else {
			createShopOrderProduct(spb);
		}
		return spb;
	}

	@Override
	public List<ShopOrderProductBean> queryShopOrderProduct(ShopOrderProductBean bean){
		return shopOrderDao.queryShopOrderProduct(bean);
	}
	@Override
	public List<ShopOrderEffProductBean> queryShopOrderEffProduct(ShopOrderEffProductBean bean){
		return shopOrderDao.queryShopOrderEffProduct(bean);
	}

	@Override
	public List<ShopOrderInfoBean> queryShopOrderAndProductList(ShopOrderInfoBean bean){
		
		return shopOrderDao.queryShopOrderAndProductList(bean);
	}
	@Override
	public List<ShopOrderEffInfoBean> queryShopOrderEffAndProductList(ShopOrderEffInfoBean bean){
		
		return shopOrderDao.queryShopOrderEffAndProductList(bean);
	}

	/**
	 * 从cookie的值中查询商品列表
	 * 入参格式为：{"productInfos":[{"spid":29,"num":1},{"spid":30,"num":2}]}
	 * @param productJson
	 * @return
	 */
	@Override
	public ShopOrderInfoBean createOrderLogByUserCartList(Integer supplier_id,String openid,String productJson,Long time,Integer type){
		return createOrderLogByUserCartList(supplier_id,openid, productJson, time,null,type);
	}
	
	/**
	 * 从cookie的值中查询商品列表
	 * 入参格式为：{"productInfos":[{"spid":29,"num":1},{"spid":30,"num":2}]}
	 * @param productJson
	 * @return
	 */
	@Override
	public ShopOrderInfoBean createOrderLogByUserCartList(Integer supplier_id,String openid,String productJson,Long time,String effDate,Integer type){

		boolean isEff = StringUtil.isNotNull(effDate);
		ShopOrderInfoBean order = new ShopOrderEffInfoBean();
		List<? extends ShopOrderProductBean> productList =  new ArrayList<ShopOrderEffProductBean>();
		order.setProductList(productList);
		order.setSupplier_id(supplier_id);
		order.setCost_points(0);
		order.setCost_price(0);
		order.setCost_money(0);
		order.setCost_balance(0);
		order.setCoupon_total_price(0);
		order.setStore_card_total_price(0);
		order.setType(type);
		((ShopOrderEffInfoBean)order).setEff_date(effDate);
		
		
		try{
			if(StringUtil.isNull(productJson)){
				return order;
			}
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				return order;
			}
			
			//入参是界面编码过的，要解码
			productJson = URLDecoder.decode(productJson, "utf-8");
			
			JSONObject jso = new JSONObject(productJson);
			if(time == null)
			{
				time = jso.optLong("time",System.currentTimeMillis());
			}
			Date date = new Date(time);
			//预订单的前缀不同
			String prefix ;
			switch (type) {
			case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_EFF:
				prefix = UserConsumeInfoServiceImpl.OUTTRADE_TYPE_SHOPEFF;
				break;
			case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_OFFLINE:
				prefix = UserConsumeInfoServiceImpl.OUTTRADE_TYPE_SHOPOFFLINE;
				break;
			case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_ONLINE:
				prefix = UserConsumeInfoServiceImpl.OUTTRADE_TYPE_SHOPONLINE;
				break;
			default:
				prefix = UserConsumeInfoServiceImpl.OUTTRADE_TYPE_SHOP;
				break;
			}
			
			String order_code = userConsumeInfoService.createOutTradeNo(prefix, user.getId().toString(),date);

			order.setOrder_code(order_code);
			List<? extends ShopOrderInfoBean> list =  isEff ? queryShopOrderEffInfo((ShopOrderEffInfoBean) order) :queryShopOrderInfo(order);
			if(list.size() > 0){
				order = list.get(0);
				
				ShopOrderEffProductBean condition = new ShopOrderEffProductBean();
				condition.setOrder_code(order_code);
				productList = isEff?   queryShopOrderEffProduct(condition ): queryShopOrderProduct(condition );
				order.setProductList(productList);
				
			}else{
				order.setUser_id(user.getId());
				order.setNick_name(user.getNick_name());
				order.setCreate_time(DateUtil.getNowDateStr());
				order.setStatus(ShopOrderInfoBean.STATUS_WAIT);
				order.setEvent_type(isEff? ShopOrderInfoBean.EVENT_TYPE_USER_ORDER_EFF :ShopOrderInfoBean.EVENT_TYPE_USER_ORDER_SHOP);
				order.setOrder_code(order_code);
				//创建定单
				if(isEff) {
					createShopOrderEffInfo((ShopOrderEffInfoBean) order);
				}else {
					createShopOrderInfo(order);
				}
			
				JSONArray array = jso.getJSONArray("productInfos");
				List<Integer> ids = new ArrayList<Integer>();
				
				for(int i = 0 ; i < array.length(); i++){
					
					JSONObject prodJson = array.getJSONObject(i);
					ids.add(prodJson.getInt("spid"));
				}
				
				if(ids.size() > 0) {
					String order_name = "";
					List<SupplierProductBean> spList = supplierProductService.querySupplierProductByIds(ids);
					//查出数据后，要对比是否有大于库存的，并封装成对象
					for(SupplierProductBean sp : spList){
						for(int i = 0 ; i < array.length(); i++){
							
							JSONObject prodJson = array.getJSONObject(i);
							int id = prodJson.getInt("spid");
							int num = prodJson.getInt("num");
							if(sp.getId() == id && sp.getStore_num() > 0 ){
								if(sp.getStore_num() < num){
									num = sp.getStore_num();
								}
								//技算折扣和积分抵扣等
								int sp_total_price = (int)(num * sp.getProduct_price() * sp.getDiscount() /100d);
								int offset_points = sp.getProduct_offset_points() * num;
								//抵扣积分 
								int userPoints = user.getPoints();
								if(offset_points > userPoints){
									offset_points = userPoints;
								}
								// 扣过积分的价格
								int payPrice = sp_total_price - offset_points;
								if(order_name.length() == 0){
									order_name = sp.getProduct_name();
									order.setOrder_img(sp.getProduct_img());
									if(spList.size() > 1){
										order_name += "等"+spList.size()+"类商品";
									}
								}
								//生成订单商品信息
								ShopOrderEffProductBean spb = createShopOrderProductBySpid(sp.getId(), num, ShopOrderProductBean.TYPE_NORMAL, offset_points, payPrice, order_code);
								((ArrayList<ShopOrderEffProductBean>)productList).add(spb);

								order.setCost_money(order.getCost_money() + payPrice);
								order.setCost_price(order.getCost_price() + sp_total_price);
								order.setCost_points(order.getCost_points() + offset_points);
								order.setOrder_name(order_name);

								order.setSupplier_id(sp.getSupplier_id());
								
								if(isEff) {
									updateShopOrderEffInfo((ShopOrderEffInfoBean) order);
								}else {
									updateShopOrderInfo(order);
								}
	//							int give_points = sp.getGive_points() * num;   // 用户还没有付款呢
	//							if(offset_points >0){
	//								user.setPoints(user.getPoints() - offset_points);
	//								userCommissionPointsService.createUserPointLog(user.getId(), -offset_points, "购买商品"+ sp.getProduct_name()+" *"+num+" 扣减积分" );
	//							}
								
							}
						}
					}
					
					//判断是否需要运费
//					int needExpressPrice = configAttributeService.getIntValueByCode(AttributeConstant.CODE_SHOP_ORDER_NEED_EXPRESS_BOTTOM_PRICE);
//					if(needExpressPrice > order.getCost_money()){
//						int expressPrice = configAttributeService.getIntValueByCode(AttributeConstant.CODE_SHOP_ORDER_EXPRESS_PRICE);
//						order.setExpress_price(expressPrice);
//						order.setCost_money(order.getCost_money() + expressPrice);
//						order.setCost_price(order.getCost_price() + expressPrice);
//					}else{
						order.setExpress_price(0);
//					}
						
					if(isEff) {
						updateShopOrderEffInfo((ShopOrderEffInfoBean) order);
					}else {
						updateShopOrderInfo(order);
					}
				}
			}
		}catch(Exception e) {
			log.error(e, e);
		}
		
		return order;
	}
	
	/**
	 * 更新商品订单为已支付，并将订单关联的优惠卷等状态实际变更
	 */
	@Override
	public void updateShopOrderPaySuccess(int payMoney,String orderCode){
		ShopOrderInfoBean order = findShopOrderInfoByCode(orderCode);
		
		// 判断订单金额与用户实际支付金额是否一致
		if(order.getCost_money().intValue() == payMoney){
			updateShopOrderStatus(orderCode,ShopOrderInfoBean.STATUS_PAYSUCCESS);
			List<SupplierCouponRecordBean> recordList = supplierCouponService.queryCouponRecordByOrderCode(orderCode,order.getUser_id());
			
			//处理订单的优惠卷为已使用，并创建赠送的订单商品
			handleOrderCouponToUsed(order, recordList);
			
			SupplierUserBean user = supplierUserService.findSupplierUser(order.getUser_id(), order.getSupplier_id());
			//扣减用户积分
			int costPoints = order.getCost_points();   // 用户还没有付款呢
			if(costPoints >0){
				user.setPoints(user.getPoints() - costPoints);
				supplierUserService.updateSupplierUser(user);
				userCommissionPointsService.createUserPointLog(user.getSupplier_id(), user.getUser_id(), -costPoints, "购买"+ order.getOrder_name()+" 扣减积分" );
			}
			
			SupplierBean supplier = supplierService.findSupplierById(order.getSupplier_id());

			if(StringUtil.isNull(order.getContact_name())) {
				order.setContact_name(user.getNick_name());
			}
			if(StringUtil.isNull(order.getContact_phone())) {
				order.setContact_phone("无");
			}
			if(StringUtil.isNull(order.getContact_address())) {
				order.setContact_address("店铺内下单，无需配送");
			}
			//保存并处理用户购买成功的动作
			int type = NumberUtil.convertNull(order.getType());
			
			//不同的订单类型，需要推送不同的消息
			switch (type) {
			case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_ONLINE:
				msgCenterActionService.saveAndHandleUserAction(supplier.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_NEED_DELIVERY , null, order);

				break;
			case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_OFFLINE:
				msgCenterActionService.saveAndHandleUserAction(supplier.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_PAY , null, order);
				break;

			default:
				break;
			}
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_PAY_NOTIFY_FRIENDS , null, order);
			
			
			
		}else{
			//订单金额有问题
			updateShopOrderStatus(orderCode,ShopOrderInfoBean.STATUS_NEED_AGAIN_ORDER);
			
		}
	}
	
	/**
	 * 处理订单的优惠卷使用情况
	 * @param order
	 * @param list
	 */
	private void handleOrderCouponToUsed(ShopOrderInfoBean order , List<SupplierCouponRecordBean> recordList){
		
		//订单的价格在用户下单支付时已更新，这里仅需要更新订单产品即可
		for(SupplierCouponRecordBean record : recordList){
			// 不同的优惠卷类型，对订单的处理方式不同
			switch(record.getCoupon_type()){
			case SupplierCouponConfigBean.TYPE_CASH:
				// 暂无商品要处理
				break;
			case SupplierCouponConfigBean.TYPE_CHANGE:
			case SupplierCouponConfigBean.TYPE_GIFT:
				// 产品列表
				SupplierCouponConfigBean coupon = supplierCouponService.findCouponInfoByCouponid(record.getCoupon_id());
				String spidStr = coupon.getCoupon_spid();
				if(StringUtil.isNotNull(spidStr)){
					String[] spids = spidStr.split(",");
					for(String spid : spids){
						// 根据配置的商户商品ID 创建 优惠卷增加的订单商品
						createShopOrderProductBySpid(Integer.parseInt(spid), 1, ShopOrderProductBean.TYPE_COUPON, 0, 0, order.getOrder_code());
					}
					
					//更新订单名称，增加数量
					String orderName = order.getOrder_name();
					if(orderName.indexOf("等")>=0){
						ShopOrderProductBean bean = new ShopOrderProductBean();
						bean.setOrder_code(order.getOrder_code());
						List<ShopOrderProductBean> products = shopOrderDao.queryShopOrderProduct(bean );
						orderName = orderName.split("等")[0] + "等" + (products.size() + spids.length )+ "类商品";
					}else{
						orderName = orderName+"等"+ (spids.length + 1 ) +"类商品";
					}
					
				}
				break;
			case SupplierCouponConfigBean.TYPE_DISCOUNT:
				// 修改订单商品价格
//				coupon = supplierCouponService.findCouponInfoByCouponid(record.getCoupon_id());
//				int newMoney = costMoney * coupon.getCoupon_discount() / 100;
				
				
				break;
			case SupplierCouponConfigBean.TYPE_EXPERIENCE:
				// 待补充
				break;
			}
		}
		//更新订单的优惠卷为已使用
		supplierCouponService.updateCouponRecordStatusBecomeUsedByOrderCode(order.getOrder_code(), order.getOrder_name());
		
	}
	
	/**
	 * 根据优惠卷更新订单价格数据
	 * @param orderCode
	 * @param bean
	 */
	@Override
	public void updateShopOrderByCoupon(String orderCode,SupplierCouponRecordBean bean){
		
		if(bean == null){
			return;
		}
		SupplierCouponConfigBean coupon = supplierCouponService.findCouponInfoByCouponid(bean.getCoupon_id());
		ShopOrderInfoBean order = findShopOrderInfoByCode(orderCode);

		int costMoney = order.getCost_money();
		// 给订单增加优惠卷减免的金额
		int couponMoney = coupon.getCoupon_offsetmoney();
		order.setCoupon_total_price(couponMoney);
		
		// 不同的优惠卷类型，对订单的处理方式不同
		switch(coupon.getType()){
		case SupplierCouponConfigBean.TYPE_CASH:
			order.setCost_money(costMoney - couponMoney);
			
			break;
		case SupplierCouponConfigBean.TYPE_DISCOUNT:
			int newMoney = costMoney * coupon.getCoupon_discount() / 100;
			int value = costMoney - newMoney;
			if(value > couponMoney){
				value = couponMoney;
			}
			order.setCoupon_total_price(value);
			order.setCost_money(costMoney - value);
			
			break;
		case SupplierCouponConfigBean.TYPE_GIFT:
		case SupplierCouponConfigBean.TYPE_CHANGE:
			//因为赠送了商品，订单总价要增加，优惠也要增加
			order.setCost_price(order.getCost_price() + couponMoney);
			
			break;
		case SupplierCouponConfigBean.TYPE_EXPERIENCE:
			// 待补充
			break;
		}
		bean.setOrder_id(order.getId());
		bean.setOrder_code(order.getOrder_code());
		//更新订单
		updateShopOrderInfo(order);
		
		//更新优惠卷中的订单编号
		supplierCouponService.updateOrderCodeCouponRecordById(bean.getId(), orderCode);
	}
	
	@Override
	public void updateOrderStatusToCancel(String orderCode,String openid,String remark) {
		
		ShopOrderEffInfoBean effOrder = findShopOrderEffInfoByCode(orderCode);
		ShopOrderInfoBean order = findShopOrderInfoByCode(orderCode);

		effOrder.setStatus(ShopOrderInfoBean.STATUS_CANCEL);
		effOrder.setRemark(remark);
		order.setStatus(ShopOrderInfoBean.STATUS_CANCEL);
		order.setRemark(remark);
		
		updateShopOrderEffInfo(effOrder);
		updateShopOrderInfo(order);
		
		msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_CANCEL, "cancel", effOrder);
		
	}
	

	@Override
	public void updateShopOrderStatus(String order_code,int status) {
		ShopOrderInfoBean order = new ShopOrderInfoBean();
		order.setOrder_code(order_code);
		order.setStatus(status);
		updateShopOrderInfo(order);
	}

	@Override
	public void updateShopOrderEffStatus(String order_code,int status) {
		ShopOrderEffInfoBean order = new ShopOrderEffInfoBean();
		order.setOrder_code(order_code);
		order.setStatus(status);
		updateShopOrderEffInfo(order);
	}
	@Override
	public void delteShopOrderById(int id){
		shopOrderDao.delteShopOrderById(id);
	}
	
	@Override
	public void updateShopOrderStatusToDelete(String order_code){
		//多个订单表一起更新
		updateShopOrderStatus(order_code, ShopOrderInfoBean.STATUS_USER_DELETE);
		updateShopOrderEffStatus(order_code, ShopOrderInfoBean.STATUS_USER_DELETE);
	}
	
	
	@Override
	public void setupOrderAddress(String order_code,int userAddrId){
		
		ShopOrderInfoBean order = findShopOrderInfoByCode(order_code);
		UserAddressBean userAddr = userAddressService.findUserAddressById(userAddrId);
		
		order.setContact_address(userAddr.getContact_address());
		order.setContact_name(userAddr.getContact_name());
		order.setContact_phone(userAddr.getContact_phone());
		
		updateShopOrderInfo(order);
		
	}
	
	@Override
	public ShopOrderRemindBean createShopOrderRemind(String remind,UserWechatBean user,ShopOrderInfoBean order){
		
		ShopOrderRemindBean bean = new ShopOrderRemindBean();
		bean.setCreateDate(DateUtil.getNowDateStr());
		bean.setRemind(remind);
		bean.setOrderCode(order.getOrder_code());
		bean.setUserId(user.getId());
//		bean.setNickName(user.getNick_name());
		bean.setOrderId(order.getId());
		bean.setReadFlag(ShopOrderRemindBean.READFALG_NO);
		bean.setContactName(order.getContact_name());
		bean.setContactPhone(order.getContact_phone());
		bean.setOrderName(order.getOrder_name());
		
		
		shopOrderDao.createShopOrderRemind(bean);
		
		return bean;
	}
	
	

	@Override
	public String notifyShopOrderByBalance(Integer sid,String orderCode,String openid, int balance,Integer coupon_id,Integer card_record_id , Integer type,String remark) {
		String result = "您的储值卡余额无法支付当前订单，请重新选择";
		SupplierUserBean spUser = supplierUserService.findSupplierUser(openid, sid);
		//如果优惠卷规记录id不等于空
		if(coupon_id != null ){
			SupplierCouponRecordBean couponRecordBean = new SupplierCouponRecordBean();
			couponRecordBean.setUserid(spUser.getUser_id());
			couponRecordBean.setStatus(SupplierCouponRecordBean.STATUS_CANUSE);
			couponRecordBean.setId(coupon_id);
			couponRecordBean  = supplierCouponService.findCouponRecord(couponRecordBean);
			//根据优惠卷信息 更新订单数据，主要是价格侧的变动
			this.updateShopOrderByCoupon(orderCode, couponRecordBean);
			if(couponRecordBean == null){
				log.error(" notifyShopOrder couponRecord is null ,userid:"+ spUser.getUser_id() +" recordid:" + coupon_id);
			}
		}
		
		// 使用了折扣卡/储值卡 
		if(card_record_id != null) {
			SupplierStoreBalanceCardRecordBean cardRecord = supplierStoreService.findStoreBalanceCardRecordById(card_record_id);
			
			ShopOrderInfoBean order = this.findShopOrderInfoByCode(orderCode);
			int money = order.getCost_money();
			int discountMoney = money - (money * cardRecord.getDiscount()) / 1000;
			balance = money * cardRecord.getDiscount() / 1000;
			if(money <= cardRecord.getBalance() && balance <= cardRecord.getBalance() ) {
				order.setCost_balance(balance);
				order.setCost_money(0);
				order.setStore_card_total_price(discountMoney);
				//修改订单余额
				updateShopOrderInfo(order);
			}else {
				return result;
			}
			result = notifyShopOrder(sid, orderCode, openid, 0, coupon_id, card_record_id, type, remark);
			if("success".equalsIgnoreCase(result)) {
				String remarkStr  = "订单消费";
				supplierStoreService.updateStoreCardRecordBalance(openid, card_record_id, -balance, sid,orderCode,remarkStr);
			}
		}
		
		return result;
	}
	
	@Override
	public String notifyShopOrder(Integer sid,String orderCode,String openid, int cash_fee,Integer coupon_id,Integer card_record_id , Integer type,String remark) {
		
		switch (type) {
		case SupplierBalanceLogBean.TYPE_USER_PAYDIRECT:
			supplierService.updateSupplierBalance(sid, cash_fee, orderCode, openid ,type,remark);
			
			break;
		case SupplierBalanceLogBean.TYPE_USER_RECHARGE:
//			supplierService.updateSupplierBalance(sid, cash_fee, orderCode, result.getOpenid(),type,remark);
			
			break;
		case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_ONLINE:

			//更新充值记录表和订购表(通过订单号更新ll_user_consume_info表中的状态)
			userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, orderCode);
			//通过订单号在订购表中更改状态为更新支付成功状态
			this.updateShopOrderPaySuccess(cash_fee,orderCode );
			supplierService.updateSupplierBalance(sid, cash_fee, orderCode, openid,type,remark);
			
			break;
		case SupplierBalanceLogBean.TYPE_USER_SHOPORDER_OFFLINE:

			//更新充值记录表和订购表(通过订单号更新ll_user_consume_info表中的状态)
			userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, orderCode );
			//通过订单号在订购表中更改状态为更新支付成功状态
			this.updateShopOrderPaySuccess(cash_fee,orderCode );
			//修改订单状态为完成
			this.updateShopOrderStatus(orderCode, ShopOrderInfoBean.STATUS_FINISH);
			supplierService.updateSupplierBalance(sid, cash_fee, orderCode, openid,type,remark);
			
			break;
		}
		
		
		
		
		return "success";
		
	}
	
}
