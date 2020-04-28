/**
 * 
 */
package com.yd.business.partner.service;

import java.util.Map;

import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.partner.bean.PartnerInterfaceBean;

/**
 * @author ice
 *
 */
public interface IPartnerInterfaceService {

	PartnerInterfaceBean handlePartnerInterface(Map<String, String> params, String method);

	PartnerInterfaceBean queryCustomerBalance(int customer_id, String partnerCode);

	PartnerOrderProductBean queryPartnerOrder(int customer_id, String partnerCode, String partner_out_trade_no);

	PartnerInterfaceBean handlerISPInterfaceCallBack(String orderCode, int status, String remark);

}
