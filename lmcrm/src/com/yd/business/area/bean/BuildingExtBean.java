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
	
}
