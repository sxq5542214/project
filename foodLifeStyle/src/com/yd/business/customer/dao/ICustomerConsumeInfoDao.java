/**
 * 
 */
package com.yd.business.customer.dao;

import com.yd.business.customer.bean.CustomerConsumeInfoBean;

/**
 * @author ice
 *
 */
public interface ICustomerConsumeInfoDao {

	void createCustomerConsumeInfo(CustomerConsumeInfoBean bean);

	CustomerConsumeInfoBean findCustomerConsumeInfo(String out_trade_no);

	void updateUserConsumeInfo(CustomerConsumeInfoBean cconsume);

}
