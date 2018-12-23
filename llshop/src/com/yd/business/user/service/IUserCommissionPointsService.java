/**
 * 
 */
package com.yd.business.user.service;

import java.util.List;

import com.yd.business.user.bean.UserCommissionPointsBean;

/**
 * @author ice
 *
 */
public interface IUserCommissionPointsService {

	void createUserPointLog(int userId, int point, String detail);

	List<UserCommissionPointsBean> queryUserCommissionPoints(int userId);

}
