package com.yd.business.activity.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("activityWinHis")
public class ActivityWinHisBean extends ActivityBean {

	/**
	 * 已中奖
	 */
	public static final Integer STATUS_WINED = 1;
	/**
	 * 已分享
	 */
	public static final Integer STATUS_SHARED = 2;
	/**
	 * 已发过红包
	 */
	public static final Integer STATUS_ALREADYSEND = 3;
	
	
	private Date win_time;
	private Integer win_id;
	private Integer status;
	private String arrstatus;//用于批量查询
	private Date beginWinTime;
	private Date endWinTime;
	
	public Date getBeginWinTime() {
		return beginWinTime;
	}
	public void setBeginWinTime(Date beginWinTime) {
		this.beginWinTime = beginWinTime;
	}
	public Date getEndWinTime() {
		return endWinTime;
	}
	public void setEndWinTime(Date endWinTime) {
		this.endWinTime = endWinTime;
	}
	public String getArrstatus() {
		return arrstatus;
	}
	public void setArrstatus(String arrstatus) {
		this.arrstatus = arrstatus;
	}
	public Date getWin_time() {
		return win_time;
	}
	public void setWin_time(Date win_time) {
		this.win_time = win_time;
	}
	public Integer getWin_id() {
		return win_id;
	}
	public void setWin_id(Integer win_id) {
		this.win_id = win_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
