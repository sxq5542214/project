/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("supplierTopic")
public class SupplierTopicBean extends SupplierArticleBean {

	private String prize_content;
	private Integer joinCount;
	private String searchName;
	private Integer bonusMaxUserId;
	private Integer bonusNum;
	private String comment_code;
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
	public String getPrize_content() {
		return prize_content;
	}
	public void setPrize_content(String prize_content) {
		this.prize_content = prize_content;
	}
	public String getComment_code() {
		return comment_code;
	}
	public void setComment_code(String comment_code) {
		this.comment_code = comment_code;
	}
	
	
}
