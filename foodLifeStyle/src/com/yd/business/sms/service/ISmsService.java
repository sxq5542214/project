package com.yd.business.sms.service;

import java.util.List;
import java.util.Map;

import com.taobao.api.ApiException;
import com.yd.business.sms.bean.SmsBean;
import com.yd.business.sms.bean.SmsCustomerBean;

/**
 * 消息服务类
 * @author Zhang
 *
 */
public interface ISmsService {

	/**
	 * 短信发送接口
	 * @param smsType 消息类型，默认值为：normal
	 * @param smsFreeSignName 消息签名，例如：【万大商城】推送消息~~~
	 * @param recNum 消息发送号码,多个号码以,号隔开
	 * @param smsTemplateCode 消息模板编码
	 * @param params 消息参数"{"code":"ceshi"}"
	 * @return
	 * @throws ApiException
	 */
	public String sendAliMsg(String smsType,String smsFreeSignName,String recNum,String smsTemplateCode,String params) throws ApiException;
	public void insertSms(SmsBean bean);
	public void deleteSms(SmsBean bean);
	public List<SmsBean> listSms(SmsBean bean);
	public SmsBean findById(int id);
	public SmsBean findByTempId(String id);
	String sendSuccessMsgMyPhoneNumAndContent(String recNum, String wechatName, String productName, Integer customerid);
	String sendFaildMsgByActivity(String recNum, String activity_name, String reason, Integer customerid);
	String sendFaildMsgByOrder(String recNum, String productName, String reason, Integer customerid);
	String sendSMSAndSaveLog(String recNum, Map<String, String> map, SmsCustomerBean bean);
	void queryWaitAndSend();
}
