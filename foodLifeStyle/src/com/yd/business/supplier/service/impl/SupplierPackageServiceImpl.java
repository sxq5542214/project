/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;
import com.yd.business.supplier.dao.ISupplierPackageDao;
import com.yd.business.supplier.service.ISupplierPackageService;

/**
 * @author ice
 *
 */
@Service("supplierPackageService")
public class SupplierPackageServiceImpl extends BaseService implements ISupplierPackageService {
	@Resource
	private ISupplierPackageDao supplierPackageDao;
	
	

	@Override
	public void createSupplierPackage(SupplierPackageBean bean) {
		supplierPackageDao.createSupplierPackage(bean);
	}
	
	@Override
	public List<SupplierPackageBean> querySupplierPackage(SupplierPackageBean bean){
		return supplierPackageDao.querySupplierPackage(bean);
	}
	
	@Override
	public void createSupplierPackageProduct(SupplierPackageProductBean bean) {
		supplierPackageDao.createSupplierPackageProduct(bean);
	}
	@Override
	public List<SupplierPackageProductBean> querySupplierPackageProduct(SupplierPackageProductBean bean){
		return supplierPackageDao.querySupplierPackageProduct(bean);
	}
	@Override
	public void createSupplierPackageProductRecord(SupplierPackageProductRecordBean bean) {
		supplierPackageDao.createSupplierPackageProductRecord(bean);
	}
	@Override
	public List<SupplierPackageProductRecordBean> querySupplierPackageProductRecord(SupplierPackageProductRecordBean bean){
		return supplierPackageDao.querySupplierPackageProductRecord(bean);
	}
	
	
}
