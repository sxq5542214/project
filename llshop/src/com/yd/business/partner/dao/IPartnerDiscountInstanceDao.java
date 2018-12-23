/**
 * 
 */
package com.yd.business.partner.dao;

import java.util.List;

import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.partner.bean.PartnerDiscountInstanceBean;

/**
 * @author ice
 *
 */
public interface IPartnerDiscountInstanceDao {

	List<PartnerDiscountInstanceBean> queryPartnerDiscountInstance(PartnerDiscountInstanceBean bean);

	PartnerDiscountInstanceBean findPartnerDiscountInstance(PartnerDiscountInstanceBean bean);

	CustomerDiscountBean findPartnerDiscountByGroup(PartnerDiscountInstanceBean bean);

}
