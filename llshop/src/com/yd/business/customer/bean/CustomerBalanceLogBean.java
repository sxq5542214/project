/**
 * 
 */
package com.yd.business.customer.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("customerBalanceLog")
public class CustomerBalanceLogBean extends BaseBean {
	private Integer id;
	private Integer customer_id;
	private String customer_name;
	private Integer get_balance;
	private Integer type;
	private String create_time;
	private Integer total_balance;
	private String out_trade_no;
	private String remark;
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
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Integer getGet_balance() {
		return get_balance;
	}
	public void setGet_balance(Integer get_balance) {
		this.get_balance = get_balance;
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
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
}
