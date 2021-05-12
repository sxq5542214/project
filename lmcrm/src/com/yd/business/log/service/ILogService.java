package com.yd.business.log.service;

public interface ILogService {
//	public List<LogBean> listLog(LogBean bean);
//	public void insertLog(LogBean bean);
	public void createAdminLog(Integer type,Integer action,String content,String function);

	void createUserOperationLog(String openid, String action_type, String action_value, Object action_param);

	int queryUserOperationLogCount(String openid, String action_type);
}
