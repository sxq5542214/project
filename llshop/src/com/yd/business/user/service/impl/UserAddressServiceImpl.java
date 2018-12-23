/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.AddressBean;
import com.yd.business.other.service.IAddressService;
import com.yd.business.user.bean.UserAddressBean;
import com.yd.business.user.dao.IUserAddressDao;
import com.yd.business.user.service.IUserAddressService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("userAddressService")
public class UserAddressServiceImpl extends BaseService implements IUserAddressService {
	@Resource
	private IUserAddressDao userAddressDao;
	@Resource
	private IAddressService	addressService;
	
	@Override
	public void createUserAddress(UserAddressBean bean){
		
		AddressBean address = addressService.findAddressById(bean.getAddress_id());
		
		bean.setContact_address(address.getFull_name() + bean.getStreet_name());
		bean.setStatus(UserAddressBean.STATUS_DEFAULT);
		bean.setCreate_time(DateUtil.getNowDateStr());
		userAddressDao.createUserAddress(bean);
	}
	
	
	@Override
	public void updateUserAddress(UserAddressBean bean){
		userAddressDao.updateUserAddress(bean);
	}
	
	@Override
	public void deleteUserAddress(int id){
		userAddressDao.deleteUserAddress(id);
	}
	
	@Override
	public List<UserAddressBean> queryUserAddress(UserAddressBean bean){
		return userAddressDao.queryUserAddress(bean);
	}
	@Override
	public UserAddressBean findUserAddressById(int id){
		UserAddressBean bean = new UserAddressBean();
		bean.setId(id);
		List<UserAddressBean> list = queryUserAddress(bean );
		if(list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
}
