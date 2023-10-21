/**
 * 
 */
package com.yd.business.area.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("areaData")
public class AreaDataBean extends BaseBean {

	public static final String PROVINCE_QG = "全国";
	public static final String PROVINCE_AH = "安徽";
	public static final String BRAND_YD = "移动";
	public static final String BRAND_DX = "电信";
	public static final String BRAND_LT = "联通";

	
	private Integer id;
	private String code;
	private String province;
	private String area_code;
	private String city;
	private String brand;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
}
