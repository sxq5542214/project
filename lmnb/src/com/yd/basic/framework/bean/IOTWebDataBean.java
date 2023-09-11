/**
 * 
 */
package com.yd.basic.framework.bean;

/**
 * 
 */
public class IOTWebDataBean {

	public static final int CODE_IOTWEB_SUCCESS = 20000; 
	public static final int CODE_IOTWEB_LOGIN_EXPIRED = 50014; 
	public static final int CODE_IOTWEB_QUERY_ERROR = -10000; 
	public static final int CODE_IOTWEB_INSERT_ERROR = -10001; 
	public static final int CODE_IOTWEB_UPDATE_ERROR = -10002; 
	
	private String message;
	private Integer code = CODE_IOTWEB_SUCCESS;
	private Object info;
	private Object data;
	private String token;
	private Long total;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public class Data{
		
		
	}
}
