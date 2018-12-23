package com.yd.business.activity.dao;

import java.util.Date;
import java.util.List;

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

public interface IActivityDao {

	ActivityUserRelationBean findRelationByBean(ActivityUserRelationBean bean);

	ActivityUserRelationBean findRelation(String weixin_id, Date join_date);

	void saveActivityUserRelateion(ActivityUserRelationBean bean);
	
	void saveActivityUserRelateionNoPrize(ActivityUserRelationBean bean);

	int getActivityJoinCount(int activity_config_id, Date join_date);

//	ActivityBean findActivityBean(Integer user_id, Integer activity_config_id);

	void deleteActivityWinData();

	void saveActivityWinHis(ActivityWinHisBean bean);

	List<ActivityWinHisBean> queryActivityWinHis(ActivityWinHisBean bean);

	ActivityUserRelationBean findRelation(String weixin_id, Integer activity_id);

	List<ActivityProductBean> queryActivityProductList(ActivityProductBean bean);
	void updateActivityProduct(ActivityProductBean bean);

	void updateUserRelateion(ActivityUserRelationBean bean);

	ActivityProductBean findActivityProductById(int id);

	List<ActivityUserRelationBean> findUserRelationByTestTable();
	
	List<ActivityUserRelationBean> findUserRelationByPhone(int activity_config_id, String phone);

	List<ActivityOlympicGuessBean> queryOlympicGuessInfo(ActivityOlympicGuessBean bean);

	void createOlympicGuessInfo(ActivityOlympicGuessBean bean);

	void updateOlympicGuess(ActivityOlympicGuessBean bean);

	List<ActivityUserRelationBean> queryActivityRelation(ActivityUserRelationBean bean);
	
	void createOrUpdateActivityInstance(ActivityInstanceBean bean);
	
	List<ActivityInstanceBean> queryActivityInstance(ActivityInstanceBean bean);
	
	public List<ActivityUserRelationBean> findRelationByInstanceBean(ActivityUserRelationBean bean);
	
	ActivityPrize findActivityPrizeById(int id);
	
	void createOrUpdateActivityPirze(ActivityPrize bean);
	
	List<ActivityUserRelationBean> findUserRelationByPage(ActivityUserRelationBean bean);
	
	List<ActivityRule> queryActivityRuleByBean(ActivityRule bean);
	
	void createOrInsertActivityRemind(ActivityRemindBean bean);
	
	List<ActivityRemindBean> queryActivityRemindByBean(ActivityRemindBean bean);
	
	
	List<ActivityOriginalRelationBean> queryActivityOriginalRelationBean(ActivityOriginalRelationBean bean);
}
