/**
 * 
 */
package com.yd.business.lottery.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("expertResultAndFlow")
public class ExpertResultAndFlowBean extends ExpertResultBean {
	private Integer money_sum;
	private Integer cash_pool;
	
	private Integer current_loss_days;
	
	public Integer getMoney_sum() {
		return money_sum;
	}

	public void setMoney_sum(Integer money_sum) {
		this.money_sum = money_sum;
	}

	public Integer getCash_pool() {
		return cash_pool;
	}

	public void setCash_pool(Integer cash_pool) {
		this.cash_pool = cash_pool;
	}

	public Integer getCurrent_loss_days() {
		return current_loss_days;
	}

	public void setCurrent_loss_days(Integer current_loss_days) {
		this.current_loss_days = current_loss_days;
	}
	
	
}
