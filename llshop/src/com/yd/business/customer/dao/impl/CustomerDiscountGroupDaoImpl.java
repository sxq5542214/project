package com.yd.business.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerDiscountGroupBean;
import com.yd.business.customer.dao.ICustomerDiscountGroupDao;
import com.yd.util.NumberUtil;
@Repository("customerDiscountGroupDao")
public class CustomerDiscountGroupDaoImpl extends BaseDao implements
		ICustomerDiscountGroupDao {
	private static final String NAMESPACE = "customerDiscountGroup.";
	@Override
	public int insertCustomerDiscountGroup(CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		return NumberUtil.toInt(sqlSessionTemplate.insert(NAMESPACE+"insertCustomerDiscountGroup", bean),-1);
	}

	@Override
	public void updateCustomerDiscountGroup(CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCustomerDiscountGroup", bean);
	}

	@Override
	public void deleteCustomerDiscountGroup(CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCustomerDiscountGroup", bean);
	}

	@Override
	public List<CustomerDiscountGroupBean> listCustomerDiscountGroup(
			CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerDiscountGroup", bean);
	}

	@Override
	public void batchDeleteCustomerDiscountGroup(
			List<CustomerDiscountGroupBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDeleteCustomerDiscountGroup", list);
	}

	@Override
	public List<CustomerDiscountGroupBean> listCustDisGroupInfo(
			CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerDisGroupInfo", bean);
	}

	@Override
	public CustomerDiscountGroupBean findCustDisGroupById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerDiscountGroupById", id);
	}

}
