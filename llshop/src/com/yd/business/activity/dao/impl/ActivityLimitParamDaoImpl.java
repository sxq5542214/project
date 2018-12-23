package com.yd.business.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.dao.IActivityLimitParamDao;

/**
 * 活动限制的操作
 * @author BoBo
 *
 */
@Repository("activityLimitParamDao")
public class ActivityLimitParamDaoImpl extends BaseDao implements IActivityLimitParamDao {

	public static final String NAMESPACE = "activityLimitParam.";
	
	@Override
	public ActicityLimitParamBean updataActicityLimitParamBean(ActicityLimitParamBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updataActicityLimitParamBean",bean);
		return bean;
	}

	@Override
	public ActicityLimitParamBean insertIntoActicityLimitParamBean(ActicityLimitParamBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertIntoActicityLimitParamBean",bean);
		return bean;
	}

	@Override
	public void deleteActicityLimitParamBean(ActicityLimitParamBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteActicityLimitParamBean", bean);
	}

}
