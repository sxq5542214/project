package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierProductAttachBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierProductCategoryBean;

public interface ISupplierProductDao {

	public int insertSupplierProduct(SupplierProductBean bean);
	public void updateSupplierProduct(SupplierProductBean bean);
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
	public void createSupplierProductCategory(SupplierProductCategoryBean bean);
	List<SupplierProductCategoryBean> querySupplierProductCategory(SupplierProductCategoryBean bean);
	void updateSupplierProductCategory(SupplierProductCategoryBean bean);
	void updateSupplierProductsCategoryName(SupplierProductCategoryBean bean);
}
