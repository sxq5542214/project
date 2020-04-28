package com.yd.business.supplier.bean;

import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
/**
 * 商户信息表
 * @author ZDL
 *
 */
@Alias("supplier")
public class SupplierBean extends BaseBean {
	public static int ISSALE_TRUE = 1;
	public static int ISSALE_FALSE = 0;
	
	public static int STATUS_N = 0;
	public static int STATUS_Y = 1;
	
	public static int PLATFROM_SUPPLIER_ID = 1;

	private Integer id;
	private Integer customer_id;
	private Integer parent_customer_id;
	private Integer level;
	private String parent_name;
	private String name;
	private Integer status;
	private Integer type;
	private Integer issale;
	private String address;
	private Integer balance;
	private Integer points;
	private Integer deposit_money;
	private String create_time;
	private String contacts_name;
	private String contacts_phone;
	private String remark;
	private String discount;
	private String modify_time;
	private Integer expired_day;
	private String discount_name;
	private String disinfo;
	
	public String getDisinfo() {
		return disinfo;
	}
	public void setDisinfo(String disinfo) {
		this.disinfo = disinfo;
	}
	public Integer getExpired_day() {
		return expired_day;
	}
	public void setExpired_day(Integer expired_day) {
		this.expired_day = expired_day;
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
	public Integer getParent_customer_id() {
		return parent_customer_id;
	}
	public void setParent_customer_id(Integer parent_customer_id) {
		this.parent_customer_id = parent_customer_id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIssale() {
		return issale;
	}
	public void setIssale(Integer issale) {
		this.issale = issale;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getDeposit_money() {
		return deposit_money;
	}
	public void setDeposit_money(Integer deposit_money) {
		this.deposit_money = deposit_money;
	}
	public String getDiscount_name() {
		return discount_name;
	}
	public void setDiscount_name(String discount_name) {
		this.discount_name = discount_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getContacts_name() {
		return contacts_name;
	}
	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}
	public String getContacts_phone() {
		return contacts_phone;
	}
	public void setContacts_phone(String contacts_phone) {
		this.contacts_phone = contacts_phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	
}
