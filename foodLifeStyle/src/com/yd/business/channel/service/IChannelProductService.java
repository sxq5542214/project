/**
 * 
 */
package com.yd.business.channel.service;

import java.util.List;

import com.yd.business.channel.bean.ChannelCustomerBean;
import com.yd.business.channel.bean.ChannelProductBean;
import com.yd.business.channel.bean.ChannelProductParamBean;
import com.yd.business.channel.bean.ChannelServerBean;
import com.yd.business.isp.bean.ISPInterfaceBean;

/**
 * @author ice
 *
 */
public interface IChannelProductService {

	List<ChannelProductBean> queryChannelProduct(ChannelProductBean bean);

	List<ChannelCustomerBean> queryChannelCustomer(ChannelCustomerBean bean);

	ChannelProductBean getRandomChannelProduct(String province, int product_id, int customer_id);

	ISPInterfaceBean orderProductByChannel(int customer_id, String order_code, String phone, String param,
			int product_id);

	ISPInterfaceBean orderProductByChannel(int channel_id, String order_code, String phone, String param,
			int product_id, String server_name);

	ChannelCustomerBean getRandomChannelCustomer(String province, Integer customer_id, int product_id);

	List<ChannelProductParamBean> queryChannelProductParam(ChannelProductParamBean bean);

	ChannelProductParamBean findChannelProductParam(int product_id, int channel_id);

	void minusChannelBalance(int channel_id, int product_id, String order_code);

	void updateChannelProduct(ChannelProductBean bean);

	void updateChannelCustomer(ChannelCustomerBean bean);

	ChannelProductBean findChannelProductById(int id);

	void checkChannelAlive(int channel_id, int product_id);

}
