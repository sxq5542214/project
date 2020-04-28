package com.yd.business.activity.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.user.bean.UserWechatBean;
@Alias("activityInstance")
public class ActivityInstanceBean extends BaseBean {

	/**
	 * 实例可用
	 */
	public static final int ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE = 1;
	/**
	 * 实例不可用
	 */
	public static final int ACTIVITYINSTANCE_LIFE_STATUS_DIE = 0;
	/**
	 * 实例的默认参与人数
	 */
	public static final int ACTIVITYINSTANCE_REAL_JOINNUM_DEFAULT = 0;
	/**
	 * 实例的默认中奖人数
	 */
	public static final int ACTIVITYINSTANCE_REAL_WINNUM_DEFAULT = 0;
	
	private Integer id;
	private Integer activity_id;
	private Integer real_join_num;
	private String name;
	private Integer life_status;
	private String createdate;
	private Integer max_join_num;
	/**
	 * 活动开始的小时
	 */
	private Integer start_hour;
	private String start_day;
	/**
	 * 活动持续时间
	 */
	private Integer life_age;
	private String remark;
	private String code;
	private Integer frequency;
	/**
	 * 活动开始时间
	 */
	private Integer time;
	
	private Integer max_win_num;
	private Integer real_win_num;
	
	/**
	 * 活动的订阅提醒，用于保存用户信息，与真实的实例无关系
	 */
	private UserWechatBean user;
	
	private String openid;
	
	private List<ActivityRemindBean> remindList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public Integer getReal_join_num() {
		return real_join_num;
	}
	public void setReal_join_num(Integer real_join_num) {
		this.real_join_num = real_join_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLife_status() {
		return life_status;
	}
	public void setLife_status(Integer life_status) {
		this.life_status = life_status;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public Integer getMax_join_num() {
		return max_join_num;
	}
	public void setMax_join_num(Integer max_join_num) {
		this.max_join_num = max_join_num;
	}
	public Integer getStart_hour() {
		return start_hour;
	}
	public void setStart_hour(Integer start_hour) {
		this.start_hour = start_hour;
	}
	public Integer getLife_age() {
		return life_age;
	}
	public void setLife_age(Integer life_age) {
		this.life_age = life_age;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStart_day() {
		return start_day;
	}
	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getMax_win_num() {
		return max_win_num;
	}
	public void setMax_win_num(Integer max_win_num) {
		this.max_win_num = max_win_num;
	}
	public Integer getReal_win_num() {
		return real_win_num;
	}
	public void setReal_win_num(Integer real_win_num) {
		this.real_win_num = real_win_num;
	}
	public List<ActivityRemindBean> getRemindList() {
		return remindList;
	}
	public void setRemindList(List<ActivityRemindBean> remindList) {
		this.remindList = remindList;
	}
	public UserWechatBean getUser() {
		return user;
	}
	public void setUser(UserWechatBean user) {
		this.user = user;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
