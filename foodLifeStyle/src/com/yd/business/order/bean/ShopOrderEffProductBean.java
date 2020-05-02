/**
 * 
 */
package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.util.AutoInvokeGetSetMethod;

/**
 * @author ice
 *
 */
@Alias("shopOrderEffProduct")
public class ShopOrderEffProductBean extends ShopOrderProductBean {
	public ShopOrderEffProductBean() {
		super();
	}
	public ShopOrderEffProductBean(ShopOrderProductBean bean) throws Exception {
		AutoInvokeGetSetMethod.autoInvoke(bean, this);
	}
}
