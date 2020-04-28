/**
 * 
 */
package com.yd.business.product.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("product")
public class ProductBean extends BaseBean {
	/**
	 * 待上架
	 */
	public static final int STATUS_WAITUP = 0;
	/**
	 * 上架
	 */
	public static final int STATUS_UP = 1;
	/**
	 * 下架
	 */
	public static final int STATUS_DOWN = 2;
	
	private Integer id;
	private String name;
	private String code;
	private Integer product_price;
	private Integer product_real_price;
	private Integer product_brand;
	private String product_brand_name;
	private String product_title;
	private String product_desc;
	private Integer product_type;
	private String product_type_name;
	private Integer product_limit_points;
	private String product_province;
	private Integer discount;
	private String head_img;
	private Integer status;
	private String attribute;
	private Integer give_points;
	private String intf_code;
	private String intf_param;
	private String wanliu_code;
	
	private Integer package_size;
	private Integer package_size_simple;
	private Integer month_count;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}
	public Integer getProduct_real_price() {
		return product_real_price;
	}
	public void setProduct_real_price(Integer product_real_price) {
		this.product_real_price = product_real_price;
	}
	public Integer getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(Integer product_brand) {
		this.product_brand = product_brand;
	}
	public String getProduct_title() {
		return product_title;
	}
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	public Integer getProduct_type() {
		return product_type;
	}
	public void setProduct_type(Integer product_type) {
		this.product_type = product_type;
	}
	public Integer getProduct_limit_points() {
		return product_limit_points;
	}
	public void setProduct_limit_points(Integer product_limit_points) {
		this.product_limit_points = product_limit_points;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProduct_brand_name() {
		return product_brand_name;
	}
	public void setProduct_brand_name(String product_brand_name) {
		this.product_brand_name = product_brand_name;
	}
	public String getProduct_type_name() {
		return product_type_name;
	}
	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public Integer getGive_points() {
		return give_points;
	}
	public void setGive_points(Integer give_points) {
		this.give_points = give_points;
	}
	public String getIntf_code() {
		return intf_code;
	}
	public void setIntf_code(String intf_code) {
		this.intf_code = intf_code;
	}
	public String getIntf_param() {
		return intf_param;
	}
	public void setIntf_param(String intf_param) {
		this.intf_param = intf_param;
	}
	public String getProduct_province() {
		return product_province;
	}
	public void setProduct_province(String product_province) {
		this.product_province = product_province;
	}
	public Integer getPackage_size() {
		return package_size;
	}
	public void setPackage_size(Integer package_size) {
		this.package_size = package_size;
	}
	public Integer getPackage_size_simple() {
		return package_size_simple;
	}
	public void setPackage_size_simple(Integer package_size_simple) {
		this.package_size_simple = package_size_simple;
	}
	public String getWanliu_code() {
		return wanliu_code;
	}
	public void setWanliu_code(String wanliu_code) {
		this.wanliu_code = wanliu_code;
	}
	public Integer getMonth_count() {
		return month_count;
	}
	public void setMonth_count(Integer month_count) {
		this.month_count = month_count;
	}
	
}
