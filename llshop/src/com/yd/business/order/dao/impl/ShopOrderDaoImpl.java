/**
 * 
 */
package com.yd.business.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.bean.ShopOrderRemindBean;
import com.yd.business.order.dao.IShopOrderDao;

/**
 * @author ice
 *
 */
@Repository("shopOrderDao")
public class ShopOrderDaoImpl extends BaseDao implements IShopOrderDao {
	private static final String NAMESPACE = "shopOrder.";
	
	@Override
	public void createShopOrderInfo(ShopOrderInfoBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createShopOrderInfo", bean);
	}
	
	@Override
	public void updateShopOrderInfo(ShopOrderInfoBean bean){
		sqlSessionTemplate.update(NAMESPACE + "updateShopOrderInfo", bean);
	}
	@Override
	public List<ShopOrderInfoBean> queryShopOrderInfo(ShopOrderInfoBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryShopOrderInfo", bean);
	}
	@Override
	public void createShopOrderProduct(ShopOrderProductBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createShopOrderProduct",bean);
	}
	@Override
	public List<ShopOrderProductBean> queryShopOrderProduct(ShopOrderProductBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryShopOrderProduct", bean);
	}

	@Override
	public void delteShopOrderById(int id) {
		sqlSessionTemplate.delete(NAMESPACE +"delteShopOrderById", id);
	}

	@Override
	public List<ShopOrderInfoBean> queryShopOrderAndProductList(ShopOrderInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE +"queryShopOrderAndProductList", bean);
	}
	
	@Override
	public int createShopOrderRemind(ShopOrderRemindBean bean){
		return sqlSessionTemplate.insert(NAMESPACE +"createShopOrderRemind", bean);
	}
	
	
}
