package com.yd.business.supplier.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.supplier.bean.SupplierVolumBean;
import com.yd.business.user.bean.UserWechatBean;

public interface ISupplierVolumService {

	void createMoneyVolum(SupplierVolumBean bean);

	void createExchangeVolum(SupplierVolumBean bean);

	PageinationData querySupplierVolum(SupplierVolumBean bean);

	Integer updateVolumOfGetUser(SupplierVolumBean bean, UserWechatBean user);

	SupplierVolumBean findVolumById(Integer id);

	List<SupplierVolumBean> queryFreeVolum(Integer count);

}
