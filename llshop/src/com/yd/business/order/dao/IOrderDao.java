package com.yd.business.order.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.order.bean.AreaData;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductEffShowPageBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;

public interface IOrderDao {

	List<AreaData> queryAreaData(AreaData ad);

	void savePartnerOrderProduct(PartnerOrderProductBean bean);

	PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partnerOrderCode);

	void updatePartnerOrderProduct(PartnerOrderProductBean bean);

	List<OrderProductEffBean> queryOrderProductEffByStatus(String beforDate, Integer status);

	void createOrderProductEff(OrderProductEffBean bean);

	void updateOrderProductEff(OrderProductEffBean bean);

	List<OrderProductEffBean> queryOrderProductEff(OrderProductEffBean bean);
	
	OrderProductEffBean queryOrderProductEffById(long id);

	List<OrderProductEffShowPageBean> queryEffOrderProductLog(Map<String, Object> map);

}
