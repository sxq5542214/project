package com.yd.business.log.service;

import javax.servlet.http.HttpServletRequest;

import com.yd.business.operator.bean.OperatorBean;

public interface IOperatorLogService {

	void createOperatoryLog(OperatorBean op, HttpServletRequest request);

}
