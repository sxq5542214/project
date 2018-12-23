package com.yd.business.other.dao;

import java.util.List;

import com.yd.business.other.bean.AddressBean;

public interface IAddressDao {

	List<AddressBean> queryAddress(AddressBean bean);

}
