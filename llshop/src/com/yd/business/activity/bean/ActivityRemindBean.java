package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 
 * @author BoBo
 * 用户设置提醒的关系类
 */
@Alias("activityRemind")
public class ActivityRemindBean extends BaseBean {

	private Integer id;
	private Integer user_id;
	private Integer instance_id;
	private String openid;
	private String instance_name;
	private String remind_time;
	private String create_time;
	private String remark;
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
	public Integer getInstance_id() {
		return instance_id;
	}
	public void setInstance_id(Integer instance_id) {
		this.instance_id = instance_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getInstance_name() {
		return instance_name;
	}
	public void setInstance_name(String instance_name) {
		this.instance_name = instance_name;
	}
	public String getRemind_time() {
		return remind_time;
	}
	public void setRemind_time(String remind_time) {
		this.remind_time = remind_time;
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
	
	
	
}
