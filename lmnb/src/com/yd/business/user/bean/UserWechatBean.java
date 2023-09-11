package com.yd.business.user.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 微信用户信息
 * @author ice
 *
 */
@Alias("userWechat")
public class UserWechatBean extends BaseBean {
	
	public static final String OPENID_SYSTEM_MASS_NOTIFY = "system_mass_notify";
	
	/**
	 * 普通客户
	 */
	public static final int TYPE_NORMAL = 1;
	/**
	 * VIP客户
	 */
	public static final int TYPE_VIP = 2;
	/**
	 * 推广客户
	 */
	public static final int TYPE_EXTENSION = 3;
	/**
	 * 滴滴客户
	 */
	public static final int TYPE_DIDI = 4;
	/**
	 * 企业客户
	 */
	public static final int TYPE_ENTERPRISE = 5;
	
	

	public static final int STATUS_SUBSCRIBE = 1;
	
	public static final int STATUS_UNSUBSCRIBE = 0;
	
	
	
	private Integer id;
	private Integer parentid;
	private String openid;
	private String create_time;
	private String nick_name;
	private Integer phone;
	private String sex;
	private String province;
	private String city;
	private Integer status;
	private String expire_date;
	private String access_token;
	private String token_end_time;
	private Integer level;
	private Integer offline_num;
	private Integer join_img_num;
	private Integer join_offline_num;
	private Integer winning_num;
	private String last_upload_time;
	private String last_offline_time;
	private String head_img;
	
	private String last_access_time;
	private Integer sence_type;
	private Integer senceid;
	
	private Integer points;
	private Integer balance;
	private String last_order_time;
	private Integer type;
	private Integer share_type;
	private String share_time;
	
	private String unionid;
	private String originalid;//公众号ID
	
	
	
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOffline_num() {
		return offline_num;
	}
	public void setOffline_num(Integer offline_num) {
		this.offline_num = offline_num;
	}
	public Integer getJoin_img_num() {
		return join_img_num;
	}
	public void setJoin_img_num(Integer join_img_num) {
		this.join_img_num = join_img_num;
	}
	public Integer getJoin_offline_num() {
		return join_offline_num;
	}
	public void setJoin_offline_num(Integer join_offline_num) {
		this.join_offline_num = join_offline_num;
	}
	public Integer getWinning_num() {
		return winning_num;
	}
	public void setWinning_num(Integer winning_num) {
		this.winning_num = winning_num;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public String getToken_end_time() {
		return token_end_time;
	}
	public void setToken_end_time(String token_end_time) {
		this.token_end_time = token_end_time;
	}
	public String getLast_upload_time() {
		return last_upload_time;
	}
	public void setLast_upload_time(String last_upload_time) {
		this.last_upload_time = last_upload_time;
	}
	public String getLast_offline_time() {
		return last_offline_time;
	}
	public void setLast_offline_time(String last_offline_time) {
		this.last_offline_time = last_offline_time;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
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
	public String getLast_access_time() {
		return last_access_time;
	}
	public void setLast_access_time(String last_access_time) {
		this.last_access_time = last_access_time;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getLast_order_time() {
		return last_order_time;
	}
	public void setLast_order_time(String last_order_time) {
		this.last_order_time = last_order_time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getOriginalid() {
		return originalid;
	}
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
