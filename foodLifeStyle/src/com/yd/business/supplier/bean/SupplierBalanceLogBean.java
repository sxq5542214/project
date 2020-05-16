/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("supplierBalanceLog")
public class SupplierBalanceLogBean extends BaseBean {
	public static final int TYPE_USER_PAYDIRECT = 1;	//用户直接支付，未选商品
	public static final int TYPE_USER_RECHARGE = 2;	//用户充值余额支付，获得赠送
	public static final int TYPE_USER_SHOPORDER = 3;	//用户店铺购买商品支付
	public static final int TYPE_USER_SHOPORDER_LOCAL = 4;	//用户在本地店铺购买商品支付，无需配送
	public static final int TYPE_SUPPLIER_DEPOSIT = 7;	//添加保证金
	public static final int TYPE_SUPPLIER_CASHOUT = 9; //商户提现
	
	
	private Integer id ;
	private Integer supplier_id;
	private String order_code;
	private String openid;
	private Integer add_balance;
	private Integer total_balance;
	private Integer service_fee;
	private Integer charge_rate;
	private Integer type;
	private String create_time;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getAdd_balance() {
		return add_balance;
	}
	public void setAdd_balance(Integer add_balance) {
		this.add_balance = add_balance;
	}
	public Integer getTotal_balance() {
		return total_balance;
	}
	public void setTotal_balance(Integer total_balance) {
		this.total_balance = total_balance;
	}
	public Integer getService_fee() {
		return service_fee;
	}
	public void setService_fee(Integer service_fee) {
		this.service_fee = service_fee;
	}
	public Integer getCharge_rate() {
		return charge_rate;
	}
	public void setCharge_rate(Integer charge_rate) {
		this.charge_rate = charge_rate;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
