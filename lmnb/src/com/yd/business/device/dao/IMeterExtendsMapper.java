package com.yd.business.device.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.iotbusiness.mapper.dao.LmMeterModelMapper;

public interface IMeterExtendsMapper extends LmMeterModelMapper{
	List<MeterModelExtendsBean> queryMeterAndUserList(MeterModelExtendsBean bean);
	int countMeterAndUserList(MeterModelExtendsBean bean);
	int updateNopayBillMeterBalance(String billMonth);
	int updateMeterBalanceByMinConsumamount(String billMonth);
	int countDayMeterReading(Map<String, Object> map);
	List<Map<String, Object>> queryLast2MonthMeterReadingList(Map<String, Object> map);
	List<Map<String, Object>> queryLast2MonthOpenedMeterCountList(Map<String, Object> map);
	List<Map<String, Object>> queryQingSongMeterList(Map<String, Object> map);
	
	
}
