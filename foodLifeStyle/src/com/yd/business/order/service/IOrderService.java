package com.yd.business.order.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.user.bean.UserConsumeInfoBean;

public interface IOrderService {

	AreaDataBean getAreaDataByPhone(String phone);

	PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partnerOrderCode);

	List<OrderProductEffBean> queryOrderProductEffByStatus(int status);

	void createOrderProductEff(OrderProductEffBean bean);

	void updateOrderProductEff(OrderProductEffBean bean);

//	OrderProductLogBean orderProductByOrderProductLog(OrderProductLogBean orderLog);

	List<OrderProductEffBean> queryOrderProductEff(OrderProductEffBean bean);

	OrderProductEffBean createOrderProductEffByOrderProductLog(OrderProductLogBean orderLog, int month_offset);


	void saveOrUpdatePartnerOrderProduct(PartnerOrderProductBean bean);

	String createUnifiedOrder(Integer adminid, String phone, Integer spid, String interfaceType, Integer event_type);

	OrderProductEffBean updateOrderProductEffDate(int id, OrderProductLogBean bean,int month_offset);
	
	OrderProductEffBean queryOrderProductEffById(long id);
	/**
	 * 查询预约订单明细
	 * @param userId
	 * @return
	 */
	List<OrderProductEffShowPageBean> queryEffOrderProductLogByUserId(int userId, int status);
	
	
	/**
	 * 为用户生成打包订单，用户一次性购买打包商品时，要对于下的几个商品生成打包订单（预约订单）
	 * @param orderLog
	 */
	void createPackageOrderProduct(OrderProductLogBean orderLog,UserConsumeInfoBean consumeInfo);
}
