package com.yd.business.activity.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityPrizeRelationBean;
import com.yd.business.activity.bean.ActivityPrizeRuleBean;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.activity.dao.IActivityPrizeDao;
import com.yd.business.activity.dao.IActivityRuleDao;
import com.yd.util.StringUtil;

/**
 * 活动奖品的操作
 * @author BoBo
 *
 */
@Repository("activityPrizeDao")
public class ActivityPrizeDaoImpl extends BaseDao implements IActivityPrizeDao {

	public static final String NAMESPACE = "activityPrize.";
	
	@Override
	public ActivityProductBean updataActivityProductBean(ActivityProductBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updataActivityProductBean",bean);
		return bean;
	}

	@Override
	public ActivityProductBean insertActivityProductBean(ActivityProductBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertIntoActivityProductBean",bean);
		return bean;
	}

	@Override
	public void deleteActivityProductBean(ActivityProductBean bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteActivityProductBean", bean);
	}
	
	@Override
	public ActivityPrize updataActivityPrizeBean(ActivityPrize bean) {
		sqlSessionTemplate.update(NAMESPACE+"updataActivityPrizeBean",bean);
		return bean;
	}
	
	@Override
	public ActivityPrize insertActivityPrizeBean(ActivityPrize bean) {
		sqlSessionTemplate.insert(NAMESPACE+"insertActivityPrizeBean",bean);
		return bean;
	}
	
	@Override
	public void deleteActivityPrizeBean(ActivityPrize bean) {
		sqlSessionTemplate.delete(NAMESPACE+"deleteActivityPrizeBean", bean);
	}

	@Override
	public List<ActivityPrize> queryActivityPrize(ActivityPrize prize) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActivityPrize", prize);
	}

	@Override
	public ActivityPrize findActivityPrizeById(ActivityPrize prize) {
		if(!StringUtil.isNull(prize.getId())){
			return sqlSessionTemplate.selectOne(NAMESPACE + "queryActivityPrize", prize);
		}else{
			return null;
		}
		
	}

	@Override
	public List<ActivityProductBean> queryActivityProductBean(ActivityProductBean prize) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActivityProductBean", prize);
	}
	
	@Override
	public List<ActivityPrizeRuleBean> queryPrizeRule(ActivityPrizeRuleBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryPrizeRule", bean);
	}
	@Override
	public List<ActivityPrizeRelationBean> queryActivityPrizeRelation(ActivityPrizeRelationBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryActivityPrizeRelation", bean);
	}
	
	
	@Override
	public int execActivityPrizeRuleSQL(String sql){
		return sqlSessionTemplate.selectOne(NAMESPACE+"execActivityPrizeRule", sql);
	}
	
	
	@Override 
	public void updateActivityWinHis(ActivityWinHisBean bean) {
		sqlSessionTemplate.update(NAMESPACE +"updateActivityWinHis", bean);
	}
	
}
