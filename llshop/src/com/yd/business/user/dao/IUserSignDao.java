/**
 * 
 */
package com.yd.business.user.dao;

import java.util.List;

import com.yd.business.user.bean.UserSignBean;

/**
 * @author ice
 *
 */
public interface IUserSignDao {

	UserSignBean findLastUserSign(int userId);

	List<UserSignBean> queryUserSign(UserSignBean bean);

	void createUserSign(UserSignBean bean);

	void updateUserSign(UserSignBean bean);

	
	/**
	 * 分页查询ll_usersign表信息
	 */
	public List<UserSignBean> queryUserSingPage(UserSignBean bean);
	
	
	/**
	 * 查询usersign表总数
	 */
	public int userSingCount(UserSignBean bean);
	
	/**
	 * 删除usersign表信息
	 */
	public void deleteUserSign(UserSignBean bean);
}
