package com.yd.business.bill.dao;

import java.util.List;

import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.iotbusiness.mapper.dao.LmRecordModelMapper;
import com.yd.iotbusiness.mapper.model.LmRecordModel;

public interface IRecordExtendsMapper extends LmRecordModelMapper{
	List<LmRecordModel> selectRecordList(LmRecordModel bean);
	int countSelectRecordList(LmRecordModel bean);
}
