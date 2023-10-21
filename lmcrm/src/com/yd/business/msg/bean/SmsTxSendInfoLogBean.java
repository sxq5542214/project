/**
 * 
 */
package com.yd.business.msg.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("smsTxSendInfoLog")
public class SmsTxSendInfoLogBean extends BaseBean {
	private Integer id;
	private Integer templateId;
	private String templateName;
	private String send_content;
	private String send_operator;
	private String send_time;
	private String request_id;
	private Integer send_count;
	private Integer success_count;
	private Integer error_count;
	private Integer company;
	
	public Integer getCompany() {
		return company;
	}
	public void setCompany(Integer company) {
		this.company = company;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getSend_content() {
		return send_content;
	}
	public void setSend_content(String send_content) {
		this.send_content = send_content;
	}
	public String getSend_operator() {
		return send_operator;
	}
	public void setSend_operator(String send_operator) {
		this.send_operator = send_operator;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public Integer getSend_count() {
		return send_count;
	}
	public void setSend_count(Integer send_count) {
		this.send_count = send_count;
	}
	public Integer getSuccess_count() {
		return success_count;
	}
	public void setSuccess_count(Integer success_count) {
		this.success_count = success_count;
	}
	public Integer getError_count() {
		return error_count;
	}
	public void setError_count(Integer error_count) {
		this.error_count = error_count;
	}
	
}
