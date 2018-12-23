package com.yd.business.sms.dao;

import java.util.List;

import com.yd.business.sms.bean.SmsCustomerBean;

public interface ISmsCustomerDao {
	
	public List<SmsCustomerBean> listSmsCust(SmsCustomerBean bean);
	public void insertSmsCust(SmsCustomerBean bean);
	public void deleteSmsCust(SmsCustomerBean bean);
	public void updateSmsCust(SmsCustomerBean bean);
	public SmsCustomerBean querySmsCustomerBean(SmsCustomerBean bean);
	public SmsCustomerBean querySmsCustomerById(Integer id);

}
