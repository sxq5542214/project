package com.yd.business.bill.dao;

import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;

public interface IBillExtendsMapper extends LmBillModelMapper{
//	List<MeterModelExtendsBean> queryMeterAndUserList(MeterModelExtendsBean bean);
	int createNoPayUserBills(String billMonth);
	int updateBillMinConsumamount(String billMonth);
	int updateBillCyclebuyamount(String billMonth);
}
