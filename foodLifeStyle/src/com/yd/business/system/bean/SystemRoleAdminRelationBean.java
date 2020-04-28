/**
 * 
 */
package com.yd.business.system.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("systemRoleAdminRelation")
public class SystemRoleAdminRelationBean extends SystemBean {
	
	public static final String ACTION_STATUS_VALUE_LOSEEFF = "loseEff";
	public static final String ACTION_STATUS_VALUE_EFF = "eff";
	
	public static final int RELATION_STATUS_EFF = 1;
	public static final int RELATION_STATUS_LOSSEFF = 0;
	
	private Integer role_id;
	private String role_name;
	private Integer customer_id;
	private String customer_name;
	private Integer admin_id;
	private String admin_name;
	private String status_value;
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
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Integer getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getStatus_value() {
		return status_value;
	}
	public void setStatus_value(String status_value) {
		this.status_value = status_value;
	}
	
}
