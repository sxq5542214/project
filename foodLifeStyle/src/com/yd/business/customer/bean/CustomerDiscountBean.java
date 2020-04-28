package com.yd.business.customer.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("customerDiscount")
public class CustomerDiscountBean extends BaseBean {

	private Integer id;
	private Integer customer_id;
	private Integer group_id;
	private String name;
	private Integer min_price;
	private Integer max_price;
	private Integer discount;
	private Integer product_brand;
	private String product_brand_name;
	private Integer product_type;
	
	public String getProduct_brand_name() {
		return product_brand_name;
	}
	public void setProduct_brand_name(String product_brand_name) {
		this.product_brand_name = product_brand_name;
	}
	public Integer getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(Integer product_brand) {
		this.product_brand = product_brand;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMin_price() {
		return min_price;
	}
	public void setMin_price(Integer min_price) {
		this.min_price = min_price;
	}
	public Integer getMax_price() {
		return max_price;
	}
	public void setMax_price(Integer max_price) {
		this.max_price = max_price;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getProduct_type() {
		return product_type;
	}
	public void setProduct_type(Integer product_type) {
		this.product_type = product_type;
	}
	
}
