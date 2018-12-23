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
@Alias("channelServer")
public class ChannelServerBean extends BaseBean {
	
	private Integer id;
	private Integer channel_id;
	private String channel_name;
	private String server_name;
	private String server_url;
	private Integer weight;
	


	private Integer weight_min;
	private Integer weight_max;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public String getServer_url() {
		return server_url;
	}
	public void setServer_url(String server_url) {
		this.server_url = server_url;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
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
	
}
