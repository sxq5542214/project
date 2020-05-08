/**
 * 
 */
package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;
import com.yd.business.supplier.dao.ISupplierPackageDao;

/**
 * @author ice
 *
 */
@Repository("supplierPackageDao")
public class SupplierPackageDaoImpl extends BaseDao implements ISupplierPackageDao {
	private static final String NAMESPACE = "supplierPackage.";
	
	@Override
	public void createSupplierPackage(SupplierPackageBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "createSupplierPackage", bean);
	}
	
	@Override
	public List<SupplierPackageBean> querySupplierPackage(SupplierPackageBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "querySupplierPackage", bean);
	}
	
	@Override
	public void createSupplierPackageProduct(SupplierPackageProductBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "createSupplierPackageProduct", bean);
	}
	@Override
	public List<SupplierPackageProductBean> querySupplierPackageProduct(SupplierPackageProductBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"querySupplierPackageProduct", bean);
	}
	@Override
	public void createSupplierPackageProductRecord(SupplierPackageProductRecordBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "createSupplierPackageProductRecord", bean);
	}
	@Override
	public List<SupplierPackageProductRecordBean> querySupplierPackageProductRecord(SupplierPackageProductRecordBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "querySupplierPackageProductRecord", bean);
	}
	
}
