/**
 * 
 */
package com.yd.business.msgcenter.bean;

/**
 * @author ice
 *
 */

public class MsgCenterArticleConditionBean extends MsgCenterArticleBean {
	private Boolean not_repeat;
	private Integer user_id;
	
	private String now_time;
	private String send_time;
	
	public Boolean getNot_repeat() {
		return not_repeat;
	}

	public void setNot_repeat(Boolean not_repeat) {
		this.not_repeat = not_repeat;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getNow_time() {
		return now_time;
	}

	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
}
