package com.yd.business.wechat.bean;

public class QrCodeBean extends BaseMessage{
	public QrCodeBean(){};
	public QrCodeBean(BaseMessage base){
		baseInfo(base);
	}
	
	private String ticket;
	private String expire_seconds;
	private String url;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(String expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
