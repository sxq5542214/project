/**
 * 
 */
package com.yd.business.partner.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("partnerDiscountInstance")
public class PartnerDiscountInstanceBean extends BaseBean {
	
	private Integer id;
	private Integer customer_id;
	private Integer partner_id;
	private Integer product_brand;
	private String product_brand_name;
	private Integer product_type;
	private String product_type_name;
	private Integer min_price;
	private Integer max_price;
	private Integer discount;
	private Integer product_id;
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
	public Integer getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(Integer partner_id) {
		this.partner_id = partner_id;
	}
	public Integer getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(Integer product_brand) {
		this.product_brand = product_brand;
	}
	public String getProduct_brand_name() {
		return product_brand_name;
	}
	public void setProduct_brand_name(String product_brand_name) {
		this.product_brand_name = product_brand_name;
	}
	public Integer getProduct_type() {
		return product_type;
	}
	public void setProduct_type(Integer product_type) {
		this.product_type = product_type;
	}
	public String getProduct_type_name() {
		return product_type_name;
	}
	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
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
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	
	
}
