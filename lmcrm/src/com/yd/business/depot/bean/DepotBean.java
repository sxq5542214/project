package com.yd.business.depot.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * DepotBean entity. @author MyEclipse Persistence Tools
 */
@Alias("depot")
public class DepotBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -658857068415301186L;
	private Long d_id;
	private Long d_companyid;
	private String d_name;
	private Date d_createdate;
	private Date d_updatedate;

	// Constructors

	/** default constructor */
	public DepotBean() {
	}

	/** full constructor */
	public DepotBean(Long d_companyid, String d_name, Date d_createdate, Date d_updatedate) {
		this.d_companyid = d_companyid;
		this.d_name = d_name;
		this.d_createdate = d_createdate;
		this.d_updatedate = d_updatedate;
	}

	// Property accessors

	public Long getD_id() {
		return this.d_id;
	}

	public void setD_id(Long d_id) {
		this.d_id = d_id;
	}

	public Long getD_companyid() {
		return this.d_companyid;
	}

	public void setD_companyid(Long d_companyid) {
		this.d_companyid = d_companyid;
	}

	public String getD_name() {
		return this.d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public Date getD_createdate() {
		return this.d_createdate;
	}

	public void setD_createdate(Date d_createdate) {
		this.d_createdate = d_createdate;
	}

	public Date getD_updatedate() {
		return this.d_updatedate;
	}

	public void setD_updatedate(Date d_updatedate) {
		this.d_updatedate = d_updatedate;
	}

}