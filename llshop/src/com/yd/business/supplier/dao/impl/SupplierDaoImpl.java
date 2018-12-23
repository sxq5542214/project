package com.yd.business.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.dao.ISupplierDao;
/**
 * 
 * @author Anlins
 *
 */
@Repository("supplierDao")
public class SupplierDaoImpl extends BaseDao implements ISupplierDao {

	private static final String NAMESPACE = "supplier.";
	@Override
	public void insertSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSupplier", bean);
	}

	@Override
	public void updateSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateSupplier", bean);
	}

	@Override
	public void deleteSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSupplier", bean);
	}

	@Override
	public List<SupplierBean> listSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplier", bean);
	}
	
	@Override
	public List<SupplierBean> querySupplierByCustomerId(int customerId){
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierByCustomerId", customerId);
	}

	@Override
	public void batchDeleteSupplier(List<SupplierBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDeleteSupplier", list);
	}

	@Override
	public SupplierBean findSupplierById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySupplierById", id);
	}

	@Override
	public List<SupplierBean> listSupplierByProductid(Integer parentcustomerid,
			Integer productid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerid", parentcustomerid);
		params.put("productid", productid);
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierByProductid", params);
	}

	@Override
	public List<SupplierBean> listSupplierByPower(int parentCustomerId) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierByPower", parentCustomerId);
	}

	@Override
	public SupplierBean queryMealSupplier(int customerid) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryMealSupplier", customerid);
	}

	@Override
	public List<SupplierBean> querySupplierByMinus(int customerid,
			int productid, int storenum) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customer_id", customerid);
		map.put("product_id", productid);
		map.put("store_num", storenum);
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplierByMinus", map);
	}

}
