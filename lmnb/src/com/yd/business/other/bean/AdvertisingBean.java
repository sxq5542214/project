package com.yd.business.other.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author zxz
 *
 */
@Alias("advertising")
public class AdvertisingBean extends BaseBean {
		public static final int STATUS_USE = 1;
		public static final int STATUS_NO_USE = 0;

		public static String EDIT_SUCCESS = "edit_success";

		//商户主页
		public static String CODE_SUPPLIERINDEXPAGE = "supplierIndexPage";
		//商户分类页
		public static String CODE_SUPPLIERCAGEGORYPAGE = "supplierCagegoryPage";
		//平台主页
		public static String CODE_PLATFORMINDEXPAGE = "platformIndexPage";
		
		
		private Integer id ; 
		private String code;
		private String type;
		private String picture;
		private String picture_link;
		private Integer status;
		private String create_time;
		private String modify_time;
		private Integer seq;
		
		private String remark; 
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}
		public String getPicture_link() {
			return picture_link;
		}
		public void setPicture_link(String picture_link) {
			this.picture_link = picture_link;
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
		public Integer getSeq() {
			return seq;
		}
		public void setSeq(Integer seq) {
			this.seq = seq;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
}
