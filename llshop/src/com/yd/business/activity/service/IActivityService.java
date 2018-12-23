package com.yd.business.activity.service;

import java.util.List;

import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityInstanceBean;
import com.yd.business.activity.bean.ActivityOlympicGuessBean;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityRemindBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.wechat.bean.ImageBean;

public interface IActivityService {

	List<ActivityWinHisBean> queryActivityWinHis(ActivityWinHisBean bean);

	ActivityUserRelationBean saveActivityUserRelation(ActivityConfigBean config, ImageBean image, UserWechatBean user);

	String userGrabActivity(UserWechatBean user, Integer activity_config_id);

	ActivityUserRelationBean joinFirstShakeActivity(UserWechatBean user);

	ActivityUserRelationBean firstShakeAddPhone(String openid, int relationId, String phone);

	ActivityUserRelationBean firstShakeShareSuccessToOrderProduct(String openid, int share_type, int relation_id);

	ActivityUserRelationBean findUserRelation(String activity_code, String openid);

	ActivityUserRelationBean findUserRelation(String openid, int relation_id);

	List<ActivityUserRelationBean> findUserRelationByTestTable();

	List<ActivityOlympicGuessBean> queryUserOlympicGuessInfo(int user_id);

	String joinOlympicGuessActivity(String openid, int guess_num, int shared);

	ActivityOlympicGuessBean findOlympicGuess(String openid, int guess_id);

	ActivityUserRelationBean joinActivityRelation(UserWechatBean user, String activity_code);

	void updateOlympicGuess(ActivityOlympicGuessBean bean);

	boolean checkPhoneHasJoinedRelation(String openid, int relation_id, String phone);

	void createOrUpdateActivityInstance(ActivityInstanceBean bean); 
	
	List<ActivityInstanceBean> queryActivityInstance(ActivityInstanceBean bean);
	
	ActivityUserRelationBean findUserRelationByUserAndInstanceId(String openid, int instanceId);
	
	ActivityPrize recordUserInstanceActivityInfo(UserWechatBean user,ActivityInstanceBean bean);

	void initActivity(ActivityConfigBean activityConfigBean, int user_id);
	
	List<ActivityInstanceBean> findActivityInstanceList(UserWechatBean user,ActivityConfigBean activityConfigBean,int count);
	
	ActivityConfigBean findEnableActivityConfigByActivityIdAndCode(Integer id,String code);
	
	ActivityConfigBean findActivityConfigByCode(String code);
	
	List<ActivityUserRelationBean> findUserRelationByPage(int start,int pagesize);
	
	ActivityInstanceBean findActivityInstanceByIdAndCode(String id,String code);
	
	List<ActivityRule> queryActivityRuleByActivityConfigId(Integer activity_id);
	
	ActivityPrize checkActivityParams(ActivityPrize prize,ActivityConfigBean activityConfigBean,UserWechatBean user,ActivityInstanceBean instanceBean);
	
	ActivityPrize checkActivityParamsEasy(ActivityPrize prize,ActivityConfigBean activityConfigBean,UserWechatBean user,ActivityInstanceBean instanceBean);

	ActivityRemindBean remindUserJoinActivity(String openid,String instanceid);
	
	String checkLimitParams(ActivityConfigBean activityConfigBean,UserWechatBean user);
	
}
