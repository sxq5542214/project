/**
 * 
 */
package com.yd.business.msgcenter.dao;

import java.util.List;

import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterActionTriggerConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;

/**
 * @author ice
 *
 */
public interface IMsgCenterActionDao {

	MsgCenterActionDefineBean findActionDefineById(int id);

	List<MsgCenterActionDefineBean> queryActionDefine(MsgCenterActionDefineBean bean);

	List<MsgCenterUserActionBean> queryUserAction(MsgCenterUserActionBean bean);

	void createUserAction(MsgCenterUserActionBean bean);

	void updateUserAction(MsgCenterUserActionBean bean);

	List<MsgCenterActionArticleRelationBean> queryMsgCenterActionArticleRelation(MsgCenterActionArticleRelationBean bean);

	MsgCenterActionArticleRelationBean findMsgCenterActionArticleRelation(MsgCenterActionArticleRelationBean bean);

	List<MsgCenterActionTriggerConditionBean> queryActionTriggerCondition(MsgCenterActionTriggerConditionBean bean);

	void createActionArticleRelation(MsgCenterActionArticleRelationBean bean);

	void updateActionArticleRelation(MsgCenterActionArticleRelationBean bean);

}
