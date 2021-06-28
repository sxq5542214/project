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
@Alias("systemMenu")
public class SystemMenuBean extends SystemBean {
	private Integer parentid;
	private String description;
	private String icon_path;
	private String path;
	private Integer type;
	private String rank_hex;
	private String status_value;
	private List<SystemMenuBean> children_menu;
	/**
	 * 菜单权限集合
	 */
	private List<SystemRoleMenuRelationBean> menu_roles;
	
	/**
	 * 菜单序号
	 */
	private Integer seq;
	
	private String parent_name;
	
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public String getRank_hex() {
		return rank_hex;
	}
	public void setRank_hex(String rank_hex) {
		this.rank_hex = rank_hex;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon_path() {
		return icon_path;
	}
	public void setIcon_path(String icon_path) {
		this.icon_path = icon_path;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<SystemMenuBean> getChildren_menu() {
		return children_menu;
	}
	public void setChildren_menu(List<SystemMenuBean> children_menu) {
		this.children_menu = children_menu;
	}
	public List<SystemRoleMenuRelationBean> getMenu_roles() {
		return menu_roles;
	}
	public void setMenu_roles(List<SystemRoleMenuRelationBean> menu_roles) {
		this.menu_roles = menu_roles;
	}
	public String getStatus_value() {
		return status_value;
	}
	public void setStatus_value(String status_value) {
		this.status_value = status_value;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
