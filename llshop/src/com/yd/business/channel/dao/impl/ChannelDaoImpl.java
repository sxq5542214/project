/**
 * 
 */
package com.yd.business.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.channel.bean.ChannelBalanceLogBean;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelServerBean;
import com.yd.business.channel.bean.TimeLimitBean;
import com.yd.business.channel.dao.IChannelDao;

/**
 * @author ice
 *
 */
@Repository("channelDao")
public class ChannelDaoImpl extends BaseDao implements IChannelDao {
	private static final String NAMESPACE = "channel.";
	
	@Override
	public ChannelBean findChannelById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findChannelById", id);
	}
	
	@Override
	public List<TimeLimitBean> queryTimeLimit(TimeLimitBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryTimeLimit", bean);
	}
	
	@Override
	public List<ChannelServerBean> queryChannelServer(ChannelServerBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryChannelServer", bean);
	}

	@Deprecated
	@Override
	public void updateChannelBalance(ChannelBean bean) {
		sqlSessionTemplate.update(NAMESPACE+"updateChannelBalance", bean);
	}
	
	@Override
	public void updateChannelCalcBalance(int channel_id,int price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel_id", channel_id);
		map.put("price", price);
		sqlSessionTemplate.update(NAMESPACE+"updateChannelCalcBalance", map);
	}

	@Override
	public List<ChannelBean> queryChannel(ChannelBean bean) {
		
		return sqlSessionTemplate.selectList(NAMESPACE +"queryChannel", bean);
	}

	@Override
	public void createChannelBalanceLog(ChannelBalanceLogBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"createChannelBalanceLog", bean);
	}
	
	
	
	
}
