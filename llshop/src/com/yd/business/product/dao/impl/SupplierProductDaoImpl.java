package com.yd.business.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.product.bean.SupplierProductAttachBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.dao.ISupplierProductDao;
@Repository("supplierProductDao")
public class SupplierProductDaoImpl extends BaseDao implements
		ISupplierProductDao {

	private static final String NAMESPACE = "supplierProduct.";
	@Override
	public int insertSupplierProduct(SupplierProductBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(NAMESPACE+"insertSupplierProduct", bean);
	}

	@Override
	public void updateSupplierProduct(SupplierProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateSupplierProduct", bean);
	}

	@Override
	public SupplierProductBean findSupplierProductById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySupplierProductById", id);
	}
	
	

	@Override
	public List<SupplierProductBean> querySupplierProduct(SupplierProductBean sp){
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierProduct", sp);
	}
	
	@Override
	public List<SupplierProductBean> queryPlatformProduct(SupplierProductBean sp){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryPlatfromProduct", sp);
	}
	
	@Override
	public SupplierProductBean findSupplierProductBySpid(int spid){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findSupplierProductBySpid", spid);
	}

	@Override
	public List<SupplierProductBean> queryProBySupParCustomerId(
			Integer parcustid, Integer custid) {
		// TODO Auto-generated method stub
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("parentcustomerid", parcustid);
		params.put("customerid", custid);
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierProductBySupplierParentCustomerId", params);
	}

	@Override
	public void deleteSupplierProduct(SupplierProductBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSupplierProduct", bean);
	}

	@Override
	public void batchDeleteSupplierProduct(List<SupplierProductBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDeleteSupplierProduct", list);
	}
	
	/**
	 * 查询商户的附加商品
	 */
	@Override
	public List<SupplierProductAttachBean> queryAttachProduct(SupplierProductAttachBean bean){
		
		return sqlSessionTemplate.selectList(NAMESPACE + "queryAttachProduct", bean);
	}

	@Override
	public List<SupplierProductBean> querySupplierProductByIds(List<Integer> ids) {
		return sqlSessionTemplate.selectList(NAMESPACE +"querySupplierProductByIdList", ids);
	}

	@Override
	public List<SupplierProductBean> querySupplierProductByIds(String ids) {
		return sqlSessionTemplate.selectList(NAMESPACE +"querySupplierProductByIdsString", ids);
	}

}
