/**
 * 
 */
package com.yd.business.area.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("buildingExt")
public class BuildingExtBean extends BuildingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866687326416689130L;
	
	private String text;
	private String href;
	private String full_name;
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
}
