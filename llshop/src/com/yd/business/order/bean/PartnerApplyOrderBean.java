package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("partnerApplyOrder")
public class PartnerApplyOrderBean extends BaseBean {

	public static final Integer STATUS_WSH = 0;//未审核
	public static final Integer STATUS_YES = 1;//审核通过
	public static final Integer STATUS_NO = 2;//不通过
	public static final Integer STATUS_CAN = 3;//已取消
	
	private Integer id;
	private String code;
	private String name;
	private String create_time;
	private String audit_time;
	private Integer status;
	private String apply_customer_name;
	private Integer apply_customer_id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getApply_customer_name() {
		return apply_customer_name;
	}
	public void setApply_customer_name(String apply_customer_name) {
		this.apply_customer_name = apply_customer_name;
	}
	public Integer getApply_customer_id() {
		return apply_customer_id;
	}
	public void setApply_customer_id(Integer apply_customer_id) {
		this.apply_customer_id = apply_customer_id;
	}
	
}
