package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierStoreBalanceCardBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardCostlogBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;

public interface ISupplierStoreService {
	
	void createStoreBalanceCard(SupplierStoreBalanceCardBean bean);

	void createStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean);

	void createStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean);

	int updateStoreBalanceCard(SupplierStoreBalanceCardBean bean);

	int updateStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean);

	List<SupplierStoreBalanceCardBean> queryStoreBalanceCard(SupplierStoreBalanceCardBean bean);

	List<SupplierStoreBalanceCardRecordBean> queryStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean);

	List<SupplierStoreBalanceCardCostlogBean> queryStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean);

	SupplierStoreBalanceCardBean findStoreBalanceCardById(Integer id);

	SupplierStoreBalanceCardBean findStoreBalanceCardById(Integer id, Integer sid);

	int createStoreBalanceCardRecord(Integer userId,Integer cardId, Integer sid);

	SupplierStoreBalanceCardRecordBean findStoreBalanceCardRecordById(Integer id);

	List<SupplierStoreBalanceCardRecordBean> queryUserCanuseStoreBalanceCardRecord(String openid, Integer sid);

	int updateStoreCardRecordBalance(String openid, Integer id, Integer addBalance, Integer sid, String orderCode,
			String remark);


}
