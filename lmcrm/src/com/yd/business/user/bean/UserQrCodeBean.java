/**
 * 
 */
package com.yd.business.user.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("userQrCode")
public class UserQrCodeBean extends BaseBean {
	private Integer id;
	private Integer senceCode;
	private Integer senceId;
	private String openId;
	private String ticket;
	private String ticket_url;
	private Date ticket_expire;
	private String createDate;
	private String originalid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSenceCode() {
		return senceCode;
	}
	public void setSenceCode(Integer senceCode) {
		this.senceCode = senceCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getTicket_url() {
		return ticket_url;
	}
	public void setTicket_url(String ticket_url) {
		this.ticket_url = ticket_url;
	}
	public Date getTicket_expire() {
		return ticket_expire;
	}
	public void setTicket_expire(Date ticket_expire) {
		this.ticket_expire = ticket_expire;
	}
	public Integer getSenceId() {
		return senceId;
	}
	public void setSenceId(Integer senceId) {
		this.senceId = senceId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOriginalid() {
		return originalid;
	}
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}
	
}
