package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

/**
 * 活动与公众号的关联
 * @author BoBo
 *
 */
@Alias("activityOriginalRelation")
public class ActivityOriginalRelationBean {

	private Integer id;
	private String activity_code;
	private String original_id;
	private String original_name;
	private Integer status;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivity_code() {
		return activity_code;
	}
	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}
	public String getOriginal_id() {
		return original_id;
	}
	public void setOriginal_id(String original_id) {
		this.original_id = original_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	
	
	
}
