/**
 * 
 */
package com.yd.business.image.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("image")
public class ImageBean extends BaseBean {
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	public static final int STATUS_FAILD = -1;
	
	public static final int thumb_width = 100;
	public static final int thumb_height = 100;
	public static final String THUMB_IMG_DIR = "images/upload/thumb/";
	
	
	private Integer id;
	private Integer status;
	private String create_time;
	private String modify_time;
	
	private Integer width;
	private Integer height;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
}
