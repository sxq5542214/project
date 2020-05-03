package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierUserBean;

public interface ISupplierUserService {

	void createSupplierUser(SupplierUserBean bean);

	void updateSupplierUser(SupplierUserBean bean);

	List<SupplierUserBean> querySupplierUser(SupplierUserBean bean);

	void createOrUpdateSupplierUser(String openid, Integer sid) ;

	SupplierUserBean findSupplierUser(String openid, int sid);
	
}
