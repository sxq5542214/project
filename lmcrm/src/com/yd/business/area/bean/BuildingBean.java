package com.yd.business.area.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * BuildingBean entity. @author MyEclipse Persistence Tools
 */
@Alias("building")
public class BuildingBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2344383650253557839L;
	private Long b_id;
	private Long b_areaid;
	private String b_name;
	private Date b_createdate;
	private Date b_updatedate;

	// Constructors

	/** default constructor */
	public BuildingBean() {
	}

	/** full constructor */
	public BuildingBean(Long b_areaid, String b_name, Date b_createdate, Date b_updatedate) {
		this.b_areaid = b_areaid;
		this.b_name = b_name;
		this.b_createdate = b_createdate;
		this.b_updatedate = b_updatedate;
	}

	// Property accessors

	public Long getB_id() {
		return this.b_id;
	}

	public void setB_id(Long b_id) {
		this.b_id = b_id;
	}

	public Long getB_areaid() {
		return this.b_areaid;
	}

	public void setB_areaid(Long b_areaid) {
		this.b_areaid = b_areaid;
	}

	public String getB_name() {
		return this.b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public Date getB_createdate() {
		return this.b_createdate;
	}

	public void setB_createdate(Date b_createdate) {
		this.b_createdate = b_createdate;
	}

	public Date getB_updatedate() {
		return this.b_updatedate;
	}

	public void setB_updatedate(Date b_updatedate) {
		this.b_updatedate = b_updatedate;
	}

}