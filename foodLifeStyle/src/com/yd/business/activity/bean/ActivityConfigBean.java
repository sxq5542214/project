/**
 * 
 */
package com.yd.business.activity.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("activityConfig")
public class ActivityConfigBean extends BaseBean {
	
	public static final String CODE_FIRSTSHAKE_ACTIVITY = "firstShake_activity";
	public static final String CODE_SKILL_ACTIVITY = "skill_activity";//秒杀活动
	public static final String CODE_OLYMPICGUESS_ACTIVITY = "olympicGuess_activity";
	/**
	 * //每天
	 */
	public static final int ACTIVITY_FREQUENCY_DAY = 2;
	/**
	 * //每周
	 */
	public static final int ACTIVITY_FREQUENCY_WEEK = 1;
	/**
	 * //每月
	 */
	public static final int ACTIVITY_FREQUENCY_MONTH = 0;
	/**
	 * //一直有效
	 */
	public static final int ACTIVITY_FREQUENCY_OTHER = -1;
	/**
	 * //活动不可重复参加
	 */
	public static final int ACTIVITY_IS_REPEAT_NO = 0;
	/**
	 * //活动可重复参加
	 */
	public static final int ACTIVITY_IS_REPEAT_YES = 1;
	
	
	/**
	 * 首次关注抢流量红包活动
	 */
	public static final int ACTIVITY_TYPE_FIRST_SUBSCRIBE = 1;
	/**
	 * 领卷活动
	 */
	public static final int ACTIVITY_TYPE_VOLUM = 2;
	/**
	 * 用户签到活动
	 */
	public static final int ACTIVITY_TYPE_USERSIGN = 3;
	
	/**
	 * 红包加优惠卷活动
	 */
	public static final int ACTIVITY_TYPE_BONUS_AND_COUPON = 5;
	
	/**
	 * 启用
	 */
	public static final int ACTIVITY_STATUS_ENABLE = 1;
	
	/**
	 * 不启用
	 */
	public static final int ACTIVITY_STATUS_DISABLE = 0;
	
	public static final String ACTIVITY_DEFALT_IMG = "page/user/activity/signActivity/images/shopping.png";
	
	public static final String ACTIVITY_DEFALT_LIST_IMG = "page/user/activity/signActivity/images/shopping_list.jpg";
	
	/**
	 * 需要展示详情页
	 */
	public static final int ACTIVITY_IS_SHOW_DETAIL_YES = 1;
	
	/**
	 * 不需要展示详情页
	 */
	public static final int ACTIVITY_IS_SHOW_DETAIL_NO = 0;
	
	
	
	private Integer id;
	private String name;
	private String code;
	private Integer activity_type;
	private String start_date;
	private String end_date;
	private String start_hour;
	private Integer start_minute;
	private Integer max_join_num;
	private Integer max_win_num;
	private Integer total_money;
	private Integer single_max;
	private Integer single_min;
	private Integer outside_join_num;
	private Integer outside_win_num;
	private Integer outside_total_money;
	private Integer status;
	private String create_time;
	private String modify_time;
	private Integer reference_id;
	private Integer life_age;
	private Integer frequency;
	private Integer cost_points;
	private String description_img;
	private String description;
	private String start_times;
	private Integer is_repeat;
	private String activity_jump_url;
	private String activity_jump_url_code;
	private List<ActivityRule> rule;
	private String show_list_img;
	private String activity_img;
	private String activity_img_jump_url;
	private String activity_img_jump_url_code;
	/**
	 * 是否展示活动详情页
	 */
	private Integer is_show_detail;
	
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivity_type() {
		return activity_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setActivity_type(Integer activity_type) {
		this.activity_type = activity_type;
	}
	
	public String getStart_hour() {
		return start_hour;
	}
	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}
	public Integer getStart_minute() {
		return start_minute;
	}
	public void setStart_minute(Integer start_minute) {
		this.start_minute = start_minute;
	}
	public Integer getMax_join_num() {
		return max_join_num;
	}
	public void setMax_join_num(Integer max_join_num) {
		this.max_join_num = max_join_num;
	}
	public Integer getMax_win_num() {
		return max_win_num;
	}
	public void setMax_win_num(Integer max_win_num) {
		this.max_win_num = max_win_num;
	}
	public Integer getTotal_money() {
		return total_money;
	}
	public void setTotal_money(Integer total_money) {
		this.total_money = total_money;
	}
	public Integer getOutside_join_num() {
		return outside_join_num;
	}
	public void setOutside_join_num(Integer outside_join_num) {
		this.outside_join_num = outside_join_num;
	}
	public Integer getOutside_win_num() {
		return outside_win_num;
	}
	public void setOutside_win_num(Integer outside_win_num) {
		this.outside_win_num = outside_win_num;
	}
	public Integer getOutside_total_money() {
		return outside_total_money;
	}
	public void setOutside_total_money(Integer outside_total_money) {
		this.outside_total_money = outside_total_money;
	}
	public Integer getSingle_max() {
		return single_max;
	}
	public void setSingle_max(Integer single_max) {
		this.single_max = single_max;
	}
	public Integer getSingle_min() {
		return single_min;
	}
	public void setSingle_min(Integer single_min) {
		this.single_min = single_min;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
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
	public Integer getReference_id() {
		return reference_id;
	}
	public void setReference_id(Integer reference_id) {
		this.reference_id = reference_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getLife_age() {
		return life_age;
	}
	public void setLife_age(Integer life_age) {
		this.life_age = life_age;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Integer getCost_points() {
		return cost_points;
	}
	public void setCost_points(Integer cost_points) {
		this.cost_points = cost_points;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStart_times() {
		return start_times;
	}
	public void setStart_times(String start_times) {
		this.start_times = start_times;
	}
	public Integer getIs_repeat() {
		return is_repeat;
	}
	public void setIs_repeat(Integer is_repeat) {
		this.is_repeat = is_repeat;
	}

	public List<ActivityRule> getRule() {
		return rule;
	}
	public void setRule(List<ActivityRule> rule) {
		this.rule = rule;
	}
	public String getDescription_img() {
		return description_img;
	}
	public void setDescription_img(String description_img) {
		this.description_img = description_img;
	}
	public String getActivity_jump_url() {
		return activity_jump_url;
	}
	public void setActivity_jump_url(String activity_jump_url) {
		this.activity_jump_url = activity_jump_url;
	}
	public String getShow_list_img() {
		return show_list_img;
	}
	public void setShow_list_img(String show_list_img) {
		this.show_list_img = show_list_img;
	}
	public String getActivity_img() {
		return activity_img;
	}
	public void setActivity_img(String activity_img) {
		this.activity_img = activity_img;
	}
	public String getActivity_img_jump_url() {
		return activity_img_jump_url;
	}
	public void setActivity_img_jump_url(String activity_img_jump_url) {
		this.activity_img_jump_url = activity_img_jump_url;
	}
	public Integer getIs_show_detail() {
		return is_show_detail;
	}
	public void setIs_show_detail(Integer is_show_detail) {
		this.is_show_detail = is_show_detail;
	}
	public String getActivity_jump_url_code() {
		return activity_jump_url_code;
	}
	public void setActivity_jump_url_code(String activity_jump_url_code) {
		this.activity_jump_url_code = activity_jump_url_code;
	}
	public String getActivity_img_jump_url_code() {
		return activity_img_jump_url_code;
	}
	public void setActivity_img_jump_url_code(String activity_img_jump_url_code) {
		this.activity_img_jump_url_code = activity_img_jump_url_code;
	}

	
}
