/**
 * 
 */
package com.yd.business.order.service;

import java.util.List;

import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.bean.ShopOrderRemindBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.user.bean.UserCartBean;
import com.yd.business.user.bean.UserWechatBean;

/**
 * @author ice
 *
 */
public interface IShopOrderService {

	void createShopOrderInfo(ShopOrderInfoBean bean);

	void updateShopOrderInfo(ShopOrderInfoBean bean);

	List<ShopOrderInfoBean> queryShopOrderInfo(ShopOrderInfoBean bean);

	void createShopOrderProduct(ShopOrderProductBean bean);

	List<ShopOrderProductBean> queryShopOrderProduct(ShopOrderProductBean bean);

	void setupOrderAddress(String order_code, int userAddrId);

	ShopOrderInfoBean findShopOrderInfoByCode(String order_code);

	void updateShopOrderStatus(String order_code, int status);

	ShopOrderInfoBean findShopOrderInfoById(int id);

	void delteShopOrderById(int id);

	void updateShopOrderStatusToDelete(String order_code);

	void updateShopOrderExpressInfo(ShopOrderInfoBean order, String mode, String code, Integer price);

	ShopOrderInfoBean createOrderLogByUserCartList(String openid, String productJson, Long time);

	void updateShopOrderPaySuccess(int payMoney, String orderCode);

	void updateShopOrderByCoupon(String orderCode, SupplierCouponRecordBean bean);

	List<ShopOrderInfoBean> queryShopOrderAndProductList(ShopOrderInfoBean bean);

	ShopOrderRemindBean createShopOrderRemind(String remind, UserWechatBean user, ShopOrderInfoBean order);

	int updateRabbishOrderStatus(Integer userId);


}
