/**
 * 
 */
package com.yd.business.msgcenter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterActionTriggerConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.dao.IMsgCenterActionDao;

/**
 * @author ice
 *
 */
@Repository("msgCenterActionDao")
public class MsgCenterActionDaoImpl extends BaseDao implements IMsgCenterActionDao {
	private final static String NAMESPACE = "msgCenterAction.";
	
	@Override
	public MsgCenterActionDefineBean findActionDefineById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE + "findActionDefineById", id);
	}
	
	@Override
	public List<MsgCenterActionDefineBean> queryActionDefine(MsgCenterActionDefineBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActionDefine", bean);
	}
	
	@Override
	public List<MsgCenterUserActionBean> queryUserAction(MsgCenterUserActionBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryUserAction", bean);
	}

	@Override
	public void createUserAction(MsgCenterUserActionBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createUserAction",bean);
	}
	
	@Override
	public void updateUserAction(MsgCenterUserActionBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateUserAction", bean);
	}
	
	@Override
	public List<MsgCenterActionArticleRelationBean> queryMsgCenterActionArticleRelation(MsgCenterActionArticleRelationBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryMsgCenterActionArticleRelation", bean);
	}
	
	@Override
	public MsgCenterActionArticleRelationBean findMsgCenterActionArticleRelation(MsgCenterActionArticleRelationBean bean){
		List<MsgCenterActionArticleRelationBean> list = queryMsgCenterActionArticleRelation(bean);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<MsgCenterActionTriggerConditionBean> queryActionTriggerCondition(MsgCenterActionTriggerConditionBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryActionTriggerCondition", bean);
	}

	@Override
	public void createActionArticleRelation(MsgCenterActionArticleRelationBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "createActionArticleRelation", bean);
	}

	@Override
	public void updateActionArticleRelation(MsgCenterActionArticleRelationBean bean) {
		sqlSessionTemplate.update(NAMESPACE + "updateActionArticleRelation", bean);
	}
	
	
}
