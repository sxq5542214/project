package com.yd.business.sms.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.sms.bean.SmsLogBean;
import com.yd.business.sms.dao.ISmsLogDao;
@Repository("smsLogDao")
public class SmsLogDaoImpl extends BaseDao implements ISmsLogDao {
	private static final String NAMESPACE = "smslog.";
	@Override
	public void insertSmsLog(SmsLogBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSmsLog", bean);
	}

}
