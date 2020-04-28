package com.yd.business.user.bean;

public class UserWechatExtendBean extends UserWechatBean {
	
	public static final int SUBSCRIBE_YES = 1;
	public static final int SUBSCRIBE_NO = 0;
	

	//非数据库字段
	private Integer subscribe;

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	
	
}
