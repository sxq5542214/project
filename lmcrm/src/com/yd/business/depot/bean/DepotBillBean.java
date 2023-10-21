package com.yd.business.depot.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * DepotBillBean entity. @author MyEclipse Persistence Tools
 */
@Alias("depotBill")
public class DepotBillBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926878240324031194L;
	private Long db_id;
	private String db_no;
	private Integer db_type;
	private Integer db_status;
	private Date db_createdate;
	private Date db_updatedate;

	// Constructors

	/** default constructor */
	public DepotBillBean() {
	}

	/** full constructor */
	public DepotBillBean(String db_no, Integer db_type, Integer db_status, Date db_createdate, Date db_updatedate) {
		this.db_no = db_no;
		this.db_type = db_type;
		this.db_status = db_status;
		this.db_createdate = db_createdate;
		this.db_updatedate = db_updatedate;
	}

	// Property accessors

	public Long getDb_id() {
		return this.db_id;
	}

	public void setDb_id(Long db_id) {
		this.db_id = db_id;
	}

	public String getDb_no() {
		return this.db_no;
	}

	public void setDb_no(String db_no) {
		this.db_no = db_no;
	}

	public Integer getDb_type() {
		return this.db_type;
	}

	public void setDb_type(Integer db_type) {
		this.db_type = db_type;
	}

	public Integer getDb_status() {
		return this.db_status;
	}

	public void setDb_status(Integer db_status) {
		this.db_status = db_status;
	}

	public Date getDb_createdate() {
		return this.db_createdate;
	}

	public void setDb_createdate(Date db_createdate) {
		this.db_createdate = db_createdate;
	}

	public Date getDb_updatedate() {
		return this.db_updatedate;
	}

	public void setDb_updatedate(Date db_updatedate) {
		this.db_updatedate = db_updatedate;
	}

}