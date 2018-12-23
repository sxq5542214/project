package com.yd.business.channel.dao;

import java.util.List;

import com.yd.business.channel.bean.ChannelCustomerBean;
import com.yd.business.channel.bean.ChannelProductBean;
import com.yd.business.channel.bean.ChannelProductParamBean;

public interface IChannelProductDao {

	List<ChannelProductBean> queryChannelProduct(ChannelProductBean bean);

	List<ChannelCustomerBean> queryChannelCustomer(ChannelCustomerBean bean);

	List<ChannelProductParamBean> queryChannelProductParam(ChannelProductParamBean bean);

	void updateChannelProduct(ChannelProductBean bean);

	void updateChannelCustomer(ChannelCustomerBean bean);

}
