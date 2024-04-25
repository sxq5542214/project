/**
 * 
 */
package com.yd.business.msg.bean;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LlSmsSendlogModel;

/**
 * 
 */
@Alias("smsSendLogBean")
public class SMSSendLogBean extends LlSmsSendlogModel {
	public static final String STATUS_SUCCESS ="发送成功";
	public static final String STATUS_FAIL ="发送失败";
	public static final String STATUS_WAIT ="等待发送";

	public static final String SENDTYPE_BALANCEALARM = "余额告警";
	public static final String SENDTYPE_STOPVALVE = "关阀告警";
	public static final String SENDTYPE_MANUALSENDING = "手动群发短信"	;
	public static final String SENDTYPE_CHARGENOTIFY = "缴费通知";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4597672068244402554L;
	
	
	
	
}
