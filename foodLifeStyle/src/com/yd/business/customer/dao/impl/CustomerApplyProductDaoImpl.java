package com.yd.business.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerApplyProductBean;
import com.yd.business.customer.dao.ICustomerApplyProductDao;
/**
 * 客户申领商品
 * @author Anlins
 *
 */
@Repository("customerApplyProductDao")
public class CustomerApplyProductDaoImpl extends BaseDao implements
		ICustomerApplyProductDao {
	private static final String NAMESPACE = "customerApplyProduct.";

	@Override
	public void insertCustomerApplyProduct(CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCustomerApplyProduct", bean);
	}

	@Override
	public void deleteCustomerApplyProduct(CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCustomerApplyProduct", bean);
	}

	@Override
	public void updateCustomerApplyProduct(CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCustomerApplyProduct", bean);
	}

	@Override
	public List<CustomerApplyProductBean> listCustomerApplyProduct(
			CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerApplyProduct", bean);
	}

	@Override
	public void batchDeleteCustomerApplyProduct(
			List<CustomerApplyProductBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDelete", list);
	}

	@Override
	public CustomerApplyProductBean findCustApplyProductById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerApplyProductById", id);
	}
}
