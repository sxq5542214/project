/**
 * 
 */
package com.yd.business.isp.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("yunzhangtongInterface")
public class YunZhangTongInterfaceBean extends ISPInterfaceBean {

	public static final String RESULT_CODE_SUCCESS = "00000";

	public static final int STATUS_CALLBACK_SUCCESS = 1;
	
	
	private String appKey;
	private String appSecret;
	private String random;
	private String sign;
	private String[] phones;
	private String phone;
	private int mobilePackageSize;
	private int unicomPackageSize;
	private int telecomPackageSize;
	private String cpBatchId;
	private String requestNo;
	private String serialNo;
	
	private String create_time;
	private String modify_time;
	
	private String code;
	private String msg;
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String[] getPhones() {
		return phones;
	}
	public void setPhones(String[] phones) {
		this.phones = phones;
	}
	public void setPhone(String phone){
		this.phone = phone;
		phones = new String[]{phone};
	}
	public int getMobilePackageSize() {
		return mobilePackageSize;
	}
	public void setMobilePackageSize(int mobilePackageSize) {
		this.mobilePackageSize = mobilePackageSize;
	}
	public int getUnicomPackageSize() {
		return unicomPackageSize;
	}
	public void setUnicomPackageSize(int unicomPackageSize) {
		this.unicomPackageSize = unicomPackageSize;
	}
	public int getTelecomPackageSize() {
		return telecomPackageSize;
	}
	public void setTelecomPackageSize(int telecomPackageSize) {
		this.telecomPackageSize = telecomPackageSize;
	}
	public String getCpBatchId() {
		return cpBatchId;
	}
	public void setCpBatchId(String cpBatchId) {
		this.cpBatchId = cpBatchId;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getPhone() {
		return phone;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
