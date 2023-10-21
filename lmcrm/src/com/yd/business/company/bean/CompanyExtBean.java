/**
 * 
 */
package com.yd.business.company.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.business.area.bean.AreaExtBean;

/**
 * @author ice
 *
 */
@Alias("companyExt")
public class CompanyExtBean extends CompanyBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2035622446124746356L;
	private String text;
	private String href;
	private List<AreaExtBean> nodes;
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
	public List<AreaExtBean> getNodes() {
		return nodes;
	}
	public void setNodes(List<AreaExtBean> nodes) {
		this.nodes = nodes;
	}
	
}
