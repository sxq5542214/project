/**
 * 
 */
package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.supplier.bean.SupplierTopicBean;

/**
 * @author ice
 *
 */
public interface ISupplierTopicDao {

	List<SupplierTopicBean> querySupplierTopic(SupplierTopicBean bean);

	SupplierTopicBean createSupplierTopic(SupplierTopicBean bean);

	void updateSupplierTopic(SupplierTopicBean bean);

	void deleteSupplierTopic(SupplierTopicBean bean);

	SupplierTopicBean findSupplierTopicById(int id);

	void addTopicReadNum(int id, int num);

	List<SupplierTopicBean> queryAfterEndTimeSupplierTopic(SupplierTopicBean bean);

	List<SupplierTopicBean> queryBeforEndTimeSupplierTopic(SupplierTopicBean bean);

}
