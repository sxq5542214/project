package com.yd.business.material.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * MaterialTypeBean entity. @author MyEclipse Persistence Tools
 */
@Alias("materialType")
public class MaterialTypeBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1932048619344209126L;
	private Long mt_id;
	private String mt_no;
	private String mt_name;
	private Date mt_createdate;
	private Date mt_updatedate;

	// Constructors

	/** default constructor */
	public MaterialTypeBean() {
	}

	/** full constructor */
	public MaterialTypeBean(String mt_no, String mt_name, Date mt_createdate, Date mt_updatedate) {
		this.mt_no = mt_no;
		this.mt_name = mt_name;
		this.mt_createdate = mt_createdate;
		this.mt_updatedate = mt_updatedate;
	}

	// Property accessors

	public Long getMt_id() {
		return this.mt_id;
	}

	public void setMt_id(Long mt_id) {
		this.mt_id = mt_id;
	}

	public String getMt_no() {
		return this.mt_no;
	}

	public void setMt_no(String mt_no) {
		this.mt_no = mt_no;
	}

	public String getMt_name() {
		return this.mt_name;
	}

	public void setMt_name(String mt_name) {
		this.mt_name = mt_name;
	}

	public Date getMt_createdate() {
		return this.mt_createdate;
	}

	public void setMt_createdate(Date mt_createdate) {
		this.mt_createdate = mt_createdate;
	}

	public Date getMt_updatedate() {
		return this.mt_updatedate;
	}

	public void setMt_updatedate(Date mt_updatedate) {
		this.mt_updatedate = mt_updatedate;
	}

}