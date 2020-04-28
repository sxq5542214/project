/**
 * 
 */
package com.yd.business.customer.bean;

import org.apache.ibatis.type.Alias;

import com.yd.business.user.bean.UserConsumeInfoBean;

/**
 * @author ice
 *
 */
@Alias("customerConsumeInfo")
public class CustomerConsumeInfoBean extends UserConsumeInfoBean {

	public static final String INTERFACETYPE_WEICHAT = "微信支付";
	public static final String INTERFACETYPE_ALIPAY = "支付宝支付";
	
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_CANCEL = 2;
	
	
	private Integer id;
	private Integer money;
	private String create_date;
	private String finish_date;
	private Integer customer_id;
	private String transaction_id;
	private String out_trade_code;
	private String interface_type;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getFinish_date() {
		return finish_date;
	}
	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_code() {
		return out_trade_code;
	}
	public void setOut_trade_code(String out_trade_code) {
		this.out_trade_code = out_trade_code;
	}
	public String getInterface_type() {
		return interface_type;
	}
	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
