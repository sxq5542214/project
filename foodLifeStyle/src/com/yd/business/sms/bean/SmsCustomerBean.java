package com.yd.business.sms.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("smsCustomer")
public class SmsCustomerBean extends BaseBean {
	
	public static final int ORDERSTATUS_SUCCESS = 1;
	public static final int ORDERSTATUS_FAILD = -1;
	public static final int ORDERSTATUS_CANCEL = 2;
	public static final int ORDERSTATUS_PAYSUCCESS = 3;
	
	public static final int EVENTTYPE_ORDER = 1;
	public static final int EVENTTYPE_ACTIVITY = 2;
	
	

	private Integer id;
	private Integer customerid;
	private String smsType;
	private String smsFreeSignName;
	private String smsTemplateCode;
	private String customername;
	private Integer orderStatus;
	private Integer eventType;
	
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerid() {
		return customerid;
	}
	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	public String getSmsFreeSignName() {
		return smsFreeSignName;
	}
	public void setSmsFreeSignName(String smsFreeSignName) {
		this.smsFreeSignName = smsFreeSignName;
	}
	public String getSmsTemplateCode() {
		return smsTemplateCode;
	}
	public void setSmsTemplateCode(String smsTemplateCode) {
		this.smsTemplateCode = smsTemplateCode;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	
}