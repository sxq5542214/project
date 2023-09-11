/**
 * 
 */
package com.yd.business.msg.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.msg.bean.SmsTxSendInfoLogBean;
import com.yd.business.msg.bean.SmsTxSendPhoneLogBean;
import com.yd.business.msg.dao.ISMSDao;

/**
 * @author ice
 *
 */
@Repository("smsDao")
public class SMSDaoImpl extends BaseDao implements ISMSDao {
	public static final String NAMESPACE = "sms." ;
	
	@Override
	public void saveTxSendInfoLog(SmsTxSendInfoLogBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "saveTxSendInfoLog", bean);
	}
	
	@Override
	public List<SmsTxSendInfoLogBean> queryTxSendInfoLogList(SmsTxSendInfoLogBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryTxSendInfoLogList", bean);
	}
	
	@Override
	public void saveTxSendPhoneLog(SmsTxSendPhoneLogBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "saveTxSendPhoneLog", bean);
	}
	
	@Override
	public List<SmsTxSendPhoneLogBean> queryTxSendPhoneLogList(SmsTxSendPhoneLogBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryTxSendPhoneLogList", bean);
	}
	
	
	
	
}
