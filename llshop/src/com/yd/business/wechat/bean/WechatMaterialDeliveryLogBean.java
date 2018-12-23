package com.yd.business.wechat.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 用户后台编辑微信图文的消息记录
 * @author BoBo
 *
 */
@Alias("wechatMaterialDeliveryLog")
public class WechatMaterialDeliveryLogBean extends BaseBean {

	public static String ACTION_ADD = "ADD";
	public static String ACTION_UPDATE = "UPDATE";
	public static String ACTION_DEL = "DEL";
	public static String ACTION_GET = "GET";
	/**
	 * 程序异常，不对消息操作
	 */
	public static String ACTION_NO = "NOACTION";
	
	/**
	 * 操作人ID
	 */
	private Integer user_id;
	/**
	 * 操作的素材ID
	 */
	private String media_id;
	/**
	 * 最后一次操作时间
	 */
	private String delivery_time;
	/**
	 * 操作类型
	 */
	private String action;
	/**
	 * 分发的公众号id
	 */
	private String originalid;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOriginalid() {
		return originalid;
	}
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}
	
	
}
