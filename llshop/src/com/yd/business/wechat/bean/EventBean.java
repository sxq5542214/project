package com.yd.business.wechat.bean;

public class EventBean extends BaseMessage {
	
	public EventBean(){}
	
	public EventBean(BaseMessage base){
		baseInfo(base);
	}
	
	/**
	 * 事件类型
	 */
	private String Event;
	
	/**
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 */
	private String EventKey;
	
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	private String Ticket;
	/**
	 * 地理位置纬度
	 */
	private String Latitude;
	/**
	 * 地理位置经度
	 */
	private String Longitude;
	/**
	 * 地理位置精度
	 */
	private String Precision;
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	
}
