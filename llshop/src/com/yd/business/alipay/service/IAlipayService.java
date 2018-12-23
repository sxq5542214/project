/**
 * 
 */
package com.yd.business.alipay.service;

import java.util.Map;

import com.yd.business.alipay.bean.AliPayLogBean;

/**
 * @author ice
 *
 */
public interface IAlipayService {

	String createOrderProductUnifiedOrder(Integer adminid, String phone, Integer spid);

	AliPayLogBean handleServerNotify(Map<String, String[]> requestParams, String method) throws Exception;

	String createCustomerRechargeUnifiedOrder(int customer_id, int moeny);

	String toAliPayPage(String out_no, int type);

}
