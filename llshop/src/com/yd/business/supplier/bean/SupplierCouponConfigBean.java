 package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("supplierCouponConfig")
public class SupplierCouponConfigBean  extends BaseBean {

	public static final Integer USE_STATUS = 1;			//使用状态
	public static final Integer MATCH_TO_PRODUCT = 99;  //该优惠卷匹配到可以展示的产品
	
	public	static final Integer COUPON_RULE_LIST_IS_ZERO = 0;

	public static final Integer COUPON_SHOW_PRODUCT_ZERO = 0 ;
	
	public static final Integer COUPON_DISCOUNT_ZERO = 0 ;
	
	public static final Integer COUPON_DISCOUNT_DIVIDE_ONE_HUNDRED = 100 ;
	public static final Integer COUPON_OFFSET_MONEY_DIVIDE_TEN = 10;
	
	public static final Integer PRICE_IS_ZERO = 0;
	
	public static final int SUBSTRING_ZEOR = 0;
	public static final int SUBSTRING_TEN = 10;

	public static final int INT_ZERO = 0 ;
	
	public static final String CONFIG_CRUX_TYPE_POP_NEWS = "pop_news"; 					
	public static final String CONFIG_CRUX_KEY_OPERATION_SUCCESS = "operation_success"; //操作成功
	public static final String CONFIG_CRUX_KEY_DATA_REPEAT = "data_repeat"; 			//数据重复
	public static final String CONFIG_CRUX_DELETE_ERROR = "delete_error";				//删除错误
	
	private Integer id;
	private Integer merchant_id;		//商户id
	private String merchant_name;	//商户名称
	private String code;			
	private Integer type;
	private String coupon_name;		//优惠卷名称
	private Integer coupon_discount;	//优惠卷折扣
	private Integer coupon_offsetmoney;	//优惠卷抵扣金钱	
	private Integer status;				//状态
	private Integer number;				//目前优惠卷剩余的数量
	private Integer receive_limit_num;	//用户领取数量限制
	private Integer coupon_count;	//优惠卷总数
	private String begin_time;		//优惠卷开始时间
	private String end_time;			//优惠卷结束时间
	private String remark;			//备注
	private String coupon_backgroup;//优惠卷背景颜色
	private String couponshow_product;//优惠卷展示的产品
	
	private String rule_name;			//优惠卷规则  	此字段是从优惠卷规则表中取
	private String reveiveResult;		//存储领取优惠卷执行结果
	private String openid;				//存储openid
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(Integer merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public Integer getCoupon_discount() {
		return coupon_discount;
	}
	public void setCoupon_discount(Integer coupon_discount) {
		this.coupon_discount = coupon_discount;
	}
	public Integer getCoupon_offsetmoney() {
		return coupon_offsetmoney;
	}
	public void setCoupon_offsetmoney(Integer coupon_offsetmoney) {
		this.coupon_offsetmoney = coupon_offsetmoney;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getReceive_limit_num() {
		return receive_limit_num;
	}
	public void setReceive_limit_num(Integer receive_limit_num) {
		this.receive_limit_num = receive_limit_num;
	}
	public Integer getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCoupon_backgroup() {
		return coupon_backgroup;
	}
	public void setCoupon_backgroup(String coupon_backgroup) {
		this.coupon_backgroup = coupon_backgroup;
	}
	public String getCouponshow_product() {
		return couponshow_product;
	}
	public void setCouponshow_product(String couponshow_product) {
		this.couponshow_product = couponshow_product;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getReveiveResult() {
		return reveiveResult;
	}
	public void setReveiveResult(String reveiveResult) {
		this.reveiveResult = reveiveResult;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
