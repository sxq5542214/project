package com.yd.business.area.bean;

import org.apache.ibatis.type.Alias;

/**
 * AreaOperatorBeanId entity. @author MyEclipse Persistence Tools
 */

@Alias("areaOperator")
public class AreaOperatorBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511545737768345145L;
	private Long ao_areaid;
	private Long ao_operatorid;

	// Constructors


	// Property accessors

	public Long getAo_areaid() {
		return this.ao_areaid;
	}

	public void setAo_areaid(Long ao_areaid) {
		this.ao_areaid = ao_areaid;
	}

	public Long getAo_operatorid() {
		return this.ao_operatorid;
	}

	public void setAo_operatorid(Long ao_operatorid) {
		this.ao_operatorid = ao_operatorid;
	}



}