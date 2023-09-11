package com.yd.business.bill.service;

import com.yd.iotbusiness.mapper.model.LmRecordModel;

public interface IRecordService {

	LmRecordModel saveRecord(LmRecordModel model);

	LmRecordModel findLastRecord(String meterCode);

}
