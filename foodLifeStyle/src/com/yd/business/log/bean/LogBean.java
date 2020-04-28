package com.yd.business.log.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
/**
 * 操作日志
 * @author Anlins
 *
 */
@Alias("log")
public class LogBean extends BaseBean {
	public static final int TYPE_CU = 1;
	public static final int TYPE_SU = 2;
	public static final int TYPE_OP = 3;
	
	public static final int ACTION_DE = 1;
	public static final int ACTION_IN = 2;
	public static final int ACTION_UP = 3;
	
	public static final String FUN_OPERAT = "操作员管理";
	private Integer id;
	private Integer userid;
	private Integer type;
	private Date date;
	private Integer action;
	private String content;
	private String function;
	private Integer supplier_id;
	
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	
}
