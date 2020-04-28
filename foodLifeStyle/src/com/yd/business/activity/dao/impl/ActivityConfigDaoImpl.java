package com.yd.business.activity.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.dao.IActivityConfigDao;

@Repository("activityConfigDao")
public class ActivityConfigDaoImpl extends BaseDao implements IActivityConfigDao {
	public final static String NAMESPACE = "activityConfig.";
	
	@Override
	public List<ActivityConfigBean> queryAllActivityConfig(Integer status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAllActivityConfig",map);
	}
	
	@Override
	public void createActivityConfig(ActivityConfigBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createActivityConfig",bean);
	}
	
	@Override
	public void updateActivityConfigStatus(int activity_id,int status ){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activity_id", activity_id);
		map.put("status", status);
		sqlSessionTemplate.update(NAMESPACE+"updateActivityConfigStatus",map);
	}
	
	@Override
	public ActivityConfigBean queryActivityConfigByReferenceId(int referenceId){
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryActivityConfigByReferenceId", referenceId);
	}

	@Override
	public ActivityConfigBean findActivityConfigByCode(String code) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"findActivityConfigByCode", code);
	}

	@Override
	public int queryBySql(String querySql) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryBySQL", querySql);
	}

	@Override
	public List<ActicityLimitParamBean> queryActivityLimitParam(ActicityLimitParamBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"activityLimitParam", bean);
	}

	@Override
	public List<ActivityConfigBean> queryActivityConfigByActivity(ActivityConfigBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryActivityConfigByActivity", bean);
	}

	@Override
	public void updateActivityConfig(ActivityConfigBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updateActivityConfig", bean);
		
	}

	@Override
	public void deleteActivityConfig(ActivityConfigBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteActivityConfig", bean);		
	}
	
	
	
}
