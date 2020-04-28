/**
 * 
 */
package com.yd.business.channel.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("channelCustomer")
public class ChannelCustomerBean extends BaseBean {
	
	public static int TIME_LIMIT_TRUE = 1;
	public static int TIME_LIMIT_FALSE = 0;
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	private Integer id;
	private Integer customer_id;
	private Integer channel_id;
	private String channel_name;
	private Integer weight;
	private String province;
	private Integer product_brand;
	private Integer time_limit;
	private Integer status;
	

	private Integer weight_min;
	private Integer weight_max;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getTime_limit() {
		return time_limit;
	}
	public void setTime_limit(Integer time_limit) {
		this.time_limit = time_limit;
	}
	public Integer getWeight_min() {
		return weight_min;
	}
	public void setWeight_min(Integer weight_min) {
		this.weight_min = weight_min;
	}
	public Integer getWeight_max() {
		return weight_max;
	}
	public void setWeight_max(Integer weight_max) {
		this.weight_max = weight_max;
	}
	public Integer getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(Integer product_brand) {
		this.product_brand = product_brand;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
