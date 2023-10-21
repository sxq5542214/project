/**
 * 
 */
package com.yd.business.user.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("userWechatFriend")
public class UserWechatFriendBean extends BaseBean {
	
	private Integer id;
	private Integer userid;
	private Integer friendUserid;
	private String openid;
	private String friendOpenid;
	private String createDate;
	private Integer fromSenceId;
	private Integer fromSenceType;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getFriendUserid() {
		return friendUserid;
	}
	public void setFriendUserid(Integer friendUserid) {
		this.friendUserid = friendUserid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getFriendOpenid() {
		return friendOpenid;
	}
	public void setFriendOpenid(String friendOpenid) {
		this.friendOpenid = friendOpenid;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getFromSenceId() {
		return fromSenceId;
	}
	public void setFromSenceId(Integer fromSenceId) {
		this.fromSenceId = fromSenceId;
	}
	public Integer getFromSenceType() {
		return fromSenceType;
	}
	public void setFromSenceType(Integer fromSenceType) {
		this.fromSenceType = fromSenceType;
	}
}
