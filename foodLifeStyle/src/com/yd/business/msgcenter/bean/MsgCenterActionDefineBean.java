/**
 * 
 */
package com.yd.business.msgcenter.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("msgCenterActionDefine")
public class MsgCenterActionDefineBean extends BaseBean {

	public static final int STATUS_DISABLE = 0;
	public static final int STATUS_ENABLE = 1;
	
	public static final int CHECK_KEY_YES = 1;
	public static final int CHECK_KEY_NO = 0;

	public static final int IS_SAVE_YES = 1;
	public static final int IS_SAVE_NO = 0;

	/**
	 * 用户输入文本
	 */
	public static final String ACTION_TYPE_WECHAT_USER_INPUT = "wechat_user_input";
	/**
	 * 用户点击菜单
	 */
	public static final String ACTION_TYPE_WECHAT_USER_CLICK = "wechat_user_click";
	/**
	 * 用户关注
	 */
	public static final String ACTION_TYPE_WECHAT_USER_SUBSCRIBE = "wechat_user_subscribe";
	/**
	 * 用户关注,通过商户预约订单
	 */
	public static final String ACTION_TYPE_WECHAT_USER_SUBSCRIBE_SHOP_EFF = "wechat_user_subscribe_shop_eff";
	/**
	 * 用户关注,通过商户店铺
	 */
	public static final String ACTION_TYPE_WECHAT_USER_SUBSCRIBE_SHOP = "wechat_user_subscribe_shop";
	/**
	 * 已关注用户再次扫码
	 */
	public static final String ACTION_TYPE_WECHAT_USER_SCAN = "wechat_user_scan";
	/**
	 * 用户分享
	 */
	public static final String ACTION_TYPE_WECHAT_USER_SHARE = "wechat_user_share";
	/**
	 * 用户订购成功
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS = "wechat_user_order_success";
	/**
	 * 用户订购成功，通知父用户
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS_NOTIFY_PARENT = "wechat_user_order_success_notify_parent";
	/**
	 * 用户订购成功，通知子用户
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS_NOTIFY_CHILD = "wechat_user_order_success_notify_child";
	/**
	 * 用户订购失败
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_FAILD = "wechat_user_order_faild";
	/**
	 * 异步下单
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_ASYNC = "wechat_user_order_async";
	/**
	 * 下单预约
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_EFF = "wechat_user_order_eff";
	/**
	 * 订单支付
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_PAY = "wechat_user_order_pay";
	/**
	 * 订单支付成功通知用户好友
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_PAY_NOTIFY_FRIENDS = "wechat_user_order_pay_notify_friends";
	/**
	 * 订单发货
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_DELIVERY = "wechat_user_order_delivery";
	/**
	 * 订单催单
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_REMIND = "wechat_user_order_remind";
	/**
	 * 取消支付
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ORDER_CANCEL = "wechat_user_order_cancel";
	/**
	 * 有新的子用户
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ADD_CHILD = "wechat_user_add_child";
	/**
	 * 参与活动获得奖品
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE = "wechat_user_activity_get_prize";
	/**
	 * 参与活动获得奖品，通知好友
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE_FRIENDS = "wechat_user_activity_get_prize_friends";
	/**
	 * 参与活动获得奖品，通知好友的好友
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE_HELP_FRIEND_FRIENDS = "wechat_user_activity_get_prize_help_friend_friends";

	/**
	 * 用户订阅活动提醒
	 */
	public static final String ACTION_TYPE_WECHAT_USER_ACTIVITY_REMIND = "wechat_user_activity_remind";
	/**
	 * 用户留言新消息提醒
	 */
	public static final String ACTION_TYPE_WECHAT_USER_GET_NEW_COMMENT_REMIND = "wechat_user_get_new_comment_remind";
	/**
	 * 系统群发通知用户签到
	 */
	public static final String ACTION_TYPE_SYSTEM_SIGN_MASS = "system_sign_mass";
	/**
	 * 系统群发通知用户签到
	 */
	public static final String ACTION_TYPE_WECHAT_TEMPLATE_MSG_USER_SIGN = "wechat_template_msg_user_sign";
	/**
	 * 通道余额告警
	 */
	public static final String ACTION_TYPE_WECHAT_TEMPLATE_CHANNEL_PRICE_ALARM = "wechat_template_channel_price_alarm";

	/**
	 * 客户余额告警
	 */
	public static final String ACTION_TYPE_WECHAT_TEMPLATE_CUSTOMER_PRICE_ALARM = "wechat_template_customer_price_alarm";

	/**
	 * 客户余额添加
	 */
	public static final String ACTION_TYPE_WECHAT_TEMPLATE_CUSTOMER_PRICE_ADD = "wechat_template_customer_price_add";

	/**
	 * 通道失败告警
	 */
	public static final String ACTION_TYPE_WECHAT_TEMPLATE_CHANNEL_FAILD = "wechat_template_channel_faild";

	/**
	 * 系统群发指定文章发送时间的动作
	 */
	public static final String ACTION_TYPE_SYSTEM_ASSIGN_SEND_TIME ="system_assign_send_time";
	/**
	 * 系统给上个月这一天购买过的人群发的动作
	 */
	public static final String ACTION_TYPE_SYSTEM_ORDERBUY_MONTH ="system_orderbuy_month";
	
	/**
	 * 给访问过系统后的用户发需要的阅读
	 */
	public static final String ACTION_TYPE_SYSTEM_ACCESS_READING = "system_access_reading";

	/**
	 * 用户参与活动获得参与码
	 */
	public static final String ACTION_TYPE_SUPPLIER_GET_CODE = "supplier_get_code";

	/**
	 * 	商户给用户分配产品套餐
	 */
	public static final String ACTION_TYPE_SUPPLIER_PACKAGE_ASSIGN = "supplier_package_assign";
	/**
	 * 	商户给用户分配套餐内产品
	 */
	public static final String ACTION_TYPE_SUPPLIER_PACKAGE_UPDATE_RECORD = "supplier_package_update_record";
	/**
	 * 	商户了新增用户
	 */
	public static final String ACTION_TYPE_SUPPLIER_USER_ADD = "supplier_user_add";

	/**
	 * 一元购开奖通知的动作
	 */
	public static final String ACTION_TYPE_YYG_LOTTERY_USER_MSG = "yyg_lottery_user_msg";
	/**
	 * 一元购好友中奖通知
	 */
	public static final String ACTION_TYPE_YYG_LOTTERY_FRIENDS_MSG = "yyg_lottery_friends_msg";
	/**
	 * 一元购好友的好友中奖通知
	 */
	public static final String ACTION_TYPE_YYG_LOTTERY_FRIENDS_FIRENDS_MSG = "yyg_lottery_friends_friends_msg";
	/**
	 * 一元购商品买满通知动作
	 */
	public static final String ACTION_TYPE_YYG_BUY_COMPLETE_MSG = "yyg_buy_complete_msg";
	
	private Integer id ;
	private String action_type;
	private String action_name;
	private String parent_action_type;
	private String parent_action_name;
	private String action_key;
	private Integer status;
	private Integer check_key; //是否检查key 的转换action_type: 0不检查 1检查
	private String target_object_name; //目标对象名称
	private String target_object_sql;	//目标对象脚本
	private Integer is_save;
	
	public String getTarget_object_name() {
		return target_object_name;
	}
	public void setTarget_object_name(String target_object_name) {
		this.target_object_name = target_object_name;
	}
	public String getTarget_object_sql() {
		return target_object_sql;
	}
	public void setTarget_object_sql(String target_object_sql) {
		this.target_object_sql = target_object_sql;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public String getAction_key() {
		return action_key;
	}
	public void setAction_key(String action_key) {
		this.action_key = action_key;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCheck_key() {
		return check_key;
	}
	public void setCheck_key(Integer check_key) {
		this.check_key = check_key;
	}
	public String getParent_action_type() {
		return parent_action_type;
	}
	public void setParent_action_type(String parent_action_type) {
		this.parent_action_type = parent_action_type;
	}
	public String getParent_action_name() {
		return parent_action_name;
	}
	public void setParent_action_name(String parent_action_name) {
		this.parent_action_name = parent_action_name;
	}
	public Integer getIs_save() {
		return is_save;
	}
	public void setIs_save(Integer is_save) {
		this.is_save = is_save;
	}
	
}
