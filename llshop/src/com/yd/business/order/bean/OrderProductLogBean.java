/**
 * 
 */
package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.business.isp.bean.InterfaceBean;
import com.yd.business.user.bean.UserConsumeInfoBean;

/**
 * @author ice
 *
 */
@Alias("orderProductLog")
public class OrderProductLogBean extends InterfaceBean {
	
	public static final String DICT_FIELD_STATUS = "status";

	/**
	 * 用户订购
	 */
	public static final int EVENT_TYPE_USER_ORDER = UserConsumeInfoBean.EVENT_TYPE_USER_ORDER;
	/**
	 * 用户订购
	 */
	public static final int EVENT_TYPE_USER_ORDER_SHOP = UserConsumeInfoBean.EVENT_TYPE_USER_ORDER_SHOP;
	/**
	 * 用户参加摇一摇活动
	 */
	public static final int EVENT_TYPE_USER_ACTIVITY_SHAKE = UserConsumeInfoBean.EVENT_TYPE_USER_ACTIVITY_SHAKE;
	/**
	 * 子用户消费
	 */
	public static final int EVENT_TYPE_USER_SUBCOST = UserConsumeInfoBean.EVENT_TYPE_USER_SUBCOST;
	/**
	 * 用户微信提现
	 */
	public static final int EVENT_TYPE_USER_WECHAT_BOUNS = UserConsumeInfoBean.EVENT_TYPE_USER_WECHAT_BOUNS;
	/**
	 * 红包返还，现金收入
	 */
	public static final int EVENT_TYPE_USER_BALANCE_INCOME = UserConsumeInfoBean.EVENT_TYPE_USER_BALANCE_INCOME;
	/**
	 * 预约多月的后续几个月
	 */
	public static final int EVENT_TYPE_USER_ORDER_EFF = UserConsumeInfoBean.EVENT_TYPE_USER_ORDER_EFF;
	
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_CANCEL = 2;
	public static final int STATUS_PAYSUCCESS = 3;
	public static final int STATUS_ORDERING = 4;
	public static final int STATUS_ALREADY_DELIVERY = 5; //已发货，待收货
	public static final int STATUS_NEED_AGAIN_ORDER = 6; //余额不足,链接超时类似情况需要在此订购
	public static final int STATUS_FAILD = -1;
	public static final int STATUS_UNFIND_CHANNEL = -2; //未找到通道
	public static final int STATUS_USER_DELETE = -3; //用户删除


	public static final int IS_SENDED_SUCCESS_BALANCE = 2;//红包已发送(余额)
	public static final int IS_SENDED_SUCCESS_HONBAO = 1;//红包已发送(红包)
	public static final int IS_SENDED_WAIT = 0;//红包未发送
	public static final int IS_SENDED_FAILD = -1;//红包发送失败
	public static final int IS_SENDED_NO = -2;//不发送红包
	
	
	public static final int NUMBER_ZEOR = 0;

	
	private Integer id;
	private Integer supplier_id;
	private String supplier_name;
	private Integer supplier_product_id;
	private String product_name;
	private Integer product_price;
	private String order_code;
	private String order_account;
	private String create_time;
	private String modify_time;
	private Integer cost_price; //花费总价
	private Integer cost_points;//花费的积分
	private Integer cost_money; //花费的现金（如支付宝或微信充值的）
	private Integer cost_balance;//花费的余额
	private Integer admin_id;
	private Integer user_id;
	private Integer channel_id;
	private Integer event_type;
	private Integer lucky_money;//幸运红包(分为单位)
	private String share_time;//分享时间
	private Integer share_type;//分享类型
	private Integer is_sended;//红包是否发送
	
	private String notInStatus;
	private String money;
	private String nick_name;
	private String reason;
	private String lastmonthbegin;	//上个月的开始时间
	private String lastmonthend; 	//上个月的结束时间s
	
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
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getOrder_account() {
		return order_account;
	}
	public void setOrder_account(String order_account) {
		this.order_account = order_account;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getCost_price() {
		return cost_price;
	}
	public void setCost_price(Integer cost_price) {
		this.cost_price = cost_price;
	}
	public Integer getCost_points() {
		return cost_points;
	}
	public void setCost_points(Integer cost_points) {
		this.cost_points = cost_points;
	}
	public Integer getCost_money() {
		return cost_money;
	}
	public void setCost_money(Integer cost_money) {
		this.cost_money = cost_money;
	}
	public Integer getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getCost_balance() {
		return cost_balance;
	}
	public void setCost_balance(Integer cost_balance) {
		this.cost_balance = cost_balance;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public Integer getEvent_type() {
		return event_type;
	}
	public void setEvent_type(Integer event_type) {
		this.event_type = event_type;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getNotInStatus() {
		return notInStatus;
	}
	public void setNotInStatus(String notInStatus) {
		this.notInStatus = notInStatus;
	}

	public Integer getLucky_money() {
		return lucky_money;
	}
	public void setLucky_money(Integer lucky_money) {
		this.lucky_money = lucky_money;
	}
	public String getShare_time() {
		return share_time;
	}
	public void setShare_time(String share_time) {
		this.share_time = share_time;
	}
	public Integer getShare_type() {
		return share_type;
	}
	public void setShare_type(Integer share_type) {
		this.share_type = share_type;
	}
	public Integer getIs_sended() {
		return is_sended;
	}
	public void setIs_sended(Integer is_sended) {
		this.is_sended = is_sended;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLastmonthbegin() {
		return lastmonthbegin;
	}
	public void setLastmonthbegin(String lastmonthbegin) {
		this.lastmonthbegin = lastmonthbegin;
	}
	public String getLastmonthend() {
		return lastmonthend;
	}
	public void setLastmonthend(String lastmonthend) {
		this.lastmonthend = lastmonthend;
	}
	
}
