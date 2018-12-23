/**
 * 
 */
package com.yd.business.msgcenter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean;
import com.yd.business.msgcenter.bean.MsgCenterUserSubscribeBean;
import com.yd.business.msgcenter.dao.IMsgCenterArticleDao;

/**
 * @author ice
 *
 */
@Repository("msgCenterArticleDao")
public class MsgCenterArticleDaoImpl extends BaseDao implements IMsgCenterArticleDao {
	private static final String NAMESPACE = "msgCenterArticle.";
	
	@Override
	public List<MsgCenterArticleBean> queryArticle(MsgCenterArticleBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryArticle", bean);
	}
	
	@Override
	public List<MsgCenterArticleBean> queryArticle(MsgCenterArticleConditionBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryArticleByCondition", bean,rowBound(bean));
	}
	
	@Override
	public MsgCenterArticleBean findArticle(MsgCenterArticleBean bean){
		List<MsgCenterArticleBean> list = queryArticle(bean);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public MsgCenterArticleBean findArticleById(int id){
		MsgCenterArticleBean bean = new MsgCenterArticleBean();
		bean.setId(id);
		return findArticle(bean);
	}
	
	@Override
	public void updateArticle(MsgCenterArticleBean bean){
		sqlSessionTemplate.update(NAMESPACE+"updateArticle", bean);
	}
	@Override
	public void createArticle(MsgCenterArticleBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createArticle",bean);
	}
	@Override
	public List<MsgCenterArticleTypeBean> queryArticleType(MsgCenterArticleTypeBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryArticleType", bean);
		
	}
	
	@Override
	public MsgCenterArticleTypeBean findArticleType(MsgCenterArticleTypeBean bean){
		List<MsgCenterArticleTypeBean> list = queryArticleType(bean);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void createUserSubscribeInfo(MsgCenterUserSubscribeBean bean) {
		sqlSessionTemplate.insert(NAMESPACE +"createUserSubscribeInfo", bean);
	}
	
	@Override
	public void updateUserSubscribeInfo(MsgCenterUserSubscribeBean bean) {
		sqlSessionTemplate.update(NAMESPACE +"updateUserSubscribeInfo", bean);
	}
	
	@Override
	public List<MsgCenterUserSubscribeBean> queryUserSubscribeInfo(MsgCenterUserSubscribeBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE +"queryUserSubscribeInfo", bean);
	}
	
}
