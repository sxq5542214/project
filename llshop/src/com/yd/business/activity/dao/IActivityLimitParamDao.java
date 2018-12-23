package com.yd.business.activity.dao;

import com.yd.business.activity.bean.ActicityLimitParamBean;

public interface IActivityLimitParamDao {

	/**
	 * 更新
	 * @param bean
	 * @return
	 */
	ActicityLimitParamBean updataActicityLimitParamBean(ActicityLimitParamBean bean);
	
	/**
	 * 新建
	 * @param bean
	 * @return
	 */
	ActicityLimitParamBean insertIntoActicityLimitParamBean(ActicityLimitParamBean bean);
	
	/**
	 * 删除
	 * @param bean
	 */
	void deleteActicityLimitParamBean(ActicityLimitParamBean bean);
}
