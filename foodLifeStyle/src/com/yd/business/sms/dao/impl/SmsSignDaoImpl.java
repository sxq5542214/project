package com.yd.business.sms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.sms.bean.SmsSignBean;
import com.yd.business.sms.dao.ISmsSignDao;
@Repository("smsSignDao")
public class SmsSignDaoImpl extends BaseDao implements ISmsSignDao {
	private static final String NAMESPACE = "smsSign.";
	@Override
	public void insertSmsSign(SmsSignBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSmsSign", bean);
	}

	@Override
	public void deleteSmsSign(SmsSignBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSmsSign", bean);
	}

	@Override
	public List<SmsSignBean> listSmsSign(SmsSignBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySmsSign", bean);
	}

	@Override
	public SmsSignBean findByid(int id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySmsSignById", id);
	}

}
