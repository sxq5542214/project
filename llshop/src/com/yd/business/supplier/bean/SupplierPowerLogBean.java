package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("supplierPowerLog")
public class SupplierPowerLogBean extends BaseBean {

	private Integer id;
	private Integer supplier_id;
	private Integer customer_id;
	private String supplier_name;
	private String customer_name;
	private Integer product_id;
	private String product_name;
	private String power_time;
	private String dff_time;
	private String eff_time;
	private Integer power_num;
	private Integer use_num;
	private Integer store_num;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
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
	public String getPower_time() {
		return power_time;
	}
	public void setPower_time(String power_time) {
		this.power_time = power_time;
	}
	public String getDff_time() {
		return dff_time;
	}
	public void setDff_time(String dff_time) {
		this.dff_time = dff_time;
	}
	public String getEff_time() {
		return eff_time;
	}
	public void setEff_time(String eff_time) {
		this.eff_time = eff_time;
	}
	public Integer getPower_num() {
		return power_num;
	}
	public void setPower_num(Integer power_num) {
		this.power_num = power_num;
	}
	public Integer getUse_num() {
		return use_num;
	}
	public void setUse_num(Integer use_num) {
		this.use_num = use_num;
	}
	public Integer getStore_num() {
		return store_num;
	}
	public void setStore_num(Integer store_num) {
		this.store_num = store_num;
	}
	
}
