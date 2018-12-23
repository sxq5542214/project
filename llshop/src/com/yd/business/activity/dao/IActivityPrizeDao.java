package com.yd.business.activity.dao;

import java.util.List;

import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityProductBean;

public interface IActivityPrizeDao {

	/**
	 * 更新
	 * @param bean
	 * @return
	 */
	ActivityProductBean updataActivityProductBean(ActivityProductBean bean);
	
	/**
	 * 新建
	 * @param bean
	 * @return
	 */
	ActivityProductBean insertActivityProductBean(ActivityProductBean bean);
	
	/**
	 * 删除
	 * @param bean
	 */
	void deleteActivityProductBean(ActivityProductBean bean);
	/**
	 * 更新奖品
	 * @param bean
	 * @return
	 */
	ActivityPrize updataActivityPrizeBean(ActivityPrize bean);
	
	/**
	 * 新建奖品
	 * @param bean
	 * @return
	 */
	ActivityPrize insertActivityPrizeBean(ActivityPrize bean);
	
	/**
	 * 删除奖品
	 * @param bean
	 */
	void deleteActivityPrizeBean(ActivityPrize bean);
	
	/**
	 * 查找奖品
	 * @param prize
	 * @return
	 */
	List<ActivityPrize> queryActivityPrize(ActivityPrize prize);
	/**
	 * 查找奖品ByID
	 * @param prize
	 * @return
	 */
	ActivityPrize findActivityPrizeById(ActivityPrize prize);
	
	
	/**
	 * 查找关系
	 * @param prize
	 * @return
	 */
	List<ActivityProductBean> queryActivityProductBean(ActivityProductBean prize);
}
