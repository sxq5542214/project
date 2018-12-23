package com.yd.business.supplier.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
/**
 * 商户文章表（广告）
 * @author ZDL
 *
 */
@Alias("supplierArticle")
public class SupplierArticleBean extends BaseBean {
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	

	private Integer id;
	private String title;
	private String title_attached;
	private String content;
	private String descrip;
	private String url;
	private String img_url;
	private String media_id;
	private String wechat_server_id;
	private Integer status;
	private String create_time;
	private String modify_time;
	private String end_time;
	private Integer read_num;
	private Integer supplier_id;
	private Integer type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getWechat_server_id() {
		return wechat_server_id;
	}
	public void setWechat_server_id(String wechat_server_id) {
		this.wechat_server_id = wechat_server_id;
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
	public Integer getRead_num() {
		return read_num;
	}
	public void setRead_num(Integer read_num) {
		this.read_num = read_num;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getTitle_attached() {
		return title_attached;
	}
	public void setTitle_attached(String title_attached) {
		this.title_attached = title_attached;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
