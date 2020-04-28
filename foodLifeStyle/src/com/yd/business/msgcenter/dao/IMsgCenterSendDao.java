package com.yd.business.msgcenter.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.msgcenter.bean.MsgCenterSendLogBean;
import com.yd.business.msgcenter.bean.MsgCenterWaitSendBean;

public interface IMsgCenterSendDao {

	void createSendLog(MsgCenterSendLogBean bean);

	void createWaitSend(MsgCenterWaitSendBean bean);

	List<Map<String, Object>> execSQL(String sql);

	List<MsgCenterWaitSendBean> queryWaitSendList(MsgCenterWaitSendBean bean);

	List<MsgCenterSendLogBean> querySendLogList(MsgCenterSendLogBean bean);

	void updateWaitSend(MsgCenterWaitSendBean bean);

}
