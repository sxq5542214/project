package com.yd.business.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.order.bean.PartnerApplyOrderProductBean;
import com.yd.business.order.dao.IPartnerApplyOrderProductDao;
@Repository("partnerApplyOrderProductDao")
public class PartnerApplyOrderProductDaoImpl extends BaseDao implements
		IPartnerApplyOrderProductDao {
	private static final String NAMESPACE = "partnerApplyOrderProduct.";
	@Override
	public PartnerApplyOrderProductBean findPartnerApplyOrderProductById(
			Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryOrderProduct", id);
	}

	@Override
	public List<PartnerApplyOrderProductBean> listPartnerApplyOrderProduct(
			PartnerApplyOrderProductBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrderProduct", bean);
	}

	@Override
	public void insertPartnerApplyOrderProduct(PartnerApplyOrderProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertOrderProduct", bean);
	}

	@Override
	public void updatePartnerApplyOrderProduct(PartnerApplyOrderProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateOrderProduct", bean);
	}

	@Override
	public void deletePartnerApplyOrderProductById(Integer id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteOrderProduct", id);
	}

	@Override
	public void deletePartnerApplyOrderProductByOrderId(Integer id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteOrderProductByOrderId", id);
	}

}
