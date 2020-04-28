package com.yd.business.customer.dao;

import java.util.List;

import com.yd.business.customer.bean.CustomerApplyProductBean;

/**
 * 客户商品申领
 * @author Anlins
 *
 */
public interface ICustomerApplyProductDao {
	
	/**
	 * 客户申领商品增加
	 * @param bean
	 */
	public void insertCustomerApplyProduct(CustomerApplyProductBean bean);
	/**
	 * 客户申领商品修改
	 * @param bean
	 */
	public void deleteCustomerApplyProduct(CustomerApplyProductBean bean);
	/**
	 * 客户申领商品修改
	 * @param bean
	 */
	public void updateCustomerApplyProduct(CustomerApplyProductBean bean);
	/**
	 * 客户申领商品加载
	 * @param bean
	 * @return
	 */
	public List<CustomerApplyProductBean> listCustomerApplyProduct(CustomerApplyProductBean bean);

	/**
	 * 批量删除
	 * @param list
	 */
	public void batchDeleteCustomerApplyProduct(List<CustomerApplyProductBean> list);
	/**
	 * 根据id获取申领工单信息
	 * @param id
	 * @return
	 */
	public CustomerApplyProductBean findCustApplyProductById(Integer id);
}
