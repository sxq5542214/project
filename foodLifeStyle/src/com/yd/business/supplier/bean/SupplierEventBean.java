/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("supplierEvent")
public class SupplierEventBean extends SupplierArticleBean {

	/**
	 * 系统文章
	 */
	public static final int TYPE_SYSTEM_EVENT = 1;
	/**
	 * 用户列表可见的文章
	 */
	public static final int TYPE_USER_SEE_EVENT = 2;
	public static final int TYPE_OTHER = 3;
	
	
	private String lotteryInfo;
	private String lotteryDate;
	private String lotteryNumber;
	private String prize_content;
	private Integer joinCount;
	private String searchName;
	private String lotteryPlace;
	private Integer bonusMaxUserId;
	private Integer bonusNum;
	
	public String getLotteryInfo() {
		return lotteryInfo;
	}
	public void setLotteryInfo(String lotteryInfo) {
		this.lotteryInfo = lotteryInfo;
	}
	public String getLotteryDate() {
		return lotteryDate;
	}
	public void setLotteryDate(String lotteryDate) {
		this.lotteryDate = lotteryDate;
	}
	public String getLotteryNumber() {
		return lotteryNumber;
	}
	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	public Integer getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getPrize_content() {
		return prize_content;
	}
	public void setPrize_content(String prize_content) {
		this.prize_content = prize_content;
	}
	public String getLotteryPlace() {
		return lotteryPlace;
	}
	public void setLotteryPlace(String lotteryPlace) {
		this.lotteryPlace = lotteryPlace;
	}
	public Integer getBonusMaxUserId() {
		return bonusMaxUserId;
	}
	public void setBonusMaxUserId(Integer bonusMaxUserId) {
		this.bonusMaxUserId = bonusMaxUserId;
	}
	public Integer getBonusNum() {
		return bonusNum;
	}
	public void setBonusNum(Integer bonusNum) {
		this.bonusNum = bonusNum;
	}
	
}
