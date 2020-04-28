package com.yd.business.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.customer.dao.ICustomerDiscountDao;
import com.yd.business.customer.service.ICustomerDiscountService;
@Service("customerDiscountService")
public class CustomerDiscountServiceImpl extends BaseService implements
		ICustomerDiscountService {

	@Resource
	private ICustomerDiscountDao customerDiscountDao;
	@Override
	public void insertCustomerDiscount(CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		customerDiscountDao.insertCustomerDiscount(bean);
	}

	@Override
	public void updateCustomerDiscount(CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		customerDiscountDao.updateCustomerDiscount(bean);
	}

	@Override
	public void deleteCustomerDiscount(CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		customerDiscountDao.deleteCustomerDiscount(bean);
	}

	@Override
	public List<CustomerDiscountBean> listCustomerDiscount(
			CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		return customerDiscountDao.listCustomerDiscount(bean);
	}

	@Override
	public void batchDeleteCustomerDiscount(List<CustomerDiscountBean> list) {
		// TODO Auto-generated method stub
		customerDiscountDao.batchDeleteCustomerDiscount(list);
	}

	@Override
	public List<CustomerDiscountBean> listCustDisGroupBrand(
			CustomerDiscountBean bean) {
		// TODO Auto-generated method stub
		return customerDiscountDao.listCustDisGroupBrand(bean);
	}

	@Override
	public void batchDeleteCustomerDiscountById(List<CustomerDiscountBean> list) {
		// TODO Auto-generated method stub
		customerDiscountDao.batchDeleteCustomerDiscountById(list);
	}

	@Override
	public CustomerDiscountBean findCustomerDiscountById(Integer id) {
		// TODO Auto-generated method stub
		return customerDiscountDao.findCustomerDiscountById(id);
	}

}
