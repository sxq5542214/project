/**
 * 
 */
package com.yd.business.report.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("reportParams")
public class ReportParamsBean extends BaseBean {
	private Integer id;
	private Integer report_id;
	private String report_name;
	private String param_name;
	private String param_code;
	private String param_type;
	private String param_sql;
	private String param_url;
	private String param_category;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReport_id() {
		return report_id;
	}
	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}
	public String getReport_name() {
		return report_name;
	}
	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}
	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public String getParam_code() {
		return param_code;
	}
	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}
	public String getParam_type() {
		return param_type;
	}
	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}
	public String getParam_sql() {
		return param_sql;
	}
	public void setParam_sql(String param_sql) {
		this.param_sql = param_sql;
	}
	public String getParam_url() {
		return param_url;
	}
	public void setParam_url(String param_url) {
		this.param_url = param_url;
	}
	public String getParam_category() {
		return param_category;
	}
	public void setParam_category(String param_category) {
		this.param_category = param_category;
	}
}
