package com.yd.business.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardCostlogBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;
import com.yd.business.supplier.dao.ISupplierStoreDao;

@Repository("supplierStoreDao")
public class SupplierStoreDaoImpl extends BaseDao implements
		ISupplierStoreDao {

	private static final String NAMESPACE = "supplierStore.";
	
	@Override
	public void createStoreBalanceCard(SupplierStoreBalanceCardBean bean) {
		sqlSessionTemplate.insert(NAMESPACE +"createStoreBalanceCard", bean);
	}
	@Override
	public void createStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean) {
		sqlSessionTemplate.insert(NAMESPACE +"createStoreBalanceCardRecord", bean);
	}
	@Override
	public void createStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean) {
		sqlSessionTemplate.insert(NAMESPACE +"createStoreBalanceCardCostlog", bean);
	}
	@Override
	public int updateStoreBalanceCard(SupplierStoreBalanceCardBean bean) {
		return sqlSessionTemplate.update(NAMESPACE +"updateStoreBalanceCard", bean);
	}
	@Override
	public int updateStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean) {
		return sqlSessionTemplate.update(NAMESPACE +"updateStoreBalanceCardRecord", bean);
	}
	@Override
	public int updateStoreCardRecordBalance(int id ,  int addBalance) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("addBalance", addBalance);
		return sqlSessionTemplate.update(NAMESPACE +"updateStoreCardRecordBalance", map);
	}
	@Override
	public List<SupplierStoreBalanceCardBean> queryStoreBalanceCard(SupplierStoreBalanceCardBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryStoreBalanceCard", bean);
	}
	@Override
	public List<SupplierStoreBalanceCardRecordBean> queryStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryStoreBalanceCardRecord", bean);
	}
	@Override
	public List<SupplierStoreBalanceCardCostlogBean> queryStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryStoreBalanceCardCostlog", bean);
	}
}
