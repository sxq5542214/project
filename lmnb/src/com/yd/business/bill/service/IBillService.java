package com.yd.business.bill.service;

import java.math.BigDecimal;
import java.util.List;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.device.bean.ChangeMeterExtBean;
import com.yd.iotbusiness.mapper.model.LmBillModel;

public interface IBillService {

	IOTWebDataBean queryBillList(LmBillModel model);



}
