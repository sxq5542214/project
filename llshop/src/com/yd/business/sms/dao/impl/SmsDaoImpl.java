package com.yd.business.sms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.sms.bean.SmsBean;
import com.yd.business.sms.bean.SmsWaitSendBean;
import com.yd.business.sms.dao.ISmsDao;
@Repository("smsDao")
public class SmsDaoImpl extends BaseDao implements ISmsDao {
	private static final String NAMESPACE = "sms.";
	@Override
	public void insertSms(SmsBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSms", bean);
	}

	@Override
	public void deleteSms(SmsBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSms", bean);
	}

	@Override
	public List<SmsBean> listSms(SmsBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySms", bean);
	}

	@Override
	public SmsBean findById(int id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySmsById", id);
	}

	@Override
	public SmsBean findByTempId(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySmsByTempId", id);
	}
	
	@Override
	public List<SmsWaitSendBean> queryWaitSendList(SmsWaitSendBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryWaitSendList", bean);
	}
	
	@Override
	public void updateWaitSend(SmsWaitSendBean bean) {
		sqlSessionTemplate.update(NAMESPACE + "updateWaitSend", bean);
	}
	

}
