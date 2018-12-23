package com.yd.business.sms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.sms.bean.SmsCustomerBean;
import com.yd.business.sms.dao.ISmsCustomerDao;
@Repository("smsCustomerDao")
public class SmsCustomerDaoImpl extends BaseDao implements ISmsCustomerDao {

	private static final String NAMESPACE = "smsCustomer.";
	@Override
	public List<SmsCustomerBean> listSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySmsCustomer", bean);
	}

	@Override
	public void insertSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSmsCustomer", bean);
	}

	@Override
	public void deleteSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSmsCustomer", bean);
	}

	@Override
	public void updateSmsCust(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateSmsCustomer", bean);
	}

	@Override
	public SmsCustomerBean querySmsCustomerBean(SmsCustomerBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySmsCustomerByCustId", bean);
	}

	@Override
	public SmsCustomerBean querySmsCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySmsCustomerById", id);
	}

}
