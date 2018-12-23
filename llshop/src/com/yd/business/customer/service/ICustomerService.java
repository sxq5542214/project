package com.yd.business.customer.service;

import java.util.List;

import com.yd.business.customer.bean.CustomerBalanceLogBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.supplier.bean.SupplierBean;
/**
 * 客户信息服务
 * @author Anlins
 *
 */
public interface ICustomerService {
	/**
	 * 系统登录
	 * @param username
	 * @param password
	 */
	public CustomerBean login(String username,String password);
	/**
	 * 退出
	 */
	public void logoff();
	public void setSession(CustomerBean bean);
	/**
	 * 获取用户信息
	 * @return
	 */
	public CustomerBean getUser();
	public String createToken();
	public Integer getUserId();
	
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
	public CustomerBean findCustomerByPhone(String phone);
	/**
	 * 根据商户的父客户id查询
	 * @param list
	 * @return
	 */
	public List<CustomerBean> listCustomerBySupplier(List<SupplierBean> list);
	void addCustomerBalance(CustomerBean bean,String out_trade_no,  Integer balance, String remark);
	


	/**
	 * 添加客户余额
	 * @param customer_id  
	 * @param balance	 正数为增，负数为减
	 * @param remark	备注
	 */
	void addCustomerBalance(String out_trade_no, Integer customer_id, Integer balance, String remark);
	/**
	 * 获取平台商的客户id
	 * @return
	 */
	public CustomerBean queryAdminCustomer();
	void addCustomerBalanceLog(CustomerBalanceLogBean bean);
	public void updateCustomerBalance(Integer id, int balance);
	CustomerBean findCustomerByOrderCode(String orderCode);
}
