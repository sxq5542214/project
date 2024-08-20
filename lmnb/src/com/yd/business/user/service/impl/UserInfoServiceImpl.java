/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.bean.UserModelExtendsBean;
import com.yd.business.user.dao.IUserExtendsMapper;
import com.yd.business.user.dao.IUserInfoDao;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmUserModelMapper;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.iotbusiness.mapper.model.LmUserModelExample;
import com.yd.iotbusiness.mapper.model.LmUserModelExample.Criteria;

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
	@Autowired
	private IUserExtendsMapper userMapper;
	@Autowired
	private IDeviceInfoService deviceInfoService;
	
	@Override
	public List<UserInfoBean> queryUserInfo(UserInfoBean bean){
		return userInfoDao.queryUserInfoList(bean);
	}

	@Override
	public IOTWebDataBean queryUserList(LmUserModel bean){
		
		LmUserModelExample e = new LmUserModelExample(bean);
		
		LmUserModelExample.Criteria cri = e.createCriteria();
		cri.andIdcardLike(bean.getIdcard());
		cri.andIdEqualTo(bean.getId());
		cri.andNameLike(bean.getName());
		cri.andMobileLike(bean.getMobile());
		cri.andArea1Like(bean.getArea1());
		cri.andArea2Like(bean.getArea2());
		cri.andArea3Like(bean.getArea3());
		cri.andArea4Like(bean.getArea4());
		cri.andAddressidEqualTo(bean.getAddressid());
		e.setOrderByClause("id desc");
		
		long total = userMapper.countByExample(e);
		List<LmUserModel> list = userMapper.selectByExample(e);
		
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(list);
		result.setTotal(total);
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		
		return result;
	}
	@Override
	public IOTWebDataBean queryUserAndMeterList(UserModelExtendsBean bean){
		
		List<UserModelExtendsBean> list = userMapper.queryUserAndMeterByExtend(bean);
		bean.setRows(null);   // 不然分页查询的时候带上limit 会报错
		long total = userMapper.countUserAndMeterByExtend(bean);
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(list);
		result.setTotal(total);
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		
		return result;
	}
	

	@Override
	public LmUserModel  findUserById(Integer id){
		return userMapper.selectByPrimaryKey(id);
		
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
	public int addOrUpdateUser(LmUserModel bean) {
		int num =0;
		
		if(bean.getId() == null) {
			bean.setCreatetime(new Date());
			bean.setModifytime(new Date());
			
			num = userMapper.insertSelective(bean);
			//设置用户编码（户号）
			bean.setCode(String.valueOf(bean.getId()));
			userMapper.updateByPrimaryKey(bean);
		}else {
			bean.setModifytime(new Date());
			num = userMapper.updateByPrimaryKey(bean);
		}
		
		return num;
	}

	@Override
	public int deleteUser(int userid) {
		LmUserModel user = findUserById(userid);
		user.setStatus(UserModelExtendsBean.STATUS_DELETED);
		
		int r = userMapper.updateByPrimaryKeySelective(user);
		
		LmMeterModel meter = new LmMeterModel();
		meter.setUserid(user.getId());
		//删除用户下的水表
		IOTWebDataBean result = deviceInfoService.queryMeterList(meter );
		List<LmMeterModel> list =  (List<LmMeterModel>)result.getData();
		for(LmMeterModel m : list) {
			deviceInfoService.deleteMeterForStatus(m.getId());
		}
		
		return r;
	}
	
	@Override
	public List<LmUserModel> queryUsersPhoneByAddressList(Set<Integer> addressids){
		
		if(addressids.size() == 0) {
			return null;
		}
		LmUserModelExample ex = new LmUserModelExample();
		LmUserModelExample.Criteria cri = ex.createCriteria();
		cri.andAddressidIn(new ArrayList<>(addressids));
		
		return userMapper.selectByExample(ex);
		
	}
	
}
