package com.yd.business.bill.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * BillAuditBean entity. @author MyEclipse Persistence Tools
 */
@Alias("billAudit")
public class BillAuditBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 452351402825458850L;
	private Long ba_id;
	private Long ba_billid;
	private Integer ba_status;
	private Long ba_operatorid;
	private Date ba_happendate;

	// Constructors

	/** default constructor */
	public BillAuditBean() {
	}

	/** full constructor */
	public BillAuditBean(Long ba_billid, Integer ba_status, Long ba_operatorid, Date ba_happendate) {
		this.ba_billid = ba_billid;
		this.ba_status = ba_status;
		this.ba_operatorid = ba_operatorid;
		this.ba_happendate = ba_happendate;
	}

	// Property accessors

	public Long getBa_id() {
		return this.ba_id;
	}

	public void setBa_id(Long ba_id) {
		this.ba_id = ba_id;
	}

	public Long getBa_billid() {
		return this.ba_billid;
	}

	public void setBa_billid(Long ba_billid) {
		this.ba_billid = ba_billid;
	}

	public Integer getBa_status() {
		return this.ba_status;
	}

	public void setBa_status(Integer ba_status) {
		this.ba_status = ba_status;
	}

	public Long getBa_operatorid() {
		return this.ba_operatorid;
	}

	public void setBa_operatorid(Long ba_operatorid) {
		this.ba_operatorid = ba_operatorid;
	}

	public Date getBa_happendate() {
		return this.ba_happendate;
	}

	public void setBa_happendate(Date ba_happendate) {
		this.ba_happendate = ba_happendate;
	}

}