package com.yd.business.comment.dao;

import java.util.List;

import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.WechatCommentBean;

public interface IWechatCommentReplyDao {

	public List<WechatCommentBean> queryMsgCenterUserComment(WechatCommentBean bean);

	public List<CommentInfoBean> queryWechatCommentInfoByParentId(List<WechatCommentBean> beanList);
	
	public void insertReplyWechatCommentInfo(CommentInfoBean bean);
	
	public List<WechatCommentBean> queryNewMsgCenterUserComment(WechatCommentBean bean);

}
