/**
 * 
 */
package com.yd.business.wechat.dao;

import java.util.List;

import com.yd.business.wechat.bean.WechatSendRedPackLogBean;

/**
 * @author ice
 *
 */
public interface IWechatPayDao {

	void createWechatPayBonusLog(WechatSendRedPackLogBean bean);

	WechatSendRedPackLogBean getLastTimeSendPackLog(String openid);
	
	List<WechatSendRedPackLogBean> getAllSendPackLog(String openid);

}
