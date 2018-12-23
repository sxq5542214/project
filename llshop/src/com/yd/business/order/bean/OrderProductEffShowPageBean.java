/**
 * 
 */
package com.yd.business.order.bean;

import java.util.Map;

import org.apache.ibatis.type.Alias;

/**
 * @author bobo
 *
 */
@Alias("orderProductEffShowPage")
public class OrderProductEffShowPageBean extends OrderProductEffBean {
	/**
	 * 订购商品名称
	 */
	private String product_name;
	

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

}
