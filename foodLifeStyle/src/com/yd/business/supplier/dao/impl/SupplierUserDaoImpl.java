/**
 * 
 */
package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.dao.ISupplierUserDao;

/**
 * @author ice
 *
 */
@Repository("supplierUserDao")
public class SupplierUserDaoImpl extends BaseDao implements ISupplierUserDao {
	private static final String NAMESPACE = "supplierUser.";
	
	@Override
	public List<SupplierUserBean> querySupplierUser(SupplierUserBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "querySupplierUser", bean);
	}
	
	@Override
	public SupplierUserBean findSupplierUserById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE +"findSupplierUserById", id);
	}
	
	@Override
	public SupplierUserBean createSupplierUser(SupplierUserBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createSupplierUser", bean);
		return bean;
	}
	
	@Override
	public void updateSupplierUser(SupplierUserBean bean){
		sqlSessionTemplate.update(NAMESPACE + "updateSupplierUser", bean);
	}
	
	@Override
	public void deleteSupplierUser(SupplierUserBean bean){
		sqlSessionTemplate.delete(NAMESPACE +"deleteSupplierUser", bean);
	}
	
	
}
