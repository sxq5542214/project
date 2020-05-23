/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("supplierStoreBalanceCardRecord")
public class SupplierStoreBalanceCardRecordBean extends SupplierStoreBalanceCardBean {
	private Integer card_id;
	private String openid;
	private Integer user_id;
	private Integer balance;
	private Integer status;
	private String create_time;
	private String eff_time;
	private String dff_time;
	
	
	public Integer getCard_id() {
		return card_id;
	}
	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
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
