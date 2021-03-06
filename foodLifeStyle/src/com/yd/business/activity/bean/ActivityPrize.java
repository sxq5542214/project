package com.yd.business.activity.bean;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.dictionary.bean.DictionaryBean;

/**
 * 活动奖品表
 * @author BoBo
 *
 */
@Alias("activityPrize")
public class ActivityPrize extends BaseBean {

	public static final String PRODUCT_TABLE_COUPONCONFIG = "ll_coupon_config" ;
	public static final String PRODUCT_TABLE_SUPPLIERPRODUCT = "ll_supplier_product" ;
	/**
	 * 启用
	 */
	public static final int ACTIVITY_PRIZE_STATUS_ENABLE = 1;
	/**
	 * 不启用
	 */
	public static final int ACTIVITY_PRIZE_STATUS_DISABLE = 0;
	/**
	 * 最后一个奖品
	 */
	public static final int ACTIVITY_PRIZE_LAST_PRIZE = 1;
	/**
	 * 未中奖奖品
	 */
	public static final String ERROR_CODE_NO_WINNER = "noWinner";
	/**
	 * 获取失败
	 */
	public static final String ERROR_CODE_FAIL = "FAIL";
	/**
	 * 重复参与
	 */
	public static final String ERROR_CODE_REPEAT = "REPEAT";
	

	/**
	 * 优惠卷
	 */
	public static final int CATEGORY_COUPON = 1;
	/**
	 * 现金红包
	 */
	public static final int CATEGORY_CASH_BONUS = 2;
	/**
	 * 商品
	 */
	public static final int CATEGORY_CASH_PRODUCT = 3;
	
	

	private Integer id;
	/**
	 * 产品ID
	 */
	private Integer product_id;
	/**
	 * 奖品名称
	 */
	private String prize_name;
	private String remark;
	private Integer status;
	private String create_date;
	/**
	 * 对应产品的表名
	 */
	private String product_table;
	/**
	 * 奖品种类
	 */
	private Integer category;
	/**
	 * 奖品剩余数
	 */
	private int remain_num;
	
	private String prize_img_url;
	
	private Integer bonus_money;
	
	private String errorCode;
	
	private Map<String,List<DictionaryBean>> dicMap;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getPrize_name() {
		return prize_name;
	}
	public void setPrize_name(String prize_name) {
		this.prize_name = prize_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProduct_table() {
		return product_table;
	}
	public void setProduct_table(String product_table) {
		this.product_table = product_table;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public int getRemain_num() {
		return remain_num;
	}
	public void setRemain_num(int remain_num) {
		this.remain_num = remain_num;
	}
	public String getPrize_img_url() {
		return prize_img_url;
	}
	public void setPrize_img_url(String prize_img_url) {
		this.prize_img_url = prize_img_url;
	}
	public Integer getBonus_money() {
		return bonus_money;
	}
	public void setBonus_money(Integer bonus_money) {
		this.bonus_money = bonus_money;
	}
	public Map<String, List<DictionaryBean>> getDicMap() {
		return dicMap;
	}
	public void setDicMap(Map<String, List<DictionaryBean>> dicMap) {
		this.dicMap = dicMap;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
}
