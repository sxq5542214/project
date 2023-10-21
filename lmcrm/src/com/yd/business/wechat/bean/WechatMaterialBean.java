/**
 * 
 */
package com.yd.business.wechat.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("wechatMaterial")
public class WechatMaterialBean extends BaseBean {
	
	/**
	 * 图文素材【批量分发得到的】
	 */
	public static final String MATERIAL_SUCAITYPE_NEWSALL = "newsall";
	
	/**
	 * 图文素材
	 */
	public static final String MATERIAL_SUCAITYPE_NEWS = "news";
	
	/**
	 * 图片素材
	 */
	public static final String MATERIAL_SUCAITYPE_IMAGE = "image";
	
	/**
	 * 音频素材
	 */
	public static final String MATERIAL_SUCAITYPE_VOICE = "voice";
	
	/**
	 * 视频素材
	 */
	public static final String MATERIAL_SUCAITYPE_VIDEO = "video";
	
	private Integer id;
	private String sucai_title;
	private String sucai_digest;
	private String sucai_author;
	private String sucai_content;
	private String sucai_content_source_url;
	private String sucai_thumb_media_id;
	private Integer sucai_show_cover_pic;
	private String sucai_url;
	private String sucai_thumb_url;
	private String sucai_type;
	private String sucai_media_id;
	private String sucai_name;
	private String create_time;
	private String modify_time;
	private Integer seq;
	private String parent_media_id;
	private String originalid;
	private String original_name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSucai_title() {
		return sucai_title;
	}
	public void setSucai_title(String sucai_title) {
		this.sucai_title = sucai_title;
	}
	public String getSucai_digest() {
		return sucai_digest;
	}
	public void setSucai_digest(String sucai_digest) {
		this.sucai_digest = sucai_digest;
	}
	public String getSucai_author() {
		return sucai_author;
	}
	public void setSucai_author(String sucai_author) {
		this.sucai_author = sucai_author;
	}
	public String getSucai_content() {
		return sucai_content;
	}
	public void setSucai_content(String sucai_content) {
		this.sucai_content = sucai_content;
	}
	public String getSucai_content_source_url() {
		return sucai_content_source_url;
	}
	public void setSucai_content_source_url(String sucai_content_source_url) {
		this.sucai_content_source_url = sucai_content_source_url;
	}
	public String getSucai_thumb_media_id() {
		return sucai_thumb_media_id;
	}
	public void setSucai_thumb_media_id(String sucai_thumb_media_id) {
		this.sucai_thumb_media_id = sucai_thumb_media_id;
	}
	public Integer getSucai_show_cover_pic() {
		return sucai_show_cover_pic;
	}
	public void setSucai_show_cover_pic(Integer sucai_show_cover_pic) {
		this.sucai_show_cover_pic = sucai_show_cover_pic;
	}
	public String getSucai_url() {
		return sucai_url;
	}
	public void setSucai_url(String sucai_url) {
		this.sucai_url = sucai_url;
	}
	public String getSucai_thumb_url() {
		return sucai_thumb_url;
	}
	public void setSucai_thumb_url(String sucai_thumb_url) {
		this.sucai_thumb_url = sucai_thumb_url;
	}
	public String getSucai_type() {
		return sucai_type;
	}
	public void setSucai_type(String sucai_type) {
		this.sucai_type = sucai_type;
	}
	public String getSucai_media_id() {
		return sucai_media_id;
	}
	public void setSucai_media_id(String sucai_media_id) {
		this.sucai_media_id = sucai_media_id;
	}
	public String getSucai_name() {
		return sucai_name;
	}
	public void setSucai_name(String sucai_name) {
		this.sucai_name = sucai_name;
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getParent_media_id() {
		return parent_media_id;
	}
	public void setParent_media_id(String parent_media_id) {
		this.parent_media_id = parent_media_id;
	}
	public String getOriginalid() {
		return originalid;
	}
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	
	
}
