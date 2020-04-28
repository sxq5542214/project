/**
 * 
 */
package com.yd.business.isp.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("dahansantongInterface")
public class DaHanSanTongInterfaceBean extends ISPInterfaceBean {

	public static final String RESULT_CODE_SUCCESS ="00";

	public static final int STATUS_CALLBACK_SUCCESS = 0;
	public static final String RESULTCODE_CALLBACK_SUCCESS = "0000";
	public static final String RESULTCODE_CALLBACK_FAILD = "1111";
	
	
	private String timestamp;
	private String account;
	private String mobiles;
	private String sign;
	private String packageSize;
	private String msgTemplateId;
	private String clientOrderId;
	
	private String reportTime;
	private String callBackTime;
	private String intervalTime;
	private String clientSubmitTime;
	private String discount;
	private String costMoney;
	
	private String resultCode;
	private String resultMsg;
	
	private String create_time;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public String getMsgTemplateId() {
		return msgTemplateId;
	}
	public void setMsgTemplateId(String msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}
	public String getClientOrderId() {
		return clientOrderId;
	}
	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getCallBackTime() {
		return callBackTime;
	}
	public void setCallBackTime(String callBackTime) {
		this.callBackTime = callBackTime;
	}
	public String getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getClientSubmitTime() {
		return clientSubmitTime;
	}
	public void setClientSubmitTime(String clientSubmitTime) {
		this.clientSubmitTime = clientSubmitTime;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(String costMoney) {
		this.costMoney = costMoney;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
