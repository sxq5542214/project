/**
 * 
 */
package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierTopicBean;

/**
 * @author ice
 *
 */
public interface ISupplierTopicService {

	List<SupplierTopicBean> querySupplierTopic(SupplierTopicBean bean);

	SupplierTopicBean createSupplierTopic(SupplierTopicBean bean);

	void updateSupplierTopic(SupplierTopicBean bean);

	void deleteSupplierTopic(SupplierTopicBean bean);

	SupplierTopicBean findSupplierTopicById(int id);

	void updateEventReadNum(int id, int num);

	List<SupplierTopicBean> queryBeforEndTimeSupplierTopic(SupplierTopicBean bean);

	List<SupplierTopicBean> queryAfterEndTimeSupplierTopic(SupplierTopicBean bean);

}
