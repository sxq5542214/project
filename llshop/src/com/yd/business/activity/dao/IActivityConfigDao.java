package com.yd.business.activity.dao;

import java.util.List;

import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityConfigBean;

public interface IActivityConfigDao {

	List<ActivityConfigBean> queryAllActivityConfig(Integer status);

	void createActivityConfig(ActivityConfigBean bean);

	void updateActivityConfigStatus(int activity_id, int status);

	ActivityConfigBean queryActivityConfigByReferenceId(int referenceId);

	ActivityConfigBean findActivityConfigByCode(String code);

	int queryBySql(String querySql);
	
	List<ActicityLimitParamBean> queryActivityLimitParam(ActicityLimitParamBean bean);
	
	List<ActivityConfigBean> queryActivityConfigByActivity(ActivityConfigBean bean);
	
	void updateActivityConfig(ActivityConfigBean bean);
	void deleteActivityConfig(ActivityConfigBean bean);
}
