package com.yd.business.log.service;

import javax.servlet.http.HttpServletRequest;

import com.yd.business.operator.bean.OperatorBean;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;

public interface IOperatorLogService {

	void createOperatoryLog(OperatorBean op, HttpServletRequest request);

	void createOperatoryLog(LmOperatorModel op, HttpServletRequest request);

}
