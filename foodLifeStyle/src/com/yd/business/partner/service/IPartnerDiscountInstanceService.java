/**
 * 
 */
package com.yd.business.partner.service;

import java.util.List;

import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.partner.bean.PartnerDiscountInstanceBean;

/**
 * @author ice
 *
 */
public interface IPartnerDiscountInstanceService {

	List<PartnerDiscountInstanceBean> queryPartnerDiscountInstance(PartnerDiscountInstanceBean bean);

	PartnerDiscountInstanceBean findPartnerDiscountInstance(Integer customer_id, int last_month_money,
			int product_brand, int product_type,Integer product_id);

	CustomerDiscountBean findPartnerDiscountByGroup(Integer customer_id, int last_month_money, int product_brand,
			int product_type);

}
