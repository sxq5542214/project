/**
 * 
 */
package com.yd.business.system.bean;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
public class SystemBean extends BaseBean {
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	public static final String ACTION_TYPE_ADD = "add";
	public static final String ACTION_TYPE_MODIFY = "modify";
	public static final String ACTION_TYPE_DELETE = "delete";
	
	
	public static final String BEAN_TYPE_MENU = "menu";
	public static final String BEAN_TYPE_ADMIN = "admin";
	
	
	private Integer id;
	private String name;
	private String code;
	private Integer status;
	private String create_time;
	private String modify_time;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
}
