package com.yd.business.activity.dao;

import com.yd.business.activity.bean.ActivityRule;

public interface IActivityRuleDao {

	/**
	 * 更新
	 * @param bean
	 * @return
	 */
	ActivityRule updataActivityRule(ActivityRule bean);
	
	/**
	 * 新建
	 * @param bean
	 * @return
	 */
	ActivityRule insertIntoActivityRule(ActivityRule bean);
	
	/**
	 * 删除
	 * @param bean
	 */
	void deleteActivityRule(ActivityRule bean);
}
