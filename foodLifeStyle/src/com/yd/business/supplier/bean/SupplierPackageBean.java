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
@Alias("supplierPackage")
public class SupplierPackageBean extends BaseBean {
	public static final int DELETE_FLAG_NO = 0;
	public static final int DELETE_FLAG_YES = 1;
	
	
	private Integer id;
	private Integer supplier_id;
	private String name;
	private String title;
	private String code;
	private String create_time;
	private String modify_time;
	private Integer status;
	private Integer delete_flag;
	private Integer useful_lift;
	private String remark;
	private Integer seq;
	private Integer product_price;
	private Integer product_real_price;
	private Integer prime_cost_price;
	public Integer getId() {
		return id;
	}
	public Integer getPrime_cost_price() {
		return prime_cost_price;
	}
	public void setPrime_cost_price(Integer prime_cost_price) {
		this.prime_cost_price = prime_cost_price;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}
	public Integer getUseful_lift() {
		return useful_lift;
	}
	public void setUseful_lift(Integer useful_lift) {
		this.useful_lift = useful_lift;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}
	public Integer getProduct_real_price() {
		return product_real_price;
	}
	public void setProduct_real_price(Integer product_real_price) {
		this.product_real_price = product_real_price;
	}
}
