package com.yd.business.supplier.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.supplier.bean.SupplierDiscountRelationBean;
import com.yd.business.supplier.dao.ISupplierDiscountRelationDao;
import com.yd.business.supplier.service.ISupplierDiscountRelationService;
@Service("supplierDiscountRelationService")
public class SupplierDiscountRelationServiceImpl extends BaseService implements
		ISupplierDiscountRelationService {

	@Resource
	private ISupplierDiscountRelationDao supplierDiscountRelationDao;
	@Override
	public int insertSupplierDiscountRelation(SupplierDiscountRelationBean bean) {
		// TODO Auto-generated method stub
		return supplierDiscountRelationDao.insertSupplierDiscountRelation(bean);
	}

	@Override
	public void deleteSupplierDiscountRelation(SupplierDiscountRelationBean bean) {
		// TODO Auto-generated method stub
		supplierDiscountRelationDao.deleteSupplierDiscountRelation(bean);
	}

	@Override
	public List<SupplierDiscountRelationBean> listSupDisRela(
			SupplierDiscountRelationBean bean) {
		// TODO Auto-generated method stub
		return supplierDiscountRelationDao.listSupDisRela(bean);
	}

	@Override
	public void batchDeleteSupplierDiscountRelation(
			List<SupplierDiscountRelationBean> list) {
		// TODO Auto-generated method stub
		supplierDiscountRelationDao.batchDeleteSupplierDiscountRelation(list);
	}

}
