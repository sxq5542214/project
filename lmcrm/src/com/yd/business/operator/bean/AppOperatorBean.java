package com.yd.business.operator.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * AppOperatorBean entity. @author MyEclipse Persistence Tools
 */

@Alias("appOperator")
public class AppOperatorBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3225100982696975781L;
	private Long ao_id;
	private String ao_appcode;
	private String ao_phone;
	private String ao_remark;
	private Date ao_createdate;
	private Date ao_updatedate;

	// Constructors

	/** default constructor */
	public AppOperatorBean() {
	}

	/** minimal constructor */
	public AppOperatorBean(String ao_appcode, Date ao_createdate, Date ao_updatedate) {
		this.ao_appcode = ao_appcode;
		this.ao_createdate = ao_createdate;
		this.ao_updatedate = ao_updatedate;
	}

	/** full constructor */
	public AppOperatorBean(String ao_appcode, String ao_phone, String ao_remark, Date ao_createdate,
			Date ao_updatedate) {
		this.ao_appcode = ao_appcode;
		this.ao_phone = ao_phone;
		this.ao_remark = ao_remark;
		this.ao_createdate = ao_createdate;
		this.ao_updatedate = ao_updatedate;
	}

	// Property accessors

	public Long getAo_id() {
		return this.ao_id;
	}

	public void setAo_id(Long ao_id) {
		this.ao_id = ao_id;
	}

	public String getAo_appcode() {
		return this.ao_appcode;
	}

	public void setAo_appcode(String ao_appcode) {
		this.ao_appcode = ao_appcode;
	}

	public String getAo_phone() {
		return this.ao_phone;
	}

	public void setAo_phone(String ao_phone) {
		this.ao_phone = ao_phone;
	}

	public String getAo_remark() {
		return this.ao_remark;
	}

	public void setAo_remark(String ao_remark) {
		this.ao_remark = ao_remark;
	}

	public Date getAo_createdate() {
		return this.ao_createdate;
	}

	public void setAo_createdate(Date ao_createdate) {
		this.ao_createdate = ao_createdate;
	}

	public Date getAo_updatedate() {
		return this.ao_updatedate;
	}

	public void setAo_updatedate(Date ao_updatedate) {
		this.ao_updatedate = ao_updatedate;
	}

}