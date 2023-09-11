package com.yd.business.device.dao;

import java.util.List;

import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.iotbusiness.mapper.dao.LmMeterModelMapper;

public interface IMeterExtendsMapper extends LmMeterModelMapper{
	List<MeterModelExtendsBean> queryMeterAndUserList(MeterModelExtendsBean bean);
	int countMeterAndUserList(MeterModelExtendsBean bean);
}
