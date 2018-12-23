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
@Alias("expertResultFlow")
public class ExpertResultFlowBean extends BaseBean {
	private Integer id;
	private Integer expert_id;
	private String expert_name;
	private Integer expert_result_id;
	private Integer current_join_money;
	private Integer lottery_money;
	private Integer init_money;
	private Integer money_sum;
	private Integer sum_lottery_money;
	private Integer sum_loss_money;
	private Integer sum_lottery_count;
	private Integer sum_loss_count;
	private Integer sum_not_join_count;
	private Integer long_loss_days;
	private Integer loss_days_count;
	private Integer loss_money_continue_sum;
	private Integer sum_flow;
	private Integer current_loss_days;
	private Integer index_no;
	private String create_time;
	private Integer num_of_break_even;
	private Integer max_long_loss_days;
	private String loss_count_cost_multiple;
	private String userids;
	private Integer cash_pool;
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
	public Integer getExpert_result_id() {
		return expert_result_id;
	}
	public void setExpert_result_id(Integer expert_result_id) {
		this.expert_result_id = expert_result_id;
	}
	public Integer getCurrent_join_money() {
		return current_join_money;
	}
	public void setCurrent_join_money(Integer current_join_money) {
		this.current_join_money = current_join_money;
	}
	public Integer getLottery_money() {
		return lottery_money;
	}
	public void setLottery_money(Integer lottery_money) {
		this.lottery_money = lottery_money;
	}
	public Integer getInit_money() {
		return init_money;
	}
	public void setInit_money(Integer init_money) {
		this.init_money = init_money;
	}
	public Integer getMoney_sum() {
		return money_sum;
	}
	public void setMoney_sum(Integer money_sum) {
		this.money_sum = money_sum;
	}
	public Integer getSum_lottery_money() {
		return sum_lottery_money;
	}
	public void setSum_lottery_money(Integer sum_lottery_money) {
		this.sum_lottery_money = sum_lottery_money;
	}
	public Integer getSum_loss_money() {
		return sum_loss_money;
	}
	public void setSum_loss_money(Integer sum_loss_money) {
		this.sum_loss_money = sum_loss_money;
	}
	public Integer getSum_lottery_count() {
		return sum_lottery_count;
	}
	public void setSum_lottery_count(Integer sum_lottery_count) {
		this.sum_lottery_count = sum_lottery_count;
	}
	public Integer getSum_loss_count() {
		return sum_loss_count;
	}
	public void setSum_loss_count(Integer sum_loss_count) {
		this.sum_loss_count = sum_loss_count;
	}
	public Integer getSum_not_join_count() {
		return sum_not_join_count;
	}
	public void setSum_not_join_count(Integer sum_not_join_count) {
		this.sum_not_join_count = sum_not_join_count;
	}
	public Integer getLong_loss_days() {
		return long_loss_days;
	}
	public void setLong_loss_days(Integer long_loss_days) {
		this.long_loss_days = long_loss_days;
	}
	public Integer getLoss_days_count() {
		return loss_days_count;
	}
	public void setLoss_days_count(Integer loss_days_count) {
		this.loss_days_count = loss_days_count;
	}
	public Integer getLoss_money_continue_sum() {
		return loss_money_continue_sum;
	}
	public void setLoss_money_continue_sum(Integer loss_money_continue_sum) {
		this.loss_money_continue_sum = loss_money_continue_sum;
	}
	public Integer getSum_flow() {
		return sum_flow;
	}
	public void setSum_flow(Integer sum_flow) {
		this.sum_flow = sum_flow;
	}
	public Integer getCurrent_loss_days() {
		return current_loss_days;
	}
	public void setCurrent_loss_days(Integer current_loss_days) {
		this.current_loss_days = current_loss_days;
	}
	public Integer getIndex_no() {
		return index_no;
	}
	public void setIndex_no(Integer index_no) {
		this.index_no = index_no;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getNum_of_break_even() {
		return num_of_break_even;
	}
	public void setNum_of_break_even(Integer num_of_break_even) {
		this.num_of_break_even = num_of_break_even;
	}
	public Integer getMax_long_loss_days() {
		return max_long_loss_days;
	}
	public void setMax_long_loss_days(Integer max_long_loss_days) {
		this.max_long_loss_days = max_long_loss_days;
	}
	public String getLoss_count_cost_multiple() {
		return loss_count_cost_multiple;
	}
	public void setLoss_count_cost_multiple(String loss_count_cost_multiple) {
		this.loss_count_cost_multiple = loss_count_cost_multiple;
	}
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
	public Integer getCash_pool() {
		return cash_pool;
	}
	public void setCash_pool(Integer cash_pool) {
		this.cash_pool = cash_pool;
	}
	
	
	
}
