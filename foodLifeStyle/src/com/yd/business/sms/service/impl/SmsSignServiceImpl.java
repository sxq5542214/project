package com.yd.business.sms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.sms.bean.SmsSignBean;
import com.yd.business.sms.dao.ISmsSignDao;
import com.yd.business.sms.service.ISmsSignService;
@Service("smsSignService")
public class SmsSignServiceImpl extends BaseService implements ISmsSignService {

	@Resource
	private ISmsSignDao smsSignDao;
	@Override
	public void insertSmsSign(SmsSignBean bean) {
		// TODO Auto-generated method stub
		smsSignDao.insertSmsSign(bean);
	}

	@Override
	public void deleteSmsSign(SmsSignBean bean) {
		// TODO Auto-generated method stub
		smsSignDao.deleteSmsSign(bean);
	}

	@Override
	public List<SmsSignBean> listSmsSign(SmsSignBean bean) {
		// TODO Auto-generated method stub
		return smsSignDao.listSmsSign(bean);
	}

	@Override
	public SmsSignBean findByid(int id) {
		// TODO Auto-generated method stub
		return smsSignDao.findByid(id);
	}

}
