package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierPowerLogBean;

public interface ISupplierPowerLogService {
	public void insertSupplierPowerLog(SupplierPowerLogBean bean);
	public List<SupplierPowerLogBean> listSupplierPowerLog(SupplierPowerLogBean bean);
	public void insertSupplierPowerLog(int supplier_id,int product_id,String product_name,int power_num);
}
