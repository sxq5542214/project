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
@Alias("userSign")
public class UserSignBean extends BaseBean {
	/**
	 * 签到日期等字段的分隔符
	 */
	public static final String SIGN_SPLIT_STR = ",";
	
	
	private Integer id;
	private String create_time;
	private String modify_time;
	private String sign_month;
	private String sign_date;
	private Integer user_id;
	private Integer sence_id;
	private Integer sign_count;
	private Integer last_points;
	private String points_month;
	private String last_sign_date;
	private Integer serial_sign_count;
	
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getSign_date() {
		return sign_date;
	}
	public void setSign_date(String sign_date) {
		this.sign_date = sign_date;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getSence_id() {
		return sence_id;
	}
	public void setSence_id(Integer sence_id) {
		this.sence_id = sence_id;
	}
	public Integer getSign_count() {
		return sign_count;
	}
	public void setSign_count(Integer sign_count) {
		this.sign_count = sign_count;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getSign_month() {
		return sign_month;
	}
	public void setSign_month(String sign_month) {
		this.sign_month = sign_month;
	}
	public Integer getLast_points() {
		return last_points;
	}
	public void setLast_points(Integer last_points) {
		this.last_points = last_points;
	}
	public String getLast_sign_date() {
		return last_sign_date;
	}
	public void setLast_sign_date(String last_sign_date) {
		this.last_sign_date = last_sign_date;
	}
	public Integer getSerial_sign_count() {
		return serial_sign_count;
	}
	public void setSerial_sign_count(Integer serial_sign_count) {
		this.serial_sign_count = serial_sign_count;
	}
	public String getPoints_month() {
		return points_month;
	}
	public void setPoints_month(String points_month) {
		this.points_month = points_month;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
