package com.yd.business.log.dao;

import java.util.List;

import com.yd.business.log.bean.LogBean;
import com.yd.business.user.bean.UserOperationLogBean;

public interface ILogDao {

	public List<LogBean> listLog(LogBean bean);
	public void insertLog(LogBean bean);
	void createUserOperationLog(UserOperationLogBean bean);
	int queryUserOperationLogCount(UserOperationLogBean bean);
	
}
