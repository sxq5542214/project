/**
 * 
 */
package com.yd.business.isp.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("zhuoweiInterface")
public class ZhuoweiInterfaceBean extends ISPInterfaceBean {
	
	public static final String RESCODE_WAIT = "0";
	private String table_name = "ll_interface_zhuowei_log";
	
	private Integer id;
	private String ec_code;
	private String order_code;
	private String phone;
	private String create_time;
	private String modify_time;
	private String action;
	private String isp_order_code;
	private String cost_money;
	
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getEc_code() {
		return ec_code;
	}
	public void setEc_code(String ec_code) {
		this.ec_code = ec_code;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIsp_order_code() {
		return isp_order_code;
	}
	public void setIsp_order_code(String isp_order_code) {
		this.isp_order_code = isp_order_code;
	}
	public String getCost_money() {
		return cost_money;
	}
	public void setCost_money(String cost_money) {
		this.cost_money = cost_money;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
