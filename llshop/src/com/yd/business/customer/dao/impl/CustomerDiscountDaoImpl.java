package com.yd.business.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.customer.dao.ICustomerDiscountDao;
@Repository("customerDiscountDao")
public class CustomerDiscountDaoImpl extends BaseDao implements
		ICustomerDiscountDao {
	private static final String NAMESPACE = "customerDiscount.";
	@Override
	public void insertCustomerDiscount(CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCustomerDiscount", bean);
	}

	@Override
	public void updateCustomerDiscount(CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCustomerDiscount", bean);
	}

	@Override
	public void deleteCustomerDiscount(CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCustomerDiscount", bean);
	}

	@Override
	public List<CustomerDiscountBean> listCustomerDiscount(
			CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerDiscount", bean);
	}

	@Override
	public void batchDeleteCustomerDiscount(List<CustomerDiscountBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDeleteCustomerDiscount", list);
	}

	@Override
	public List<CustomerDiscountBean> listCustDisGroupBrand(
			CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerDiscountBrand", bean);
	}

	@Override
	public void batchDeleteCustomerDiscountById(List<CustomerDiscountBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDeleteCustomerDiscountById", list);
	}

	@Override
	public CustomerDiscountBean findCustomerDiscountById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerDiscountById", id);
	}

}
