package com.yd.business.price.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yd.iotbusiness.mapper.dao.LmPaymentModelMapper;

public interface IPaymentExtendsMapper extends LmPaymentModelMapper{
	BigDecimal sumMonthChargeAmout(Map<String,Object> map);
	int countDayBuyAmountMeter(Map<String, Object> map);
	List<Integer> queryDayBuyAmountSumListOfMonth(Map<String, Object> map);
}
