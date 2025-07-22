/**
 * 
 */
package com.yd.business.user.bean;

import org.apache.ibatis.type.Alias;

import com.yd.iotbusiness.mapper.model.LmUserModel;

/**
 * 
 */

@Alias("userModelExtendsBean")
public class UserModelExtendsBean extends LmUserModel {
	public static final int STATUS_DELETED = -1;
	public static final int STATUS_NORMAL = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1189972174820310962L;
	
	private Integer productid;
	private String metercode;
	private Integer priceCode;
	private String priceName;
	private String meterOpened;
	private String meterStoptime;
	private String factoryCode;
	private String factoryName;
	private Byte changed;
	
	public Byte getChanged() {
		return changed;
	}
	public void setChanged(Byte changed) {
		this.changed = changed;
	}
	public String getMetercode() {
		return metercode;
	}
	public void setMetercode(String metercode) {
		this.metercode = metercode;
	}
	public String getMeterOpened() {
		return meterOpened;
	}
	public void setMeterOpened(String meterOpened) {
		this.meterOpened = meterOpened;
	}
	public String getMeterStoptime() {
		return meterStoptime;
	}
	public void setMeterStoptime(String meterStoptime) {
		this.meterStoptime = meterStoptime;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public Integer getPriceCode() {
		return priceCode;
	}
	public void setPriceCode(Integer priceCode) {
		this.priceCode = priceCode;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	
	
}
