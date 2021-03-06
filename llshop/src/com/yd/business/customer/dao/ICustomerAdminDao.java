package com.yd.business.customer.dao;

import java.util.List;

import com.yd.business.customer.bean.CustomerAdminBean;
/**
 * 操作员dao层接口类
 * @author Anlins
 *
 */
public interface ICustomerAdminDao {

	/**
	 * 新增操作员
	 * @param bean
	 */
	public void insertCustomerAdmin(CustomerAdminBean bean);
	/**
	 * 修改操作员
	 * @param bean
	 */
	public void updateCustomerAdmin(CustomerAdminBean bean);
	/**
	 * 删除操作员
	 * @param bean
	 */
	public void deleteCustomerAdmin(CustomerAdminBean bean);
	/**
	 * 根据id查询操作员信息
	 * @param id
	 */
	public CustomerAdminBean findCustomerAdminById(Integer id);
	/**
	 * 获取操作员列表信息
	 * @param bean
	 * @return
	 */
	public List<CustomerAdminBean> listCustomerAdmin(CustomerAdminBean bean);
	/**
	 * 批量删除操作
	 * @param list
	 */
	public void batDelCustomerAdmin(List<CustomerAdminBean> list);
	/**
	 * 操作员管理登录
	 * @param username
	 * @param password
	 * @return
	 */
	public CustomerAdminBean loginByCustomerAdmin(String username,String password);
}
