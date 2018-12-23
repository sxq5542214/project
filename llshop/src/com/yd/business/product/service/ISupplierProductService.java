package com.yd.business.product.service;

import java.util.List;

import com.yd.business.product.bean.SupplierProductAttachBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;

public interface ISupplierProductService {
	public int insertSupplierProduct(SupplierProductBean bean);
	public void updateSupplierProduct(SupplierProductBean bean);
	public List<SupplierProductBean> listSupplierProduct(SupplierProductBean bean);
	public SupplierProductBean findSupplierProductById(Integer id);
	

	List<CustomerSupplierProductBean> queryCustomerSupplierProduct(int customerId);

	List<CustomerSupplierProductBean> queryCustomerProductByPhone(int customer_id, String phone);
	
	List<CustomerSupplierProductBean> queryPlatformSupplierProduct();

	SupplierProductBean findSupplierProductBySpid(Integer spid);
	
	public List<SupplierProductBean> queryProBySupParCustomerId(Integer parcustid,Integer custid);
	public void deleteSupplierProduct(SupplierProductBean bean);
	public void batchDeleteSupplierProduct(List<SupplierProductBean> list);
	List<SupplierProductAttachBean> querySupplierAttachProductBySpid(int spid);
	int returnProductLuckMoneyByOrder(int spid);
	void createOrUpdateSupplierProduct(SupplierProductBean bean);
	List<SupplierProductBean> querySupplierProductByIds(String ids);
	List<SupplierProductBean> querySupplierProductByIds(List<Integer> ids);
}
