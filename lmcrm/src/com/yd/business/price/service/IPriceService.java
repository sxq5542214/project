package com.yd.business.price.service;

import java.util.List;

import com.yd.business.price.bean.PriceBean;

public interface IPriceService {

	int disablePrice(long priceId);

	int enablePrice(long priceId);

	List<PriceBean> queryALLPriceList(PriceBean bean);
	
	List<PriceBean> queryPriceListByCompany(Long companyid);
	
	
	int addOrUpdatePrice(PriceBean bean);

	PriceBean findPriceById(long id);

}
