/**
 * 
 */
package com.yd.business.user.bean;

import java.math.BigDecimal;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 霸王餐用户信息
 * @author ice
 */
public class UserBWCBean extends BaseBean {
	public static final String USERTYPE_NORMAL = "0";
	public static final String USERTYPE_ADMIN = "1";
	public static final String USERTYPE_SCHOOLPROMOTION = "3";
	public static final String USERTYPE_VIRTUAL = "4";
	public static final String USERTYPE_MANAGER = "5";
	
	
	
	private Integer userId;
	private String userName;
	private String userPwd;
	private String changePwdNo;
	private String changePwdTime;
	private String realName;
	private String cardNo;
	private String mobilePhone;
	private String mobileCheck;
	private String phone;
	private String qq;
	private String mail;
	private String mailCheck;
	private String sex;
	private String birthday;
	private String faceImg;
	private String location;
	private String postNo;
	private String ip_address;
	private String ip_location;
	private String oldIpAddress;
	private String marital_status;
	private String Monthly_income;
	private String Interests;
	private String Attribute_22;
	private String oldDate;
	private String newDate;
	private BigDecimal balance;
	private String userType;
	private Integer experience;
	private String signature;
	private Integer invite;
	private Double commissionCount;
	private Double commissionBalance;
	private Double commissionMention;
	private Integer commissionPoints;
	private String openId;
	private Integer accessNumber;
	private String guanggaoImg;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getChangePwdNo() {
		return changePwdNo;
	}
	public void setChangePwdNo(String changePwdNo) {
		this.changePwdNo = changePwdNo;
	}
	public String getChangePwdTime() {
		return changePwdTime;
	}
	public void setChangePwdTime(String changePwdTime) {
		this.changePwdTime = changePwdTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getMobileCheck() {
		return mobileCheck;
	}
	public void setMobileCheck(String mobileCheck) {
		this.mobileCheck = mobileCheck;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMailCheck() {
		return mailCheck;
	}
	public void setMailCheck(String mailCheck) {
		this.mailCheck = mailCheck;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getFaceImg() {
		return faceImg;
	}
	public void setFaceImg(String faceImg) {
		this.faceImg = faceImg;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPostNo() {
		return postNo;
	}
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getIp_location() {
		return ip_location;
	}
	public void setIp_location(String ip_location) {
		this.ip_location = ip_location;
	}
	public String getOldIpAddress() {
		return oldIpAddress;
	}
	public void setOldIpAddress(String oldIpAddress) {
		this.oldIpAddress = oldIpAddress;
	}
	public String getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}
	public String getMonthly_income() {
		return Monthly_income;
	}
	public void setMonthly_income(String monthly_income) {
		Monthly_income = monthly_income;
	}
	public String getInterests() {
		return Interests;
	}
	public void setInterests(String interests) {
		Interests = interests;
	}
	public String getAttribute_22() {
		return Attribute_22;
	}
	public void setAttribute_22(String attribute_22) {
		Attribute_22 = attribute_22;
	}
	public String getOldDate() {
		return oldDate;
	}
	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Integer getInvite() {
		return invite;
	}
	public void setInvite(Integer invite) {
		this.invite = invite;
	}
	public Double getCommissionCount() {
		return commissionCount;
	}
	public void setCommissionCount(Double commissionCount) {
		this.commissionCount = commissionCount;
	}
	public Double getCommissionBalance() {
		return commissionBalance;
	}
	public void setCommissionBalance(Double commissionBalance) {
		this.commissionBalance = commissionBalance;
	}
	public Double getCommissionMention() {
		return commissionMention;
	}
	public void setCommissionMention(Double commissionMention) {
		this.commissionMention = commissionMention;
	}
	public Integer getCommissionPoints() {
		return commissionPoints;
	}
	public void setCommissionPoints(Integer commissionPoints) {
		this.commissionPoints = commissionPoints;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getAccessNumber() {
		return accessNumber;
	}
	public void setAccessNumber(Integer accessNumber) {
		this.accessNumber = accessNumber;
	}
	public String getGuanggaoImg() {
		return guanggaoImg;
	}
	public void setGuanggaoImg(String guanggaoImg) {
		this.guanggaoImg = guanggaoImg;
	}
	
}
