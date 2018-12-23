package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
@Alias("partnerApplyOrderProduct")
public class PartnerApplyOrderProductBean extends BaseBean {

	public static final Integer STATUS_W = 0;//未审核
	public static final Integer STATUS_Y = 1;//审核通过
	public static final Integer STATUS_N = 2;//不通过
	
	private Integer id;
	private Integer orderid;
	private Integer product_id;
	private String product_name;
	private Integer status;
	private String comments;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
