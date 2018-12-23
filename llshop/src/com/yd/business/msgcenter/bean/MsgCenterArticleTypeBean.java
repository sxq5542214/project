/**
 * 
 */
package com.yd.business.msgcenter.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("msgCenterArticleType")
public class MsgCenterArticleTypeBean extends BaseBean {
	
	/**
	 * 状态启用
	 */
	public static final int STATUS_ENABLE = 1;
	/**
	 * 状态停用
	 */
	public static final int STATUS_DISABLE = 0;
	
	/**
	 * 可重复
	 */
	public static final int REPEAT_YES = 1;
	/**
	 * 不可重复
	 */
	public static final int REPEAT_NO = 0;
	/**
	 * 排序，由低到高
	 */
	public static final String ORDERBY_ASC = "asc";
	/**
	 * 排序，由高到低
	 */
	public static final String ORDERBY_DESEC = "desc";
	
	
	
	private Integer id;
	private String arcticle_type;
	private String arcticle_type_name;
	private String orderby;
	private Integer num;
	private Integer status;
	private Integer repeat;
	private Integer priority;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getArcticle_type() {
		return arcticle_type;
	}
	public void setArcticle_type(String arcticle_type) {
		this.arcticle_type = arcticle_type;
	}
	public String getArcticle_type_name() {
		return arcticle_type_name;
	}
	public void setArcticle_type_name(String arcticle_type_name) {
		this.arcticle_type_name = arcticle_type_name;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRepeat() {
		return repeat;
	}
	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
