/**
 * 
 */
package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("activityUserRelation")
public class ActivityUserRelationBean extends BaseBean {
	public static final int CATEGORY_IMAGE = 1;
	public static final int CATEGORY_OFFLINE = 2;
	
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_USED = 1;
	public static final int STATUS_FAILD = -1;
	
	public static final int SHARE_STATUS_NO = 0;//未分享
	public static final int SHARE_STATUS_FIREND = 1;//分享到朋友
	public static final int SHARE_STATUS_TIMELINE = 2;//分享到朋友圈
	
	
	private Integer id;
	private Integer user_id;
	private String openid;
	private String activity_name;
	private Integer activity_config_id;
	private String activity_time;
	private Integer activity_type;
	private String join_date;
	private String product_name;
	private Integer act_prod_id;
	private Integer product_id;
	private String phone;
	private Integer category;
	private String modify_time;
	private String create_time;
	private Integer status;
	private Integer share_status;
	private String user_ip;
	private String order_code;
	private String remark;
	//======用于展示
	private String nick_name;//用户名
	private String head_img;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(Integer activity_type) {
		this.activity_type = activity_type;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getActivity_config_id() {
		return activity_config_id;
	}
	public void setActivity_config_id(Integer activity_config_id) {
		this.activity_config_id = activity_config_id;
	}
	public String getActivity_time() {
		return activity_time;
	}
	public void setActivity_time(String activity_time) {
		this.activity_time = activity_time;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getAct_prod_id() {
		return act_prod_id;
	}
	public void setAct_prod_id(Integer act_prod_id) {
		this.act_prod_id = act_prod_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public Integer getShare_status() {
		return share_status;
	}
	public void setShare_status(Integer share_status) {
		this.share_status = share_status;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	
}
