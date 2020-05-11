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
@Alias("supplierPackageProductRecord")
public class SupplierPackageProductRecordBean extends BaseBean {
	private Integer id;
	private Integer user_id;
	private String openid;
	private Integer supplier_id;
	private Integer supplier_package_id;
	private String supplier_package_name;
	private Integer supplier_product_id;
	private String supplier_product_name;
	private String create_time;
	private String modify_time;
	private String eff_time;
	private String dff_time;
	private Integer num;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSupplier_package_name() {
		return supplier_package_name;
	}
	public void setSupplier_package_name(String supplier_package_name) {
		this.supplier_package_name = supplier_package_name;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getSupplier_package_id() {
		return supplier_package_id;
	}
	public void setSupplier_package_id(Integer supplier_package_id) {
		this.supplier_package_id = supplier_package_id;
	}
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public String getSupplier_product_name() {
		return supplier_product_name;
	}
	public void setSupplier_product_name(String supplier_product_name) {
		this.supplier_product_name = supplier_product_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	
	
}
