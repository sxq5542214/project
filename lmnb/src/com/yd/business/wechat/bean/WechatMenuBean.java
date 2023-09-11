package com.yd.business.wechat.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 微信菜单
 * @author BoBo
 *
 */
@Alias("wechatMenuBean")
public class WechatMenuBean extends BaseBean {

	private Integer id;
	private String type;
	private String name;
	/**
	 * click等点击类型必须	
	 */
	private String menu_key;
	/**
	 * view类型必须
	 */
	private String url;
	/**
	 * 系统自带跳转链接的code
	 */
	private String url_code;
	/**
	 * media_id类型和view_limited类型必须
	 */
	private String media_id;
	
	private String media_code;
	
	private Integer parent_id;
	
	private String original_id;
	/**
	 * 最后一次同步时间
	 */
	private String last_sync_time;
	/**
	 * 修改时间
	 */
	private String modify_time;
	/**
	 * 二级菜单
	 */
	private List<WechatMenuBean> children_button;
	private List<WechatMenuBean> button;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getMenu_key() {
		return menu_key;
	}
	public void setMenu_key(String menu_key) {
		this.menu_key = menu_key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
//	public List<WechatMenuBean> getSub_button() {
//		return sub_button;
//	}
//	public void setSub_button(List<WechatMenuBean> sub_button) {
//		this.sub_button = sub_button;
//	}
	
	public String toString(){
		return this.getName();
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getOriginal_id() {
		return original_id;
	}
	public void setOriginal_id(String original_id) {
		this.original_id = original_id;
	}
	public List<WechatMenuBean> getChildren_button() {
		return children_button;
	}
	public void setChildren_button(List<WechatMenuBean> children_button) {
		this.children_button = children_button;
	}
	public String getLast_sync_time() {
		return last_sync_time;
	}
	public void setLast_sync_time(String last_sync_time) {
		this.last_sync_time = last_sync_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public List<WechatMenuBean> getButton() {
		return button;
	}
	public void setButton(List<WechatMenuBean> button) {
		this.button = button;
	}
	public String getMedia_code() {
		return media_code;
	}
	public void setMedia_code(String media_code) {
		this.media_code = media_code;
	}
	public String getUrl_code() {
		return url_code;
	}
	public void setUrl_code(String url_code) {
		this.url_code = url_code;
	}
	
}
