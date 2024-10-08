package com.yd.business.log.dao;

import java.util.List;

import com.yd.business.log.bean.LogTemplateBean;
import com.yd.business.log.bean.OperatorLogBean;

public interface IOperatorLogDao {

	List<OperatorLogBean> listOperatorLog(OperatorLogBean bean);

	void insertOperatorLog(OperatorLogBean bean);

	LogTemplateBean findLogTemplateByMethod(String method, String hierarchy);

}
