/**
 * 
 */
package com.yd.business.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.user.bean.UserCommissionPointsBean;
import com.yd.business.user.dao.IUserCommissionPointsDao;

/**
 * @author ice
 *
 */
@Repository("userCommissionPointsDao")
public class UserCommissionPointsDaoImpl extends BaseDao implements IUserCommissionPointsDao {
	private static final String NAMESPACE = "userCommissionPoints.";
	

	@Override
	public void createUserCommissionPoint(UserCommissionPointsBean bean ){
		sqlSessionTemplate.insert(NAMESPACE+"createUserCommissionPoint",bean);
	}
	
	@Override
	public List<UserCommissionPointsBean> queryUserCommissionPoints(UserCommissionPointsBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryUserCommissionPoints", bean);
	}
	
}
