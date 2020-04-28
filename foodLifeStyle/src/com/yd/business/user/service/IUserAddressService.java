/**
 * 
 */
package com.yd.business.user.service;

import java.util.List;

import com.yd.business.user.bean.UserAddressBean;

/**
 * @author ice
 *
 */
public interface IUserAddressService {

	void createUserAddress(UserAddressBean bean);

	void updateUserAddress(UserAddressBean bean);

	void deleteUserAddress(int id);

	List<UserAddressBean> queryUserAddress(UserAddressBean bean);

	UserAddressBean findUserAddressById(int id);

}
