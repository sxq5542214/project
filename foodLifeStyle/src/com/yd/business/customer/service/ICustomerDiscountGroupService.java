package com.yd.business.customer.service;

import java.util.List;

import com.yd.business.customer.bean.CustomerDiscountGroupBean;

public interface ICustomerDiscountGroupService {
	public int insertCustomerDiscountGroup(CustomerDiscountGroupBean bean);
	public void updateCustomerDiscountGroup(CustomerDiscountGroupBean bean);
	public void deleteCustomerDiscountGroup(CustomerDiscountGroupBean bean);
	public List<CustomerDiscountGroupBean> listCustomerDiscountGroup(CustomerDiscountGroupBean bean);
	public void batchDeleteCustomerDiscountGroup(List<CustomerDiscountGroupBean> list);
	public List<CustomerDiscountGroupBean> listCustDisGroupInfo(CustomerDiscountGroupBean bean);
	public CustomerDiscountGroupBean findCustDisGroupById(Integer id);
}
