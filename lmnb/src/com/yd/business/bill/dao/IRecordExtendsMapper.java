package com.yd.business.bill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yd.business.bill.bean.RecordModelExtendsBean;
import com.yd.iotbusiness.mapper.dao.LmRecordModelMapper;
import com.yd.iotbusiness.mapper.model.LmRecordModel;

public interface IRecordExtendsMapper extends LmRecordModelMapper{
	List<RecordModelExtendsBean> selectRecordList(LmRecordModel bean);
	int countSelectRecordList(LmRecordModel bean);
	void updateRecordMeterCode(@Param("oldMeterCode") String oldMeterCode,@Param("newMeterCode")  String newMeterCode);

}
