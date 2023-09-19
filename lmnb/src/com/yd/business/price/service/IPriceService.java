package com.yd.business.price.service;

import java.util.List;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.iotbusiness.mapper.model.LmPriceModel;
import com.yd.iotbusiness.mapper.model.LmPricedetailModel;

public interface IPriceService {

	int disablePrice(long priceId);

	int enablePrice(long priceId);

	List<PriceBean> queryALLPriceList(PriceBean bean);
	
	int addOrUpdatePrice(PriceBean bean);

	IOTWebDataBean queryPriceListByCompany(Integer systemid);

	LmPricedetailModel findPriceDetailById(int id);

	LmPricedetailModel findPriceDetailByPriceId(int id);

	LmPriceModel findPriceById(int id);

}
