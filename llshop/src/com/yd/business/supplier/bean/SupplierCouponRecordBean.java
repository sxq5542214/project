package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("supplierCouponRecord")
public class SupplierCouponRecordBean  extends BaseBean {
	
	public static final Integer USE_STATUS = 1;//可以使用状态
	public static final Integer USERED_STATUS = 0;//该优惠卷已经使用

	public static final String  USER_STATUS_DESCRIPTION = "拥有优惠卷可以使用";//可以使用状态
	public static final String  USERED_STATUS_DESCRIPTION = "该优惠卷已经使用";//可以使用状态

	public static final Integer USE_NO_COUPON_COUNT = 0 ;	//用户没有优惠总数为0
	public static final Integer LIST_SIZE_ZERO = 0 ;	//list为0
	
	public static final Integer COUNT_NUMBER_ZERO = 0 ;	//count总数为0
	
	public static final int SUBTRING_NUMBER_ZERO = 0 ; //substring方法中用到的数字0
	public static final int SUBTRING_NUMBER_TEN = 10 ; //substring方法中用到的数字10

	
	public static final String CONFIG_CRUX_TYPE_POP_NEWS = "pop_news"; 					
	public static final String CONFIG_CRUX_KEY_OPERATION_SUCCESS = "operation_success"; //操作成功
	public static final String CONFIG_CRUX_KEY_DATA_REPEAT = "data_repeat"; 			//数据重复
	public static final String CONFIG_CRUX_DELETE_ERROR = "delete_error";				//删除错误
	
	private Integer id;				//序列号
	private Integer userid;			//用户id
	private Integer supplier_id;		//产品id
	private String supplier_name;//产品名称
	private Integer coupon_id;		//优惠卷id
	private Integer order_id;		//优惠卷使用规则id
	private String order_codé;		//订单编号
	private String product_name;	//产品名称
	private String create_time;	//用户领用优惠卷时间
	private String modify_time;	//修改时间
	private String use_time;		//用户使用优惠卷时间
	private String expire_time;	//优惠卷到期时间
	private int status;			//状态
	private String status_description;	//状态描述
	private String remark;		//备注信息
	private String order_code;	//订单编号
	
	//关联ll_coupon_config表查询用到的字段
	private String merchant_name;		//商户名称
	private String coupon_name;			//优惠卷名称
	private int coupon_offsetmoney;		//优惠卷抵扣现金
	private int coupon_discount;		//优惠卷折扣
	private String coupon_backgroup;	//背景颜色
	private String couponshow_product;	//优惠卷可以展示的商品
	
	//关联ll_coupon_record表查询用到的字段
	private String rule_name ;			//规则名称
	
	private String  reveiveresult ;		//领取优惠卷结果
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getUse_time() {
		return use_time;
	}
	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}
	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatus_description() {
		return status_description;
	}
	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public int getCoupon_offsetmoney() {
		return coupon_offsetmoney;
	}
	public void setCoupon_offsetmoney(int coupon_offsetmoney) {
		this.coupon_offsetmoney = coupon_offsetmoney;
	}
	public int getCoupon_discount() {
		return coupon_discount;
	}
	public void setCoupon_discount(int coupon_discount) {
		this.coupon_discount = coupon_discount;
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
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getOrder_codé() {
		return order_codé;
	}
	public void setOrder_codé(String order_codé) {
		this.order_codé = order_codé;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getReveiveresult() {
		return reveiveresult;
	}
	public void setReveiveresult(String reveiveresult) {
		this.reveiveresult = reveiveresult;
	}
	
	
	
}
