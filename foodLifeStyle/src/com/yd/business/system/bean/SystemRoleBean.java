/**
 * 
 */
package com.yd.business.system.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("systemRole")
public class SystemRoleBean extends SystemBean {
	private String status_value;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 权限关联用户集合
	 */
	private List<SystemRoleAdminRelationBean> roleAdminList;
	/**
	 * 权限关联菜单集合
	 */
	private List<SystemRoleMenuRelationBean> roleMenuList;
	public List<SystemRoleAdminRelationBean> getRoleAdminList() {
		return roleAdminList;
	}
	public void setRoleAdminList(List<SystemRoleAdminRelationBean> roleAdminList) {
		this.roleAdminList = roleAdminList;
	}
	public List<SystemRoleMenuRelationBean> getRoleMenuList() {
		return roleMenuList;
	}
	public void setRoleMenuList(List<SystemRoleMenuRelationBean> roleMenuList) {
		this.roleMenuList = roleMenuList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus_value() {
		return status_value;
	}
	public void setStatus_value(String status_value) {
		this.status_value = status_value;
	}
	
}
