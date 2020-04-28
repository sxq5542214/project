/**
 * 
 */
package com.yd.business.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.dao.ISupplierTopicDao;

/**
 * @author ice
 *
 */
@Repository("supplierTopicDao")
public class SupplierTopicDaoImpl extends BaseDao implements ISupplierTopicDao {
	private static final String NAMESPACE = "supplierTopic.";
	
	@Override
	public List<SupplierTopicBean> querySupplierTopic(SupplierTopicBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "querySupplierTopic", bean);
	}
	
	@Override
	public SupplierTopicBean findSupplierTopicById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE +"findSupplierTopicById", id);
	}
	
	@Override
	public SupplierTopicBean createSupplierTopic(SupplierTopicBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createSupplierTopic", bean);
		return bean;
	}
	
	@Override
	public void updateSupplierTopic(SupplierTopicBean bean){
		sqlSessionTemplate.update(NAMESPACE + "updateSupplierTopic", bean);
	}
	
	@Override
	public void deleteSupplierTopic(SupplierTopicBean bean){
		sqlSessionTemplate.delete(NAMESPACE +"deleteSupplierTopic", bean);
	}
	@Override
	public void addTopicReadNum(int id, int num) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("num", num);
		sqlSessionTemplate.update("addTopicReadNum", map);
		
	}
	
	@Override
	public List<SupplierTopicBean> queryAfterEndTimeSupplierTopic(SupplierTopicBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAfterEndTimeSupplierTopic", bean);
	}

	@Override
	public List<SupplierTopicBean> queryBeforEndTimeSupplierTopic(SupplierTopicBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryBeforEndTimeSupplierTopic", bean);
	}
	
	
}
