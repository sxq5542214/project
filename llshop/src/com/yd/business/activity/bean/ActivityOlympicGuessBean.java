/**
 * 
 */
package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("activityOlympicGuess")
public class ActivityOlympicGuessBean extends BaseBean {

	public static final int STATUS_JOINED = 0;
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_WAIT = 2;
	public static final int STATUS_NOTWIN = 3;
	
	public static final int SHARED_YES = 0;
	public static final int SHARED_NO = 1;
	
	
	private Integer id;
	private Integer user_id;
	private String guess_date;
	private Integer guess_num;
	private String create_time;
	private Integer status;
	private Integer shared;
	private Integer win_num;
	private String win_desc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getGuess_date() {
		return guess_date;
	}
	public void setGuess_date(String guess_date) {
		this.guess_date = guess_date;
	}
	public Integer getGuess_num() {
		return guess_num;
	}
	public void setGuess_num(Integer guess_num) {
		this.guess_num = guess_num;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getWin_num() {
		return win_num;
	}
	public void setWin_num(Integer win_num) {
		this.win_num = win_num;
	}
	public String getWin_desc() {
		return win_desc;
	}
	public void setWin_desc(String win_desc) {
		this.win_desc = win_desc;
	}
	public Integer getShared() {
		return shared;
	}
	public void setShared(Integer shared) {
		this.shared = shared;
	}
	
}
