package com.yd.business.sms.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("sms")
public class SmsBean extends BaseBean {
	
	public static final String serverUrl = "http://gw.api.taobao.com/router/rest";
	public static final String appKey = "23403328";
	public static final String appSecret = "c54fece25265d02d684bab58c5462632";

	public static final int STATUS_Y = 1;//可用
	public static final int STATUS_N = 0;//不可用
	private Integer id;//存储序列ID
	private String order_code;//阿里大鱼申请工单编码
	private String template_name;//模板名称
	private String template_id;//模板ID
	private String add_time;//申请时间
	private Integer status;//状态
	private String content;//模板内容
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
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
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
