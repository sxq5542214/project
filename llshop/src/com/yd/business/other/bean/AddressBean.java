/**
 * 
 */
package com.yd.business.other.bean;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("address")
public class AddressBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 263321027535622625L;
	/**
	 * 省
	 */
	public static final int LEVEL_PROVINCE = 1;
	/**
	 * 市
	 */
	public static final int LEVEL_CITY = 2;
	/**
	 * 区、县
	 */
	public static final int LEVEL_DISTRICT = 3;
	
	private Integer id;
	private String name;
	private Integer level;
	private String code;
	private String full_name;
	private Integer parent_id;
	private String comment;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
