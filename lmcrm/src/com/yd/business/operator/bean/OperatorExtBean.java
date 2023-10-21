/**
 * 
 */
package com.yd.business.operator.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("operatorExt")
public class OperatorExtBean extends OperatorBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2091680158106665598L;
	
	private String company_name;

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	
}
