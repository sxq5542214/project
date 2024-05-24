package com.yd.business.bill.dao;

import java.util.List;

import com.yd.business.bill.bean.BillModelExtendBean;
import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;

public interface IBillExtendsMapper extends LmBillModelMapper{
	List<BillModelExtendBean> queryBillWaterList(BillModelExtendBean bean);
	int createNoPayUserBills(String billMonth);
	int updateBillMinConsumamount(String billMonth);
	int updateBillCyclebuyamount(String billMonth);
}
