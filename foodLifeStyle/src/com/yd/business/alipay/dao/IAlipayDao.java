/**
 * 
 */
package com.yd.business.alipay.dao;

import java.util.List;

import com.yd.business.alipay.bean.AliPayLogBean;

/**
 * @author ice
 *
 */
public interface IAlipayDao {

	List<AliPayLogBean> queryAliPayLog(AliPayLogBean bean);

	AliPayLogBean findAliPayLog(AliPayLogBean bean);

	/**
	 * 根据定单号查
	 * @param out_trade_no
	 * @return
	 */
	AliPayLogBean findAliPayLogByOutNo(String out_trade_no);
	
	/**
	 * 根据支付宝交易号查
	 * @param trade_no
	 * @return
	 */
	AliPayLogBean findAliPayLogByTradeNo(String trade_no, String status);
	void createAliPayLog(AliPayLogBean bean);


}
