package com.yd.business.area.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * AreaBean entity. @author MyEclipse Persistence Tools
 */

@Alias("area")
public class AreaBean implements java.io.Serializable {

	
	// Fields
	public static final int LEVEL_COMPANY = 1 ;
	public static final int LEVEL_AREA = 2 ;
	public static final int LEVEL_BUILDING = 3 ;
			
	/**
	 * 
	 */
	private static final long serialVersionUID = -1997670723595276919L;
	private Long a_id;
	private Long a_companyid;
	private String a_name;
	private Date a_createdate;
	private Date a_updatedate;

	// Constructors

	/** default constructor */
	public AreaBean() {
	}

	/** full constructor */
	public AreaBean(Long a_companyid, String a_name, Date a_createdate, Date a_updatedate) {
		this.a_companyid = a_companyid;
		this.a_name = a_name;
		this.a_createdate = a_createdate;
		this.a_updatedate = a_updatedate;
	}

	// Property accessors

	public Long getA_id() {
		return this.a_id;
	}

	public void setA_id(Long a_id) {
		this.a_id = a_id;
	}

	public Long getA_companyid() {
		return this.a_companyid;
	}

	public void setA_companyid(Long a_companyid) {
		this.a_companyid = a_companyid;
	}

	public String getA_name() {
		return this.a_name;
	}

	public void setA_name(String a_name) {
		this.a_name = a_name;
	}

	public Date getA_createdate() {
		return this.a_createdate;
	}

	public void setA_createdate(Date a_createdate) {
		this.a_createdate = a_createdate;
	}

	public Date getA_updatedate() {
		return this.a_updatedate;
	}

	public void setA_updatedate(Date a_updatedate) {
		this.a_updatedate = a_updatedate;
	}

}