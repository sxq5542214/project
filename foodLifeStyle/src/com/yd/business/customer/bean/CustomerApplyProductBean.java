package com.yd.business.customer.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
/**
 * 客户申领
 * @author Anlins
 *
 */
@Alias("customerApplyProduct")
public class CustomerApplyProductBean extends BaseBean {

	public static final Integer APPLY_STATUS_YSQ = 0;//已申请未审核
	public static final Integer APPLY_STATUS_YES = 1;//审核通过
	public static final Integer APPLY_STATUS_NO = 2;//审核不通过
	public static final Integer APPLY_STATUS_CA = 3;//用户取消申请
	
	private Integer id;
	private Integer customer_id;
	private Integer apply_supplier_id;
	private Integer apply_customer_id;
	private Integer product_id;
	private String product_name;
	private Integer apply_num;
	private Integer status;
	private String create_time;
	private String remark;
	private String modify_time;
	private Integer supplier_store_num;
	private String supplier_name;
	private Integer use_num;
	private Integer audit_by_num;
	
	public Integer getUse_num() {
		return use_num;
	}
	public void setUse_num(Integer use_num) {
		this.use_num = use_num;
	}
	public Integer getAudit_by_num() {
		return audit_by_num;
	}
	public void setAudit_by_num(Integer audit_by_num) {
		this.audit_by_num = audit_by_num;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
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
	public Integer getApply_supplier_id() {
		return apply_supplier_id;
	}
	public void setApply_supplier_id(Integer apply_supplier_id) {
		this.apply_supplier_id = apply_supplier_id;
	}
	public Integer getApply_customer_id() {
		return apply_customer_id;
	}
	public void setApply_customer_id(Integer apply_customer_id) {
		this.apply_customer_id = apply_customer_id;
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
	public Integer getApply_num() {
		return apply_num;
	}
	public void setApply_num(Integer apply_num) {
		this.apply_num = apply_num;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getSupplier_store_num() {
		return supplier_store_num;
	}
	public void setSupplier_store_num(Integer supplier_store_num) {
		this.supplier_store_num = supplier_store_num;
	}
	
}
