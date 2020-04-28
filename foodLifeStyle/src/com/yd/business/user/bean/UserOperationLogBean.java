/**
 * 
 */
package com.yd.business.user.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("userOperationLog")
public class UserOperationLogBean extends BaseBean {

	private Integer id;
	private Integer user_id;
	private String openid;
	private String nick_name;
	private String action_type;
	private String action_name;
	private String action_value;
	private String action_param;
	private String action_param_class;
	private String create_time;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public String getAction_value() {
		return action_value;
	}
	public void setAction_value(String action_value) {
		this.action_value = action_value;
	}
	public String getAction_param() {
		return action_param;
	}
	public void setAction_param(String action_param) {
		this.action_param = action_param;
	}
	public String getAction_param_class() {
		return action_param_class;
	}
	public void setAction_param_class(String action_param_class) {
		this.action_param_class = action_param_class;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
