package com.yd.business.msgcenter.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

@Alias("msgCenterArticle")
public class MsgCenterArticleBean extends BaseBean {
	
	/**
	 * 微信文本消息
	 */
	public static final int SEND_TYPE_WECHAT_TEXT = 1;
	/**
	 * 短信消息
	 */
	public static final int SEND_TYPE_SMS_TEXT = 2;
	/**
	 * 微信单图文链接
	 */
	public static final int SEND_TYPE_WECHAT_NEWS = 3;
	/**
	 * 外系统图文链接
	 */
	public static final int SEND_TYPE_SYSTEM_NEWS = 4;
	/**
	 * 微信多图文链接
	 */
	public static final int SEND_TYPE_WECHAT_NEWS_ALL = 5;
	/**
	 * 系统文章
	 */
	public static final int SEND_TYPE_SYSTEM_TOPIC = 6;
	/**
	 * 微信模板消息
	 */
	public static final int SEND_TYPE_WECHAT_TEMPLATE_MSG = 7;
	
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	
	public static final int ASSIGN_SEND_STATUS_SUCCESS = 1;
	public static final int ASSIGN_SEND_STATUS_WAIT = 0;
	
	
	
	private Integer id;
	private String name;
	private Integer type;
	private String type_name;
	private String area;
	private Integer send_type;
	private Integer material_id;
	private String material_code;
	private String material_name;
	private String remark;
	private String param;
	private String create_time;
	private String start_time;
	private String end_time;
	private Integer status;
	private Integer sex_type;
	private Integer seq;
	
	private String assign_send_time;
	private Integer assign_send_status;
	private String delivery_original_name;

	
	public Integer getSex_type() {
		return sex_type;
	}
	public void setSex_type(Integer sex_type) {
		this.sex_type = sex_type;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getSend_type() {
		return send_type;
	}
	public void setSend_type(Integer send_type) {
		this.send_type = send_type;
	}
	public Integer getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(Integer material_id) {
		this.material_id = material_id;
	}
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getAssign_send_time() {
		return assign_send_time;
	}
	public void setAssign_send_time(String assign_send_time) {
		this.assign_send_time = assign_send_time;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public Integer getAssign_send_status() {
		return assign_send_status;
	}
	public void setAssign_send_status(Integer assign_send_status) {
		this.assign_send_status = assign_send_status;
	}
	public String getDelivery_original_name() {
		return delivery_original_name;
	}
	public void setDelivery_original_name(String delivery_original_name) {
		this.delivery_original_name = delivery_original_name;
	}
	
}
