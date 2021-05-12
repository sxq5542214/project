/**
 * 
 */
package com.yd.business.operator.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.dao.IOperatorDao;
import com.yd.business.operator.service.IOperatorService;

/**
 * @author ice
 *
 */
@Service("operatorService")
public class OperatorServiceImpl extends BaseService implements IOperatorService {
	@Resource
	private IOperatorDao operatorDao;
	@Override
	public OperatorBean findOperatorById(Long id) {
		OperatorBean bean = new OperatorBean();
		bean.setO_id(id);
		List<OperatorBean> list = operatorDao.queryOperatorList(bean);
		return list.size() >0 ? list.get(0): null;
	}
	
	@Override
	public OperatorBean findOperatorByNameAndPass(String username,String password) {
		OperatorBean bean = new OperatorBean();
		bean.setO_name(username);
		bean.setO_password(password);
		List<OperatorBean> list = operatorDao.queryOperatorList(bean);
		return list.size() >0 ? list.get(0): null;
	}
	
	
	
	
}
