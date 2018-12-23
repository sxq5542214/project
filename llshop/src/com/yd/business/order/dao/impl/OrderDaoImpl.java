/**
 * 
 */
package com.yd.business.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.order.bean.AreaData;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.dao.IOrderDao;

/**
 * @author ice
 *
 */
@Repository("orderDao")
public class OrderDaoImpl extends BaseDao implements IOrderDao {
	private static final String NAMESPACE = "order.";
	
	@Override
	public List<AreaData> queryAreaData(AreaData ad){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAreaData", ad);
	}
	
	@Override
	public void savePartnerOrderProduct(PartnerOrderProductBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"savePartnerOrderProduct", bean);
	}
	
	@Override
	public void updatePartnerOrderProduct(PartnerOrderProductBean bean){
		sqlSessionTemplate.update(NAMESPACE+"updatePartnerOrderProduct", bean);
	}
	
	@Override
	public PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partnerOrderCode){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findPartnerOrderProductByPartnerOrderCode",partnerOrderCode);
	}

	@Override
	public List<OrderProductEffBean> queryOrderProductEffByStatus(String beforDate,Integer status) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beforDate", beforDate);
		map.put("status", status);
		
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrderProductEffByStatus", map);
	}
	
	@Override
	public List<OrderProductEffBean> queryOrderProductEff(OrderProductEffBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrderProductEff", bean);
	}

	@Override
	public void createOrderProductEff(OrderProductEffBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createOrderProductEff", bean);
	}

	@Override
	public void updateOrderProductEff(OrderProductEffBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updateOrderProductEff", bean);
	}

	@Override
	public OrderProductEffBean queryOrderProductEffById(long id) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryOrderProductEffById", id);
	}
	
	@Override
	public List<OrderProductEffShowPageBean> queryEffOrderProductLog(Map<String, Object> map) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryEffOrderProductLog", map);
	}
}
