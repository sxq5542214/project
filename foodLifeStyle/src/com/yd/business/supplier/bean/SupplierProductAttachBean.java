/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("supplierProductAttach")
public class SupplierProductAttachBean extends BaseBean {
	private Integer id;
	private Integer supplier_product_id;
	private Integer attach_supplier_product_id;
	private Integer customer_id;
	private Integer store_num;
	private String attach_product_name;
	private Integer attach_product_price;
	private Integer discount;
	private String create_time;
	private String eff_time;
	private String dff_time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public Integer getAttach_supplier_product_id() {
		return attach_supplier_product_id;
	}
	public void setAttach_supplier_product_id(Integer attach_supplier_product_id) {
		this.attach_supplier_product_id = attach_supplier_product_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getStore_num() {
		return store_num;
	}
	public void setStore_num(Integer store_num) {
		this.store_num = store_num;
	}
	public String getAttach_product_name() {
		return attach_product_name;
	}
	public void setAttach_product_name(String attach_product_name) {
		this.attach_product_name = attach_product_name;
	}
	public Integer getAttach_product_price() {
		return attach_product_price;
	}
	public void setAttach_product_price(Integer attach_product_price) {
		this.attach_product_price = attach_product_price;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getEff_time() {
		return eff_time;
	}
	public void setEff_time(String eff_time) {
		this.eff_time = eff_time;
	}
	public String getDff_time() {
		return dff_time;
	}
	public void setDff_time(String dff_time) {
		this.dff_time = dff_time;
	}
}
