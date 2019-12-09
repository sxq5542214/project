/**
 * 
 */
package com.yd.business.other.constant;

/**
 * @author ice
 *
 */
public interface AttributeConstant {
	
	public static final String CODE_WECHATSERVERURL = "wechat_server_url";
	public static final String CODE_IMAGESERVERURL = "image_server_url";
	public static final String CODE_IMAGESERVERURL_POST = "image_server_url_post";
	
	/**
	 * 微信菜单跳转前缀
	 */
	public static final String CODE_WECHAT_MENU_JUMP_SERVER = "wechat_menu_jump_server";

//	public static final String CODE_QRCODE = "QrCode";
	public static final String CODE_OAUTH = "oauth2.0";
	public static final String CODE_SERVER_URL = "server_url";
	public static final String CODE_DEFAULT_MCH_NAME = "mch_name";
	/**
	 * 系统文章前缀
	 */
	public static final String CODE_ARTICLE_URL_PRE = "article_url_pre";

	/**
	 * 创建红包的最小金额
	 */
	public static final String CODE_BWC_CREATE_MIN_PRICE = "bwc_create_min_price";
	/**
	 * 创建红包的最大中奖人数
	 */
	public static final String CODE_BWC_MAX_WIN_BONUS_PERCENT = "bwc_max_win_bonus_percent";
	/**
	 * 创建福分的最大中奖人数
	 */
	public static final String CODE_BWC_MAX_WIN_POINT_PERCENT = "bwc_max_win_point_percent";
	
	public static final String CODE_USER_MONEY_NOUPLOAD = "user_money_noupload";
	public static final String CODE_USER_MONEY_SUCCESS = "user_money_success";
	public static final String CODE_USER_JOIN_ACTIVITY_SUCCESS = "user_join_activity_success";
	public static final String CODE_USER_JOIN_ACTIVITY_MAX = "user_join_activity_max";
	public static final String CODE_USER_JOIN_ACTIVITY_ALREADY = "user_join_activity_already";
	public static final String CODE_USER_JOIN_ACTIVITY_NO_OFFLINE = "user_join_activity_no_offline";

	public static final String CODE_ACTIVITY_RUNNING = "activity_running";
	public static final String CODE_SYSTEM_IS_SHOP_ORDER = "system_is_shop_order";
	
	public static final String CODE_CHANNEL_CHECK_COUNT = "channel_check_count";
	public static final String CODE_CHANNEL_CHECK_INTERVAL_TIME ="channel_check_interval_time";
	
	// 微信相关的
	public static final String CODE_WECHAT_USER_SUBSCRIBE_WELCOM = "user_subscribe_welcom";
	public static final String CODE_WECHAT_USER_ORDER_SUCCESS = "wechat_user_order_success";
	public static final String CODE_WECHAT_USER_ORDER_ASYNC_SUCCESS = "wechat_user_order_async_success";
	public static final String CODE_WECHAT_USER_ORDER_FAILD = "wechat_user_order_faild";
	public static final String CODE_WECHAT_USER_ORDER_EFF_SUCCESS = "wechat_user_order_eff_success";
	public static final String CODE_WECHAT_USER_ORDER_SUCCESS_PARENT = "wechat_user_order_success_parent";
	public static final String CODE_WECHAT_USER_ORDER_SUCCESS_CHILD = "wechat_user_order_success_child";
	public static final String CODE_WECHAT_USER_UNSUBSCRIBE = "user_unsubscribe";
	public static final String CODE_WECHAT_USER_ORDER_NOPAY_REFOUND = "user_order_nopay_refound";
	public static final String CODE_WECHAT_USER_ACCESS_WITHIN_48HOUR= "user_access_within_48hour";
	public static final String CODE_WECHAT_BONUS_TIPS= "wechat_bonus_tips";
	public static final String CODE_WECHAT_USER_ORDER_ERROR = "user_order_error";
	public static final String CODE_WECHAT_TEMPLATE_MSG_USER_SIGN = "wechat_template_msg_user_sign";
	
	public static final String CODE_WECHAT_USER_WIN_PARENT = "user_win_parent";
	public static final String CODE_WECHAT_USER_WIN_PARENT_FRIENDS = "user_win_parent_firends";
	public static final String CODE_WECHAT_USER_WIN_CHILD = "user_win_child";
	public static final String CODE_WECHAT_USER_WIN_GRANDSON = "user_win_grandson";
	public static final String CODE_WECHAT_USER_WIN_UNCLES = "user_win_uncles";

	public static final String CODE_WECHAT_USER_TEXT_NOFIND = "wechat_user_text_nofind";

	public static final String CODE_WECHAT_SUBCRIBE_INITPOINT = "wechat_subcribe_initpoint";
	public static final String CODE_WECHAT_SUBCRIBE_PARENTADDPOINT = "wechat_subcribe_parentaddpoint";
	public static final String CODE_WECHAT_SUBCRIBE_INITPROMOTIONPOINT = "wechat_subcribe_initpromotionpoint";
	public static final String CODE_WECHAT_SUBCRIBE_PARENTPROMOTIONADDPOINT = "wechat_subcribe_parentpromotionaddpoint";

	public static final String CODE_WECHAT_SUBCRIBE_ADDCHILD = "wechat_subcribe_addchild";
	public static final String CODE_WECHAT_LONGTIME_NOACCESS = "wechat_longtime_noaccess";

	public static final String CODE_WECHAT_NOTIFY_MANAGER_SENDREDPACK = "wechat_notify_manager_sendredpack";
	/**
	 * 普通红包的请求URL
	 */
	public static final String CODE_PAY_WECHAT_BONUS_URL = "pay_wechat_bonus_url";
	/**
	 * 裂变红包的请求URL
	 */
	public static final String CODE_PAY_WECHAT_GROUP_BONUS_URL = "pay_wechat_group_bonus_url";

	public static final String CODE_WECHAT_DEFAULT_SHARE_URL = "wechat_default_share_url";
	public static final String CODE_WECHAT_DEFAULT_SHARE_TITLE = "wechat_default_share_title";
	public static final String CODE_WECHAT_USER_SIGN_LAST_48HOUR_SUCCESS = "wechat_user_sign_last_48hour_success";
	public static final String CODE_WECHAT_USER_PAIED_REMIND_TAKE_RED = "wechat_user_paied_remind_take_red";


	//支付成功回调URL
	public static final String CODE_PAY_WECHAT_NOTIFY_URL = "pay_wechat_notify_url";
	public static final String CODE_PAY_WECHAT_UNIFIED_URL = "pay_wechat_unified_url";
	
	public static final String CODE_TRADE_TYPE = "pay_wechat_trade_type";
	
	
	
// 支付宝相关的	

	public static final String CODE_ALIPAY_PARTNER_KEY = "alipay_partner_key";
	public static final String CODE_ALIPAY_PARTNER_ID = "alipay_partner_id";
	public static final String CODE_ALIPAY_NOTIFY_URL = "alipay_notify_url";
	public static final String CODE_ALIPAY_RETURN_URL = "alipay_return_url";

	//yd  app key
	public static final String CODE_YD_APPKEY = "yd_appkey";
	public static final String CODE_YD_LOGIN_NO = "yd_login_no";
	public static final String CODE_YD_USER_NAME = "yd_user_name";
	public static final String CODE_YD_VERSION = "yd_version";
	public static final String CODE_YD_BASEURL = "yd_baseurl";
	public static final String CODE_YD_PRIVATEKEY = "yd_privatekey";
	// 1正式 2沙箱
	public static final String CODE_YD_ENVIRONMENT = "yd_environment";

	//dx
	public static final String CODE_DX_AH_ORDER_URL = "dx_ah_order_url"; //定购URL
	public static final String CODE_DX_AH_QUERY_URL = "dx_ah_query_url"; //定购URL
	public static final String CODE_DX_AH_QDID = "dx_ah_qdid"; //渠道ID
	public static final String CODE_DX_AH_SQNID = "dx_ah_sqnid"; //产品ID
	public static final String CODE_DX_AH_QDKEY = "dx_ah_qdkey"; //渠道密钥

	//dx
	public static final String CODE_DX_AH_FY_QDID = "dx_ah_fy_qdid"; //阜阳电信渠道ID
	public static final String CODE_DX_AH_FY_QDKEY = "dx_ah_fy_qdkey"; //阜阳电信渠道密钥

	//云掌通
	public static final String CODE_THIRDPARTY_YUNZHANGTONG_APPKEY = "thirdparty_yunzhangtong_appkey"; //云掌通APPkey
	public static final String CODE_THIRDPARTY_YUNZHANGTONG_APPSECRET = "thirdparty_yunzhangtong_appsecret"; //云掌通appsecret
	public static final String CODE_THIRDPARTY_YUNZHANGTONG_ORDER_URL = "thirdparty_yunzhangtong_order_url"; //云掌通订购URL
	public static final String CODE_THIRDPARTY_YUNZHANGTONG_QUERY_URL = "thirdparty_yunzhangtong_query_url"; //云掌通查询URL
	//大汉三通
	public static final String CODE_THIRDPARTY_DAHANSANTONG_ACCOUNT = "thirdparty_dahansantong_account"; //大汉三通账号
	public static final String CODE_THIRDPARTY_DAHANSANTONG_PWD = "thirdparty_dahansantong_pwd"; //大汉三通密码
	public static final String CODE_THIRDPARTY_DAHANSANTONG_ORDER_URL = "thirdparty_dahansantong_order_url"; //大汉三通订购URL
	public static final String CODE_THIRDPARTY_DAHANSANTONG_QUERY_URL = "thirdparty_dahansantong_query_url"; //大汉三通查询URL
	//弯流科技
	public static final String CODE_THIRDPARTY_WANLIUKEJI_APIKEY = "thirdparty_wanliukeji_apikey"; //弯流科技APIKEY
	public static final String CODE_THIRDPARTY_WANLIUKEJI_ACCOUNT = "thirdparty_wanliukeji_account"; //弯流科技账号
	public static final String CODE_THIRDPARTY_WANLIUKEJI_PWD = "thirdparty_wanliukeji_pwd"; //弯流科技密码
	public static final String CODE_THIRDPARTY_WANLIUKEJI_ORDER_URL = "thirdparty_wanliukeji_order_url"; //弯流科技订购URL
	public static final String CODE_THIRDPARTY_WANLIUKEJI_ORDER_QUERY_URL = "thirdparty_wanliukeji_order_query_url"; //弯流科技查询订单URL
	public static final String CODE_THIRDPARTY_WANLIUKEJI_QUERY_BALANCE_URL = "thirdparty_wanliukeji_query_balance_url"; //弯流科技查询余额URL

	//卓威流量
	public static final String CODE_THIRDPARTY_ZHUOWEI_EC_CODE = "thirdparty_zhuowei_ec_code";
	public static final String CODE_THIRDPARTY_ZHUOWEI_KEY = "thirdparty_zhuowei_key";
	public static final String CODE_THIRDPARTY_ZHUOWEI_ORDER_URL = "thirdparty_zhuowei_order_url";
	public static final String CODE_THIRDPARTY_ZHUOWEI_QUERY_URL = "thirdparty_zhuowei_query_url";
	
	//南京盛世流量
	public static final String CODE_THIRDPARTY_NANJINGSHENGSHI_AUTH_CODE = "thirdparty_nanjingshengshi_auth_code";
	public static final String CODE_THIRDPARTY_NANJINGSHENGSHI_AUTH_KEY = "thirdparty_nanjingshengshi_auth_key";
	public static final String CODE_THIRDPARTY_NANJINGSHENGSHI_ORDER_URL = "thirdparty_nanjingshengshi_order_url";
	public static final String CODE_THIRDPARTY_NANJINGSHENGSHI_QUERY_URL = "thirdparty_nanjingshengshi_query_url";
	
	//杰拓流量
	public static final String CODE_THIRDPARTY_JIETUO_PARTNER_NO = "thirdparty_jietuo_partner_no";
	public static final String CODE_THIRDPARTY_JIETUO_CONTRACT_ID = "thirdparty_jietuo_contract_id";
	public static final String CODE_THIRDPARTY_JIETUO_KEY = "thirdparty_jietuo_key";
	public static final String CODE_THIRDPARTY_JIETUO_VI = "thirdparty_jietuo_vi";
	public static final String CODE_THIRDPARTY_JIETUO__ORDER_URL = "thirdparty_jietuo_order_url";
	public static final String CODE_THIRDPARTY_JIETUO__QUERY_URL = "thirdparty_jietuo_query_url";
	public static final String CODE_THIRDPARTY_JIETUO__NOTIFY_URL = "thirdparty_jietuo_notify_url";
		
		
	
	
	public static final String CODE_PARTNER_INTERFACE_HASINVALIDDAY = "partner_interface_hasInvalidDay"; // 伙伴接口是否有不可调用日期

	//用户相关
	public static final String CODE_USER_LEVELUP_VIP_NUM = "user_levelup_vip_num"; //普通用户升级到VIP需要的数量
	public static final String CODE_USER_REBATE_TYPE_LEVEL = "user_rebate_type_level"; //根据用户类型和级别进行返利
	public static final String CODE_USER_SIGN_MIN_POINTS = "user_sign_min_points"; //用户签到最小获得的积分 
	public static final String CODE_USER_SIGN_MAX_POINTS = "user_sign_max_points"; //用户签到最大获得的积分 
	public static final String CODE_USER_SHARE_ADD_POINTS = "user_share_add_points"; //用户分享后获得的积分 


	//订单的
	public static final String CODE_SHOP_ORDER_EXPRESS_PRICE = "shop_order_express_price"; // 运费价格
	public static final String CODE_SHOP_ORDER_NEED_EXPRESS_BOTTOM_PRICE = "shop_order_need_express_bottom_price"; // 触发需运费的订单价格
	
	
	
	//短信的
	public static final String CODE_SMS_REMARK_INFO = "sms_remark_info";
	
	// 开采网的开奖接口URL
	public static final String CODE_LOTTERY_API_URL = "lottery_api_url";

	//体彩初始化金额
	public static final String CODE_LOTTERY_SPORT_INIT_MONEY = "lottery_sport_init_money";
	//体彩多少次开始计算保本
	public static final String CODE_LOTTERY_SPORT_BREAK_EVEN = "lottery_sport_break_even";
	//体彩最多连续亏本次数
	public static final String CODE_LOTTERY_SPORT_MAX_LOSS_DAYS = "lottery_sport_max_loss_days";
	//体彩计算保本后赚的倍数
	public static final String CODE_LOTTERY_SPORT_LOSS_MULTIPLE = "lottery_sport_loss_multiple";
	
	
	
}
