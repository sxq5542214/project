/**
 * 
 */
package com.yd.business.image.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("uploadLog")
public class UploadLogBean extends ImageBean {
	private String server_name;
	private String request_method;
	private String request_ip;
	private String request_file_url;
	private String return_file_url;
	private String real_file_path;
	private String thumb_file_path;
	private String thumb_file_url;
	
	
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public String getRequest_method() {
		return request_method;
	}
	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}
	public String getRequest_ip() {
		return request_ip;
	}
	public void setRequest_ip(String request_ip) {
		this.request_ip = request_ip;
	}
	public String getRequest_file_url() {
		return request_file_url;
	}
	public void setRequest_file_url(String request_file_url) {
		this.request_file_url = request_file_url;
	}
	public String getReturn_file_url() {
		return return_file_url;
	}
	public void setReturn_file_url(String return_file_url) {
		this.return_file_url = return_file_url;
	}
	public String getReal_file_path() {
		return real_file_path;
	}
	public void setReal_file_path(String real_file_path) {
		this.real_file_path = real_file_path;
	}
	public String getThumb_file_path() {
		return thumb_file_path;
	}
	public void setThumb_file_path(String thumb_file_path) {
		this.thumb_file_path = thumb_file_path;
	}
	public String getThumb_file_url() {
		return thumb_file_url;
	}
	public void setThumb_file_url(String thumb_file_url) {
		this.thumb_file_url = thumb_file_url;
	}
	
	
	
}
