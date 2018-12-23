package com.yd.business.comment.service;

import java.util.List;
import java.util.Map;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.comment.bean.CommentConfigBean;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.CommentUserRelationBean;
import com.yd.business.user.bean.UserWechatBean;

public interface ICommentInfoService {

	/**
	 * 初始化留言配置信息
	 * @param bean
	 */
	public void initCommentConfig(String code,String start_time,String end_time,String link_url,String name);
	/**
	 * 新增或者修改留言配置信息
	 * @param bean  json串
	 */
	public CommentConfigBean createOrUpdateCommentConfig(String bean);
	
	/**
	 * 新增留言信息
	 * @param bean
	 */
	public void insertCommentInfo(CommentInfoBean bean);
	
	/**
	 * 新增留言信息
	 * @param bean
	 */
	public CommentInfoBean insertCommentInfo(Object img_arr,String ip,String openid,String msgtext,String conf_id,String conf_code,String parentid,String replyid,boolean isadmin);
	
	/**
	 * 查询留言板配置信息
	 * @return
	 */
	public CommentConfigBean findCommentConfigInfoById(int id);
	
	/**
	 * 查询留言信息
	 */
	public List<CommentInfoBean> showCommentInfoByConfCode(String code,String openid,String ip,String id,String nowpage,String idName);
	
	/**
	 * 处理用户点赞事件
	 * @param openid
	 * @param commentid
	 * @return
	 */
	public CommentInfoBean dealUserAgreeCommentInfo(String ip,String openid,String commentid,String action);
	
	/**
	 * 获取留言信息
	 * @param bean
	 * @return
	 */
	public PageinationData queryCommentInfoByBean(CommentInfoBean bean) throws Exception;
	
	/**
	 * 获取留言信息
	 * @param bean
	 * @return
	 */
	public CommentInfoBean findCommentInfoWithChildren(int id);
	
	/**
	 * 获取留言信息
	 * @param bean
	 * @return
	 */
	public CommentInfoBean findCommentInfoById(Integer id);
	
	/**
	 * 审核留言
	 * @param bean
	 * @return
	 */
	public List<CommentInfoBean> checkCommentInfoByAction(String action,String id);
	
	/**
	 * 删除留言
	 * @param bean
	 */
	public void deleteCommentInfo(String id);

	/**
	 * 删除点赞记录
	 * @param reBean
	 */
	public void deleteCommentInfoVotedInfo(String openid);
	
	/**
	 * 查询留言配置表信息
	 * @return
	 */
	public List<CommentConfigBean> queryCommentConfigByBean(CommentConfigBean bean);
	
	/**
	 * 删除图片
	 * @param bean
	 */
	public void deleteCommentInfoImg(String id);
	
	/**
	 * 用于后台展示的配置查询方法
	 * @return
	 */
	public PageinationData getCommentConfigListForShow(Map<String,String[]> requestMap,CommentConfigBean configBean);
	
	public List<CommentConfigBean> deleteCommentConfigBean(String ids);
}
