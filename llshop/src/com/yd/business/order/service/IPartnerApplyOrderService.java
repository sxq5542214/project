package com.yd.business.order.service;

import java.util.List;

import com.yd.business.order.bean.PartnerApplyOrderBean;

public interface IPartnerApplyOrderService {
	public PartnerApplyOrderBean findPartnerApplyOrderById(Integer id);
	public List<PartnerApplyOrderBean> listPartnerApplyOrder(PartnerApplyOrderBean bean);
	public void updatePartnerApplyOrder(PartnerApplyOrderBean bean);
	public void deletePartnerApplyOrder(Integer id);
	public void insertPartnerApplyOrder(PartnerApplyOrderBean bean);
}
