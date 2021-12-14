/**
 * 
 */
package com.yd.business.system.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("systemRoleMenuRelation")
public class SystemRoleMenuRelationBean extends SystemBean {
	
	public static final String ACTION_STATUS_VALUE_LOSEEFF = "loseEff";
	public static final String ACTION_STATUS_VALUE_EFF = "eff";
	
	public static final int RELATION_STATUS_EFF = 1;
	public static final int RELATION_STATUS_LOSSEFF = 0;
	
	
	private Integer role_id;
	private String role_name;
	private Integer menu_id;
	private String menu_name;
	private String status_value;
	private Integer company_id;
	
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Integer getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getStatus_value() {
		return status_value;
	}
	public void setStatus_value(String status_value) {
		this.status_value = status_value;
	}
	
}
