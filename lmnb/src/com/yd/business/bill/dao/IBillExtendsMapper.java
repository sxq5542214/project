package com.yd.business.bill.dao;

import java.util.List;

import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;

public interface IBillExtendsMapper extends LmBillModelMapper{
	List<MeterModelExtendsBean> queryMeterAndUserList(MeterModelExtendsBean bean);
	int countMeterAndUserList(MeterModelExtendsBean bean);
	
}
