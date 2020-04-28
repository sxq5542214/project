/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.dao.ISupplierTopicDao;
import com.yd.business.supplier.service.ISupplierTopicService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("supplierTopicService")
public class SupplierTopicServiceImpl extends BaseService implements ISupplierTopicService {
	
	@Resource
	private ISupplierTopicDao supplierTopicDao;
	

	@Override
	public List<SupplierTopicBean> querySupplierTopic(SupplierTopicBean bean){
		return supplierTopicDao.querySupplierTopic(bean);
	}
	
	@Override
	public SupplierTopicBean findSupplierTopicById(int id){
		return supplierTopicDao.findSupplierTopicById(id);
	}
	
	@Override
	public SupplierTopicBean createSupplierTopic(SupplierTopicBean bean){
		supplierTopicDao.createSupplierTopic(bean);
		return bean;
	}
	
	@Override
	public void updateSupplierTopic(SupplierTopicBean bean){
		supplierTopicDao.updateSupplierTopic(bean);
	}
	
	@Override
	public void deleteSupplierTopic(SupplierTopicBean bean){
		supplierTopicDao.deleteSupplierTopic(bean);
	}
	@Override
	public void updateEventReadNum(int id,int num){
		supplierTopicDao.addTopicReadNum(id, num);
	}

	@Override
	public List<SupplierTopicBean> queryBeforEndTimeSupplierTopic(SupplierTopicBean bean) {
		return supplierTopicDao.queryBeforEndTimeSupplierTopic(bean);
	}
	
	@Override
	public List<SupplierTopicBean> queryAfterEndTimeSupplierTopic(SupplierTopicBean bean) {
		return supplierTopicDao.queryAfterEndTimeSupplierTopic(bean);
	}
	
}
