/**
 * 
 */
package com.yd.business.msgcenter.dao;

import java.util.List;

import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean;
import com.yd.business.msgcenter.bean.MsgCenterUserSubscribeBean;

/**
 * @author ice
 *
 */
public interface IMsgCenterArticleDao {

	List<MsgCenterArticleBean> queryArticle(MsgCenterArticleBean bean);

	MsgCenterArticleBean findArticle(MsgCenterArticleBean bean);

	void updateArticle(MsgCenterArticleBean bean);

	void createArticle(MsgCenterArticleBean bean);

	List<MsgCenterArticleTypeBean> queryArticleType(MsgCenterArticleTypeBean bean);

	MsgCenterArticleTypeBean findArticleType(MsgCenterArticleTypeBean bean);

	MsgCenterArticleBean findArticleById(int id);

	List<MsgCenterArticleBean> queryArticle(MsgCenterArticleConditionBean bean);

	void createUserSubscribeInfo(MsgCenterUserSubscribeBean bean);

	void updateUserSubscribeInfo(MsgCenterUserSubscribeBean bean);

	List<MsgCenterUserSubscribeBean> queryUserSubscribeInfo(MsgCenterUserSubscribeBean bean);

}
