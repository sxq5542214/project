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
@Alias("channelBalanceLog")
public class ChannelBalanceLogBean extends BaseBean {
	private Integer id;
	private Integer channel_id;
	private String channel_name;
	private Integer get_balance;
	private String out_trade_no;
	private Integer type;
	private String create_time;
	private Integer total_balance;
	private String remark;
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
	public Integer getGet_balance() {
		return get_balance;
	}
	public void setGet_balance(Integer get_balance) {
		this.get_balance = get_balance;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getTotal_balance() {
		return total_balance;
	}
	public void setTotal_balance(Integer total_balance) {
		this.total_balance = total_balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
