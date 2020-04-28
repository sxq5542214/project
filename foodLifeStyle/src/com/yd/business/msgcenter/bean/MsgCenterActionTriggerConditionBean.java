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
@Alias("msgCenterActionTriggerCondition")
public class MsgCenterActionTriggerConditionBean extends BaseBean {
	private Integer id;
	private String action_type;
	private String action_name;
	private String not_trigger_action;
	private String not_trigger_action_name;
	private Integer status;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getNot_trigger_action() {
		return not_trigger_action;
	}
	public void setNot_trigger_action(String not_trigger_action) {
		this.not_trigger_action = not_trigger_action;
	}
	public String getNot_trigger_action_name() {
		return not_trigger_action_name;
	}
	public void setNot_trigger_action_name(String not_trigger_action_name) {
		this.not_trigger_action_name = not_trigger_action_name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
