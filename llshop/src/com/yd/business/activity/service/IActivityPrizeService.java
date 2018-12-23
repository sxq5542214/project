package com.yd.business.activity.service;

import java.util.List;

import com.yd.business.activity.bean.ActivityPrize;

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
	
}
