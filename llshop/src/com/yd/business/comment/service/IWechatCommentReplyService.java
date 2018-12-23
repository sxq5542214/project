package com.yd.business.comment.service;

import java.util.List;
import java.util.Map;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.WechatCommentBean;
import com.yd.business.wechat.bean.TextBean;


public interface IWechatCommentReplyService {

	public PageinationData queryMsgCenterUserComment(String nowpage,String openid);
	
	public CommentInfoBean replyMsgToWeChatUser(String mag,String commentid,String adminopenid);
	/**
	 * 查询所有的回复
	 * @param beanList
	 * @return
	 */
	public List<CommentInfoBean> queryWechatCommentInfoByParentId(List<WechatCommentBean> beanList);

	/**
	 * 新增留言信息
	 * @param bean
	 */
	public void insertCommentInfo(CommentInfoBean bean);
	
	/**
	 * 获取新消息数量
	 * @param bean
	 */
	public List<WechatCommentBean> getNewMsgCount(String commentid);
	
	/**
	 * 根据用户留言，获取该用户的近期所有留言信息
	 * @param commentid
	 * @return
	 */
	public List<WechatCommentBean> getWechatUserCommentListByOpenid(String openid,String adminopenid);
	
	/**
	 * 根据ID得到实例
	 * @param bean
	 * @return
	 */
	public WechatCommentBean findWechatUserCommentByBean(WechatCommentBean bean);
	
	/**
	 * 根据openid判断是否展示用户留言列表
	 * @param openid
	 * @return
	 */
	public boolean isShowCommentList(String openid);
}
