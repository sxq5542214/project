package com.yd.business.log.dao;

import java.util.List;

import com.yd.business.log.bean.LogBean;

public interface ILogDao {

	public List<LogBean> listLog(LogBean bean);
	public void insertLog(LogBean bean);
	
}
