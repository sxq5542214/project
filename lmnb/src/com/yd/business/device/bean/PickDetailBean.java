package com.yd.business.device.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 抄表明细
 * PickDetailBean entity. @author MyEclipse Persistence Tools
 */
@Alias("pickDetail")
public class PickDetailBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4070478975006739714L;
	private Long pd_id;
	private Long pd_userid;
	private Long pd_operatorid;
	private BigDecimal pd_startamount;
	private BigDecimal pd_endamount;
	private Integer pd_year;
	private Integer pd_month;
	private Integer pd_status;
	private Date pd_happendate;
	private Date pd_readingdate;
	private Long pd_readingid;
	private Long pd_settlementid;
	private Date pd_settlementdate;

	// Constructors

	/** default constructor */
	public PickDetailBean() {
	}

	/** full constructor */
	public PickDetailBean(Long pd_userid, Long pd_operatorid, BigDecimal pd_startamount, BigDecimal pd_endamount,
			Integer pd_year, Integer pd_month, Integer pd_status, Date pd_happendate, Date pd_readingdate,
			Long pd_readingid, Long pd_settlementid, Date pd_settlementdate) {
		this.pd_userid = pd_userid;
		this.pd_operatorid = pd_operatorid;
		this.pd_startamount = pd_startamount;
		this.pd_endamount = pd_endamount;
		this.pd_year = pd_year;
		this.pd_month = pd_month;
		this.pd_status = pd_status;
		this.pd_happendate = pd_happendate;
		this.pd_readingdate = pd_readingdate;
		this.pd_readingid = pd_readingid;
		this.pd_settlementid = pd_settlementid;
		this.pd_settlementdate = pd_settlementdate;
	}

	// Property accessors

	public Long getPd_id() {
		return this.pd_id;
	}

	public void setPd_id(Long pd_id) {
		this.pd_id = pd_id;
	}

	public Long getPd_userid() {
		return this.pd_userid;
	}

	public void setPd_userid(Long pd_userid) {
		this.pd_userid = pd_userid;
	}

	public Long getPd_operatorid() {
		return this.pd_operatorid;
	}

	public void setPd_operatorid(Long pd_operatorid) {
		this.pd_operatorid = pd_operatorid;
	}

	public BigDecimal getPd_startamount() {
		return this.pd_startamount;
	}

	public void setPd_startamount(BigDecimal pd_startamount) {
		this.pd_startamount = pd_startamount;
	}

	public BigDecimal getPd_endamount() {
		return this.pd_endamount;
	}

	public void setPd_endamount(BigDecimal pd_endamount) {
		this.pd_endamount = pd_endamount;
	}

	public Integer getPd_year() {
		return this.pd_year;
	}

	public void setPd_year(Integer pd_year) {
		this.pd_year = pd_year;
	}

	public Integer getPd_month() {
		return this.pd_month;
	}

	public void setPd_month(Integer pd_month) {
		this.pd_month = pd_month;
	}

	public Integer getPd_status() {
		return this.pd_status;
	}

	public void setPd_status(Integer pd_status) {
		this.pd_status = pd_status;
	}

	public Date getPd_happendate() {
		return this.pd_happendate;
	}

	public void setPd_happendate(Date pd_happendate) {
		this.pd_happendate = pd_happendate;
	}

	public Date getPd_readingdate() {
		return this.pd_readingdate;
	}

	public void setPd_readingdate(Date pd_readingdate) {
		this.pd_readingdate = pd_readingdate;
	}

	public Long getPd_readingid() {
		return this.pd_readingid;
	}

	public void setPd_readingid(Long pd_readingid) {
		this.pd_readingid = pd_readingid;
	}

	public Long getPd_settlementid() {
		return this.pd_settlementid;
	}

	public void setPd_settlementid(Long pd_settlementid) {
		this.pd_settlementid = pd_settlementid;
	}

	public Date getPd_settlementdate() {
		return this.pd_settlementdate;
	}

	public void setPd_settlementdate(Date pd_settlementdate) {
		this.pd_settlementdate = pd_settlementdate;
	}

}