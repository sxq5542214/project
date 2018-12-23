package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierPowerLogBean;

public interface ISupplierPowerLogDao {

	public void insertSupplierPowerLog(SupplierPowerLogBean bean);
	public List<SupplierPowerLogBean> listSupplierPowerLog(SupplierPowerLogBean bean);
}
