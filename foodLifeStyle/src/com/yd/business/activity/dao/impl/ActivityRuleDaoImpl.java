package com.yd.business.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.dao.IActivityRuleDao;

/**
 * 活动限制的操作
 * @author BoBo
 *
 */
@Repository("activityRuleDao")
public class ActivityRuleDaoImpl extends BaseDao implements IActivityRuleDao {

	public static final String NAMESPACE = "activityRule.";
	
	@Override
	public ActivityRule updataActivityRule(ActivityRule bean) {
		sqlSessionTemplate.update(NAMESPACE+"updataActivityRule",bean);
		return bean;
	}

	@Override
	public ActivityRule insertIntoActivityRule(ActivityRule bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertIntoActivityRule",bean);
		return bean;
	}

	@Override
	public void deleteActivityRule(ActivityRule bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteActivityRule", bean);
	}

}
