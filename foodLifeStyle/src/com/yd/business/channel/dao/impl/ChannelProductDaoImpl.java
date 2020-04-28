/**
 * 
 */
package com.yd.business.channel.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.channel.bean.ChannelCustomerBean;
import com.yd.business.channel.bean.ChannelProductBean;
import com.yd.business.channel.bean.ChannelProductParamBean;
import com.yd.business.channel.dao.IChannelProductDao;

/**
 * @author ice
 *
 */
@Repository("channelProductDao")
public class ChannelProductDaoImpl extends BaseDao implements IChannelProductDao {
	private static final String NAMESPACE = "channelProduct.";
	
	@Override
	public List<ChannelProductBean> queryChannelProduct(ChannelProductBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryChannelProduct", bean);
	}
	
	
	@Override
	public List<ChannelCustomerBean> queryChannelCustomer(ChannelCustomerBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryChannelCustomer", bean);
	}
	
	@Override
	public List<ChannelProductParamBean> queryChannelProductParam(ChannelProductParamBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryChannelProductParam", bean);
	}
	@Override
	public void updateChannelProduct(ChannelProductBean bean) {
		sqlSessionTemplate.update(NAMESPACE +"updateChannelProduct", bean);
	}
	
	@Override
	public void updateChannelCustomer(ChannelCustomerBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateChannelCustomer", bean);
	}
}
