/**
 * 
 */
package com.yd.business.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.user.bean.UserSignBean;
import com.yd.business.user.dao.IUserSignDao;

/**
 * @author ice
 *
 */
@Repository("userSignDao")
public class UserSignDaoImpl extends BaseDao implements IUserSignDao {
	private static final String NAMESPACE = "userSign.";
	
	@Override
	public UserSignBean findLastUserSign(int userId){
		
		return sqlSessionTemplate.selectOne(NAMESPACE+"findLastUserSign", userId);
	}
	
	@Override
	public List<UserSignBean> queryUserSign(UserSignBean bean){
		
		return sqlSessionTemplate.selectList(NAMESPACE+"queryUserSign", bean);
	}
	
	@Override
	public void createUserSign(UserSignBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createUserSign", bean);
	}

	@Override
	public void updateUserSign(UserSignBean bean) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserSign", bean);
	}
	
	@Override
	public List<UserSignBean> queryUserSingPage(UserSignBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryUserSingPage", bean,rowBound(bean));
	}

	@Override
	public int userSingCount(UserSignBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"userSingCount",bean);
	}

	@Override
	public void deleteUserSign(UserSignBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteUserSign", bean);
	}

}
