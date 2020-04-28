/**
 * 
 */
package com.yd.business.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.test.bean.TechnicalVerificationBean;
import com.yd.business.test.dao.ITestDao;

/**
 * @author ice
 *
 */
@Repository("testDao")
public class TestDaoImpl extends BaseDao implements ITestDao {
	private static final String NAMESPACE = "test.";
	
	@Override
	public List<TechnicalVerificationBean> queryTVB(String name){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryTVB", name);
	}
	
	@Override
	public List<TechnicalVerificationBean> querySql(String sql){
		return sqlSessionTemplate.selectList(NAMESPACE + "querySql", sql);
	}
	
}
