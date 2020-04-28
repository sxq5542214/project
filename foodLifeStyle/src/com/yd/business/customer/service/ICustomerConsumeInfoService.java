package com.yd.business.customer.service;

import com.yd.business.customer.bean.CustomerConsumeInfoBean;

public interface ICustomerConsumeInfoService {

	CustomerConsumeInfoBean findCustomerConsumeInfo(String out_trade_no);

	void updateUserConsumeInfo(CustomerConsumeInfoBean cconsume);

	void createCustomerConsumeInfo(int money, int customer_id, String out_no, String interface_type, Integer event_type);

}
