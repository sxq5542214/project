package com.yd.business.comment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.comment.bean.CommentConfigBean;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.CommentInfoImgBean;
import com.yd.business.comment.bean.CommentUserRelationBean;
import com.yd.business.comment.dao.ICommentInfoDao;
import com.yd.util.StringUtil;
/**
 * 留言模块
 * @author BoBo
 *
 */
@Repository("commentInfoDao")
public class CommentInfoDaoImpl extends BaseDao implements ICommentInfoDao {
	private static final String NAMESPACE = "commentInfo.";
	
	@Override
	public void insertCommentInfo(CommentInfoBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertCommentInfo", bean);
	}
	
	@Override
	public void insertCommentInfoToBackTable(CommentInfoBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertCommentInfoToBackTable", bean);
	}

	@Override
	public List<CommentConfigBean> queryCommentConfigInfoByBean(CommentConfigBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentConfigInfoByBean", bean);
	}
	
	@Override
	public List<CommentConfigBean> queryCommentConfigInfoForShow(CommentConfigBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentConfigInfoForShow", bean, rowBound(bean));
	}
	
	@Override
	public int queryCommentConfigInfoCountForShow(CommentConfigBean bean) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCommentConfigInfoCountForShow", bean);
	}

	@Override
	public List<CommentInfoBean> queryCommentInfoByBeanForPage(CommentInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentInfoByBean", bean,rowBound(bean));
	}
	
	@Override
	public List<CommentInfoBean> queryCommentInfoByBean(CommentInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentInfoByBean", bean);
	}

	@Override
	public List<CommentInfoBean> queryCommentInfoByParentId(List<CommentInfoBean> list) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentInfoByParentId", list);
	}

	@Override
	public void updateCommentInfoAgreeCount(int id,int way) {
		if(way == CommentInfoBean.COMMENT_AGREECOUNT_ADD){
			sqlSessionTemplate.update(NAMESPACE+"updateCommentInfoAgreeCountAdd", id);
		}else if(way == CommentInfoBean.COMMENT_AGREECOUNT_MINUS){
			sqlSessionTemplate.update(NAMESPACE+"updateCommentInfoAgreeCountMinus", id);
		}
	}

	@Override
	public void insertCommentUserRelation(CommentUserRelationBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertCommentUserRelation", bean);
	}

	@Override
	public List<CommentUserRelationBean> queryCommentUserRelationByBean(CommentUserRelationBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentUserRelationByBean", bean);
	}

	@Override
	public void deleteCommentUserRelation(CommentUserRelationBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteCommentUserRelation", bean);

	}

	@Override
	public void updateCommentInfoBean(CommentInfoBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updateCommentInfoBean", bean);
	}

	@Override
	public List<CommentInfoBean> queryCommentInfoWithParent(CommentInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentInfoWithParent", bean,rowBound(bean));
	}

	@Override
	public void deleteCommentInfo(CommentInfoBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteCommentInfo", bean);
	}

	@Override
	public int queryCommentInfoCountByBean(CommentInfoBean bean) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCommentInfoCountByBean", bean);
	}

	@Override
	public void insertCommentConfig(CommentConfigBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertCommentConfig", bean);
	}

	@Override
	public int queryFailCommentInfoCountByBean(CommentInfoBean bean) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryFailCommentInfoCountByBean", bean);

	}

	@Override
	public List<CommentInfoBean> queryFailCommentInfoWithParent(CommentInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryFailCommentInfoWithParent", bean,rowBound(bean));

	}

	@Override
	public List<CommentInfoBean> queryFailCommentInfoByBean(CommentInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryFailCommentInfoByBean", bean,rowBound(bean));
	}

	@Override
	public void updateFailCommentInfoBean(CommentInfoBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updateFailCommentInfoBean", bean);
	}


	@Override
	public List<CommentInfoBean> queryFailCommentInfoByParentId(List<CommentInfoBean> list) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryFailCommentInfoByParentId", list);
	}
	

	@Override
	public void deleteFailCommentInfo(CommentInfoBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteFailCommentInfo", bean);
	}

	@Override
	public List<CommentInfoBean> queryUserAllCommentByBean(CommentInfoBean bean) {
		if(!StringUtil.isNull(bean)){
			return sqlSessionTemplate.selectList(NAMESPACE+"queryUserAllCommentByBean", bean,rowBound(bean));
		}else{
			return null;
		}
	}
	

	@Override
	public void updateCommentInfoReCount(int id,int way) {
		if(way == CommentInfoBean.COMMENT_AGREECOUNT_ADD){
			sqlSessionTemplate.update(NAMESPACE+"updateCommentInfoReCountAdd", id);
		}else if(way == CommentInfoBean.COMMENT_AGREECOUNT_MINUS){
			sqlSessionTemplate.update(NAMESPACE+"updateCommentInfoReCountMinus", id);
		}
	}

	@Override
	public void insertCommentInfoImgBean(CommentInfoImgBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertCommentInfoImgBean", bean);
	}

	@Override
	public List<CommentInfoImgBean> queryCommentImgInfoByInfoId(List<CommentInfoBean> parentId) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCommentImgInfoByInfoId", parentId);
	}

	@Override
	public void deleteCommentInfoImg(CommentInfoImgBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteCommentInfoImg", bean);
	}

	@Override
	public void updateCommentConfig(CommentConfigBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updateCommentConfig", bean);
	}

	@Override
	public void deleteCommentConfigBean(List<CommentConfigBean> list) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteCommentConfigBean", list);
	}
	
}
