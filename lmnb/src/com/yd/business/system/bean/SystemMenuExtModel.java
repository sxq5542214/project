/**
 * 
 */
package com.yd.business.system.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LlSystemMenuModel;

/**
 * 
 */
@Alias("systemMenuExtModel")
public class SystemMenuExtModel extends LlSystemMenuModel {

	public static final int STATUS_ENABLE = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = -897098686433760767L;
	
	private Integer role_id;
	private String role_name;
	private Integer opid;
	private Map<String,Object> meta;
	private Set<String> roles;
	private List<SystemMenuExtModel> children;

	public List<SystemMenuExtModel> getChildren() {
		return children;
	}

	public void setChildren(List<SystemMenuExtModel> children) {
		this.children = children;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public Integer getOpid() {
		return opid;
	}

	public void setOpid(Integer opid) {
		this.opid = opid;
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

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}
