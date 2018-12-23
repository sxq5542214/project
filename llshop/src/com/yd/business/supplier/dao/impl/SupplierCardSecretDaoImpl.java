/**
 * 
 */
package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.supplier.dao.ISupplierCardSecretDao;

/**
 * @author ice
 *
 */
@Repository("supplierCardSecretDao")
public class SupplierCardSecretDaoImpl extends BaseDao implements ISupplierCardSecretDao {
	private static final String NAMESPACE = "supplierCardSecret.";

	@Override
	public List<SupplierCardSecretBean> queryCardSecret(SupplierCardSecretBean bean) {
		
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCardSecret", bean,rowBound(bean));
	}
	
	@Override
	public void updateCardSecret(SupplierCardSecretBean bean) {
		
		sqlSessionTemplate.update(NAMESPACE+"updateCardSecret", bean);
	}
	
	@Override
	public Integer queryCardSecretCount(SupplierCardSecretBean bean) {
		
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCardSecretCount", bean);
	}
	
	@Override
	public void batchCreateCardSecret(List<SupplierCardSecretBean> list){
		sqlSessionTemplate.insert(NAMESPACE+"batchCreateCardSecret", list);
	}
	
	@Override
	public int getNewBatchNoByCustomerId(int customer_id){
		Integer batch_no = sqlSessionTemplate.selectOne(NAMESPACE+"getNewBatchNoByCustomerId", customer_id);
		
		if(batch_no == null){
			batch_no = 0;
		}
		return batch_no+1;
	}
	
}
