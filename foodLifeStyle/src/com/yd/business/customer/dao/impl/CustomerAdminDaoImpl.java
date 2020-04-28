package com.yd.business.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.dao.ICustomerAdminDao;
/**
 * 操作员操作实现类
 * @author Anlins
 *
 */
@Repository("customerAdminDao")
public class CustomerAdminDaoImpl extends BaseDao implements ICustomerAdminDao {

	private static final String NAMESPACE = "customeradmin.";
	@Override
	public void insertCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCustomerAdmin", bean);
	}

	@Override
	public void updateCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCustomerAdmin", bean);
	}

	@Override
	public void deleteCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCustomerAdmin", bean);
	}

	@Override
	public CustomerAdminBean findCustomerAdminById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerAdminById", id);
	}

	@Override
	public List<CustomerAdminBean> listCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerAdmin", bean);
	}

	@Override
	public void batDelCustomerAdmin(List<CustomerAdminBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDelete", list);
	}

	@Override
	public CustomerAdminBean loginByCustomerAdmin(String username,
			String password) {
		CustomerAdminBean bean = new CustomerAdminBean();
		bean.setUsername(username);
		bean.setPassword(password);
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerByLogin", bean);
	}
	
}
