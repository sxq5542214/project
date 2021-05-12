package com.yd.business.log.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * OperatorLogBean entity. @author MyEclipse Persistence Tools
 */
@Alias("operatorLog")
public class OperatorLogBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5437686898617506336L;
	private Long ol_id;
	private Long ol_operatorid;
	private String ol_remark;
	private Date ol_happendate;

	// Constructors

	/** default constructor */
	public OperatorLogBean() {
	}

	/** full constructor */
	public OperatorLogBean(Long ol_operatorid, String ol_remark, Date ol_happendate) {
		this.ol_operatorid = ol_operatorid;
		this.ol_remark = ol_remark;
		this.ol_happendate = ol_happendate;
	}

	// Property accessors

	public Long getOl_id() {
		return this.ol_id;
	}

	public void setOl_id(Long ol_id) {
		this.ol_id = ol_id;
	}

	public Long getOl_operatorid() {
		return this.ol_operatorid;
	}

	public void setOl_operatorid(Long ol_operatorid) {
		this.ol_operatorid = ol_operatorid;
	}

	public String getOl_remark() {
		return this.ol_remark;
	}

	public void setOl_remark(String ol_remark) {
		this.ol_remark = ol_remark;
	}

	public Date getOl_happendate() {
		return this.ol_happendate;
	}

	public void setOl_happendate(Date ol_happendate) {
		this.ol_happendate = ol_happendate;
	}

}