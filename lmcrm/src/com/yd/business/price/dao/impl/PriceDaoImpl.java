/**
 * 
 */
package com.yd.business.price.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.dao.IPriceDao;

/**
 * @author ice
 *
 */
@Repository("priceDao")
public class PriceDaoImpl extends BaseDao implements IPriceDao {
	public static final String NAMESPACE = "price." ;

	@Override
	public List<PriceBean> queryPriceList(PriceBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryPriceList", bean);
	}
	
	@Override
	public int insertPrice(PriceBean bean) {
		return sqlSessionTemplate.insert(NAMESPACE + "insertPrice", bean);
	}
	@Override
	public int updatePrice(PriceBean bean) {
		return sqlSessionTemplate.update(NAMESPACE + "updatePrice", bean);
	}
	
	
}
