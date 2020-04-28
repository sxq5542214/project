/**
 * 
 */
package com.yd.business.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.user.bean.UserAddressBean;
import com.yd.business.user.dao.IUserAddressDao;

/**
 * @author ice
 *
 */
@Repository("userAddressDao")
public class UserAddressDaoImpl extends BaseDao implements IUserAddressDao {
	private static final String NAMESPACE = "userAddress.";
	
	@Override
	public void createUserAddress(UserAddressBean bean){
		sqlSessionTemplate.insert(NAMESPACE +"createUserAddress", bean);
	}
	
	@Override
	public void updateUserAddress(UserAddressBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateUserAddress", bean);
	}
	
	@Override
	public List<UserAddressBean> queryUserAddress(UserAddressBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryUserAddress", bean);
	}
	
	@Override
	public void deleteUserAddress(int id){
		sqlSessionTemplate.delete(NAMESPACE +"deleteUserAddress", id);
	}
	
}
