/**
 * 
 */
package com.yd.business.sms.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("smsWaitSend")
public class SmsWaitSendBean extends BaseBean {
	
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_SEND = 1;
	
	
	private Integer id;
	private String phone;
	private String param;
	private Integer sms_customer_id;
	private Integer status;
	private String send_time;
	private String create_time;
	private String modify_time;
	
	private String now_time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public Integer getSms_customer_id() {
		return sms_customer_id;
	}
	public void setSms_customer_id(Integer sms_customer_id) {
		this.sms_customer_id = sms_customer_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
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
	public String getNow_time() {
		return now_time;
	}
	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}
}
