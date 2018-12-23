package com.yd.business.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.order.bean.PartnerApplyOrderBean;
import com.yd.business.order.dao.IPartnerApplyOrderDao;
@Repository("partnerApplyOrderDao")
public class PartnerApplyOrderDaoImpl extends BaseDao implements
		IPartnerApplyOrderDao {

	private static final String NAMESPACE = "partnerApplyOrder.";
	@Override
	public PartnerApplyOrderBean findPartnerApplyOrderById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryOrderById", id);
	}

	@Override
	public List<PartnerApplyOrderBean> listPartnerApplyOrder(
			PartnerApplyOrderBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOrder", bean);
	}

	@Override
	public void updatePartnerApplyOrder(PartnerApplyOrderBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateOrder", bean);
	}

	@Override
	public void deletePartnerApplyOrder(Integer id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteOrderById", id);
	}

	@Override
	public void insertPartnerApplyOrder(PartnerApplyOrderBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertOrder", bean);
	}

}
