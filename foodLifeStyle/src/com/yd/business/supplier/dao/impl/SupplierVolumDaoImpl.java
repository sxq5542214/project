/**
 * 
 */
package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierVolumBean;
import com.yd.business.supplier.dao.ISupplierVolumDao;

/**
 * @author ice
 *
 */
@Repository("supplierVolumDao")
public class SupplierVolumDaoImpl extends BaseDao implements ISupplierVolumDao {
	public static final String NAMESPACE = "supplierVolum.";
	
	@Override
	public List<SupplierVolumBean> querySupplierVolumList(SupplierVolumBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierVolumList", bean,rowBound(bean));
	}
	
	@Override
	public int querySupplierVolumListCount(SupplierVolumBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySupplierVolumListCount", bean);
	}
	
	@Override
	public Integer saveSupplierVolum(SupplierVolumBean bean){
		return sqlSessionTemplate.insert(NAMESPACE+"saveSupplierVolum",bean);
	}
	
	@Override
	public Integer updateSupplierVolum(SupplierVolumBean bean){
		return sqlSessionTemplate.update(NAMESPACE+"updateSupplierVolum",bean);
	}
	
	@Override
	public SupplierVolumBean findVolumById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findVolumById", id);
	}
	
//	@Override
//	public List<SupplierVolumBean> queryFreeVolum(SupplierVolumBean bean){
//		bean.
//		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierVolumList", bean, rowBound(bean));
//	}
	
}
