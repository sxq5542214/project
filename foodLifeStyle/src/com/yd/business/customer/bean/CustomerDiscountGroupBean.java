package com.yd.business.customer.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("customerDiscountGroup")
public class CustomerDiscountGroupBean extends BaseBean {

	private Integer id;
	private String name;
	private Integer customer_id;
	private String customer_name;
	private String remark;
	private String disinfo;
	public String getDisinfo() {
		return disinfo;
	}
	public void setDisinfo(String disinfo) {
		this.disinfo = disinfo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
