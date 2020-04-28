/**
 * 
 */
package com.yd.business.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.order.bean.OrderLogConditionBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.dao.IOrderProductLogDao;

/**
 * @author ice
 *
 */
@Repository("orderProductLogDao")
public class OrderProductLogDaoImpl extends BaseDao implements IOrderProductLogDao {
	private static final String NAMESPACE = "orderProductLog.";
	

	@Override
	public List<OrderProductLogBean> queryOrderProductLog(OrderProductLogBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrderProductLog", bean);
	}
	@Override
	public OrderProductLogBean findOrderProductLogByCode(String orderCode){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findOrderProductLogByCode", orderCode);
	}
	
	@Override
	public void updateOrderProductLogStatus(int status,String order_code){
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("status", status);
		map.put("order_code", order_code);
		
		sqlSessionTemplate.update(NAMESPACE+"updateOrderProductLogStatus", map);
		
	}
	
	@Override
	public OrderProductLogBean findOrderProductLogById(int id) {
		
		return sqlSessionTemplate.selectOne(NAMESPACE+"findOrderProductLogById", id);
	}
	@Override
	public void createOrderProductLog(OrderProductLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createOrderProductLog", bean);
	}
	@Override
	public void updateOrderProductLog(OrderProductLogBean bean){
		sqlSessionTemplate.update(NAMESPACE+"updateOrderProductLog", bean);
	}
	
	@Override
	public List<OrderProductLogBean> queryOrderProductLogByNoPay(String minTime) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryOrderProductLogByNoPay", minTime);
	}
	@Override
	public PartnerOrderProductBean findPartnerOrderProductByOrderCode(String orderCode) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"findPartnerOrderProductByOrderCode", orderCode);
	}
	@Override
	public PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partner_out_trade_no) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"findPartnerOrderProductByPartnerOrderCode", partner_out_trade_no);
	}
	@Override
	public List<PartnerOrderProductBean> queryPartnerOrderProduct(PartnerOrderProductBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryPartnerOrderProduct", bean);
	}
	
	/**
	 *根据userid查询这个人有没有历史记录手机号码
	 * @param request
   	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@Override
	public List<OrderProductLogBean> findOrderProductLogByUseridDesc(OrderProductLogBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrderProductLog", bean,rowBound(bean));
	}
	
	/**
	 *根据userid查询这个人有订购记录的用户号码
	 * @param request
   	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@Override
	public List<OrderProductLogBean> queryOrderLogByDesc(OrderProductLogBean userRecodePhone) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryOrderLogByDesc", userRecodePhone,rowBound(userRecodePhone));
	}
	
	
	/**
	 *查询已经付款的用户但是未领取红包
	 */
	@Override
	public List<OrderLogConditionBean> queryLucykIsNullUser(OrderLogConditionBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryLucykIsNullUser", bean);
	}
	
	
	@Override
	public List<OrderProductLogBean> queryOrderProductLogPage(OrderProductLogBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrderProductLogPage", bean,rowBound(bean));
	}
	
	@Override
	public int countOrderProductLog(OrderProductLogBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"countOrderProductLog",bean);
		}
	
	
	@Override
	public void deleteOrderProductLog(OrderProductLogBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteOrderProductLog", bean);
	}
	
	@Override
	public void insertOrderProductLogAdmin(OrderProductLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"insertOrderProductLogAdmin", bean);
	}
	
	@Override
	public void updateOrderProductLogAdmin(OrderProductLogBean bean){
		sqlSessionTemplate.update(NAMESPACE+"updateOrderProductLogAdmin", bean);
	}
}
