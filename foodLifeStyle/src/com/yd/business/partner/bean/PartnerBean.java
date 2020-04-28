/**
 * 
 */
package com.yd.business.partner.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("partner")
public class PartnerBean extends BaseBean {

	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	private Integer id;
	private String partner_code;
	private Integer customer_id;
	private String customer_name;
	private String secret_key;
	private String notify_url;
	private String create_time;
	private String modify_time;
	private String sign_type;
	private Integer status;
	private String remark;
	private Integer balance;
	private Integer deposit_money;
	private String allow_ips;
	private String eff_date;
	private String dff_date;
	private Integer day_limit;
	private Integer month_limit;
	private Integer last_month_money;
	
	public Integer getDay_limit() {
		return day_limit;
	}
	public void setDay_limit(Integer day_limit) {
		this.day_limit = day_limit;
	}
	public Integer getMonth_limit() {
		return month_limit;
	}
	public void setMonth_limit(Integer month_limit) {
		this.month_limit = month_limit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPartner_code() {
		return partner_code;
	}
	public void setPartner_code(String partner_code) {
		this.partner_code = partner_code;
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
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
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
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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
	public String getAllow_ips() {
		return allow_ips;
	}
	public void setAllow_ips(String allow_ips) {
		this.allow_ips = allow_ips;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getDff_date() {
		return dff_date;
	}
	public void setDff_date(String dff_date) {
		this.dff_date = dff_date;
	}
	public Integer getLast_month_money() {
		return last_month_money;
	}
	public void setLast_month_money(Integer last_month_money) {
		this.last_month_money = last_month_money;
	}
	
	
	
}
