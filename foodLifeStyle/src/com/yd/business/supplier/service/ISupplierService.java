package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierBean;

public interface ISupplierService {

	List<SupplierBean> querySupplierByCustomerId(int customerId);
	public String insertSupplier(SupplierBean bean,String disGroupIds);
	public void updateSupplier(SupplierBean bean);
	public void deleteSupplier(SupplierBean bean);
	public List<SupplierBean> listSupplier(SupplierBean bean);
	public void batchDeleteSupplier(List<SupplierBean> list);
	public SupplierBean findSupplierById(Integer id);
	/**
	 * 选择需要核减的商户
	 * @return
	 */
	public List<SupplierBean> listMinusSupplier(Integer customerid,Integer productid);
	/**
	 * 商户授权
	 * @param params
	 * @return
	 */
	public String insetSupPower(String params);
	public List<SupplierBean> listSupplierByPower(int parentCustomerId);
	/**
	 * 取消授权
	 * @return
	 */
	public String cancelPower(int id,int supplierid);
	/**
	 * 获取当前注册客户下关联的商户
	 * @param customerid
	 * @return
	 */
	public SupplierBean queryMealSupplier(int customerid);
	/**
	 * 商品授权
	 * @param params
	 * @return
	 */
	public String powerSupProduct(String params);
	/**
	 * 商品指派
	 * @param params
	 * @return
	 */
	public String designSupplierProduct(String params);
	public List<SupplierBean> querySupplierByMinus(int customerid,int productid,int storenum);
}
