package com.yd.business.operator.service;

import java.util.List;

import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;

public interface IOperatorService {

	OperatorBean findOperatorById(Long id);

	OperatorBean findOperatorByNameAndPass(String username, String password);

	int addOrUpdateOperator(OperatorBean bean);

	List<OperatorExtBean> queryOperatorList(OperatorBean bean);

	int updateOperatorRole(Long opid, String[] roleids, OperatorBean loginOperator);

}
