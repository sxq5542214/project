/**
 * 
 */
package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.bean.SupplierUserBean;

/**
 * @author ice
 *
 */
public interface ISupplierUserDao {

	List<SupplierUserBean> querySupplierUser(SupplierUserBean bean);

	SupplierUserBean findSupplierUserById(int id);

	SupplierUserBean createSupplierUser(SupplierUserBean bean);

	void updateSupplierUser(SupplierUserBean bean);

	void deleteSupplierUser(SupplierUserBean bean);

}
