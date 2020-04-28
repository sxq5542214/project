/**
 * 
 */
package com.yd.business.user.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.user.bean.UserSignBean;

/**
 * @author ice
 *
 */
public interface IUserSignService {

	UserSignBean userSignByOpenid(String openid);

	List<UserSignBean> queryUserSignHistory(int userId);
	
	UserSignBean whetherSign(String openid);

	/**
	 * 分页查询
	 */
	public PageinationData queryUserSingPage(UserSignBean bean);


	/**
	 * 删除usersign表信息
	 */
	public void deteleUserSign(UserSignBean bean);
	
	/**
	 * 更改usersign表信息
	 */
	public void editUserSign(UserSignBean bean);

}
