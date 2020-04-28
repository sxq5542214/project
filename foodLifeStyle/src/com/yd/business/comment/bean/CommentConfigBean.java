package com.yd.business.comment.bean;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.dictionary.bean.DictionaryBean;

@Alias("commentConfig")
public class CommentConfigBean extends BaseBean {
	
	/**
	 * 留言配置表的一些提示信息
	 * 
	 */
	public static final String COMMENT_INTO_NEED_CYC_UNSTART ="comment_into_need_cyc_unstart";
	public static final String COMMENT_INTO_NEED_CYC_ENDED ="comment_into_need_cyc_ended";
	public static final String COMMENT_INTO_NEED_CYC_UNSTART2 ="comment_into_need_cyc_unstart2";
	public static final String COMMENT_INTO_NOT_REJOIN ="comment_into_not_rejoin";
	public static final String COMMENT_INTO_NEED_REVIEW ="comment_into_need_review";
	public static final String COMMENT_INTO_UNNEED_REVIEW ="comment_into_unneed_review";
	public static final String COMMENT_VOTED_NEED_CYC ="comment_voted_need_cyc";
	public static final String COMMENT_VOTED_IS_STOP ="comment_voted_is_stop";
	public static final String COMMENT_VOTED_OUTCYC_YEAR ="comment_voted_outcyc_year";
	public static final String COMMENT_VOTED_OUTCYC_MONTH ="comment_voted_outcyc_month";
	public static final String COMMENT_VOTED_OUTCYC_WEEK ="comment_voted_outcyc_week";
	public static final String COMMENT_VOTED_OUTCYC_DAY ="comment_voted_outcyc_day";
	
	/**
	 * 配置默认值的code
	 */
	public static final String COMMENT_DEDAULT_INFO = "default_code";
	/**
	 * 留言展示
	 */
	public static final int COMMENT_IS_SHOW_YES = 1;
	/**
	 * 留言不展示
	 */
	public static final int COMMENT_IS_SHOW_NO = 0;
	

	/**
	 * 不可重复留言
	 */
	public static final int COMMENT_CONFIG_IS_REPEAT_NO = 0;
	/**
	 * 可重复留言
	 */
	public static final int COMMENT_CONFIG_IS_REPEAT_YES = 1;
	
	/**
	 * （0：无限制投票）
	 */
	public static final int COMMENT_PERVOTEDNUM_WITHOUTLIMIT = 0;
	
	/**
	 * 投票周期（0：每天；）
	 */
	public static final int COMMENT_VOTED_CYCLE_DAY = 0;
	/**
	 * 投票周期（1：每周；）
	 */
	public static final int COMMENT_VOTED_CYCLE_WEEK = 1;
	/**
	 * 投票周期（2：每月；）
	 */
	public static final int COMMENT_VOTED_CYCLE_MONTH = 2;
	/**
	 * 投票周期（3：每年）
	 */
	public static final int COMMENT_VOTED_CYCLE_YEAR = 3;
	
	/**
	 * 留言点赞需要用户关注
	 */
	public static final int COMMENT_VOTE_IS_USER_SUBSCRIBE_YES = 1;
	/**
	 * 留言点赞不需要用户关注
	 */
	public static final int COMMENT_VOTE_IS_USER_SUBSCRIBE_NO = 0;
	/**
	 * 留言需要用户关注
	 */
	public static final int COMMENT_IS_USER_SUBSCRIBE_YES = 1;
	/**
	 * 留言不需要用户关注
	 */
	public static final int COMMENT_IS_USER_SUBSCRIBE_NO = 0;
	/**
	 * 留言需要审核
	 */
	public static final int COMMENT_IS_ADMIN_REVIEW_YES = 1;
	/**
	 * 留言不需要审核
	 */
	public static final int COMMENT_IS_ADMIN_REVIEW_NO = 0;
	
	/**
	 * 投票无期限限制
	 */
	public static final int COMMENT_IS_VOTED_EXPIRED_ANYTIME = 0;
	/**
	 * 投票只在周期内有效
	 */
	public static final int COMMENT_IS_VOTED_EXPIRED_CYC = 1;
	
	private Integer id;
	private String activity_code;
	private Integer is_repeat;
	/**
	 * 是否需要用户关注，1：需要；0：不需要(点赞)
	 */
	private Integer vote_is_user_subscribe;
	/**
	 * 是否需要用户关注，1：需要；0：不需要
	 */
	private Integer is_user_subscribe;
	/**
	 * 每人的投票数量（0：无限制投票）,默认无限制投票
	 */
	private Integer per_voted_num = COMMENT_PERVOTEDNUM_WITHOUTLIMIT;
	/**
	 * 投票周期（0：每天；1：每周；2：每月；3：每年），默认每天
	 */
	private Integer voted_cycle = COMMENT_VOTED_CYCLE_DAY;
	/**
	 * 留言是否展示，默认不展示
	 */
	private Integer is_show = COMMENT_IS_SHOW_NO;
	/**
	 * 开始时间
	 */
	private String start_time;
	/**
	 * 结束时间
	 */
	private String end_time;
	
	private String create_time;
	private String modify_time;
	private Integer is_admin_review;
	
	/**
	 * 留言所在链接
	 */
	private String link_url;
	private String name;
	
	/**
	 * 投票是否可过期，1：有期限限制；0：无限制
	 */
	private Integer is_voted_expired;
	private String remark;
	private Map<String, List<DictionaryBean>> dicMap;
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivity_code() {
		return activity_code;
	}
	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}
	public Integer getIs_repeat() {
		return is_repeat;
	}
	public void setIs_repeat(Integer is_repeat) {
		this.is_repeat = is_repeat;
	}
	public Integer getPer_voted_num() {
		return per_voted_num;
	}
	public void setPer_voted_num(Integer per_voted_num) {
		this.per_voted_num = per_voted_num;
	}
	public Integer getVoted_cycle() {
		return voted_cycle;
	}
	public void setVoted_cycle(Integer voted_cycle) {
		this.voted_cycle = voted_cycle;
	}
	public Integer getIs_user_subscribe() {
		return is_user_subscribe;
	}
	public void setIs_user_subscribe(Integer is_user_subscribe) {
		this.is_user_subscribe = is_user_subscribe;
	}
	public Integer getVote_is_user_subscribe() {
		return vote_is_user_subscribe;
	}
	public void setVote_is_user_subscribe(Integer vote_is_user_subscribe) {
		this.vote_is_user_subscribe = vote_is_user_subscribe;
	}
	public Integer getIs_admin_review() {
		return is_admin_review;
	}
	public void setIs_admin_review(Integer is_admin_review) {
		this.is_admin_review = is_admin_review;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIs_voted_expired() {
		return is_voted_expired;
	}
	public void setIs_voted_expired(Integer is_voted_expired) {
		this.is_voted_expired = is_voted_expired;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Map<String, List<DictionaryBean>> getDicMap() {
		return dicMap;
	}
	public void setDicMap(Map<String, List<DictionaryBean>> dicMap) {
		this.dicMap = dicMap;
	}	
	
}
