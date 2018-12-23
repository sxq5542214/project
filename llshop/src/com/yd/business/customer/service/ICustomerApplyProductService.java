package com.yd.business.customer.service;

import java.util.List;

import org.json.JSONArray;

import com.yd.business.customer.bean.CustomerApplyProductBean;
/**
 * 客户申领商品服务
 * @author Anlins
 *
 */
public interface ICustomerApplyProductService {
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
	/**
	 * 申领审核
	 * @return
	 */
	public String applyAudit(Integer id,Integer status,Integer supid,Integer num);
	/**
	 * 购买商品
	 * @param money
	 * @param arr
	 * @return
	 */
	public String mealProduct(int money,JSONArray arr);
}
