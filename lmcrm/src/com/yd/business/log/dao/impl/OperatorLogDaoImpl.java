package com.yd.business.log.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.log.bean.OperatorLogBean;
import com.yd.business.log.dao.IOperatorLogDao;
/**
 * 操作日志
 * @author Anlins
 *
 */
@Repository("OperatorLogDao")
public class OperatorLogDaoImpl extends BaseDao implements IOperatorLogDao {
	 
	private final static String NAMESPACE = "operatorLog.";

	@Override
	public List<OperatorLogBean> listOperatorLog(OperatorLogBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOperatorLogList", bean);
	}

	@Override
	public void insertOperatorLog(OperatorLogBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertOperatorLog", bean);
	}
	
	
	
	
}
