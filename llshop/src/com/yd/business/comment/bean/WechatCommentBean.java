package com.yd.business.comment.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.util.StringUtil;

@Alias("wechatComment")
public class WechatCommentBean extends MsgCenterUserActionBean implements Comparable<WechatCommentBean> {

	public static final String REPLY_COMMENT_ALL = "ALL";
	public static final String REPLY_COMMENT_OWNER = "OWNER";
	
	private String original_name;
	private String head_img;
	private List<CommentInfoBean> replyBeanList;
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public List<CommentInfoBean> getReplyBeanList() {
		return replyBeanList;
	}
	public void setReplyBeanList(List<CommentInfoBean> replyBeanList) {
		this.replyBeanList = replyBeanList;
	}
	@Override
	public int compareTo(WechatCommentBean o) {
		return this.getCreate_time().compareTo(o.getCreate_time());
	}
}
