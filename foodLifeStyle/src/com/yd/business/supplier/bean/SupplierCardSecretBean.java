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
@Alias("supplierCardSecret")
public class SupplierCardSecretBean extends BaseBean {

	public static final int STATUS_WAIT = 0 ;
	public static final int STATUS_EXPORT = 1 ;
	public static final int STATUS_USED = 2 ;
	
	
	public SupplierCardSecretBean(){}
	
	private Integer id;
	private Integer supplier_id;
	private Integer product_id;
	private Integer supplier_product_id;
	private Integer customer_id;
	private String product_name;
	private String eff_time;
	private String dff_time;
	private Integer status;
	private String phone;
	private String secret_key;
	private Integer month_count;
	private String create_time;
	private String exp_time;
	private String used_time;
	private Integer batch_no;
	private Integer batch_no_count;
	private Integer random_num;
	private String order_code;
	
	
	public Integer getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(Integer batch_no) {
		this.batch_no = batch_no;
	}
	public Integer getBatch_no_count() {
		return batch_no_count;
	}
	public void setBatch_no_count(Integer batch_no_count) {
		this.batch_no_count = batch_no_count;
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
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public Integer getMonth_count() {
		return month_count;
	}
	public void setMonth_count(Integer month_count) {
		this.month_count = month_count;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getExp_time() {
		return exp_time;
	}
	public void setExp_time(String exp_time) {
		this.exp_time = exp_time;
	}
	public String getUsed_time() {
		return used_time;
	}
	public void setUsed_time(String used_time) {
		this.used_time = used_time;
	}
	public Integer getRandom_num() {
		return random_num;
	}
	public void setRandom_num(Integer random_num) {
		this.random_num = random_num;
	}
	
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public SupplierCardSecretBean(SupplierCardSecretBean copy){
		id = copy.getId();
		supplier_id = copy.getSupplier_id();
		product_id = copy.getProduct_id();
		supplier_product_id = copy.getSupplier_product_id();
		customer_id = copy.getCustomer_id();
		product_name = copy.getProduct_name();
		eff_time = copy.getEff_time();
		dff_time = copy.getDff_time();
		status = copy.getStatus();
		phone = copy.getPhone();
		secret_key = copy.getSecret_key();
		month_count = copy.getMonth_count();
		create_time = copy.getCreate_time();
		exp_time = copy.getExp_time();
		used_time = copy.getUsed_time();
		batch_no = copy.getBatch_no();
		batch_no_count = copy.getBatch_no_count();
		random_num = copy.getRandom_num();
		order_code = copy.getOrder_code();
	}
}
