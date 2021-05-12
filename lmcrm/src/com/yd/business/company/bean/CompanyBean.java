package com.yd.business.company.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * CompanyBean entity. @author MyEclipse Persistence Tools
 */
@Alias("company")
public class CompanyBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -532891931472343126L;
	private Long c_id;
	private Integer c_no;
	private String c_name;
	private Date c_createdate;
	private Date c_updatedate;
	private String c_mchid;
	private String c_key;
	private String c_commkey;

	// Constructors

	/** default constructor */
	public CompanyBean() {
	}

	/** minimal constructor */
	public CompanyBean(Integer c_no, String c_name, Date c_createdate, Date c_updatedate) {
		this.c_no = c_no;
		this.c_name = c_name;
		this.c_createdate = c_createdate;
		this.c_updatedate = c_updatedate;
	}

	/** full constructor */
	public CompanyBean(Integer c_no, String c_name, Date c_createdate, Date c_updatedate, String c_mchid, String c_key,
			String c_commkey) {
		this.c_no = c_no;
		this.c_name = c_name;
		this.c_createdate = c_createdate;
		this.c_updatedate = c_updatedate;
		this.c_mchid = c_mchid;
		this.c_key = c_key;
		this.c_commkey = c_commkey;
	}

	// Property accessors

	public Long getC_id() {
		return this.c_id;
	}

	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}

	public Integer getC_no() {
		return this.c_no;
	}

	public void setC_no(Integer c_no) {
		this.c_no = c_no;
	}

	public String getC_name() {
		return this.c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public Date getC_createdate() {
		return this.c_createdate;
	}

	public void setC_createdate(Date c_createdate) {
		this.c_createdate = c_createdate;
	}

	public Date getC_updatedate() {
		return this.c_updatedate;
	}

	public void setC_updatedate(Date c_updatedate) {
		this.c_updatedate = c_updatedate;
	}

	public String getC_mchid() {
		return this.c_mchid;
	}

	public void setC_mchid(String c_mchid) {
		this.c_mchid = c_mchid;
	}

	public String getC_key() {
		return this.c_key;
	}

	public void setC_key(String c_key) {
		this.c_key = c_key;
	}

	public String getC_commkey() {
		return this.c_commkey;
	}

	public void setC_commkey(String c_commkey) {
		this.c_commkey = c_commkey;
	}

}