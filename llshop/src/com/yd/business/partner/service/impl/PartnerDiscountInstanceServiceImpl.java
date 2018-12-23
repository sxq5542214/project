/**
 * 
 */
package com.yd.business.partner.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.partner.bean.PartnerDiscountInstanceBean;
import com.yd.business.partner.dao.IPartnerDiscountInstanceDao;
import com.yd.business.partner.dao.impl.PartnerDiscountInstanceDaoImpl;
import com.yd.business.partner.service.IPartnerDiscountInstanceService;

/**
 * @author ice
 *
 */
@Service("partnerDiscountInstanceService")
public class PartnerDiscountInstanceServiceImpl extends BaseService implements IPartnerDiscountInstanceService {
	@Resource
	private IPartnerDiscountInstanceDao partnerDiscountInstanceDao;
	
	@Override
	public List<PartnerDiscountInstanceBean> queryPartnerDiscountInstance(PartnerDiscountInstanceBean bean){
		
		return partnerDiscountInstanceDao.queryPartnerDiscountInstance(bean);
	}
	
	@Override
	public PartnerDiscountInstanceBean findPartnerDiscountInstance(Integer customer_id,int last_month_money,int product_brand,int product_type,Integer product_id){
		PartnerDiscountInstanceBean bean = new PartnerDiscountInstanceBean();
		bean.setCustomer_id(customer_id);
		bean.setMax_price(last_month_money);
		bean.setMin_price(last_month_money);
		bean.setProduct_brand(product_brand);
		bean.setProduct_type(product_type);
		bean.setProduct_id(product_id);
		return partnerDiscountInstanceDao.findPartnerDiscountInstance(bean);
	}
	
	/**
	 * 查询伙伴的折扣信息，通过引用的折扣组
	 * @param customer_id
	 * @param last_month_money
	 * @param product_brand
	 * @param product_type
	 * @return
	 */
	@Override
	public CustomerDiscountBean findPartnerDiscountByGroup(Integer customer_id,int last_month_money,int product_brand,int product_type){
		PartnerDiscountInstanceBean bean = new PartnerDiscountInstanceBean();
		bean.setCustomer_id(customer_id);
		bean.setMax_price(last_month_money);
		bean.setMin_price(last_month_money);
		bean.setProduct_brand(product_brand);
		bean.setProduct_type(product_type);
		
		return partnerDiscountInstanceDao.findPartnerDiscountByGroup(bean);
	}
	
	
	
	
}
