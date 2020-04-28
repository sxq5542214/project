package com.yd.business.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerDiscountGroupBean;
import com.yd.business.customer.dao.ICustomerDiscountGroupDao;
import com.yd.business.customer.service.ICustomerDiscountGroupService;
@Service("customerDiscountGroupService")
public class CustomerDiscountGroupServiceImpl extends BaseService implements
		ICustomerDiscountGroupService {

	@Resource
	private ICustomerDiscountGroupDao customerDiscountGroupDao;
	@Override
	public int insertCustomerDiscountGroup(CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		return customerDiscountGroupDao.insertCustomerDiscountGroup(bean);
	}

	@Override
	public void updateCustomerDiscountGroup(CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		customerDiscountGroupDao.updateCustomerDiscountGroup(bean);
	}

	@Override
	public void deleteCustomerDiscountGroup(CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		customerDiscountGroupDao.deleteCustomerDiscountGroup(bean);
	}

	@Override
	public List<CustomerDiscountGroupBean> listCustomerDiscountGroup(
			CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		return customerDiscountGroupDao.listCustomerDiscountGroup(bean);
	}

	@Override
	public void batchDeleteCustomerDiscountGroup(
			List<CustomerDiscountGroupBean> list) {
		// TODO Auto-generated method stub
		customerDiscountGroupDao.batchDeleteCustomerDiscountGroup(list);
	}

	@Override
	public List<CustomerDiscountGroupBean> listCustDisGroupInfo(
			CustomerDiscountGroupBean bean) {
		// TODO Auto-generated method stub
		return customerDiscountGroupDao.listCustDisGroupInfo(bean);
	}

	@Override
	public CustomerDiscountGroupBean findCustDisGroupById(Integer id) {
		// TODO Auto-generated method stub
		return customerDiscountGroupDao.findCustDisGroupById(id);
	}

}
