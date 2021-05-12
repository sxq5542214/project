/**
 * 
 */
package com.yd.business.price.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.dao.IPriceDao;
import com.yd.business.price.service.IPriceService;

/**
 * @author ice
 *
 */
@Service("priceService")
public class PriceServiceImpl extends BaseService implements IPriceService {
	@Resource
	private IPriceDao priceDao;
	

	@Override
	public List<PriceBean> queryALLPriceList(PriceBean bean) {
		
		return priceDao.queryPriceList(bean);
	}
	
	@Override
	public PriceBean findPriceById(long id) {
		PriceBean bean = new PriceBean();
		bean.setP_id(id);
		List<PriceBean> list = priceDao.queryPriceList(bean);
		return list.size()>0 ? list.get(0): null;
	}

	public int createPrice(PriceBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updatePrice(PriceBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int disablePrice(long priceId) {
		PriceBean bean = new PriceBean();
		bean.setP_id(priceId);
		bean.setP_enabled(PriceBean.ENABLED_FALSE);
		return updatePrice(bean);
	}
	@Override
	public int enablePrice(long priceId) {
		PriceBean bean = new PriceBean();
		bean.setP_id(priceId);
		bean.setP_enabled(PriceBean.ENABLED_TRUE);
		return updatePrice(bean);
	}

	@Override
	public List<PriceBean> queryPriceListByCompany(Long companyid) {
		if(companyid == null) {
			return null;
		}
		PriceBean bean = new PriceBean();
		bean.setP_companyid(companyid);
		
		return priceDao.queryPriceList(bean);
	}

	/**
	 * 添加或修改价格
	 */
	@Override
	public int addOrUpdatePrice(PriceBean bean) {
		
		int i = 0 ;
		
		bean.setP_price1(bean.getP_base1().add(bean.getP_other1()).add(bean.getP_other2()));
		bean.setP_price2(bean.getP_base2().add(bean.getP_other1()).add(bean.getP_other2()));
		bean.setP_price3(bean.getP_base3().add(bean.getP_other1()).add(bean.getP_other2()));
		
		bean.setP_updatedate(new Date());
		if(bean.getP_id() == null) {
			bean.setP_createdate(new Date());
			
			i = priceDao.insertPrice(bean);
		}else {
			i = priceDao.updatePrice(bean);
		}
		return i;
	}

	
	
	
	
}
