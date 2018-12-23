/**
 * 
 */
package com.yd.business.isp.client;

import java.util.List;

import org.apache.log4j.Logger;

import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.product.bean.ProductBean;

/**
 * @author ice
 *
 */
public abstract class OrderProductClient {
	protected Logger log = Logger.getLogger(getClass());
	
	public abstract ISPInterfaceBean accessOrderProduct(String orderCode,String phone,ProductBean product);
	
	protected abstract void initParams();
	
	public abstract ISPInterfaceBean queryBalance();
	
	public abstract ISPInterfaceBean queryOrderStatus(List<String> orderCode);
	
	
}
