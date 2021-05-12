package com.yd.business.material.bean;

import org.apache.ibatis.type.Alias;

/**
 * UnitBean entity. @author MyEclipse Persistence Tools
 */
@Alias("unit")
public class UnitBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5515715777910347112L;
	private Long u_id;
	private String u_name;
	private Integer u_type;

	// Constructors

	/** default constructor */
	public UnitBean() {
	}

	/** full constructor */
	public UnitBean(String u_name, Integer u_type) {
		this.u_name = u_name;
		this.u_type = u_type;
	}

	// Property accessors

	public Long getU_id() {
		return this.u_id;
	}

	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}

	public String getU_name() {
		return this.u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public Integer getU_type() {
		return this.u_type;
	}

	public void setU_type(Integer u_type) {
		this.u_type = u_type;
	}

}