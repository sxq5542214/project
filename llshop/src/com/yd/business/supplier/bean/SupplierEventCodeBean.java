/**
 * 
 */
package com.yd.business.supplier.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("supplierEventCode")
public class SupplierEventCodeBean extends BaseBean {
	private Integer id;
	private Integer eventId;
	private Integer userId;
	private Integer code;
	private Integer fromUserId;
	private String fromUserName;
	private String createDate;
	
	private String head_img;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	
}
