package com.yd.business.device.bean;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LmMeterModel;

/**
 * DeviceKindBean entity. @author MyEclipse Persistence Tools
 */
@Alias("meterModelExtendsBean")
public class MeterModelExtendsBean extends LmMeterModel implements java.io.Serializable  {

	private static final long serialVersionUID = -8923671707564903643L;
	public static final byte CHANGED_TRUE = 1;
	public static final byte CHANGED_FALSE = 0;
	//'账户状态 -1已删除 0未开户 1已开户 2已报停(不计最低消费)',
	public static final byte OPEND_NO = 0;
	public static final byte OPEND_YES = 1;
	public static final byte OPEND_CLOSE = 2;
	public static final byte OPEND_DELETED = -1;
	//'nb是否经过校验 0未经过校验 1已校验',
	public static final byte STATIONCHECKED_TRUE = 1;
	public static final byte STATIONCHECKED_FALSE = 0;
	//'阀门状态    -1   未知    0开    1关    11异常    2无阀',
	public static final byte VALVESTATE_UNKNOW = -1;
	public static final byte VALVESTATE_OPEND = 0;
	public static final byte VALVESTATE_CLOSED = 1;
	public static final byte VALVESTATE_ERROR = 11;
	public static final byte VALVESTATE_NOHAVE = 2;
	
	
	private String userCode;
	private String userName;
	private String area1;
	private String area2;
	private String area3;
	private String area4;
	private Integer addressid;
	private String phone;
	private String idcard;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
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