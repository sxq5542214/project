/**
 * 
 */
package com.yd.business.channel.runable;

import com.yd.basic.framework.runable.BaseRunable;
import com.yd.business.channel.bean.ChannelServerBean;
import com.yd.util.HttpUtil;

/**
 * 这个类用于访问通道服务
 * @author ice
 *
 */
public class AccessChannelServerRunable extends BaseRunable {
	
	private ChannelServerBean channelServer;
	private String phone;
	private int product_id;
	
	public AccessChannelServerRunable(ChannelServerBean channelServer,String phone,int product_id){
		this.channelServer = channelServer;
		this.phone = phone;
		this.product_id = product_id;
		
	}
	
	@Override
	public void runMethod() {
		
		

	}

}
