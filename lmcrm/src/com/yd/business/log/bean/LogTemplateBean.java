/**
 * 
 */
package com.yd.business.log.bean;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("logTemplate")
public class LogTemplateBean extends BaseBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5687385537985903733L;
	
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	public static final int IS_SAVE_PARAMS_TRUE = 1;
	public static final int IS_SAVE_PARAMS_FALSE = 0;
	
	
	private Integer id;
	private String method;
	private String name;
	private String hierarchy;
	private String template;
	private Integer status;
	private Integer is_save_params;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIs_save_params() {
		return is_save_params;
	}
	public void setIs_save_params(Integer is_save_params) {
		this.is_save_params = is_save_params;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
