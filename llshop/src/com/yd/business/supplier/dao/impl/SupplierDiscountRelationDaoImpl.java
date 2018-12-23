package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierDiscountRelationBean;
import com.yd.business.supplier.dao.ISupplierDiscountRelationDao;
@Repository("supplierDiscountRelationDao")
public class SupplierDiscountRelationDaoImpl extends BaseDao implements
		ISupplierDiscountRelationDao {

	private static final String NAMESPACE = "supplierDiscountRelation.";
	@Override
	public int insertSupplierDiscountRelation(SupplierDiscountRelationBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(NAMESPACE+"insertSupplierDiscountRelation", bean);
	}

	@Override
	public void deleteSupplierDiscountRelation(SupplierDiscountRelationBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSupplierDiscountRelation",bean);
	}

	@Override
	public List<SupplierDiscountRelationBean> listSupDisRela(
			SupplierDiscountRelationBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierDiscountRelation", bean);
	}

	@Override
	public void batchDeleteSupplierDiscountRelation(
			List<SupplierDiscountRelationBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDeleteSupplierDiscountRelation", list);
	}

}
