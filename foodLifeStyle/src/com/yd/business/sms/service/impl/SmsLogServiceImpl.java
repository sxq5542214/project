package com.yd.business.sms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.sms.bean.SmsLogBean;
import com.yd.business.sms.dao.ISmsLogDao;
import com.yd.business.sms.service.ISmsLogService;
@Service("smsLogService")
public class SmsLogServiceImpl extends BaseService implements ISmsLogService {

	@Resource
	private ISmsLogDao smsLogDao;
	@Override
	public void insertSmsLog(SmsLogBean bean) {
		// TODO Auto-generated method stub
		smsLogDao.insertSmsLog(bean);
	}

}
