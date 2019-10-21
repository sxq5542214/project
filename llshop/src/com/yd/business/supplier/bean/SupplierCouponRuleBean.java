 package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("supplierCouponRule")
public class SupplierCouponRuleBean  extends BaseBean {
	
	public static final int STATUS_ENABLE = 1;//可以使用状态
	public static final int STATUS_DISABLE = -1;//不可以使用状态

	public static final int TYPE_SHOW = 0;	//展示优惠卷type类型
	public static final int TYPE_RECEIVE = 1;	//领取优惠卷type类型
	public static final int TYPE_USE =2;		//使用优惠卷type类型

	public static final int RULE_SQL_COUNT_MIN_VALUE = 0;	//	
	public static final int UPDATE_COUPON_MIN_NUMBER = 0 ;		//更新优惠卷最小次数
	
	//优惠卷的
	public static final String NO_USE_COUPON_SHOW = "no_use_coupon_show";			//该优惠卷不可展示此商品
	public static final String COUPON_NO_USE_RULE = "coupon_no_use_rule";			//没有使用规则
	public static final String COUPON_POP_TYPE = "coupon_pop" ;
	public static final String RECEIVE_COUPON_SUCCESS_RESULT="receive_coupon_success_result";		//优惠卷领取成功
	public static final String RECEIVE_COUPON_FAIL_RESULT = "receive_coupon_fail_result";			//优惠卷领取失败
	public static final String RECEIVE_COUPON_INTERNET_BAD = "receive_coupon_internet_bad";			//由于网络原因优惠卷领取失败
	public static final String USER_RECEIVE_COUPON_LIMIT = "user_receive_coupon_limit";				//用户领取该优惠卷已经达到上限
	public static final String COUPON_ZERO = "coupon_zero";											//优惠卷领取完了
	public static final String COUPON_CAN_USE = "coupon_can_use";									//优惠卷满足规则可以使用
	public static final String COUPON_NO_CAN_USE = "coupon_no_can_use";								//优惠卷满足规则不可以使用
	public static final String USE_COUPON_INTERNET_BAD = "use_coupon_internet_bad";			//由于网络原因优惠卷领取失败

	
	public	static final int COUPON_RULE_LIST_IS_ONE = 1;
	public	static final int COUPON_RULE_LIST_IS_ZERO = 0;

	
	public static final String CONFIG_CRUX_TYPE_POP_NEWS = "pop_news"; 					
	public static final String CONFIG_CRUX_KEY_OPERATION_SUCCESS = "operation_success"; //操作成功
	public static final String CONFIG_CRUX_KEY_DATA_REPEAT = "data_repeat"; 			//数据重复
	public static final String CONFIG_CRUX_DELETE_ERROR = "delete_error";				//删除错误
	
	private Integer id;
	private Integer coupon_id;		//优惠卷id
	private String rule_name;	//规则名称
	private String mismatch_desc;  //规则不匹配描述
	private String explain;		//规则解释
	private Integer status;		//状态
	private Integer type;		//标注是领用还是使用
	private String begin_time;	//开始时间
	private String end_time;		//截至时间
	private String SQL;			//SQL注入
	
	private String remark;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getExplain() {
		return explain;
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
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSQL() {
		return SQL;
	}
	public void setSQL(String sQL) {
		SQL = sQL;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMismatch_desc() {
		return mismatch_desc;
	}
	public void setMismatch_desc(String mismatch_desc) {
		this.mismatch_desc = mismatch_desc;
	}
	
	
}
