package com.yd.business.depot.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 库存表
 * InventoryBean entity. @author MyEclipse Persistence Tools
 */
@Alias("inventory")
public class InventoryBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 800901985527895882L;
	private Long i_id;
	private Long i_depotid;
	private Long i_materialid;
	private Long i_count;
	private Date i_createdate;
	private Date i_updatedate;

	// Constructors

	/** default constructor */
	public InventoryBean() {
	}

	/** full constructor */
	public InventoryBean(Long i_depotid, Long i_materialid, Long i_count, Date i_createdate, Date i_updatedate) {
		this.i_depotid = i_depotid;
		this.i_materialid = i_materialid;
		this.i_count = i_count;
		this.i_createdate = i_createdate;
		this.i_updatedate = i_updatedate;
	}

	// Property accessors

	public Long getI_id() {
		return this.i_id;
	}

	public void setI_id(Long i_id) {
		this.i_id = i_id;
	}

	public Long getI_depotid() {
		return this.i_depotid;
	}

	public void setI_depotid(Long i_depotid) {
		this.i_depotid = i_depotid;
	}

	public Long getI_materialid() {
		return this.i_materialid;
	}

	public void setI_materialid(Long i_materialid) {
		this.i_materialid = i_materialid;
	}

	public Long getI_count() {
		return this.i_count;
	}

	public void setI_count(Long i_count) {
		this.i_count = i_count;
	}

	public Date getI_createdate() {
		return this.i_createdate;
	}

	public void setI_createdate(Date i_createdate) {
		this.i_createdate = i_createdate;
	}

	public Date getI_updatedate() {
		return this.i_updatedate;
	}

	public void setI_updatedate(Date i_updatedate) {
		this.i_updatedate = i_updatedate;
	}

}