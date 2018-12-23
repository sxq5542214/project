/**
 * 
 */
package com.yd.business.msgcenter.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("msgCenterWaitSend")
public class MsgCenterWaitSendBean extends BaseBean {
	/**
	 * 待发送
	 */
	public static final int STATUS_WAIT = 0;
	/**
	 * 已发送
	 */
	public static final int STATUS_SEND = 1;
	/**
	 * 已发送
	 */
	public static final int STATUS_FAILD = -1;
	/**
	 * 已重复发送
	 */
	public static final int STATUS_ALREADY_SEND = 2;
	/**
	 * 已读
	 */
	public static final int STATUS_READ = 3;
	
	private Integer id;
	private Integer article_id;
	private Integer user_action_id;
	private String action_type;
	private String action_name;
	private Integer status;
	private String create_time;
	private String modify_time;
	private String exec_time;
	private Integer user_id;
	private String openid;
	private String nick_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}
	public Integer getUser_action_id() {
		return user_action_id;
	}
	public void setUser_action_id(Integer user_action_id) {
		this.user_action_id = user_action_id;
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
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getExec_time() {
		return exec_time;
	}
	public void setExec_time(String exec_time) {
		this.exec_time = exec_time;
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
	
}
