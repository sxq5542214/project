/**
 * 
 */
package com.yd.business.channel.service;

import java.util.List;

import com.yd.business.channel.bean.ChannelBalanceLogBean;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelServerBean;

/**
 * @author ice
 *
 */
public interface IChannelService {

	boolean checkTimeLimit();

	ChannelBean findChannel(int customer_id, String phone, int product_id);

	ChannelServerBean getRandomChannelServer(int channel_id);

	ChannelBean findChannelById(Integer id);

	@Deprecated
	void updateChannelBalance(int channel_id,String balance);

	List<ChannelBean> queryChannel(ChannelBean bean);

	void createChannelBalanceLog(ChannelBalanceLogBean bean);

	void updateChannelCalcBalance(int channel_id, int price, String remark);
}
