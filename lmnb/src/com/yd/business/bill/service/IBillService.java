package com.yd.business.bill.service;

import java.math.BigDecimal;
import java.util.List;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.device.bean.ChangeMeterExtBean;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmPaymentModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;

public interface IBillService {

	IOTWebDataBean queryBillList(LmBillModel model);

	LmBillModel generatorBillByRecord(String meterCode, LmRecordModel record);

	LmBillModel queryBillByMonth(int meterId, int billMonth);

	LmBillModel generatorBillByPayment(String meterCode, LmPaymentModel payment);



}
