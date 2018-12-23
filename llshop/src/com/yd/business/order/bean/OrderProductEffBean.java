/**
 * 
 */
package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("orderProductEff")
public class OrderProductEffBean extends BaseBean {
	
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAILD = 2;

	public static final int TYPE_CARD_SECRET = 1;//卡密预约
	public static final int TYPE_USER_EFF = 2; //用户订购预约
	
	
	
	private Integer id;
	private Integer supplier_id;
	private Integer customer_id;
	private Integer product_id;
	private Integer supplier_product_id;
	private String order_code;
	private String order_account;
	private String eff_time;
	private String create_time;
	private String execute_time;
	private Integer status;
	private String remark;
	private Integer type;
	private Integer eff_id;
	
	private String product_name;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getEff_id() {
		return eff_id;
	}
	public void setEff_id(Integer eff_id) {
		this.eff_id = eff_id;
	}
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
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getOrder_account() {
		return order_account;
	}
	public void setOrder_account(String order_account) {
		this.order_account = order_account;
	}
	public String getEff_time() {
		return eff_time;
	}
	public void setEff_time(String eff_time) {
		this.eff_time = eff_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getExecute_time() {
		return execute_time;
	}
	public void setExecute_time(String execute_time) {
		this.execute_time = execute_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	
	
	
}
