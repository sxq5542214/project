package com.yd.business.wechat.service;

import java.util.List;

import com.yd.business.wechat.bean.WechatSendRedPackLogBean;

public interface IWechatPayService {

	WechatSendRedPackLogBean getLastTimeSendPackLog(String openid);

	List<WechatSendRedPackLogBean> getAllSendPackLog(String openid);

	Boolean payBonusLimit200(String openId, int payMoney, String ipAddr, String remark, int bonusNum);

}
