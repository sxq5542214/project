package com.yd.business.user.service;

import java.util.List;

import com.yd.business.user.bean.UserInfoBean;

public interface IUserInfoService {

	List<UserInfoBean> queryUserInfo(UserInfoBean bean);

	UserInfoBean findUserById(Long id);

	void updateUserStatusToNormal(Long userid);

	UserInfoBean findUserByNo(Long no);

	int addOrUpdateUser(UserInfoBean bean);

	UserInfoBean findUserByCardNo(Integer cardno);

	List<UserInfoBean> queryUserListByAddressIds(String addressIds);

}
