package com.yd.business.order.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.order.bean.AreaData;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.user.bean.UserConsumeInfoBean;

public interface IOrderService {

	AreaData getAreaDataByPhone(String phone);

	OrderProductLogBean orderProductByUser(String out_trade_no, String param);

	OrderProductLogBean orderProductBySupplierStoreNum(String adminid, String spid, String phone);

	OrderProductLogBean orderProductBySupplierBalance(String out_trade_code, Integer adminId);

	PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partnerOrderCode);

	OrderProductLogBean orderProductBySupplierCardSecret(SupplierCardSecretBean cardSecret, String phone);

	List<OrderProductEffBean> queryOrderProductEffByStatus(int status);

	void createOrderProductEff(OrderProductEffBean bean);

	void updateOrderProductEff(OrderProductEffBean bean);

//	OrderProductLogBean orderProductByOrderProductLog(OrderProductLogBean orderLog);

	List<OrderProductEffBean> queryOrderProductEff(OrderProductEffBean bean);

	OrderProductEffBean createOrderProductEffByOrderProductLog(OrderProductLogBean orderLog, int month_offset);

	OrderProductLogBean orderProductByActivity(ActivityUserRelationBean activityUserRelation);

	void saveOrUpdatePartnerOrderProduct(PartnerOrderProductBean bean);

	OrderProductLogBean handlerOrderProductCallBack(String orderCode, int status, String remark);

	String createUnifiedOrder(Integer adminid, String phone, Integer spid, String interfaceType, Integer event_type);

	OrderProductEffBean updateOrderProductEffDate(int id, OrderProductLogBean bean,int month_offset);
	
	OrderProductEffBean dealEffOrderProduct(OrderProductEffBean effOrder,OrderProductLogBean orderLog);
	
	OrderProductEffBean queryOrderProductEffById(long id);
	/**
	 * 查询预约订单明细
	 * @param userId
	 * @return
	 */
	List<OrderProductEffShowPageBean> queryEffOrderProductLogByUserId(int userId, int status);
	
	/**
	 * 直接生效订单
	 * @param id
	 * @return
	 */
	OrderProductLogBean effUserOrder(int id);
	
	/**
	 * 处理用户预约订单
	 * @param nextNum
	 * @param id
	 * @param orderCode
	 * @return
	 */
	Map<String,Object> modifyEffOrderProduct(int nextNum, int id);
	
	/**
	 * 为用户生成打包订单，用户一次性购买打包商品时，要对于下的几个商品生成打包订单（预约订单）
	 * @param orderLog
	 */
	void createPackageOrderProduct(OrderProductLogBean orderLog,UserConsumeInfoBean consumeInfo);
}
