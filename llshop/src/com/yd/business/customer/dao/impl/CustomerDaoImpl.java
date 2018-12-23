package com.yd.business.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerBalanceLogBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.dao.ICustomerDao;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.util.NumberUtil;
/**
 * 客户信息
 * @author Anlins
 *
 */
@Repository("customerDao")
public class CustomerDaoImpl extends BaseDao implements ICustomerDao {

	private static final String NAMESPACE = "customer.";

	@Override
	public CustomerBean login(String username, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", password);
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerByLogin", params);
	}

	@Override
	public int insertCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		return NumberUtil.toInt(sqlSessionTemplate.insert(NAMESPACE+"insertCustomer", bean),-1);
	}

	@Override
	public void updateCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCustomer", bean);
	}
	@Override
	public void updateCustomerBalance(Integer id,int balance) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("balance", balance);
		sqlSessionTemplate.update(NAMESPACE+"updateCustomerBalance", map);
	}

	@Override
	public void deleteCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCustomer", bean);
	}

	@Override
	public CustomerBean findCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCustomerById", id);
	}

	@Override
	public List<CustomerBean> listCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomer", bean);
	}

	@Override
	public CustomerBean findCustomerByPhone(CustomerBean bean) {
		// TODO Auto-generated method stub
		List<CustomerBean> list = listCustomer(bean);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public List<CustomerBean> listCustomerBySupplier(List<SupplierBean> list) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCustomerByParentId", list);
	}
	
	@Override
	public void addCustomerBalanceLog(CustomerBalanceLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"addCustomerBalanceLog", bean);
	}

	@Override
	public CustomerBean queryAdminCustomer() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryAdminCustomer", null);
	}
}
