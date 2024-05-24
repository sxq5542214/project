/**
 * 
 */
package com.yd.business.bill.bean;

import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LmBillModel;

/**
 * 
 */
@Alias("billModelExtendBean")
public class BillModelExtendBean extends LmBillModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6214657613529987160L;
	
	private BigDecimal quantity;
	private BigDecimal curnum;
	private String orderby;
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getCurnum() {
		return curnum;
	}
	public void setCurnum(BigDecimal curnum) {
		this.curnum = curnum;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	
}
