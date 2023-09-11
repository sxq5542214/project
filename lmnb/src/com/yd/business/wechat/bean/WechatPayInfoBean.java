/**
 * 
 */
package com.yd.business.wechat.bean;

/**
 * @author ice
 *
 */
public class WechatPayInfoBean {
	
	public static final int LUCKY_MONEY_MIN_WECHAT = 100;//微信红包最小金额
	public static final int LUCKY_MONEY_MIN_DEFAULT_MIN = 0;//最小金额
	
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String packages;
	private String signType;
	private String paySign;
	
	//非微信接口字段
	private String outTradeNo;
	private String transactionId;
	
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
}
