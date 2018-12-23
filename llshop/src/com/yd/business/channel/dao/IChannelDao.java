/**
 * 
 */
package com.yd.business.channel.dao;

import java.util.List;

import com.yd.business.channel.bean.ChannelBalanceLogBean;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelServerBean;
import com.yd.business.channel.bean.TimeLimitBean;

/**
 * @author ice
 *
 */
public interface IChannelDao {

	ChannelBean findChannelById(int id);

	List<TimeLimitBean> queryTimeLimit(TimeLimitBean bean);

	List<ChannelServerBean> queryChannelServer(ChannelServerBean bean);

	@Deprecated
	void updateChannelBalance(ChannelBean bean);

	void updateChannelCalcBalance(int channel_id, int price);

	List<ChannelBean> queryChannel(ChannelBean bean);

	void createChannelBalanceLog(ChannelBalanceLogBean bean);
}
