package com.yd.business.bill.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * BillDetailBean entity. @author MyEclipse Persistence Tools
 */
@Alias("billDetail")
public class BillDetailBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5328620068553284737L;
	private Long bd_id;
	private Long bd_depotid;
	private Long bd_newdepotid;
	private Long bd_billid;
	private Long bd_materialid;
	private Long bd_count;
	private BigDecimal bd_money;
	private String bd_remark;
	private Date bd_createdate;
	private Date bd_updatedate;

	// Constructors

	/** default constructor */
	public BillDetailBean() {
	}

	/** minimal constructor */
	public BillDetailBean(Long bd_depotid, Long bd_newdepotid, Long bd_billid, Long bd_materialid, Long bd_count,
			BigDecimal bd_money, Date bd_createdate, Date bd_updatedate) {
		this.bd_depotid = bd_depotid;
		this.bd_newdepotid = bd_newdepotid;
		this.bd_billid = bd_billid;
		this.bd_materialid = bd_materialid;
		this.bd_count = bd_count;
		this.bd_money = bd_money;
		this.bd_createdate = bd_createdate;
		this.bd_updatedate = bd_updatedate;
	}

	/** full constructor */
	public BillDetailBean(Long bd_depotid, Long bd_newdepotid, Long bd_billid, Long bd_materialid, Long bd_count,
			BigDecimal bd_money, String bd_remark, Date bd_createdate, Date bd_updatedate) {
		this.bd_depotid = bd_depotid;
		this.bd_newdepotid = bd_newdepotid;
		this.bd_billid = bd_billid;
		this.bd_materialid = bd_materialid;
		this.bd_count = bd_count;
		this.bd_money = bd_money;
		this.bd_remark = bd_remark;
		this.bd_createdate = bd_createdate;
		this.bd_updatedate = bd_updatedate;
	}

	// Property accessors

	public Long getBd_id() {
		return this.bd_id;
	}

	public void setBd_id(Long bd_id) {
		this.bd_id = bd_id;
	}

	public Long getBd_depotid() {
		return this.bd_depotid;
	}

	public void setBd_depotid(Long bd_depotid) {
		this.bd_depotid = bd_depotid;
	}

	public Long getBd_newdepotid() {
		return this.bd_newdepotid;
	}

	public void setBd_newdepotid(Long bd_newdepotid) {
		this.bd_newdepotid = bd_newdepotid;
	}

	public Long getBd_billid() {
		return this.bd_billid;
	}

	public void setBd_billid(Long bd_billid) {
		this.bd_billid = bd_billid;
	}

	public Long getBd_materialid() {
		return this.bd_materialid;
	}

	public void setBd_materialid(Long bd_materialid) {
		this.bd_materialid = bd_materialid;
	}

	public Long getBd_count() {
		return this.bd_count;
	}

	public void setBd_count(Long bd_count) {
		this.bd_count = bd_count;
	}

	public BigDecimal getBd_money() {
		return this.bd_money;
	}

	public void setBd_money(BigDecimal bd_money) {
		this.bd_money = bd_money;
	}

	public String getBd_remark() {
		return this.bd_remark;
	}

	public void setBd_remark(String bd_remark) {
		this.bd_remark = bd_remark;
	}

	public Date getBd_createdate() {
		return this.bd_createdate;
	}

	public void setBd_createdate(Date bd_createdate) {
		this.bd_createdate = bd_createdate;
	}

	public Date getBd_updatedate() {
		return this.bd_updatedate;
	}

	public void setBd_updatedate(Date bd_updatedate) {
		this.bd_updatedate = bd_updatedate;
	}

}