/**
 * 
 */
package com.yd.business.isp.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("nanJingShengShiInterface")
public class NanJingShengShiInterfaceBean extends ISPInterfaceBean {

	public static final String RESCODE_SUCCESS = "1";
	public static final String RESCODE_WAIT = "0";
	private String table_name = "ll_interface_nanjingshengshi_log";
	
	
	private String account;
	private String product_name;
	
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
}
