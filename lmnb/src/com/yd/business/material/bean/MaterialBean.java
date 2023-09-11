package com.yd.business.material.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * MaterialBean entity. @author MyEclipse Persistence Tools
 */
@Alias("material")
public class MaterialBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4784105857146262552L;
	private Long m_id;
	private Long m_typeid;
	private String m_name;
	private String m_specification;
	private String m_unit;
	private BigDecimal m_price;
	private Date m_createdate;
	private Date m_updatedate;

	// Constructors

	/** default constructor */
	public MaterialBean() {
	}

	/** full constructor */
	public MaterialBean(Long m_typeid, String m_name, String m_specification, String m_unit, BigDecimal m_price,
			Date m_createdate, Date m_updatedate) {
		this.m_typeid = m_typeid;
		this.m_name = m_name;
		this.m_specification = m_specification;
		this.m_unit = m_unit;
		this.m_price = m_price;
		this.m_createdate = m_createdate;
		this.m_updatedate = m_updatedate;
	}

	// Property accessors

	public Long getM_id() {
		return this.m_id;
	}

	public void setM_id(Long m_id) {
		this.m_id = m_id;
	}

	public Long getM_typeid() {
		return this.m_typeid;
	}

	public void setM_typeid(Long m_typeid) {
		this.m_typeid = m_typeid;
	}

	public String getM_name() {
		return this.m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_specification() {
		return this.m_specification;
	}

	public void setM_specification(String m_specification) {
		this.m_specification = m_specification;
	}

	public String getM_unit() {
		return this.m_unit;
	}

	public void setM_unit(String m_unit) {
		this.m_unit = m_unit;
	}

	public BigDecimal getM_price() {
		return this.m_price;
	}

	public void setM_price(BigDecimal m_price) {
		this.m_price = m_price;
	}

	public Date getM_createdate() {
		return this.m_createdate;
	}

	public void setM_createdate(Date m_createdate) {
		this.m_createdate = m_createdate;
	}

	public Date getM_updatedate() {
		return this.m_updatedate;
	}

	public void setM_updatedate(Date m_updatedate) {
		this.m_updatedate = m_updatedate;
	}

}