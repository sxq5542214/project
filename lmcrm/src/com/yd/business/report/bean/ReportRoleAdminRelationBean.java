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
@Alias("reportRoleAdminRelation")
public class ReportRoleAdminRelationBean extends BaseBean {
	private Integer id;
	private Integer admin_id;
	private Integer report_role_id;
	private String create_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public Integer getReport_role_id() {
		return report_role_id;
	}
	public void setReport_role_id(Integer report_role_id) {
		this.report_role_id = report_role_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}
