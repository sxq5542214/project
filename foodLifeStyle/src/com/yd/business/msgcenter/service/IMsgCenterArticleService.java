package com.yd.business.msgcenter.service;

import java.util.List;

import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.bean.MsgCenterUserSubscribeBean;

public interface IMsgCenterArticleService {

	List<MsgCenterArticleBean> queryArticle(MsgCenterArticleBean bean);

	MsgCenterArticleBean findArticle(MsgCenterArticleBean bean);

	MsgCenterArticleBean findArticleById(int id);

	List<MsgCenterArticleBean> findArticleByType(int article_type_id, MsgCenterUserActionBean action);

	List<MsgCenterArticleTypeBean> queryArticleType(MsgCenterArticleTypeBean bean);

	void saveOrUpdateArticle(MsgCenterArticleBean bean);

	List<MsgCenterArticleBean> queryArticle(MsgCenterArticleConditionBean bean);

	MsgCenterArticleBean findArticleWithDeliveryInf(int id);

	List<MsgCenterUserSubscribeBean> queryUserSubscribeInfo(MsgCenterUserSubscribeBean bean);

	void createOrUpdateUserSubscribeInfo(Integer user_id, String openid, Integer status, String code, String type,
			Integer article_id);
}
