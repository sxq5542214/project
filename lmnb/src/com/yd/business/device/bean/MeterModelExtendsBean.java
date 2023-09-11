package com.yd.business.device.bean;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LmMeterModel;

/**
 * DeviceKindBean entity. @author MyEclipse Persistence Tools
 */
@Alias("meterModelExtendsBean")
public class MeterModelExtendsBean extends LmMeterModel implements java.io.Serializable  {

	private static final long serialVersionUID = -8923671707564903643L;

	
	private String userName;
	private String area1;
	private String area2;
	private String area3;
	private String area4;
	private Integer addressid;
	private String phone;
	private String idcard;
	
	public Integer getAddressid() {
		return addressid;
	}
	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getArea1() {
		return area1;
	}
	public void setArea1(String area1) {
		this.area1 = area1;
	}
	public String getArea2() {
		return area2;
	}
	public void setArea2(String area2) {
		this.area2 = area2;
	}
	public String getArea3() {
		return area3;
	}
	public void setArea3(String area3) {
		this.area3 = area3;
	}
	public String getArea4() {
		return area4;
	}
	public void setArea4(String area4) {
		this.area4 = area4;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	
	
}