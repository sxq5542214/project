/**
 * 
 */
package com.yd.business.supplier.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.supplier.bean.SupplierCardSecretBean;

/**
 * @author ice
 *
 */
public interface ISupplierCardSecretService {

	PageinationData queryCardSecret(SupplierCardSecretBean bean) throws Exception;

	void createCardSecret(SupplierCardSecretBean bean) throws Exception;

	SupplierCardSecretBean findSupplierCardSecret(String secret_key);

	void udpateCardSecret(SupplierCardSecretBean bean);

	SupplierCardSecretBean findSupplierCardSecret(int id);

	OrderProductLogBean useCardSecret(String secret_key, String phone, boolean flag);

}
