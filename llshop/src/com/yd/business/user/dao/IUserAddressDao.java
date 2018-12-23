package com.yd.business.user.dao;

import java.util.List;

import com.yd.business.user.bean.UserAddressBean;

public interface IUserAddressDao {

	void createUserAddress(UserAddressBean bean);

	void updateUserAddress(UserAddressBean bean);

	List<UserAddressBean> queryUserAddress(UserAddressBean bean);

	void deleteUserAddress(int id);

}
