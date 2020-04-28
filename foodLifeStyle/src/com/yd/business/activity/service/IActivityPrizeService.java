package com.yd.business.activity.service;

import java.util.List;

import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityPrizeRelationBean;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.user.bean.UserWechatBean;

public interface IActivityPrizeService {

	/**
	 * 获得奖品列表
	 * @param bean
	 * @return
	 */
	List<ActivityPrize> queryActivityPrizeByBean(ActivityPrize bean);
	
	/**
	 * 提交奖品信息
	 * @param json
	 * @return
	 */
	ActivityPrize commitActivityPrizeForJson(String json);
	
	/**
	 * 删除奖品
	 * @param ids
	 */
	void deleteActivityPrizeByIds(String ids) throws Exception;

	/**
	 * 根据ID找奖品
	 * @param id
	 * @return
	 */
	ActivityPrize findActivityPrizeByID(int id);

	/**
	 * 处理用户领取奖品
	 * @param user
	 * @param activityId
	 * @param prizeId
	 * @return
	 */
	String dealUserActivityPrize(UserWechatBean user, int activityId, int prizeId);

	List<ActivityPrizeRelationBean> queryActivityPrizeRelationByActivityId(int activityId);

	String dealUserActivityPrize(UserWechatBean user, int activityId, String activityCode);

	ActivityWinHisBean findActivityWinHisById(int id);

	String userReceiveWinHisPrize(UserWechatBean user, int winHisId);

	void updateActivityWinHis(ActivityWinHisBean bean);
	
}
