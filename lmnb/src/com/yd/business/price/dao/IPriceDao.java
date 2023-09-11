package com.yd.business.price.dao;

import java.util.List;

import com.yd.business.price.bean.PriceBean;

public interface IPriceDao {

	List<PriceBean> queryPriceList(PriceBean bean);

	int insertPrice(PriceBean bean);

	int updatePrice(PriceBean bean);

}
