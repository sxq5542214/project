package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

@Alias("activity")
public class ActivityBean extends ActivityUserRelationBean {
	private String user_name;
	private Integer user_type;
	private Integer bonus_money;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getUser_type() {
		return user_type;
	}
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}
	public Integer getBonus_money() {
		return bonus_money;
	}
	public void setBonus_money(Integer bonus_money) {
		this.bonus_money = bonus_money;
	}
}
