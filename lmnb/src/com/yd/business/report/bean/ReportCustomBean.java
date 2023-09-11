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
@Alias("reportCustom")
public class ReportCustomBean extends BaseBean {
	
	private Integer id;
	private String name;
	private String code;
	private String data_sql;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getData_sql() {
		return data_sql;
	}
	public void setData_sql(String data_sql) {
		this.data_sql = data_sql;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
