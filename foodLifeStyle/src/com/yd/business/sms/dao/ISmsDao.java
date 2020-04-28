package com.yd.business.sms.dao;

import java.util.List;

import com.yd.business.sms.bean.SmsBean;
import com.yd.business.sms.bean.SmsWaitSendBean;

public interface ISmsDao {
	public void insertSms(SmsBean bean);
	public void deleteSms(SmsBean bean);
	public List<SmsBean> listSms(SmsBean bean);
	public SmsBean findById(int id);
	public SmsBean findByTempId(String id);
	List<SmsWaitSendBean> queryWaitSendList(SmsWaitSendBean bean);
	void updateWaitSend(SmsWaitSendBean bean);
}
