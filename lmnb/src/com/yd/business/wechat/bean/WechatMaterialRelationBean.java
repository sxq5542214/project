package com.yd.business.wechat.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("wechatMaterialRelationBean")
public class WechatMaterialRelationBean extends BaseBean {

	/**
	 * 新增
	 */
	public static int  ACTION_TYPE_ADD = 1;
	/**
	 * 修改
	 */
	public static int  ACTION_TYPE_UPDATE = 2;
	/**
	 * 删除  
	 */
	public static int  ACTION_TYPE_DEL = 0;
	
	
	private Integer id;
	/**
	 * 主素材ID
	 */
	private String main_media_id;
	/**
	 * 已分发素材id
	 */
	private String children_media_id;
	/**
	 * 分发的公众号id
	 */
	private String originalid;
	private String create_time;
	private String modify_time;
	/**
	 * 分发类型（最后一次）
	 */
	private Integer action_type;
	private Integer user_id;
	private Integer errorcode;
	private String original_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMain_media_id() {
		return main_media_id;
	}
	public void setMain_media_id(String main_media_id) {
		this.main_media_id = main_media_id;
	}
	public String getChildren_media_id() {
		return children_media_id;
	}
	public void setChildren_media_id(String children_media_id) {
		this.children_media_id = children_media_id;
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

	public Integer getAction_type() {
		return action_type;
	}
	public void setAction_type(Integer action_type) {
		this.action_type = action_type;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(Integer errorcode) {
		this.errorcode = errorcode;
	}
	public String getOriginalid() {
		return originalid;
	}
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	
	
}
