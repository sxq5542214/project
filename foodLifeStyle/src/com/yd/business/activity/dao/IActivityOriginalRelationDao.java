package com.yd.business.activity.dao;

import com.yd.business.activity.bean.ActivityOriginalRelationBean;

public interface IActivityOriginalRelationDao {

	/**
	 * 更新
	 * @param bean
	 * @return
	 */
	ActivityOriginalRelationBean updataActivityOriginalRelation(ActivityOriginalRelationBean bean);
	
	/**
	 * 新建
	 * @param bean
	 * @return
	 */
	ActivityOriginalRelationBean insertIntoActivityOriginalRelation(ActivityOriginalRelationBean bean);
	
	/**
	 * 删除
	 * @param bean
	 */
	void deleteActivityOriginalRelation(ActivityOriginalRelationBean bean);
}
