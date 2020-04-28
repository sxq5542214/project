package com.yd.business.sms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.sms.bean.SmsCustomerBean;
import com.yd.business.sms.dao.ISmsCustomerDao;
import com.yd.business.sms.service.ISmsCustomerService;
@Service("smsCustomerService")
public class SmsCustomerServiceImpl extends BaseService implements
		ISmsCustomerService {
	@Resource
	private ISmsCustomerDao smsCustomerDao;
	@Override
	public List<SmsCustomerBean> listSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		return smsCustomerDao.listSmsCust(bean);
	}

	@Override
	public void insertSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		smsCustomerDao.insertSmsCust(bean);
	}

	@Override
	public void deleteSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		smsCustomerDao.deleteSmsCust(bean);
	}

	@Override
	public void updateSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		smsCustomerDao.updateSmsCust(bean);
	}

	@Override
	public SmsCustomerBean querySmsCustomerBean(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		return smsCustomerDao.querySmsCustomerBean(bean);
	}

	@Override
	public SmsCustomerBean querySmsCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return smsCustomerDao.querySmsCustomerById(id);
	}

}
