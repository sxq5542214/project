package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("supplierDiscountRelation")
public class SupplierDiscountRelationBean extends BaseBean {

	private Integer id;
	private Integer customer_id;
	private String customer_name;
	private Integer discount_group_id;
	private String group_name;
	private Integer supplier_id;
	private String supplier_name;
	private Integer product_id;
	private String product_name;
	private Integer parent_discount_group_id;
	
	public Integer getParent_discount_group_id() {
		return parent_discount_group_id;
	}
	public void setParent_discount_group_id(Integer parent_discount_group_id) {
		this.parent_discount_group_id = parent_discount_group_id;
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
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Integer getDiscount_group_id() {
		return discount_group_id;
	}
	public void setDiscount_group_id(Integer discount_group_id) {
		this.discount_group_id = discount_group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
}
