package com.yd.business.price.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * VerifiedDetailBean entity. @author MyEclipse Persistence Tools
 */
@Alias("verifiedDetail")
public class VerifiedDetailBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1897659330231299680L;
	private Long vd_id;
	private Long vd_userid;
	private BigDecimal vd_money;
	private Integer vd_type;
	private Long vd_operatorid;
	private Date vd_happendate;

	// Constructors

	/** default constructor */
	public VerifiedDetailBean() {
	}

	/** full constructor */
	public VerifiedDetailBean(Long vd_userid, BigDecimal vd_money, Integer vd_type, Long vd_operatorid,
			Date vd_happendate) {
		this.vd_userid = vd_userid;
		this.vd_money = vd_money;
		this.vd_type = vd_type;
		this.vd_operatorid = vd_operatorid;
		this.vd_happendate = vd_happendate;
	}

	// Property accessors

	public Long getVd_id() {
		return this.vd_id;
	}

	public void setVd_id(Long vd_id) {
		this.vd_id = vd_id;
	}

	public Long getVd_userid() {
		return this.vd_userid;
	}

	public void setVd_userid(Long vd_userid) {
		this.vd_userid = vd_userid;
	}

	public BigDecimal getVd_money() {
		return this.vd_money;
	}

	public void setVd_money(BigDecimal vd_money) {
		this.vd_money = vd_money;
	}

	public Integer getVd_type() {
		return this.vd_type;
	}

	public void setVd_type(Integer vd_type) {
		this.vd_type = vd_type;
	}

	public Long getVd_operatorid() {
		return this.vd_operatorid;
	}

	public void setVd_operatorid(Long vd_operatorid) {
		this.vd_operatorid = vd_operatorid;
	}

	public Date getVd_happendate() {
		return this.vd_happendate;
	}

	public void setVd_happendate(Date vd_happendate) {
		this.vd_happendate = vd_happendate;
	}

}