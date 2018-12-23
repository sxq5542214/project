/**
 * 
 */
package com.yd.business.order.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.order.bean.OrderLogConditionBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.user.bean.UserConsumeInfoBean;

/**
 * @author ice
 *
 */
public interface IOrderProductLogService {

	List<OrderProductLogBean> queryOrderProductLogByUserId(int userId);

	OrderProductLogBean findOrderProductLogByCode(String out_trade_code);

	OrderProductLogBean createOrderProductLogByUserConsumeInfo(UserConsumeInfoBean consume, int costPoint, int costMoney,
			Integer admin_id);

	void updateOrderProductLogStatusToCancel(String out_code);

	void updateOrderProductLogStatusToPaySuccess(String out_code);

	void createOrUpdateOrderProductLog(OrderProductLogBean orderLog);

	List<OrderProductLogBean> queryOrderProductLogByUserId(int userId, int status);

	OrderProductLogBean createOrderProductLogByUserConsumeInfo(UserConsumeInfoBean consume, int cost_points,
			int cost_money, int cost_balance, Integer admin_id);

	OrderProductLogBean findOrderProductLogById(int id);

	PartnerOrderProductBean findPartnerOrderProductByOrderCode(String orderCode);

	PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partner_out_trade_no);

	List<PartnerOrderProductBean> queryPartnerOrderProduct(PartnerOrderProductBean bean);

	List<OrderProductLogBean> queryOrderProductLogByNoPay(String minTime);

	OrderProductLogBean findOrderProductLogByUseridDesc(int userId);
	
	OrderProductLogBean dealOrderProductLuckyMoney(OrderProductLogBean bean);
	/**
	 * 处理购买流量后分享发送用户红包逻辑
	 * @param orderId
	 * @param openId
	 */
	OrderProductLogBean dealSharedOrderAndSendLuckMoney(String orderId,String openId,String share_type,String ip);
	
	/**
	 * 查询用户订购过的历史号码
	 * @param userId
	 * 
	 */
	List<OrderProductLogBean> queryOrderLogLastTen(int userId);
	
	
	/**
	 * 查询已经付款的用户但是未领取红包
	 */
	List<OrderLogConditionBean> queryLucykIsNullUser();

	/**
	 * 给余额不够，调用接口超时超时的订单，状态改变
	 */
	void updateOrderProductLogStatusOrder(String out_code,int status);

	
	/**
	 * 查询订单表,返回多个订单
	 */
	List<OrderProductLogBean> queryOrderProductLog(OrderProductLogBean bean);
	
	
	/**
	 * 分页查询
	 */
	public PageinationData queryOrderProductLogPage(OrderProductLogBean bean);
	
	
	/**
	 * 删除order_product_log表
	 */
	public void deleteOrderProductLog(OrderProductLogBean bean);
	
	/**
	 * 编辑order_product_log表
	 */
	public void editOrderProductLog(OrderProductLogBean bean);
}
