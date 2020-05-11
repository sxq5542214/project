package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;

public interface ISupplierPackageService {

	void createSupplierPackage(SupplierPackageBean bean);

	List<SupplierPackageBean> querySupplierPackage(SupplierPackageBean bean);

	void createSupplierPackageProduct(SupplierPackageProductBean bean);

	List<SupplierPackageProductBean> querySupplierPackageProduct(SupplierPackageProductBean bean);

	void createSupplierPackageProductRecord(SupplierPackageProductRecordBean bean);

	List<SupplierPackageProductRecordBean> querySupplierPackageProductRecord(SupplierPackageProductRecordBean bean);

	SupplierPackageBean findSupplierPackageById(Integer id, Integer sid);

	void createSupplierPackageAndProducts(SupplierPackageBean bean, String[] products, String[] num);

	void updateSupplierPackage(SupplierPackageBean bean);

	void updatePackageAndProducts(SupplierPackageBean bean, String[] products, String[] nums);

	void createSupplierPackageProductRecord(Integer user_id, Integer packageid, Integer supplier_id);

	boolean updatePackageProductRecordNum(Integer recordId, Integer num, SupplierBean supplier);
}
