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
@Alias("expert")
public class ExpertBean extends BaseBean {
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	
	private Integer id;
	private String name;
	private String create_time;
	private String week_success_rate;
	private String month_success_rate;
	private String season_success_rate;
	private String year_success_rate;
	private String creator;
	private Integer status;
	private Integer cash_pool;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getWeek_success_rate() {
		return week_success_rate;
	}
	public void setWeek_success_rate(String week_success_rate) {
		this.week_success_rate = week_success_rate;
	}
	public String getMonth_success_rate() {
		return month_success_rate;
	}
	public void setMonth_success_rate(String month_success_rate) {
		this.month_success_rate = month_success_rate;
	}
	public String getSeason_success_rate() {
		return season_success_rate;
	}
	public void setSeason_success_rate(String season_success_rate) {
		this.season_success_rate = season_success_rate;
	}
	public String getYear_success_rate() {
		return year_success_rate;
	}
	public void setYear_success_rate(String year_success_rate) {
		this.year_success_rate = year_success_rate;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCash_pool() {
		return cash_pool;
	}
	public void setCash_pool(Integer cash_pool) {
		this.cash_pool = cash_pool;
	}
	
}
