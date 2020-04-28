/**
 * 
 */
package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierCardSecretBean;

/**
 * @author ice
 *
 */
public interface ISupplierCardSecretDao {

	List<SupplierCardSecretBean> queryCardSecret(SupplierCardSecretBean bean);

	Integer queryCardSecretCount(SupplierCardSecretBean bean);

	void batchCreateCardSecret(List<SupplierCardSecretBean> list);

	int getNewBatchNoByCustomerId(int customer_id);

	void updateCardSecret(SupplierCardSecretBean bean);

}
