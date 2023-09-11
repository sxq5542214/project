package com.yd.business.other.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author zxz
 *
 */
@Alias("configCrux")
public class ConfigCruxBean extends BaseBean {
	public static final int STATUS_USE = 1;
	public static final int STATUS_NO_USE = 0;
	public static final String TYPE_ORDER_ERROR ="order_error";

	public static final String TYPE_WECHAT_NOTIFY_TEXT = "wechat_notify_text";
	public static final String TYPE_WECHAT_TEMPLATE_MSG = "wechat_template_msg";
	public static final String TYPE_COMMENT_NOTIFY_TEXT = "comment_notify_text";
	public static final String TYPE_WECHAT_MENU_JUMP_URL = "wechat_menu_jump_url";
	public static final String TYPE_ACTIVITY_JUMP_URL = "activity_jump_url";
	public static final String TYPE_ACTIVITYIMG_JUMP_URL = "activityimg_jump_url";

	
	public static final String CONFIG_CRUX_TYPE_POP_NEWS = "pop_news"; 					
	public static final String CONFIG_CRUX_KEY_OPERATION_SUCCESS = "operation_success"; //操作成功
	public static final String CONFIG_CRUX_KEY_DATA_REPEAT = "data_repeat"; 			//key重复
	public static final String CONFIG_CRUX_DELETE_ERROR = "delete_error";				//删除错误

	
	public static final String DICTIONARY_TABLENAME_CONFIGCRUXBEAN = "ConfigCruxBean";
	public static final String DICTIONARY_ATTRBUTE_TYPE = "type";

	
	public static final int NUMBER_ZERO = 0 ;
	public static final int NUMBER_ONE = 1 ;
	public static final int NUMBER_TEN = 10 ;
	
		private Integer id ; 
		private String code;
		private String type;
		private String key;
		private String value;
		private Integer status;
		private String create_time;
		private String modify_time;
		private String name ;
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
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
		
		
		
	}
