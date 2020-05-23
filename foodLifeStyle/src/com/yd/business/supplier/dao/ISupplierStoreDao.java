package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardCostlogBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;

public interface ISupplierStoreDao {

	void createStoreBalanceCard(SupplierStoreBalanceCardBean bean);

	void createStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean);

	void createStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean);

	int updateStoreBalanceCard(SupplierStoreBalanceCardBean bean);

	int updateStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean);

	List<SupplierStoreBalanceCardBean> queryStoreBalanceCard(SupplierStoreBalanceCardBean bean);

	List<SupplierStoreBalanceCardRecordBean> queryStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean);

	List<SupplierStoreBalanceCardCostlogBean> queryStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean);

	int updateStoreCardRecordBalance(int id, int addBalance);

	
	
	
}
