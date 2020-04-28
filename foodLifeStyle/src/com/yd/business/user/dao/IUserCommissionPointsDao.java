package com.yd.business.user.dao;

import java.util.List;

import com.yd.business.user.bean.UserCommissionPointsBean;

public interface IUserCommissionPointsDao {

	void createUserCommissionPoint(UserCommissionPointsBean bean);

	List<UserCommissionPointsBean> queryUserCommissionPoints(UserCommissionPointsBean bean);
}
