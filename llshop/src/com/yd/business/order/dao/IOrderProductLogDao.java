/**
 * 
 */
package com.yd.business.order.dao;

import java.util.List;

import com.yd.business.order.bean.OrderLogConditionBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;

/**
 * @author ice
 *
 */
public interface IOrderProductLogDao {

	List<OrderProductLogBean> queryOrderProductLog(OrderProductLogBean bean);

	OrderProductLogBean findOrderProductLogByCode(String orderCode);

	void createOrderProductLog(OrderProductLogBean bean);

	void updateOrderProductLogStatus(int status, String order_code);

	void updateOrderProductLog(OrderProductLogBean bean);

	OrderProductLogBean findOrderProductLogById(int id);

	PartnerOrderProductBean findPartnerOrderProductByOrderCode(String orderCode);

	PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partner_out_trade_no);

	List<PartnerOrderProductBean> queryPartnerOrderProduct(PartnerOrderProductBean bean);

	List<OrderProductLogBean> queryOrderProductLogByNoPay(String minTime);


	List<OrderProductLogBean> findOrderProductLogByUseridDesc(OrderProductLogBean bean);
	
	List<OrderProductLogBean> queryOrderLogByDesc(OrderProductLogBean userRecodePhone);
	
	/**
	 *查询已经付款的用户但是未领取红包
	 */
	public List<OrderLogConditionBean> queryLucykIsNullUser(OrderLogConditionBean bean) ;
	
	/**
	 * 分页查询ll_orderProductLog表信息
	 */
	public List<OrderProductLogBean> queryOrderProductLogPage(OrderProductLogBean bean);
	
	
	/**
	 * 查询ll_orderProductLog表总数
	 */
	public int countOrderProductLog(OrderProductLogBean bean);
	
	/**
	 * 删除ll_order_product_log表数据
	 */
	public void deleteOrderProductLog(OrderProductLogBean bean);
	
	/**
	 * 插入ll_order_product_log表数据
	 */
	public void insertOrderProductLogAdmin(OrderProductLogBean bean);
	
	/**
	 * 后台更新ll_order_product_log表数据
	 */
	public void updateOrderProductLogAdmin(OrderProductLogBean bean);

}
