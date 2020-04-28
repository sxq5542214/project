/**
 * 
 */
package com.yd.business.lottery.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *	重庆时时彩信息
 */
@Alias("cqsscInfo")
public class CqsscInfoBean extends BaseBean {
	private Integer id;
	private String name;
	private String type;
	private String period;
	private String openCode;
	private Integer code;
	private Integer total;
	private String bigOrSmall;
	private String singleOrDouble;
	private String dragonOrTiger;
	private String openTime;
	private String insertTime;
	private String nextTime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getOpenCode() {
		return openCode;
	}
	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getBigOrSmall() {
		return bigOrSmall;
	}
	public void setBigOrSmall(String bigOrSmall) {
		this.bigOrSmall = bigOrSmall;
	}
	public String getSingleOrDouble() {
		return singleOrDouble;
	}
	public void setSingleOrDouble(String singleOrDouble) {
		this.singleOrDouble = singleOrDouble;
	}
	public String getDragonOrTiger() {
		return dragonOrTiger;
	}
	public void setDragonOrTiger(String dragonOrTiger) {
		this.dragonOrTiger = dragonOrTiger;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	
	
	
	
}
