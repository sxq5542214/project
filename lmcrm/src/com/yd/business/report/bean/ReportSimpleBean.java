/**
 * 
 */
package com.yd.business.report.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("reportSimple")
public class ReportSimpleBean extends BaseBean {

	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	
	private Integer id;
	private String name;
	private String code;
	private String data_sql;
	private Integer status;
	private String column_codes;
	private String column_names;
	private Integer seq;
	private String remark;
	private String access_url;
	private List<ReportParamsBean> paramsList;
	
	
	public String getAccess_url() {
		return access_url;
	}
	public void setAccess_url(String access_url) {
		this.access_url = access_url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getColumn_codes() {
		return column_codes;
	}
	public void setColumn_codes(String column_codes) {
		this.column_codes = column_codes;
	}
	public String getColumn_names() {
		return column_names;
	}
	public void setColumn_names(String column_names) {
		this.column_names = column_names;
	}
	public List<ReportParamsBean> getParamsList() {
		return paramsList;
	}
	public void setParamsList(List<ReportParamsBean> paramsList) {
		this.paramsList = paramsList;
	}
}
