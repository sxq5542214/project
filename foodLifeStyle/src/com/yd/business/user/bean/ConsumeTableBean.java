/**
 * 
 */
package com.yd.business.user.bean;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
public class ConsumeTableBean extends BaseBean {

	private Integer id;
	private Double money;//金额 
	private String date;//交易时间 
	private Integer buyCount;//拍购次数  
	private Integer userId;//用户ID  
	private String transactionId;//充值单号
	private String outTradeNo;//消费单号
	private String interfaceType;//接口类型 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	
}
