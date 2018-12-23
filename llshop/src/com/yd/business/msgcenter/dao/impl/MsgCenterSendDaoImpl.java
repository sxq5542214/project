/**
 * 
 */
package com.yd.business.msgcenter.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.msgcenter.bean.MsgCenterSendLogBean;
import com.yd.business.msgcenter.bean.MsgCenterWaitSendBean;
import com.yd.business.msgcenter.dao.IMsgCenterSendDao;

/**
 * @author ice
 *
 */
@Repository("msgCenterSendDao")
public class MsgCenterSendDaoImpl extends BaseDao implements IMsgCenterSendDao {
	private static final String NAMESPACE = "msgCenterSend.";
	
	@Override
	public void createSendLog(MsgCenterSendLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createSendLog", bean);
	}
	
	@Override
	public void createWaitSend(MsgCenterWaitSendBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createWaitSend", bean);
	}
	
	@Override
	public List<Map<String,Object>> execSQL(String sql){
		return sqlSessionTemplate.selectList(NAMESPACE+"execSQL", sql);
	}

	@Override
	public List<MsgCenterWaitSendBean> queryWaitSendList(MsgCenterWaitSendBean bean) {
		
		return sqlSessionTemplate.selectList(NAMESPACE + "queryWaitSendList", bean);
	}

	@Override
	public List<MsgCenterSendLogBean> querySendLogList(MsgCenterSendLogBean bean) {
		
		return sqlSessionTemplate.selectList(NAMESPACE + "querySendLogList", bean);
	}

	@Override
	public void updateWaitSend(MsgCenterWaitSendBean bean) {
		sqlSessionTemplate.update(NAMESPACE +"updateWaitSend", bean);
	}
	
	
	
	
}
