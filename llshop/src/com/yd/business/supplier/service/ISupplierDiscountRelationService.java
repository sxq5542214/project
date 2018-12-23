package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierDiscountRelationBean;

public interface ISupplierDiscountRelationService {
	public int insertSupplierDiscountRelation(SupplierDiscountRelationBean bean);
	public void deleteSupplierDiscountRelation(SupplierDiscountRelationBean bean);
	public List<SupplierDiscountRelationBean> listSupDisRela(SupplierDiscountRelationBean bean);
	public void batchDeleteSupplierDiscountRelation(List<SupplierDiscountRelationBean> list);
}
