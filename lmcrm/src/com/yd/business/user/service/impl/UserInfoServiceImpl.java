/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.dao.IUserInfoDao;
import com.yd.business.user.service.IUserInfoService;

/**
 * @author ice
 *
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseService implements IUserInfoService {
	
	@Autowired
	private IUserInfoDao userInfoDao;
	
	@Override
	public List<UserInfoBean> queryUserInfo(UserInfoBean bean){
		return userInfoDao.queryUserInfoList(bean);
	}
	
	@Override
	public UserInfoBean findUserById(Long id){
		UserInfoBean bean = new UserInfoBean();
		bean.setU_id(id);
		List<UserInfoBean> list = userInfoDao.queryUserInfoList(bean);
		return list.size()>0 ? list.get(0):null;
		
	}
	
	@Override
	public void updateUserStatusToNormal(Long userid) {

		UserInfoBean bean = new UserInfoBean();
		bean.setU_id(userid);
		bean.setU_status(UserInfoBean.STATUS_NORMAL);
		userInfoDao.updateUserInfo(bean);
	}
	
}
