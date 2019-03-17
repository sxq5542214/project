/**
 * 
 */
package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("shopOrderProduct")
public class ShopOrderProductBean extends BaseBean {
	
	/**
	 * 正常类商品
	 */
	public static final int TYPE_NORMAL = 1 ;
	/**
	 * 优惠卷类相关商品
	 */
	public static final int TYPE_COUPON = 2 ;
	
	
	private Integer id;
	private Integer order_info_id;
	private String order_code;
	private Integer user_id;
	private String nick_name;
	private Integer supplier_id;
	private String supplier_name;
	private Integer supplier_product_id;
	private String supplier_product_name;
	private Integer original_price;
	private Integer real_price;
	private Integer cost_points;
	private Integer discount;
	private Integer num;
	private Integer type;
	private String create_time;
	
	private String head_img;
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrder_info_id() {
		return order_info_id;
	}
	public void setOrder_info_id(Integer order_info_id) {
		this.order_info_id = order_info_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public String getSupplier_product_name() {
		return supplier_product_name;
	}
	public void setSupplier_product_name(String supplier_product_name) {
		this.supplier_product_name = supplier_product_name;
	}
	public Integer getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(Integer original_price) {
		this.original_price = original_price;
	}
	public Integer getReal_price() {
		return real_price;
	}
	public void setReal_price(Integer real_price) {
		this.real_price = real_price;
	}
	public Integer getCost_points() {
		return cost_points;
	}
	public void setCost_points(Integer cost_points) {
		this.cost_points = cost_points;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
