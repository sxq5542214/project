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
@Alias("supplierStoreBalanceCard")
public class SupplierStoreBalanceCardBean extends BaseBean {
	public static final int STATUS_UP = 1;
	public static final int STATUS_DOWN = 0;
	
	
	private Integer id;
	private String name;
	private Integer supplier_id;
	private Integer discount;
	private Integer card_price;
	private Integer init_balance;
	private Integer status;
	private String create_time;
	private String remark;
	private Integer useful_lift;
	private String except_supplier_product_ids;
	
	
	private String supplier_name;
	private String addBalance;
	private String balanceStr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddBalance() {
		return addBalance;
	}
	public void setAddBalance(String addBalance) {
		this.addBalance = addBalance;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getInit_balance() {
		return init_balance;
	}
	public void setInit_balance(Integer init_balance) {
		this.init_balance = init_balance;
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
	public Integer getCard_price() {
		return card_price;
	}
	public void setCard_price(Integer card_price) {
		this.card_price = card_price;
	}
	public String getBalanceStr() {
		return balanceStr;
	}
	public void setBalanceStr(String balanceStr) {
		this.balanceStr = balanceStr;
	}
	public String getExcept_supplier_product_ids() {
		return except_supplier_product_ids;
	}
	public void setExcept_supplier_product_ids(String except_supplier_product_ids) {
		this.except_supplier_product_ids = except_supplier_product_ids;
	}
	public Integer getUseful_lift() {
		return useful_lift;
	}
	public void setUseful_lift(Integer useful_lift) {
		this.useful_lift = useful_lift;
	}
}
