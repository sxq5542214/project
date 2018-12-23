/**
 * 
 */
package com.yd.business.test.dao;

import java.util.List;

import com.yd.business.test.bean.TechnicalVerificationBean;

/**
 * @author ice
 *
 */
public interface ITestDao {

	List<TechnicalVerificationBean> queryTVB(String name);

	List<TechnicalVerificationBean> querySql(String sql);
	
}
