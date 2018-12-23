package com.yd.business.comment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.WechatCommentBean;
import com.yd.business.comment.dao.IWechatCommentReplyDao;

/**
 * 公众号留言回复
 * @author BoBo
 *
 */
@Repository("wechatCommentReplyDao")
public class WechatCommentReplyDaoImpl extends BaseDao implements IWechatCommentReplyDao {
	private static final String NAMESPACE = "wechatCommentReply.";
	
	@Override
	public void insertReplyWechatCommentInfo(CommentInfoBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertReplyWechatCommentInfo", bean);
	}
	@Override
	public List<WechatCommentBean> queryMsgCenterUserComment(WechatCommentBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryMsgCenterUserComment", bean,rowBound(bean));
	}

	@Override
	public List<CommentInfoBean> queryWechatCommentInfoByParentId(List<WechatCommentBean> beanList) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWechatCommentInfoByParentId", beanList);
	}
	@Override
	public List<WechatCommentBean> queryNewMsgCenterUserComment(WechatCommentBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryNewMsgCenterUserComment", bean);
	}
}
