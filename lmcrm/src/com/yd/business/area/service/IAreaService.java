package com.yd.business.area.service;

import java.util.List;

import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.bean.AreaExtBean;
import com.yd.business.company.bean.CompanyExtBean;

public interface IAreaService {

	List<AreaExtBean> queryAreaList(Long companyid);

	int addOrUpdateArea(AreaBean bean);

	CompanyExtBean queryAreaAndBuildingTree(Long companyId) throws Exception;

	List<AddressBean> queryAddressList(AddressBean bean) ;

	int addOrUpdateAddress(AddressBean bean);

	String deleteAddressByIdAndCompany(int id, int companyId);

	AddressBean findAddressById(int id) ;

}
