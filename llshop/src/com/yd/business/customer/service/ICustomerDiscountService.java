package com.yd.business.customer.service;

import java.util.List;

import com.yd.business.customer.bean.CustomerDiscountBean;

public interface ICustomerDiscountService {
	public void insertCustomerDiscount(CustomerDiscountBean bean);
	public void updateCustomerDiscount(CustomerDiscountBean bean);
	public void deleteCustomerDiscount(CustomerDiscountBean bean);
	public List<CustomerDiscountBean> listCustomerDiscount(CustomerDiscountBean bean);
	public void batchDeleteCustomerDiscount(List<CustomerDiscountBean> list);
	public List<CustomerDiscountBean> listCustDisGroupBrand(CustomerDiscountBean bean);
	public void batchDeleteCustomerDiscountById(List<CustomerDiscountBean> list);
	public CustomerDiscountBean findCustomerDiscountById(Integer id);
}
