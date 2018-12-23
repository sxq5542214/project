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
@Alias("msgCenterActionArticleRelation")
public class MsgCenterActionArticleRelationBean extends BaseBean {

	public static final Integer STATUS_ENABLE = 1;
	public static final Integer STATUS_DISABLE = 0;
	
	public static final int DELAY_TIME_NOW = 0;
	
	
	private Integer id;
	private String action_type;
	private String action_name;
	private Integer article_type;
	private String article_type_name;
	private Integer article_id;
	private String article_name;
	private Integer status;
	private Integer delay_time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public Integer getArticle_type() {
		return article_type;
	}
	public void setArticle_type(Integer article_type) {
		this.article_type = article_type;
	}
	public String getArticle_type_name() {
		return article_type_name;
	}
	public void setArticle_type_name(String article_type_name) {
		this.article_type_name = article_type_name;
	}
	public Integer getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}
	public String getArticle_name() {
		return article_name;
	}
	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDelay_time() {
		return delay_time;
	}
	public void setDelay_time(Integer delay_time) {
		this.delay_time = delay_time;
	}
}
