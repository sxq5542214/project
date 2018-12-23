/**
 * 
 */
package com.yd.business.product.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("supplierProduct")
public class SupplierProductBean extends ProductBean {
	
	public static int PACKAGE_NUM_DEFAULT = 1;
	
	public static int COUPON_SHOW_PRODUCT = 1;

	public static int NO_MATCHING_PRODUCT = 0;
	
	public static Integer ZERO = 0;
	
	private Integer product_id;
	private Integer supplier_id;
	private Integer customer_id;
	private String product_name;
	private Integer store_num;
	private String create_time;
	private String eff_time;
	private String dff_time;
	private String modify_time;
	private Integer modify_admin;
	private String supplier_name;
	private Integer min_luckymoney;//最小红包金额
	private Integer max_luckymoney;//最大红包金额
	private String  now_time;	   //当前时间
	private Integer product_offset_points;//可以抵用的积分
	
	//此字段目前用于优惠卷
	private Integer coupon_discount_product;	//优惠卷可以抵扣的商品
	
	/**
	 * 打包数，该产品一共有多少子产品，一般商品都是1个，有特殊商品的（连续购买3个月的，打包数是3）
	 */
	private Integer package_num;
	
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getStore_num() {
		return store_num;
	}
	public void setStore_num(Integer store_num) {
		this.store_num = store_num;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getEff_time() {
		return eff_time;
	}
	public void setEff_time(String eff_time) {
		this.eff_time = eff_time;
	}
	public String getDff_time() {
		return dff_time;
	}
	public void setDff_time(String dff_time) {
		this.dff_time = dff_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getModify_admin() {
		return modify_admin;
	}
	public void setModify_admin(Integer modify_admin) {
		this.modify_admin = modify_admin;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getMin_luckymoney() {
		return min_luckymoney;
	}
	public void setMin_luckymoney(Integer min_luckymoney) {
		this.min_luckymoney = min_luckymoney;
	}
	public Integer getMax_luckymoney() {
		return max_luckymoney;
	}
	public void setMax_luckymoney(Integer max_luckymoney) {
		this.max_luckymoney = max_luckymoney;
	}
	public String getNow_time() {
		return now_time;
	}
	public void setNow_time(String now_time) {
		this.now_time = now_time;
	}
	public Integer getProduct_offset_points() {
		return product_offset_points;
	}
	public void setProduct_offset_points(Integer product_offset_points) {
		this.product_offset_points = product_offset_points;
	}
	public Integer getPackage_num() {
		return package_num;
	}
	public void setPackage_num(Integer package_num) {
		this.package_num = package_num;
	}
	public Integer getCoupon_discount_product() {
		return coupon_discount_product;
	}
	public void setCoupon_discount_product(Integer coupon_discount_product) {
		this.coupon_discount_product = coupon_discount_product;
	}
	
}
