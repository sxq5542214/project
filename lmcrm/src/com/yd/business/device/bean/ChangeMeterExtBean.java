/**
 * 
 */
package com.yd.business.device.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("changeMeterExt")
public class ChangeMeterExtBean extends ChangeMeterBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7458528671441710025L;
	private Long user_no;
	private String user_name;
	private String user_phone;
	private String user_address;
	private String operator_name;
	public Long getUser_no() {
		return user_no;
	}
	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	
}
