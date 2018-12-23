package com.yd.business.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.order.bean.PartnerApplyOrderProductBean;
import com.yd.business.order.dao.IPartnerApplyOrderProductDao;
import com.yd.business.order.service.IPartnerApplyOrderProductService;
@Service("partnerApplyOrderProductService")
public class PartnerApplyOrderProductServiceImpl extends BaseService implements
		IPartnerApplyOrderProductService {

	@Resource
	private IPartnerApplyOrderProductDao partnerApplyOrderProductDao;
	@Override
	public PartnerApplyOrderProductBean findPartnerApplyOrderProductById(
			Integer id) {
		// TODO Auto-generated method stub
		return partnerApplyOrderProductDao.findPartnerApplyOrderProductById(id);
	}

	@Override
	public List<PartnerApplyOrderProductBean> listPartnerApplyOrderProduct(
			PartnerApplyOrderProductBean bean) {
		// TODO Auto-generated method stub
		return partnerApplyOrderProductDao.listPartnerApplyOrderProduct(bean);
	}

	@Override
	public void insertPartnerApplyOrderProduct(PartnerApplyOrderProductBean bean) {
		// TODO Auto-generated method stub
		partnerApplyOrderProductDao.insertPartnerApplyOrderProduct(bean);
	}

	@Override
	public void updatePartnerApplyOrderProduct(PartnerApplyOrderProductBean bean) {
		// TODO Auto-generated method stub
		partnerApplyOrderProductDao.updatePartnerApplyOrderProduct(bean);
	}

	@Override
	public void deletePartnerApplyOrderProductById(Integer id) {
		// TODO Auto-generated method stub
		partnerApplyOrderProductDao.deletePartnerApplyOrderProductById(id);
	}

	@Override
	public void deletePartnerApplyOrderProductByOrderId(Integer id) {
		// TODO Auto-generated method stub
		partnerApplyOrderProductDao.deletePartnerApplyOrderProductByOrderId(id);
	}

}
