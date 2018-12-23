/**
 * 
 */
package com.yd.business.product.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.product.bean.PartnerProductBean;
import com.yd.business.product.dao.IPartnerProductDao;

/**
 * @author ice
 *
 */
@Repository("partnerProductDao")
public class PartnerProductDaoImpl extends BaseDao implements IPartnerProductDao {
	
	private static final String NAMESPACE = "partnerProduct.";
	
	@Override
	public PartnerProductBean findPartnerProduct(PartnerProductBean bean){
		
		return sqlSessionTemplate.selectOne(NAMESPACE+"findPartnerProduct", bean);
	}
	

	@Override
	public List<PartnerProductBean> queryPartnerProduct(PartnerProductBean bean){
		
		return sqlSessionTemplate.selectList(NAMESPACE+"queryPartnerProduct", bean);
	}


	@Override
	public void insertPartnerProduct(PartnerProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertPartnerProduct", bean);
	}
	
}
