package com.yd.business.system.bean;

import org.apache.ibatis.type.Alias;

/**
 * SysKindBean entity. @author MyEclipse Persistence Tools
 */
@Alias("sysKind")
public class SysKindBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5066447586139750064L;
	private Integer sk_id;
	private String sk_value;

	// Constructors

	/** default constructor */
	public SysKindBean() {
	}

	/** full constructor */
	public SysKindBean(String sk_value) {
		this.sk_value = sk_value;
	}

	// Property accessors

	public Integer getSk_id() {
		return this.sk_id;
	}

	public void setSk_id(Integer sk_id) {
		this.sk_id = sk_id;
	}

	public String getSk_value() {
		return this.sk_value;
	}

	public void setSk_value(String sk_value) {
		this.sk_value = sk_value;
	}

}