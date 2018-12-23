package com.yd.business.product.dao;

import java.util.List;

import com.yd.business.product.bean.SupplierProductAttachBean;
import com.yd.business.product.bean.SupplierProductBean;

public interface ISupplierProductDao {

	public int insertSupplierProduct(SupplierProductBean bean);
	public void updateSupplierProduct(SupplierProductBean bean);
	public List<SupplierProductBean> listSupplierProduct(SupplierProductBean bean);
	public SupplierProductBean findSupplierProductById(Integer id);
	List<SupplierProductBean> querySupplierProduct(SupplierProductBean sp);
	List<SupplierProductBean> queryPlatformProduct(SupplierProductBean sp);
	SupplierProductBean findSupplierProductBySpid(int spid);
	public List<SupplierProductBean> queryProBySupParCustomerId(Integer parcustid,Integer custid);
	public void deleteSupplierProduct(SupplierProductBean bean);
	public void batchDeleteSupplierProduct(List<SupplierProductBean> list);
	List<SupplierProductAttachBean> queryAttachProduct(SupplierProductAttachBean bean);
	public List<SupplierProductBean> querySupplierProductByIds(List<Integer> ids);
	public List<SupplierProductBean> querySupplierProductByIds(String ids);
}
