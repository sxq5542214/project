package com.yd.business.log.service;

import java.util.List;

import com.yd.business.log.bean.LogBean;
import com.yd.business.user.bean.UserOperationLogBean;

public interface ILogService {
//	public List<LogBean> listLog(LogBean bean);
//	public void insertLog(LogBean bean);
	public void createAdminLog(Integer type,Integer action,String content,String function);

	void createUserOperationLog(String openid, String action_type, String action_value, Object action_param);

	int queryUserOperationLogCount(String openid, String action_type);
}
