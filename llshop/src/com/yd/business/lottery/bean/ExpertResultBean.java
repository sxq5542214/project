/**
 * 
 */
package com.yd.business.lottery.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("expertResult")
public class ExpertResultBean extends BaseBean {
	
	public static final String LOTTER_RESULT_WAIT = "待开";
	public static final String LOTTER_RESULT_TRUE = "中奖";
	public static final String LOTTER_RESULT_FALSE = "未中";
	public static final String LOTTER_RESULT_REST = "休息";
	
	private Integer id;
	private Integer expert_id;
	private String expert_name;
	private String enter_time;
	private String create_time;
	private String modify_time;
	private String sports_lottery_date;
	private String sports_lottery_time;
	private String sports_lottery_code;
	private String sports_lottery_name;
	private Integer odds;
	private Integer join_money;
	private String lottery_result;
	private Integer lottery_money;
	private String commons;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getExpert_id() {
		return expert_id;
	}
	public void setExpert_id(Integer expert_id) {
		this.expert_id = expert_id;
	}
	public String getExpert_name() {
		return expert_name;
	}
	public void setExpert_name(String expert_name) {
		this.expert_name = expert_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getSports_lottery_date() {
		return sports_lottery_date;
	}
	public void setSports_lottery_date(String sports_lottery_date) {
		this.sports_lottery_date = sports_lottery_date;
	}
	public String getSports_lottery_time() {
		return sports_lottery_time;
	}
	public void setSports_lottery_time(String sports_lottery_time) {
		this.sports_lottery_time = sports_lottery_time;
	}
	public String getSports_lottery_code() {
		return sports_lottery_code;
	}
	public void setSports_lottery_code(String sports_lottery_code) {
		this.sports_lottery_code = sports_lottery_code;
	}
	public String getSports_lottery_name() {
		return sports_lottery_name;
	}
	public void setSports_lottery_name(String sports_lottery_name) {
		this.sports_lottery_name = sports_lottery_name;
	}
	public Integer getOdds() {
		return odds;
	}
	public void setOdds(Integer odds) {
		this.odds = odds;
	}
	public String getLottery_result() {
		return lottery_result;
	}
	public void setLottery_result(String lottery_result) {
		this.lottery_result = lottery_result;
	}
	public String getCommons() {
		return commons;
	}
	public void setCommons(String commons) {
		this.commons = commons;
	}
	public Integer getLottery_money() {
		return lottery_money;
	}
	public void setLottery_money(Integer lottery_money) {
		this.lottery_money = lottery_money;
	}
	public Integer getJoin_money() {
		return join_money;
	}
	public void setJoin_money(Integer join_money) {
		this.join_money = join_money;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getEnter_time() {
		return enter_time;
	}
	public void setEnter_time(String enter_time) {
		this.enter_time = enter_time;
	}
}
