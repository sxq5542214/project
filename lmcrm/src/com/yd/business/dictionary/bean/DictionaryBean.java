/**
 * 
 */
package com.yd.business.dictionary.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 * 
 */
@Alias("dictionary")
public class DictionaryBean extends BaseBean {
	
	public static final String ATTRIBUTE_STATUS = "status";
	public static final String ATTRIBUTE_TYPE = "type";
	public static final String ATTRIBUTE_PAY_CYCLE = "pay_cycle";
	public static final String ATTRIBUTE_ISCREATE = "iscreate";
	
	
	public static final int NUMBER_ZEOR = 0;
	
	private Integer id;
	private String beanname;
	private String attribute;
	private String value;
	private String description;
	private String commons;

	private String remark;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getBeanname() {
		return beanname;
	}

	public void setBeanname(String beanname) {
		this.beanname = beanname;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommons() {
		return commons;
	}

	public void setCommons(String commons) {
		this.commons = commons;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
