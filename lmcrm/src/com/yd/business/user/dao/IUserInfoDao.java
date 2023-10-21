package com.yd.business.user.dao;

import java.util.List;

import com.yd.business.user.bean.UserInfoBean;

public interface IUserInfoDao {

	List<UserInfoBean> queryUserInfoList(UserInfoBean bean);

	int insertUserInfo(UserInfoBean bean);

	int updateUserInfo(UserInfoBean bean);

}
