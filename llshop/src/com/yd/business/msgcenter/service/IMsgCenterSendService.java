/**
 * 
 */
package com.yd.business.msgcenter.service;

import java.util.List;

import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterSendLogBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.bean.MsgCenterWaitSendBean;

/**
 * @author ice
 *
 */
public interface IMsgCenterSendService {

	void createMsgWaitSendInfo(MsgCenterActionArticleRelationBean relation, MsgCenterUserActionBean action,
			MsgCenterArticleBean article);

	void sendArticleToTargetObject(MsgCenterArticleBean article, MsgCenterUserActionBean action);

	List<MsgCenterWaitSendBean> queryWaitSendList(MsgCenterWaitSendBean bean);

	List<MsgCenterSendLogBean> querySendLogList(MsgCenterSendLogBean bean);

	void updateWaitSend(MsgCenterWaitSendBean bean);

}
