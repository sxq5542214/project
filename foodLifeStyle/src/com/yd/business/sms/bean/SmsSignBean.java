package com.yd.business.sms.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("smsSign")
public class SmsSignBean extends BaseBean {
	
	public static final int STATUS_Y = 1;//可用
	public static final int STATUS_N = 0;//不可用

	private Integer id;//存储序列ID
	private String order_code;//申请工单号【阿里大鱼工单号】
	private String sign_name;//签名名称
	private String apply_time;//申请时间
	private String apply_commen;//申请说明
	private String sign_type;//签名类型
	private Integer status;//状态
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getSign_name() {
		return sign_name;
	}
	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getApply_commen() {
		return apply_commen;
	}
	public void setApply_commen(String apply_commen) {
		this.apply_commen = apply_commen;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}