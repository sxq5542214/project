/**
 * 
 */
package com.yd.business.supplier.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("supplierVolum")
public class SupplierVolumBean extends BaseBean {
	/**
	 * 代金卷
	 */
	public static final int TYPE_MONEY = 1;
	/**
	 * 兑换卷
	 */
	public static final int TYPE_EXCHANGE = 2;

	/**
	 * 待领取
	 */
	public static final int STATUS_WAIT_GET = 1;
	/**
	 * 已领取
	 */
	public static final int STATUS_ALREADY_GET = 2;
	/**
	 * 已使用
	 */
	public static final int STATUS_ALREADY_USE = 3;
	
	
	private Integer id;
	private Integer supplier_id;
	private Integer money;
	private String name;
	private String code;
	private String description;
	private Date create_time;
	private Date send_time;
	private Date use_start_date;
	private Date use_end_date;
	private Integer type;
	private Date use_time;
	private Integer status;
	private Integer user_id;
	private String user_name;
	
	
	//非数据库字段
	private Integer num_count;
	
	
	public Integer getNum_count() {
		return num_count;
	}
	public void setNum_count(Integer num_count) {
		this.num_count = num_count;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public Date getUse_start_date() {
		return use_start_date;
	}
	public void setUse_start_date(Date use_start_date) {
		this.use_start_date = use_start_date;
	}
	public Date getUse_end_date() {
		return use_end_date;
	}
	public void setUse_end_date(Date use_end_date) {
		this.use_end_date = use_end_date;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getUse_time() {
		return use_time;
	}
	public void setUse_time(Date use_time) {
		this.use_time = use_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
}
