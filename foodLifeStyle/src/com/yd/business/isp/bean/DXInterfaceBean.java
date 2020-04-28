/**
 * 
 */
package com.yd.business.isp.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("dxInterface")
public class DXInterfaceBean extends ISPInterfaceBean {
// http://120.209.204.229/open/abilityDetail?abilityId=107b76cb-154b-4067-b711-84e537c2083a
	
	public static final String RESULT_CODE_SUCCESS = "0";
	//业务
	private String phone;
	private String product_code;
	private String transactionId;
	private String method;
	
	//返回
	private Integer total;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
