package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.dao.ISupplierPowerLogDao;
@Repository("supplierPowerLogDao")
public class SupplierPowerLogDaoImpl extends BaseDao implements
		ISupplierPowerLogDao {

	private static final String NAMESPACE = "supplierPowerLog.";
	@Override
	public void insertSupplierPowerLog(SupplierPowerLogBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSupplierPowerLog", bean);
	}
	@Override
	public List<SupplierPowerLogBean> listSupplierPowerLog(
			SupplierPowerLogBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierPowerLog", bean);
	}

}
