/**
 * 
 */
package com.yd.business.activity.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("activityProduct")
public class ActivityProductBean extends BaseBean {
	/**
	 * weight为权重
	 */
	public static final int WEIGHT_TYPE_WEIGHT = 1;
	/**
	 * weight为比例
	 */
	public static final int WEIGHT_TYPE_RATE = 0;
	
	public static final int REAL_NUM_MIN_VALUE = 0;
	
	private Integer id;
	private Integer activity_id;
	private Integer supplier_product_id;
	private Integer other_spid;
	private String product_name;
	private String create_time;
	private String eff_time;
	private String dff_time;
	private Integer weight;
	private Integer weight_type;
	
	private Integer weight_min;
	private Integer weight_max;
	
	private Integer prize_id;
	private Integer real_num;
	private String modify_time;
	private Integer show_num;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public Integer getSupplier_product_id() {
		return supplier_product_id;
	}
	public void setSupplier_product_id(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}
	public Integer getOther_spid() {
		return other_spid;
	}
	public void setOther_spid(Integer other_spid) {
		this.other_spid = other_spid;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getWeight_min() {
		return weight_min;
	}
	public void setWeight_min(Integer weight_min) {
		this.weight_min = weight_min;
	}
	public Integer getWeight_max() {
		return weight_max;
	}
	public void setWeight_max(Integer weight_max) {
		this.weight_max = weight_max;
	}
	public Integer getPrize_id() {
		return prize_id;
	}
	public void setPrize_id(Integer prize_id) {
		this.prize_id = prize_id;
	}
	public Integer getWeight_type() {
		return weight_type;
	}
	public void setWeight_type(Integer weight_type) {
		this.weight_type = weight_type;
	}
	public Integer getReal_num() {
		return real_num;
	}
	public void setReal_num(Integer real_num) {
		this.real_num = real_num;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getShow_num() {
		return show_num;
	}
	public void setShow_num(Integer show_num) {
		this.show_num = show_num;
	}
	
}
