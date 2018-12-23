package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 
 * @author BoBo
 *
 */
@Alias("activityRule")
public class ActivityRule extends BaseBean {
	private Integer id;
	private String description;
	private Integer activity_id;
	private Integer seq;
	private String create_time;
	private String modify_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
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
	
	
}
