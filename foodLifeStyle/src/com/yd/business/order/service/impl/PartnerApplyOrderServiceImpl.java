package com.yd.business.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.order.bean.PartnerApplyOrderBean;
import com.yd.business.order.dao.IPartnerApplyOrderDao;
import com.yd.business.order.service.IPartnerApplyOrderService;
@Service("partnerApplyOrderService")
public class PartnerApplyOrderServiceImpl extends BaseService implements
		IPartnerApplyOrderService {

	@Resource
	private IPartnerApplyOrderDao partnerApplyOrderDao;
	@Override
	public PartnerApplyOrderBean findPartnerApplyOrderById(Integer id) {
		// TODO Auto-generated method stub
		return partnerApplyOrderDao.findPartnerApplyOrderById(id);
	}

	@Override
	public List<PartnerApplyOrderBean> listPartnerApplyOrder(
			PartnerApplyOrderBean bean) {
		// TODO Auto-generated method stub
		return partnerApplyOrderDao.listPartnerApplyOrder(bean);
	}

	@Override
	public void updatePartnerApplyOrder(PartnerApplyOrderBean bean) {
		// TODO Auto-generated method stub
		partnerApplyOrderDao.updatePartnerApplyOrder(bean);
	}

	@Override
	public void deletePartnerApplyOrder(Integer id) {
		// TODO Auto-generated method stub
		partnerApplyOrderDao.deletePartnerApplyOrder(id);
	}

	@Override
	public void insertPartnerApplyOrder(PartnerApplyOrderBean bean) {
		// TODO Auto-generated method stub
		partnerApplyOrderDao.insertPartnerApplyOrder(bean);
	}

}
