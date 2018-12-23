package com.yd.business.wechat.service;

import java.util.List;

import com.yd.business.wechat.bean.WechatSendRedPackLogBean;

public interface IWechatPayService {

	WechatSendRedPackLogBean getLastTimeSendPackLog(String openid);

	Boolean payBonusLimit200(String openId, int payMoney, String ipAddr);
	
	List<WechatSendRedPackLogBean> getAllSendPackLog(String openid);

}
