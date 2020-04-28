/**
 * 
 */
package com.yd.business.product.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("partnerProduct")
public class PartnerProductBean extends BaseBean {
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	private Integer id;
	private Integer customer_id;
	private Integer partner_id;
	private Integer product_id;
	private String product_code;
	private String product_name;
	private Integer product_price;
	private Integer product_type;
	private String product_type_name;
	private Integer product_brand;
	private String product_brand_name;
	private Integer status;
	private String create_time;
	private String eff_date;
	private String dff_date;
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
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getDff_date() {
		return dff_date;
	}
	public void setDff_date(String dff_date) {
		this.dff_date = dff_date;
	}
}
