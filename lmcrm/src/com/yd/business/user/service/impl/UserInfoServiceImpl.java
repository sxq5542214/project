/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
import com.yd.business.other.service.IAddressService;
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
	private IBuildingService buildingService;
	@Autowired
	private IUserInfoDao userInfoDao;
	
	private IAreaService areaService;
	
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
	public UserInfoBean findUserByNo(Long no){
		UserInfoBean bean = new UserInfoBean();
		bean.setU_no(no);
		List<UserInfoBean> list = userInfoDao.queryUserInfoList(bean);
		return list.size()>0 ? list.get(0):null;
		
	}
	@Override
	public UserInfoBean findUserByCardNo(Integer cardno){
		UserInfoBean bean = new UserInfoBean();
		bean.setU_cardno(cardno);
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
	
	@Override
	public List<UserInfoBean> queryUserListByAddressIds(String addressIds){

		UserInfoBean bean = new UserInfoBean();
		bean.setAddressIds(addressIds);
		return userInfoDao.queryUserInfoList(bean);
		
	}
	
	@Override
	public int addOrUpdateUser(UserInfoBean bean) {
		int num =0;
		
		if(bean.getU_id() == null) {
			bean.setU_createdate(new Date());
			bean.setU_updatedate(bean.getU_createdate());
			bean.setU_startdate(bean.getU_createdate());
			bean.setU_group(bean.getU_group() -1); // 界面上是从1开始，数据库是从0开始
			bean.setU_buildingid(22l); // 字段已弃用，写入固定值避免插入失败
			//设置用户编码
			
			
			num = userInfoDao.insertUserInfo(bean);
		}else {
			bean.setU_updatedate(new Date());
			if(bean.getU_group() != null) {
				bean.setU_group(bean.getU_group() -1);// 界面上是从1开始，数据库是从0开始
			}
			
			num = userInfoDao.updateUserInfo(bean);
		}
		
		return num;
	}
	
	
	
}
