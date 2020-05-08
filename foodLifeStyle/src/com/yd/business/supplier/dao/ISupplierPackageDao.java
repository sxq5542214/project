package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;

public interface ISupplierPackageDao {

	void createSupplierPackage(SupplierPackageBean bean);

	List<SupplierPackageBean> querySupplierPackage(SupplierPackageBean bean);

	void createSupplierPackageProduct(SupplierPackageProductBean bean);

	List<SupplierPackageProductBean> querySupplierPackageProduct(SupplierPackageProductBean bean);

	void createSupplierPackageProductRecord(SupplierPackageProductRecordBean bean);

	List<SupplierPackageProductRecordBean> querySupplierPackageProductRecord(SupplierPackageProductRecordBean bean);

}
