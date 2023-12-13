package com.yd.business.area.service;

import java.util.List;
import java.util.Set;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.AreaBean;
import com.yd.iotbusiness.mapper.model.LlAddressModel;

public interface IAreaService {

	int addOrUpdateArea(AreaBean bean);

	List<AddressBean> queryAddressList(AddressBean bean) ;

	IOTWebDataBean deleteAddressByIdAndCompany(int id, int companyId);

	LlAddressModel findAddressById(int id) ;

	IOTWebDataBean queryAreaList(LlAddressModel model);

	IOTWebDataBean addOrUpdateAddress(LlAddressModel bean) throws Exception;

	LlAddressModel findAddressByFullname(String fullname);

	Set<Integer> querySubAddressIdsByArray(List<LlAddressModel> addressids);

}
