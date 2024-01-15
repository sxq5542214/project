package com.yd.business.user.service;

import java.util.List;
import java.util.Set;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.iotbusiness.mapper.model.LmUserModel;

public interface IUserInfoService {

	List<UserInfoBean> queryUserInfo(UserInfoBean bean);

	void updateUserStatusToNormal(Long userid);

	UserInfoBean findUserByNo(Long no);

	UserInfoBean findUserByCardNo(Integer cardno);

	List<UserInfoBean> queryUserListByAddressIds(String addressIds);

	IOTWebDataBean queryUserList(LmUserModel bean);

	int addOrUpdateUser(LmUserModel bean);
	int addOrUpdateUser(UserInfoBean bean);

	List<LmUserModel> queryUsersPhoneByAddressList(Set<Integer> addressids);

	LmUserModel findUserById(Integer id);

}
