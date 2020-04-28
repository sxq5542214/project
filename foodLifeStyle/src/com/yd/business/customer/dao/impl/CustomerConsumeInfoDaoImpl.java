/**
 * 
 */
package com.yd.business.customer.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerConsumeInfoBean;
import com.yd.business.customer.dao.ICustomerConsumeInfoDao;

/**
 * @author ice
 *
 */
@Repository("customerConsumeInfoDao")
public class CustomerConsumeInfoDaoImpl extends BaseDao implements ICustomerConsumeInfoDao {
	private static final String namespace = "customerConsumeInfo.";
	
	@Override
	public void createCustomerConsumeInfo(CustomerConsumeInfoBean bean ){
		sqlSessionTemplate.insert(namespace + "createCustomerConsumeInfo", bean);
	}
	
	@Override
	public CustomerConsumeInfoBean findCustomerConsumeInfo(String out_trade_no){
		return sqlSessionTemplate.selectOne(namespace + "findCustomerConsumeInfo", out_trade_no);
	}

	@Override
	public void updateUserConsumeInfo(CustomerConsumeInfoBean cconsume) {
		sqlSessionTemplate.update(namespace+"updateUserConsumeInfo", cconsume);
	}
	
	
}
