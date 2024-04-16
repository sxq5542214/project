/**
 * 
 */
package com.yd.business.bill.bean;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LmRecordModel;

/**
 * 
 */
@Alias("recordModelExtendsBean")
public class RecordModelExtendsBean extends LmRecordModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3165404785116726465L;
	
	private String start_date;
	private String end_date;
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
}
