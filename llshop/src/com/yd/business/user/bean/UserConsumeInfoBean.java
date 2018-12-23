/**
 * 
 */
package com.yd.business.user.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("userConsumeInfo")
public class UserConsumeInfoBean extends BaseBean {

	public static final String INTERFACETYPE_WEICHAT = "微信支付";
	public static final String INTERFACETYPE_ALIPAY = "支付宝支付";
	public static final String INTERFACETYPE_USERBALANCE = "用户余额支付";
	public static final String INTERFACETYPE_WEICHATANDBALANCE = "微信+余额支付";
	public static final String INTERFACETYPE_SHAKE_GIFT = "摇一摇活动赠送";
	public static final String INTERFACETYPE_SHARE_LUCKYMONEY = "充值分享赠送";
	public static final String INTERFACETYPE_WECHATBONUS_CH= "微信红包";
	public static final String INTERFACETYPE_COUPONPAY = "优惠卷支付";
	
	/**
	 * 用户订购
	 */
	public static final int EVENT_TYPE_USER_ORDER = 1;
	/**
	 * 用户参加摇一摇活动
	 */
	public static final int EVENT_TYPE_USER_ACTIVITY_SHAKE = 2;
	/**
	 * 子用户消费
	 */
	public static final int EVENT_TYPE_USER_SUBCOST = 3;
	/**
	 * 用户微信提现
	 */
	public static final int EVENT_TYPE_USER_WECHAT_BOUNS = 4;
	/**
	 * 红包返还，现金收入
	 */
	public static final int EVENT_TYPE_USER_BALANCE_INCOME = 5;
	/**
	 * 预约多月的后续几个月
	 */
	public static final int EVENT_TYPE_USER_ORDER_EFF = 6;

	/**
	 * 商城用户订购
	 */
	public static final int EVENT_TYPE_USER_ORDER_SHOP = 8;
	/**
	 * 用户使用优惠卷消费
	 */
	public static final int EVENT_TYPE_USER_COUPON = 7;
	
	/**
	 * 商户卡密
	 */
	public static final int EVENT_TYPE_SUPPLIER_CARDSECRET = 201;
	/**
	 * 商户余额
	 */
	public static final int EVENT_TYPE_SUPPLIER_BALANCE = 202;
	/**
	 * 商户库存
	 */
	public static final int EVENT_TYPE_SUPPLIER_STORENUM = 203;

	/**
	 * 商户充值
	 */
	public static final int EVENT_TYPE_SUPPLIER_CHARGE = 204;

	/**
	 * 商户订购
	 */
	public static final int EVENT_TYPE_SUPPLIER_ORDER = 205;

	public static final int STATUS_WAIT = 0;
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_CANCEL = 2;
	
	private Integer id;
	private String phone;
	private Integer money;
	private String create_date;
	private Integer supplier_product_id;
	private Integer user_id;
	private String transaction_id;
	private String out_trade_code;
	private String interface_type;
	private Integer status;
	private Integer eff_num;
	private Integer event_type;
	
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
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getEff_num() {
		return eff_num;
	}
	public void setEff_num(Integer eff_num) {
		this.eff_num = eff_num;
	}
	public Integer getEvent_type() {
		return event_type;
	}
	public void setEvent_type(Integer event_type) {
		this.event_type = event_type;
	}
	
	
}
