/**
 * 
 */
package com.yd.business.msgcenter.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("msgCenterSendLog")
public class MsgCenterSendLogBean extends MsgCenterWaitSendBean {
	
	private String start_time;
	private String end_time;
	
	
	private List<String> action_type_list;

	public List<String> getAction_type_list() {
		return action_type_list;
	}

	public void setAction_type_list(List<String> action_type_list) {
		this.action_type_list = action_type_list;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	
}
