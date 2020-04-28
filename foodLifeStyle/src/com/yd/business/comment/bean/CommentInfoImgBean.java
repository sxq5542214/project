package com.yd.business.comment.bean;

import org.apache.ibatis.type.Alias;

@Alias("commentInfoImg")
public class CommentInfoImgBean {
	
	public static final int IMG_IPLOAD_SUCCESS = 1;

	private Integer id;
	private Integer info_id;
	private String img_url;
	private String thumb_url;
	private String path;
	private String create_time;
	private String img_sizex;
	/**
	 * 图片服务器的图片id
	 */
	private Integer img_id;
	/**
	 * 处理图片花费时间 ms
	 */
	private Long cost_time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInfo_id() {
		return info_id;
	}
	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Long getCost_time() {
		return cost_time;
	}
	public void setCost_time(Long cost_time) {
		this.cost_time = cost_time;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	public String getImg_sizex() {
		return img_sizex;
	}
	public void setImg_sizex(String img_sizex) {
		this.img_sizex = img_sizex;
	}
	public Integer getImg_id() {
		return img_id;
	}
	public void setImg_id(Integer img_id) {
		this.img_id = img_id;
	}
	
	
	
	
}
