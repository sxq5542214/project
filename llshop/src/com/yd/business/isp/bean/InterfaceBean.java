/**
 * 
 */
package com.yd.business.isp.bean;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
public class InterfaceBean extends BaseBean {
	
	private Integer status;
	private String remark;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
