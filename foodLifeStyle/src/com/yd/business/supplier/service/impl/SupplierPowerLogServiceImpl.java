package com.yd.business.supplier.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.dao.ISupplierPowerLogDao;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.DateUtil;
@Service("supplierPowerLogService")
public class SupplierPowerLogServiceImpl extends BaseService implements
		ISupplierPowerLogService {

	@Resource
	private ISupplierPowerLogDao supplierPowerLogDao;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Override
	public void insertSupplierPowerLog(SupplierPowerLogBean bean) {
		// TODO Auto-generated method stub
		supplierPowerLogDao.insertSupplierPowerLog(bean);
	}

	@Override
	public List<SupplierPowerLogBean> listSupplierPowerLog(
			SupplierPowerLogBean bean) {
		// TODO Auto-generated method stub
		return supplierPowerLogDao.listSupplierPowerLog(bean);
	}

	/**
	 * supplier_id-授权的商户id
	 * product_id-授权的商品id
	 * product_name-授权的商品名称
	 * power_num-授权数量
	 */
	@Override
	public void insertSupplierPowerLog(int supplier_id,
			int product_id, String product_name,int power_num) {
		SupplierBean supBean = supplierService.findSupplierById(supplier_id);
		SupplierProductBean proBean = new SupplierProductBean();
		proBean.setSupplier_id(supplier_id);
		proBean.setProduct_id(product_id);
		List<SupplierProductBean> list = supplierProductService.listSupplierProduct(proBean);
		int store_num = 0;
		if(list!=null&&list.size()>0) store_num = list.get(0).getStore_num();
		SupplierPowerLogBean bean = new SupplierPowerLogBean();
		bean.setCustomer_id(customerService.getUserId());
		bean.setSupplier_id(supplier_id);
		bean.setCustomer_name(customerService.getUser().getName());
		bean.setSupplier_name(supBean.getName());
		bean.setProduct_id(product_id);
		bean.setProduct_name(product_name);
		bean.setPower_time(DateUtil.getNowDateStr());
		bean.setEff_time(DateUtil.getNowDateStr());
		bean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), supBean.getExpired_day()));
		bean.setPower_num(power_num);
		bean.setStore_num(store_num);
		bean.setUse_num(0);//默认为0
		supplierPowerLogDao.insertSupplierPowerLog(bean);
	}

}
