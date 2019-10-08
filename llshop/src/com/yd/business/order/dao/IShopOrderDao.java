/**
 * 
 */
package com.yd.business.order.dao;

import java.util.List;

import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.bean.ShopOrderRemindBean;

/**
 * @author ice
 *
 */
public interface IShopOrderDao {

	void createShopOrderInfo(ShopOrderInfoBean bean);

	void updateShopOrderInfo(ShopOrderInfoBean bean);

	List<ShopOrderInfoBean> queryShopOrderInfo(ShopOrderInfoBean bean);

	void createShopOrderProduct(ShopOrderProductBean bean);

	List<ShopOrderProductBean> queryShopOrderProduct(ShopOrderProductBean bean);

	void delteShopOrderById(int id);

	List<ShopOrderInfoBean> queryShopOrderAndProductList(ShopOrderInfoBean bean);

	int createShopOrderRemind(ShopOrderRemindBean bean);

}
