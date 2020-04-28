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
@Alias("userSenceLog")
public class UserSenceLog extends BaseBean {
	
	public static int SHARE_TYPE_FIREND_ONE = 1;//朋友
	public static int SHARE_TYPE_FIREND_LINE = 2;//朋友圈
	
	public static final String SHARE_FROM_ORDER_PRODUCT = "order_product";
	public static final String SHARE_FROM_FIRST_ORDER_PRODUCT = "first_order_product";
	public static final String SHARE_FROM_OLYMPIC_ACTIVITY = "olympic_activity";
	public static final String SHARE_FROM_ORDER_RESULT = "order_result";

	public static final String SHARE_FROM_INVITE_FIRENDS = "invite_firends";
	public static final String SHARE_FROM_USER_SIGN = "user_sign";
	public static final String SHARE_FROM_ACTIVITY = "activity";
	public static final String SHARE_FROM_SIGN_ACTIVITY_ORDER_PRODUCT = "sign_activity_order_product";
	public static final String SHARE_FROM_MYSUPPLIER_EVENT = "mysupplier_event";
	public static final String SHARE_FROM_SUPPLIER_EVENT = "supplier_event";
	public static final String SHARE_FROM_SUPPLIER_TOPIC = "supplier_topic";
	public static final String SHARE_FROM_YYG_LOTTERY_WIN_SHARE = "yyg_lottery_win_share";
	
	private Integer id;
	private Integer user_id;
	private String user_name;
	private String openid;
	private String cratetime;
	private Integer sence_type;
	private Integer senceid;
	private Integer read_num;
	private Integer share_num;
	private Integer share_type;
	private String share_time;
	private String first_read_time;
	private String last_read_time;
	private String share_from;
	private String share_time_ms;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCratetime() {
		return cratetime;
	}
	public void setCratetime(String cratetime) {
		this.cratetime = cratetime;
	}
	public Integer getSence_type() {
		return sence_type;
	}
	public void setSence_type(Integer sence_type) {
		this.sence_type = sence_type;
	}
	public Integer getSenceid() {
		return senceid;
	}
	public void setSenceid(Integer senceid) {
		this.senceid = senceid;
	}
	public Integer getRead_num() {
		return read_num;
	}
	public void setRead_num(Integer read_num) {
		this.read_num = read_num;
	}
	public Integer getShare_type() {
		return share_type;
	}
	public void setShare_type(Integer share_type) {
		this.share_type = share_type;
	}
	public String getShare_time() {
		return share_time;
	}
	public void setShare_time(String share_time) {
		this.share_time = share_time;
	}
	public String getFirst_read_time() {
		return first_read_time;
	}
	public void setFirst_read_time(String first_read_time) {
		this.first_read_time = first_read_time;
	}
	public String getLast_read_time() {
		return last_read_time;
	}
	public void setLast_read_time(String last_read_time) {
		this.last_read_time = last_read_time;
	}
	public String getShare_from() {
		return share_from;
	}
	public void setShare_from(String share_from) {
		this.share_from = share_from;
	}
	public Integer getShare_num() {
		return share_num;
	}
	public void setShare_num(Integer share_num) {
		this.share_num = share_num;
	}
	public String getShare_time_ms() {
		return share_time_ms;
	}
	public void setShare_time_ms(String share_time_ms) {
		this.share_time_ms = share_time_ms;
	}
}
