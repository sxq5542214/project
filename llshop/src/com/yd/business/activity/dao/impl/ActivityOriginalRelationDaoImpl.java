package com.yd.business.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.activity.bean.ActivityOriginalRelationBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.dao.IActivityOriginalRelationDao;
import com.yd.business.activity.dao.IActivityRuleDao;

/**
 * 活动限制的操作
 * @author BoBo
 *
 */
@Repository("activityOringinalRelationDao")
public class ActivityOriginalRelationDaoImpl extends BaseDao implements IActivityOriginalRelationDao {

	public static final String NAMESPACE = "activityOringinalRelation.";
	
	@Override
	public ActivityOriginalRelationBean updataActivityOriginalRelation(ActivityOriginalRelationBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updataActivityOriginalRelation",bean);
		return bean;
	}

	@Override
	public ActivityOriginalRelationBean insertIntoActivityOriginalRelation(ActivityOriginalRelationBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertIntoActivityOriginalRelation",bean);
		return bean;
	}

	@Override
	public void deleteActivityOriginalRelation(ActivityOriginalRelationBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteActivityOriginalRelation", bean);
	}

}
