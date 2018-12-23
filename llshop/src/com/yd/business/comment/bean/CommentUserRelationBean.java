package com.yd.business.comment.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("commentUserRelation")
public class CommentUserRelationBean extends BaseBean {

	private Integer id;
	private Integer userid;
	private String openid;
	private Integer commentid;
	private String createtime;
	private String ip;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getCommentid() {
		return commentid;
	}
	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
