package com.yd.business.depot.bean;

import org.apache.ibatis.type.Alias;

/**
 * DepotOperatorBeanId entity. @author MyEclipse Persistence Tools
 */
@Alias("depotOperator")
public class DepotOperatorBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1107782532059592585L;
	private Integer do_depotid;
	private Integer do_operatorid;



	// Property accessors

	public Integer getDo_depotid() {
		return this.do_depotid;
	}

	public void setDo_depotid(Integer do_depotid) {
		this.do_depotid = do_depotid;
	}

	public Integer getDo_operatorid() {
		return this.do_operatorid;
	}

	public void setDo_operatorid(Integer do_operatorid) {
		this.do_operatorid = do_operatorid;
	}


}