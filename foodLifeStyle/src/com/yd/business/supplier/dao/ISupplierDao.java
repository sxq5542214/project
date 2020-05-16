package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierBalanceLogBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierTypeBean;
/**
 * 商户
 * @author Anlins
 *
 */
public interface ISupplierDao {

	public void insertSupplier(SupplierBean bean);
	public void updateSupplier(SupplierBean bean);
	public void deleteSupplier(SupplierBean bean);
	public List<SupplierBean> listSupplier(SupplierBean bean);
	public List<SupplierBean> querySupplierByCustomerId(int customerId);
	public void batchDeleteSupplier(List<SupplierBean> list);
	public SupplierBean findSupplierById(Integer id);
	public List<SupplierBean> listSupplierByProductid(Integer parentcustomerid,Integer productid);
	public List<SupplierBean> listSupplierByPower(int parentCustomerId);
	/**
	 * 获取当前注册客户下关联的商户
	 * @param customerid
	 * @return
	 */
	public SupplierBean queryMealSupplier(int customerid);
	public List<SupplierBean> querySupplierByMinus(int customerid,int productid,int storenum);
	public List<SupplierTypeBean> querySupplierType(SupplierTypeBean bean);
	public void addSupplierBalance(Integer sid, int addBalance);
	void createSupplierBalanceLog(SupplierBalanceLogBean bean);
	List<SupplierBalanceLogBean> querySupplierBalanceLog(SupplierBalanceLogBean bean);
}
