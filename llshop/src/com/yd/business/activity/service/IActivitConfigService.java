package com.yd.business.activity.service;

import java.util.List;

import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityConfigShowBean;
import com.yd.business.activity.bean.ActivityInstanceBean;
import com.yd.business.activity.bean.ActivityOriginalRelationBean;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.user.bean.UserWechatBean;

public interface IActivitConfigService {

	List<ActivityConfigBean> queryAllActivityConfig(Integer status);

	void updateActivityConfigStatus(int activity_config_id, int status);

	ActivityConfigBean findActivityConfigByCode(String code);

	List<ActivityConfigBean> returnActivityConfigByDate(List<ActivityConfigBean> activityConfigList,String dataType);
	
	List<ActivityConfigBean> queryActivityConfigByActivity(ActivityConfigBean bean);
	
	ActivityConfigBean findActivityConfigByActivityIdAndCode(Integer id,String code);
	
	int queryBySql(String querySQL);

	List<ActicityLimitParamBean> queryActivityLimitParam(ActicityLimitParamBean paramBean);
	
	List<ActivityConfigBean> queryActivityConfigByParam(Integer status,UserWechatBean user);
	
	/**
	 * 获取活动配置用于管理（修改，查看等。。。）
	 * @param bean
	 * @return
	 */
	ActivityConfigShowBean getActivityConfigForMrg(Object id);
	
	ActicityLimitParamBean commitActivityLimitParamBeanForJson(String json);
	
	/**
	 * 删除限制
	 * @param ids
	 */
	void deleteActivityLimitParamBeanByIds(String ids);
	
	/**
	 * 提交规则
	 * @param json
	 * @return
	 */
	ActivityRule commitActivityRuleForJson(String json);
	
	/**
	 * 删除规则
	 * @param ids
	 */
	void deleteActivityRuleByIds(String ids);
	
	/**
	 * 提交奖品
	 * @param json
	 * @return
	 */
	ActivityProductBean commitActivityPrizeForJson(String json);
	
	/**
	 * 删除奖品
	 * @param ids
	 */
	void deleteActivityPrizeByIds(String ids);
	/**
	 * 提交关联公众号
	 * @param json
	 * @return
	 */
	ActivityOriginalRelationBean commitActivityOriginalForJson(String json);
	
	/**
	 * 删除奖品
	 * @param ids
	 */
	void deleteActivityOriginalByIds(String ids);
	
	/**
	 * @param json
	 * @return
	 */
	ActivityConfigBean commitActivityConfigForJson(String json);
	
	/**
	 * 删除奖品
	 * @param ids
	 */
	void deleteActivityConfigByIds(String ids);
}
