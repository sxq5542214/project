package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("activityLimitParam")
public class ActicityLimitParamBean extends BaseBean {
	
	public static final int ACTIVITY_LIMIT_PARAM_STATUS_ACTIVE = 1;
	public static final int ACTIVITY_LIMIT_PARAM_STATUS_INACTIVE = 0;
	
	
	public static final int ACTIVITY_LIMIT_PARAM_TYPE_SHOW = 1;
	public static final int ACTIVITY_LIMIT_PARAM_TYPE_JOIN = 0;

	private int id;
	private int activity_id;//活动ID
	private String param_sql;//活动条件sql
	private int status;//活动条件状态
	private String remark;//活动条件提示
	private Integer type;//参数类型
	private String create_time;
	private String modify_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public String getParam_sql() {
		return param_sql;
	}
	public void setParam_sql(String param_sql) {
		this.param_sql = param_sql;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
