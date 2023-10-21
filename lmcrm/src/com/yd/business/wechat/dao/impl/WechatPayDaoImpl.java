/**
 * 
 */
package com.yd.business.wechat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.wechat.bean.WechatSendRedPackLogBean;
import com.yd.business.wechat.dao.IWechatPayDao;

/**
 * @author ice
 *
 */
@Repository("wechatPayDao")
public class WechatPayDaoImpl extends BaseDao implements IWechatPayDao {
	public static final String NAMESPACE = "wechatPay.";
	
	@Override
	public void createWechatPayBonusLog(WechatSendRedPackLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createWechatPayBonusLog", bean);
	}
	
	@Override
	public WechatSendRedPackLogBean getLastTimeSendPackLog(String openid){
		return sqlSessionTemplate.selectOne(NAMESPACE+"getLastTimeSendPackLog", openid);
	}

	@Override
	public List<WechatSendRedPackLogBean> getAllSendPackLog(String openid) {
		return sqlSessionTemplate.selectList(NAMESPACE+"getAllSendPackLog", openid);
	}
	
}
