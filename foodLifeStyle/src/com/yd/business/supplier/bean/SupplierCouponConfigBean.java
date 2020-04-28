 package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("supplierCouponConfig")
public class SupplierCouponConfigBean  extends BaseBean {

	public static final int STATUS_UP = 1;			//可用
	public static final int STATUS_DOWN = -1;			//不可用
	
	/**
	 * 代金卷
	 */
	public static final int TYPE_CASH = 1;			//代金券
	/**
	 * 折扣卷
	 */
	public static final int TYPE_DISCOUNT = 2;			//折扣券
	/**
	 * 礼品卷
	 */
	public static final int TYPE_GIFT = 3;			//礼品卷
	/**
	 * 换购卷
	 */
	public static final int TYPE_CHANGE = 4;			//换购卷
	/**
	 * 体验卷
	 */
	public static final int TYPE_EXPERIENCE = 5;			//体验卷
	
	public static final int DISPLAY_YES = 1;
	public static final int DISPLAY_NO = 0;
	
	public static final int MATCH_TO_PRODUCT = 99;  //该优惠卷匹配到可以展示的产品
	
	public	static final int COUPON_RULE_LIST_IS_ZERO = 0;

	public static final int COUPON_SHOW_PRODUCT_ZERO = 0 ;
	
	public static final int COUPON_DISCOUNT_ZERO = 0 ;
	
	public static final int COUPON_DISCOUNT_DIVIDE_ONE_HUNDRED = 100 ;
	public static final int COUPON_OFFSET_MONEY_DIVIDE_TEN = 10;
	
	public static final int PRICE_IS_ZERO = 0;
	
	public static final int SUBSTRING_ZEOR = 0;
	public static final int SUBSTRING_TEN = 10;

	public static final int INT_ZERO = 0 ;
	
	public static final String CONFIG_CRUX_TYPE_POP_NEWS = "pop_news"; 					
	public static final String CONFIG_CRUX_KEY_OPERATION_SUCCESS = "operation_success"; //操作成功
	public static final String CONFIG_CRUX_KEY_DATA_REPEAT = "data_repeat"; 			//数据重复
	public static final String CONFIG_CRUX_DELETE_ERROR = "delete_error";				//删除错误
	
	private Integer id;
	private Integer supplier_id;		//商户id
	private String supplier_name;	//商户名称
	private String code;			
	private Integer type;
	private String coupon_name;		//优惠卷名称
	private Integer coupon_discount;	//优惠卷折扣
	private Integer coupon_offsetmoney;	//优惠卷抵扣金钱	
	private Integer status;				//状态
	private Integer display;			//是否展示
	private Integer number;				//目前优惠卷剩余的数量
	private Integer receive_limit_num;	//用户领取数量限制
	private Integer coupon_count;	//优惠卷总数
	private String begin_time;		//优惠卷开始时间
	private String end_time;			//优惠卷结束时间
	private Integer useful_lift;	//使用期限，小时为单位
	private String remark;			//备注
	private String coupon_backgroup;//优惠卷背景颜色
	private String coupon_spid;//优惠卷对应的产品ID，逗号隔开
	private Integer seq;	//顺序
	
	private String rule_name;			//优惠卷规则  	此字段是从优惠卷规则表中取
	private String reveiveResult;		//存储领取优惠卷执行结果
	private String openid;				//存储openid
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
	public String getCoupon_spid() {
		return coupon_spid;
	}
	public void setCoupon_spid(String coupon_spid) {
		this.coupon_spid = coupon_spid;
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getUseful_lift() {
		return useful_lift;
	}
	public void setUseful_lift(Integer useful_lift) {
		this.useful_lift = useful_lift;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	
}
