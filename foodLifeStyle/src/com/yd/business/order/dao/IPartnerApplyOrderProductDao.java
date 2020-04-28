package com.yd.business.order.dao;

import java.util.List;

import com.yd.business.order.bean.PartnerApplyOrderProductBean;

public interface IPartnerApplyOrderProductDao {

	public PartnerApplyOrderProductBean findPartnerApplyOrderProductById(Integer id);
	public List<PartnerApplyOrderProductBean> listPartnerApplyOrderProduct(PartnerApplyOrderProductBean bean);
	public void insertPartnerApplyOrderProduct(PartnerApplyOrderProductBean bean);
	public void updatePartnerApplyOrderProduct(PartnerApplyOrderProductBean bean);
	public void deletePartnerApplyOrderProductById(Integer id);
	public void deletePartnerApplyOrderProductByOrderId(Integer id);
}
