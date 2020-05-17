package com.yd.business.user.service;

import java.util.Date;
import java.util.List;

import com.yd.business.user.bean.UserConsumeInfoBean;

public interface IUserConsumeInfoService {
	
	public static final String OUTTRADE_TYPE_PARTNER = "Partner";	//合作伙伴
	public static final String OUTTRADE_TYPE_WXPAY = "WXPay";	//微信支付
	public static final String OUTTRADE_TYPE_SHOP = "Ord";	//定单
	public static final String OUTTRADE_TYPE_SHOPONLINE = "Online";	//定单
	public static final String OUTTRADE_TYPE_SHOPEFF = "Eff";	//预订单
	public static final String OUTTRADE_TYPE_SHOPOFFLINE = "Offline";	//线下订单
	public static final String OUTTRADE_TYPE_ALIPAY = "AliPay"; //阿里支付
	public static final String OUTTRADE_TYPE_STORE = "Store"; //库存
	public static final String OUTTRADE_TYPE_CARDSECRET = "CardSecret"; //卡密
	public static final String OUTTRADE_TYPE_ALICHARGE = "AliCharge"; //阿里充值
	public static final String OUTTRADE_TYPE_USERBALANCE = "UserBalance"; //用户余额支付
	public static final String OUTTRADE_TYPE_WECHATBONUS = "WechatBouns"; //微信红包
	public static final String OUTTRADE_TYPE_SUBCOST = "SubCost"; //下级消费
	public static final String OUTTRADE_TYPE_SHAKE_GIFT = "ShakeGift"; //摇一摇活动赠送
	public static final String OUTTRADE_TYPE_SUPPLIER_BALANCE = "SupplierBalance"; //商户余额订购
	public static final String OUTTRADE_TYPE_SHAREBONUS = "ShareBouns";//分享获得红包
	
	
	String createOutTradeNo(String type, Integer userId);

	void deleteConsumeInfo(String out_trade_code);

	UserConsumeInfoBean findUserConsumeInfo(String out_trade_code);

	void updateUserConsumeInfo(UserConsumeInfoBean bean);

	UserConsumeInfoBean createConsumeInfo(String phone, Integer money,Integer sid, Integer spid, Integer user_id,
			String transactionId, String out_trade_no, String interface_type, Integer event_type);

	void updateUserConsumeInfoStatus(int status, String out_trade_no);

	List<UserConsumeInfoBean> queryUserConsumeInfo(UserConsumeInfoBean bean);

	UserConsumeInfoBean createConsumeInfo(String phone, Integer money,Integer sid, Integer spid, Integer user_id,
			String transactionId, String out_trade_no, String interface_type, Integer eff_num, Integer event_type);

	void updateUserOrderEffDate(UserConsumeInfoBean bea);

	String createOutTradeNo(String type, String userId, Date date);

	String createOutTradeNo(String type, String userId, Date date, boolean flag);

	void deleteConsumeInfoByTransactionId(String transactionId);
}
