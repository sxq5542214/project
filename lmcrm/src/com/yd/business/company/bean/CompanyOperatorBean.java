package com.yd.business.company.bean;

import org.apache.ibatis.type.Alias;

/**
 * CompanyOperatorBeanId entity. @author MyEclipse Persistence Tools
 */

@Alias("companyOperator")
public class CompanyOperatorBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1662696752449544372L;
	private Integer co_companyid;
	private Integer co_operatorid;

	// Constructors


	// Property accessors

	public Integer getCo_companyid() {
		return this.co_companyid;
	}

	public void setCo_companyid(Integer co_companyid) {
		this.co_companyid = co_companyid;
	}

	public Integer getCo_operatorid() {
		return this.co_operatorid;
	}

	public void setCo_operatorid(Integer co_operatorid) {
		this.co_operatorid = co_operatorid;
	}

}