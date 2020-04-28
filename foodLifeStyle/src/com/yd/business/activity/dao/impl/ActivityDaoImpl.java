/**
 * 
 */
package com.yd.business.activity.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.activity.bean.ActivityBean;
import com.yd.business.activity.bean.ActivityInstanceBean;
import com.yd.business.activity.bean.ActivityOlympicGuessBean;
import com.yd.business.activity.bean.ActivityOriginalRelationBean;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityRemindBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.activity.dao.IActivityDao;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Repository("activityDao")
public class ActivityDaoImpl extends BaseDao implements IActivityDao {
	public static final String NAMESPACE = "activity.";
	
	@Override
	public int getActivityJoinCount(int activity_config_id,Date join_date){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activity_config_id", activity_config_id);
		map.put("join_date", join_date);
		return sqlSessionTemplate.selectOne(NAMESPACE+"getActivityJoinCount",map);
	}
	
	@Override
	public ActivityUserRelationBean findRelationByBean(ActivityUserRelationBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findRelationByBean",bean);
	}
	@Override
	public List<ActivityUserRelationBean> findRelationByInstanceBean(ActivityUserRelationBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"findRelationByInstanceBean",bean);
	}
	@Override
	public List<ActivityUserRelationBean> queryActivityRelation(ActivityUserRelationBean bean){

		return sqlSessionTemplate.selectList(NAMESPACE+"queryActivityRelation",bean);
	}
	
	@Override
	public void updateUserRelateion(ActivityUserRelationBean bean){
		sqlSessionTemplate.update(NAMESPACE+"updateUserRelateion", bean);
	}
	
	@Override
	public ActivityUserRelationBean findRelation(String weixin_id,Date join_date){
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setOpenid(weixin_id);
		bean.setJoin_date(DateUtil.getNowDateStr());
		return findRelationByBean(bean);
	}
	
	@Override
	public ActivityUserRelationBean findRelation(String weixin_id,Integer activity_id){
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setOpenid(weixin_id);
		bean.setActivity_config_id(activity_id);
		return findRelationByBean(bean);
	}
	
	@Override
	public void saveActivityUserRelateion(ActivityUserRelationBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"saveActivityUserRelateion", bean);
	}
	
	@Override
	public void saveActivityUserRelateionNoPrize(ActivityUserRelationBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"saveActivityUserRelateionNoPrize", bean);
	}
	
//	@Override
//	public ActivityBean findActivityBean(Integer user_id,Integer activity_config_id){
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("user_id", user_id);
//		map.put("activity_config_id", activity_config_id);
//		return sqlSessionTemplate.selectOne(NAMESPACE+"findActivityBean", map);
//	}
	
	@Override
	public void deleteActivityWinData(){
		sqlSessionTemplate.delete(NAMESPACE+"deleteActivityWinData");
	}
	
	@Override
	public void saveActivityWinHis(ActivityWinHisBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"saveActivityWinHis",bean);
	}

	@Override
	public List<ActivityWinHisBean> queryActivityWinHis(ActivityWinHisBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryActivityHis", bean);
	}
	
	@Override
	public List<ActivityProductBean> queryActivityProductList(ActivityProductBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryActivityProductList", bean);
	}
	
	@Override
	public ActivityProductBean findActivityProductById(int id){
		ActivityProductBean bean = new ActivityProductBean();
		bean.setId(id);
		List<ActivityProductBean> list = queryActivityProductList(bean);
		
		if(list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	@Override
	public List<ActivityUserRelationBean> findUserRelationByTestTable(){
		return sqlSessionTemplate.selectList(NAMESPACE + "findUserRelationByTestTable");
	}
	
	@Override
	public List<ActivityOlympicGuessBean> queryOlympicGuessInfo(ActivityOlympicGuessBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOlympicGuessInfo", bean,rowBound(bean));
	}
	
	@Override
	public void createOlympicGuessInfo(ActivityOlympicGuessBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createOlympicGuessInfo", bean);
	}

	@Override
	public List<ActivityUserRelationBean> findUserRelationByPhone(int activity_config_id, String phone) {
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setPhone(phone);
		bean.setActivity_config_id(activity_config_id);
		return sqlSessionTemplate.selectList(NAMESPACE+"findRelationByBean", bean);
	}

	@Override
	public void updateOlympicGuess(ActivityOlympicGuessBean bean) {
		
		sqlSessionTemplate.update(NAMESPACE + "updateOlympicGuess", bean);
	}

	@Override
	public void createOrUpdateActivityInstance(ActivityInstanceBean bean) {
		Integer id = bean.getId();
		if(StringUtil.isNull(id)){
			sqlSessionTemplate.insert(NAMESPACE + "createActivityInstance", bean);
		}else{
			sqlSessionTemplate.insert(NAMESPACE + "updateActivityInstance", bean);
		}
	}

	@Override
	public List<ActivityInstanceBean> queryActivityInstance(ActivityInstanceBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActivityInstance", bean);
	}

	@Override
	public ActivityPrize findActivityPrizeById(int id) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "findActivityPrizeById", id);
	}
	
	@Override
	public void createOrUpdateActivityPirze(ActivityPrize bean){
		Integer id = bean.getId();
		if(StringUtil.isNull(id)){
			sqlSessionTemplate.insert(NAMESPACE + "createActivityPrize", bean);
		}else{
			sqlSessionTemplate.insert(NAMESPACE + "updateActivityPrize", bean);
		}
	}

	@Override
	public void updateActivityProduct(ActivityProductBean bean) {
		sqlSessionTemplate.update(NAMESPACE + "updateActivityProduct",bean);
	}

	@Override
	public List<ActivityUserRelationBean> findUserRelationByPage(ActivityUserRelationBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "findUserRelationByPage", bean);
	}

	@Override
	public List<ActivityRule> queryActivityRuleByBean(ActivityRule bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActivityRuleByBean", bean);
	}

	@Override
	public void createOrInsertActivityRemind(ActivityRemindBean bean) {
		Integer id = bean.getId();
		if(StringUtil.isNull(id)){
			sqlSessionTemplate.insert(NAMESPACE + "createActivityRemind", bean);
		}else{
			sqlSessionTemplate.insert(NAMESPACE + "updateActivityRemind", bean);
		}
	}

	@Override
	public List<ActivityRemindBean> queryActivityRemindByBean(ActivityRemindBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActivityRemindByBean", bean);
	}



	@Override
	public List<ActivityOriginalRelationBean> queryActivityOriginalRelationBean(
			ActivityOriginalRelationBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryActivityOriginalRelationBean", bean);

	}
	
}
