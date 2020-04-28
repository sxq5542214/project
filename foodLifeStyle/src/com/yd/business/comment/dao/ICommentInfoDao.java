package com.yd.business.comment.dao;

import java.util.List;

import com.yd.business.comment.bean.CommentConfigBean;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.CommentInfoImgBean;
import com.yd.business.comment.bean.CommentUserRelationBean;

public interface ICommentInfoDao {

	public void insertCommentInfo(CommentInfoBean bean);
	
	public void insertCommentConfig(CommentConfigBean bean);
	
	public void updateCommentConfig(CommentConfigBean bean);
	
	public void insertCommentInfoToBackTable(CommentInfoBean bean);
	
	public List<CommentConfigBean> queryCommentConfigInfoByBean(CommentConfigBean bean);
	
	public List<CommentInfoBean> queryCommentInfoByBeanForPage(CommentInfoBean bean);
	
	public List<CommentInfoBean> queryCommentInfoByBean(CommentInfoBean bean);
	
	public List<CommentInfoBean> queryFailCommentInfoByBean(CommentInfoBean bean);
	
	public int queryCommentInfoCountByBean(CommentInfoBean bean);
	
	public List<CommentInfoBean> queryCommentInfoWithParent(CommentInfoBean bean);
	
	public int queryFailCommentInfoCountByBean(CommentInfoBean bean);
	
	public List<CommentInfoBean> queryFailCommentInfoWithParent(CommentInfoBean bean);
		
	public List<CommentInfoBean> queryCommentInfoByParentId(List<CommentInfoBean> parentId);
	
	public List<CommentInfoBean> queryFailCommentInfoByParentId(List<CommentInfoBean> parentId);
	
	public void updateCommentInfoAgreeCount(int id,int way);
	
	public void insertCommentUserRelation(CommentUserRelationBean bean);
	
	public List<CommentUserRelationBean> queryCommentUserRelationByBean(CommentUserRelationBean bean);
	
	public void deleteCommentUserRelation(CommentUserRelationBean bean);
	
	public void updateCommentInfoBean(CommentInfoBean bean);
	
	public void updateFailCommentInfoBean(CommentInfoBean bean);
	
	public void deleteCommentInfo(CommentInfoBean bean);
	
	public void deleteFailCommentInfo(CommentInfoBean bean);
	
	public List<CommentInfoBean> queryUserAllCommentByBean(CommentInfoBean Bean);

	public void updateCommentInfoReCount(int id,int way);
	
	public void insertCommentInfoImgBean(CommentInfoImgBean bean);
	
	public List<CommentInfoImgBean> queryCommentImgInfoByInfoId(List<CommentInfoBean> parentId);
	
	public void deleteCommentInfoImg(CommentInfoImgBean bean);
	
	/**
	 * 用于显示的查询
	 * @param bean
	 * @return
	 */
	public List<CommentConfigBean> queryCommentConfigInfoForShow(CommentConfigBean bean);
	public int queryCommentConfigInfoCountForShow(CommentConfigBean bean);
	
	public void deleteCommentConfigBean(List<CommentConfigBean> list);
}
