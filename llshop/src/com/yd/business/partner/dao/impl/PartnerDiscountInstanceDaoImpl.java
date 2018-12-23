/**
 * 
 */
package com.yd.business.partner.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.partner.bean.PartnerDiscountInstanceBean;
import com.yd.business.partner.dao.IPartnerDiscountInstanceDao;

/**
 * @author ice
 *
 */
@Repository("partnerDiscountInstanceDao")
public class PartnerDiscountInstanceDaoImpl extends BaseDao implements IPartnerDiscountInstanceDao {
	private static final String NAMESPACE = "partnerDiscountInstance.";
	
	@Override
	public List<PartnerDiscountInstanceBean> queryPartnerDiscountInstance(PartnerDiscountInstanceBean bean){
		
		return sqlSessionTemplate.selectList(NAMESPACE+"queryPartnerDiscountInstance", bean);
	}
	
	@Override
	public PartnerDiscountInstanceBean findPartnerDiscountInstance(PartnerDiscountInstanceBean bean){
//		PartnerDiscountInstanceBean bean = new PartnerDiscountInstanceBean();
//		bean.setCustomer_id(customer_id);
//		bean.setMax_price(last_month_money);
//		bean.setMin_price(last_month_money);
		return sqlSessionTemplate.selectOne(NAMESPACE + "findPartnerDiscountInstance" , bean);
	}
	
	@Override
	public CustomerDiscountBean findPartnerDiscountByGroup(PartnerDiscountInstanceBean bean){
//		PartnerDiscountInstanceBean bean = new PartnerDiscountInstanceBean();
//		bean.setCustomer_id(customer_id);
//		bean.setMax_price(last_month_money);
//		bean.setMin_price(last_month_money);
		return sqlSessionTemplate.selectOne(NAMESPACE + "findPartnerDiscountByGroup" , bean);
	}
	
}
