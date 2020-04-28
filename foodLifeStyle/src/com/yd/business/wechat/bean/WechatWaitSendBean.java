/**
 * 
 */
package com.yd.business.wechat.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("wechatWaitSend")
public class WechatWaitSendBean extends BaseBean {
	
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_SEND = 1;
	public static final int STATUS_FAILD = -1;
	
	private Integer id;
	private String openid;
	private String nick_name;
	private String text_msg;
	private String media_id;
	private String news_title;
	private String news_description;
	private String news_url;
	private String news_picurl;
	private Integer status;
	private String create_time;
	private String modify_time;
	private String send_time;
	
	private String now_time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getText_msg() {
		return text_msg;
	}
	public void setText_msg(String text_msg) {
		this.text_msg = text_msg;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_description() {
		return news_description;
	}
	public void setNews_description(String news_description) {
		this.news_description = news_description;
	}
	public String getNews_url() {
		return news_url;
	}
	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}
	public String getNews_picurl() {
		return news_picurl;
	}
	public void setNews_picurl(String news_picurl) {
		this.news_picurl = news_picurl;
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
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getNow_time() {
		return now_time;
	}
	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}
}
