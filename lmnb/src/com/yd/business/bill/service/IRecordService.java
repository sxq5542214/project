package com.yd.business.bill.service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.bill.bean.RecordModelExtendsBean;
import com.yd.iotbusiness.mapper.model.LmRecordModel;

public interface IRecordService {

	LmRecordModel saveRecord(LmRecordModel model);

	LmRecordModel findLastRecord(String meterCode);

	IOTWebDataBean queryRecordList(RecordModelExtendsBean model);

	void updateRecordMeterCode(String oldMeterCode, String newMeterCode);

}
