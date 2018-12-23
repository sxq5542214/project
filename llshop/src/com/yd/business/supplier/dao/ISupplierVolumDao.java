/**
 * 
 */
package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierVolumBean;

/**
 * @author ice
 *
 */
public interface ISupplierVolumDao {

	Integer saveSupplierVolum(SupplierVolumBean bean);

	List<SupplierVolumBean> querySupplierVolumList(SupplierVolumBean bean);

	int querySupplierVolumListCount(SupplierVolumBean bean);

	Integer updateSupplierVolum(SupplierVolumBean bean);

	SupplierVolumBean findVolumById(int id);

}
