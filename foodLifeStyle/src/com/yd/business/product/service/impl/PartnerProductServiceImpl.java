/**
 * 
 */
package com.yd.business.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.product.bean.PartnerProductBean;
import com.yd.business.product.dao.IPartnerProductDao;
import com.yd.business.product.service.IPartnerProductService;

/**
 * @author ice
 *
 */
@Service("partnerProductService")
public class PartnerProductServiceImpl extends BaseService implements IPartnerProductService {
	@Resource
	private IPartnerProductDao partnerProductDao;
	

	@Override
	public PartnerProductBean findPartnerProduct(PartnerProductBean bean){
		
		return partnerProductDao.findPartnerProduct(bean);
	}

	@Override
	public PartnerProductBean findPartnerProduct(Integer partnerId,String productCode){
		return findPartnerProduct(partnerId, productCode, null);
	}
	

	@Override
	public PartnerProductBean findPartnerProduct(Integer partnerId,String productCode,Integer status){
		PartnerProductBean bean = new PartnerProductBean();
		bean.setPartner_id(partnerId);
		bean.setProduct_code(productCode);
		bean.setStatus(status);
		return findPartnerProduct(bean);
	}
	

	@Override
	public List<PartnerProductBean> queryPartnerProduct(PartnerProductBean bean){
		
		return partnerProductDao.queryPartnerProduct(bean);
	}

	@Override
	public void insertPartnerProduct(PartnerProductBean bean) {
		// TODO Auto-generated method stub
		partnerProductDao.insertPartnerProduct(bean);
	}
	
	
	
}
