package com.yd.business.customer.dao;

import java.util.List;

import com.yd.business.customer.bean.CustomerBalanceLogBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.supplier.bean.SupplierBean;

public interface ICustomerDao {

	/**
	 * 系统登录
	 * @param username
	 * @param password
	 */
	public CustomerBean login(String username,String password);
	/**
	 * 新增客户信息
	 * @param bean
	 */
	public int insertCustomer(CustomerBean bean);
	/**
	 * 修改客户信息
	 * @param bean
	 */
	public void updateCustomer(CustomerBean bean);
	/**
	 * 删除客户信息
	 * @param bean
	 */
	public void deleteCustomer(CustomerBean bean);
	/**
	 * 根据id查询客户信息
	 * @param id
	 * @return
	 */
	public CustomerBean findCustomerById(Integer id);
	/**
	 * 查询客户列表信息
	 * @param bean
	 * @return
	 */
	public List<CustomerBean> listCustomer(CustomerBean bean);
	/**
	 * 根据手机号码查询客户信息
	 * @param bean
	 * @return
	 */
	public CustomerBean findCustomerByPhone(CustomerBean bean);
	/**
	 * 根据商户的父客户id查询
	 * @param list
	 * @return
	 */
	public List<CustomerBean> listCustomerBySupplier(List<SupplierBean> list);
	void addCustomerBalanceLog(CustomerBalanceLogBean bean);
	void updateCustomerBalance(Integer id, int balance);
	/**
	 * 获取平台商的客户id
	 * @return
	 */
	public CustomerBean queryAdminCustomer();
}
