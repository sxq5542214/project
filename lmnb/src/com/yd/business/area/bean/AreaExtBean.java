/**
 * 
 */
package com.yd.business.area.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("areaExt")
public class AreaExtBean extends AreaBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2230580686490525108L;
	private String text;
	private String href;
	private List<BuildingExtBean> nodes;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<BuildingExtBean> getNodes() {
		return nodes;
	}
	public void setNodes(List<BuildingExtBean> nodes) {
		this.nodes = nodes;
	}
	
}
