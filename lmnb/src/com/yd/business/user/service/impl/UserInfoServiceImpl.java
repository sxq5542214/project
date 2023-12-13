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
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.dao.IUserInfoDao;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmUserModelMapper;
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
	private LmUserModelMapper userModelMapper;
	@Autowired
	private LmUserModelMapper userMapper;
	
	private IAreaService areaService;
	
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
	public int addOrUpdateUser(LmUserModel bean) {
		int num =0;
		
		if(bean.getId() == null) {
			bean.setCreatetime(new Date());
			bean.setModifytime(new Date());
			//设置用户编码
			LmUserModelExample model = new LmUserModelExample();
			
			
			num = userModelMapper.insertSelective(bean);
		}else {
			bean.setModifytime(new Date());
			num = userModelMapper.updateByPrimaryKey(bean);
		}
		
		return num;
	}

	@Override
	public int addOrUpdateUser(UserInfoBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<LmUserModel> queryUsersPhoneByAddressList(Set<Integer> addressids){
		
		if(addressids.size() == 0) {
			return null;
		}
		LmUserModelExample ex = new LmUserModelExample();
		LmUserModelExample.Criteria cri = ex.createCriteria();
		cri.andAddressidIn(new ArrayList<>(addressids));
		
		return userModelMapper.selectByExample(ex);
		
	}
	
}
