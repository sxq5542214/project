package com.yd.business.log.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.log.bean.LogBean;
import com.yd.business.log.dao.ILogDao;
import com.yd.business.user.bean.UserOperationLogBean;
/**
 * 操作日志
 * @author Anlins
 *
 */
@Repository("logDao")
public class LogDaoImpl extends BaseDao implements ILogDao {
	 
	private final static String NAMESPACE = "systemLog.";

	@Override
	public List<LogBean> listLog(LogBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryLogList", bean);
	}

	@Override
	public void insertLog(LogBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertLog", bean);
	}
	
	@Override
	public void createUserOperationLog(UserOperationLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createUserOperationLog", bean);
	}
	
	@Override
	public int queryUserOperationLogCount(UserOperationLogBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE +"queryUserOperationLogCount", bean);
	}
	
	
	
}
