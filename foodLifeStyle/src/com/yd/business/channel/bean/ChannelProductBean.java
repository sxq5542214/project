/**
 * 
 */
package com.yd.business.channel.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("channelProduct")
public class ChannelProductBean extends ChannelCustomerBean {
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	private Integer product_id;
	private String product_name;
	private Integer status;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
}
