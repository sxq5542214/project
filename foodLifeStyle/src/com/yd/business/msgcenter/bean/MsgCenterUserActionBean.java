/**
 * 
 */
package com.yd.business.msgcenter.bean;

import org.apache.ibatis.type.Alias;

import com.yd.business.user.bean.UserOperationLogBean;

/**
 * @author ice
 *
 */
@Alias("msgCenterUserAction")
public class MsgCenterUserActionBean extends UserOperationLogBean {
	public static final String ACTION_VALUE_DEFAULT = "default";
	public static final String ACTION_VALUE_NOTFIND = "notfind";

	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;

	private String assign_send_time;
	
	public String getAssign_send_time() {
		return assign_send_time;
	}
	public void setAssign_send_time(String assign_send_time) {
		this.assign_send_time = assign_send_time;
	}
	private String modify_time;
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	
}
