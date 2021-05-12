package com.yd.business.operator.service;

import com.yd.business.operator.bean.OperatorBean;

public interface IOperatorService {

	OperatorBean findOperatorById(Long id);

	OperatorBean findOperatorByNameAndPass(String username, String password);

}
