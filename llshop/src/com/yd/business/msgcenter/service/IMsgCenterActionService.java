/**
 * 
 */
package com.yd.business.msgcenter.service;

import java.util.List;

import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterActionTriggerConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;

/**
 * @author ice
 *
 */
public interface IMsgCenterActionService {

	void saveAndHandleUserAction(String openid, String action_type, String action_value, Object action_param);

	MsgCenterActionDefineBean queryActionDefineByActionType(String action_type);

	List<MsgCenterActionTriggerConditionBean> queryActionTriggerCondition(MsgCenterActionTriggerConditionBean bean);

	List<MsgCenterUserActionBean> queryUserAction(MsgCenterUserActionBean bean);

	MsgCenterUserActionBean findUserActionById(int id);

	void saveAndHandleUserAction(String openid, String action_type, String action_value, Object action_param,
			String assign_send_time);

	List<MsgCenterActionArticleRelationBean> queryUserActionArticleRelation(MsgCenterActionArticleRelationBean bean);

	List<MsgCenterActionDefineBean> queryActionDefine(MsgCenterActionDefineBean bean);

	void createOrUpdateActionArticleRelation(MsgCenterActionArticleRelationBean bean);

}
