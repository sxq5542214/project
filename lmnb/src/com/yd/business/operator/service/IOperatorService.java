package com.yd.business.operator.service;

import java.util.List;

import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;

public interface IOperatorService {

	OperatorBean findOperatorById(Long id);

	OperatorExtBean findOperatorByNameAndPass(String username, String password);

	int addOrUpdateOperator(OperatorBean bean);

	List<OperatorExtBean> queryOperatorList(OperatorBean bean);

	int updateOperatorRole(Long opid, String[] roleids, OperatorBean loginOperator);

	LmOperatorModel findLmOperatorByNameAndPass(String username, String password);

	LmOperatorModel findOperatorById(Integer id);

}
