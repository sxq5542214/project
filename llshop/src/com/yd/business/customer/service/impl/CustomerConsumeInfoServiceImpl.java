/**
 * 
 */
package com.yd.business.customer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerConsumeInfoBean;
import com.yd.business.customer.dao.ICustomerConsumeInfoDao;
import com.yd.business.customer.service.ICustomerConsumeInfoService;
import com.yd.util.DateUtil;

/**
 * @author ice
 * 
 */
@Service("customerConsumeInfoService")
public class CustomerConsumeInfoServiceImpl extends BaseService implements ICustomerConsumeInfoService {
	@Resource
	private ICustomerConsumeInfoDao customerConsumeInfoDao;

//	public void createCustomerConsumeInfo(CustomerConsumeInfoBean bean) {
//
//		customerConsumeInfoDao.createCustomerConsumeInfo(bean);
//	}

	@Override
	public void createCustomerConsumeInfo(int money,int customer_id,String out_no,String interface_type,Integer event_type) {
		CustomerConsumeInfoBean bean = new CustomerConsumeInfoBean();
		bean.setMoney(money);
		bean.setCustomer_id(customer_id);
		bean.setOut_trade_code(out_no);
		bean.setCreate_date(DateUtil.getNowDateStrSSS());
		bean.setInterface_type(interface_type);
		bean.setStatus(CustomerConsumeInfoBean.STATUS_WAIT);
		bean.setEvent_type(event_type);
		
		customerConsumeInfoDao.createCustomerConsumeInfo(bean);
	}

	@Override
	public CustomerConsumeInfoBean findCustomerConsumeInfo(String out_trade_no) {

		return customerConsumeInfoDao.findCustomerConsumeInfo(out_trade_no);
	}

	@Override
	public void updateUserConsumeInfo(CustomerConsumeInfoBean cconsume) {
		
		customerConsumeInfoDao.updateUserConsumeInfo(cconsume);
	}
	
	

}
