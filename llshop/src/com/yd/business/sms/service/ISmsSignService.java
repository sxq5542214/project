package com.yd.business.sms.service;

import java.util.List;

import com.yd.business.sms.bean.SmsSignBean;

public interface ISmsSignService {
	public void insertSmsSign(SmsSignBean bean);
	public void deleteSmsSign(SmsSignBean bean);
	public List<SmsSignBean> listSmsSign(SmsSignBean bean);
	public SmsSignBean findByid(int id);
}
