/**
 * 
 */
package com.yd.business.test.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("tvb")
public class TechnicalVerificationBean extends BaseBean {
	private Integer id ;
	private String name;
	private String date;
	private String is_lottery;
	private Integer lottery_money;
	private String commons;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIs_lottery() {
		return is_lottery;
	}
	public void setIs_lottery(String is_lottery) {
		this.is_lottery = is_lottery;
	}
	public Integer getLottery_money() {
		return lottery_money;
	}
	public void setLottery_money(Integer lottery_money) {
		this.lottery_money = lottery_money;
	}
	public String getCommons() {
		return commons;
	}
	public void setCommons(String commons) {
		this.commons = commons;
	}
	
}
