/**
 * 
 */
package com.yd.business.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.dao.IUserInfoDao;

/**
 * @author ice
 *
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDao implements IUserInfoDao {
    public static final  String NAMESPACE = "userInfo.";
    
    @Override
    public List<UserInfoBean> queryUserInfoList(UserInfoBean bean){
    	return sqlSessionTemplate.selectList(NAMESPACE + "queryUserInfoList", bean);
    }
    
    @Override
    public int insertUserInfo(UserInfoBean bean) {
    	return sqlSessionTemplate.insert(NAMESPACE + "insertUserInfo", bean);
    }
    
    @Override
    public int updateUserInfo(UserInfoBean bean) {
    	return sqlSessionTemplate.update(NAMESPACE +"updateUserInfo", bean);
    }
    
    
}
