package com.yd.business.supplier.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;

public interface ISupplierEventService {
	void update(SupplierEventBean bean);
	void delete(SupplierArticleBean bean);
	void batchDelete(List<SupplierArticleBean> list);
	SupplierEventBean queryByid(Integer id);
	void createEventCode(int eventId, int userId, String fromOpenId);
	String insert(SupplierEventBean bean);
	int queryEventCodeCount(int eventId);
	List<SupplierEventCodeBean> queryAndInitEventCode(int eventId, int userId, String fromOpenId);
	List<SupplierEventCodeBean> queryEventCode(int eventId, int userId, Integer fromUserId);
	List<SupplierEventBean> list(SupplierEventBean bean);
	List<SupplierEventBean> queryList(int status, int limit);
	SupplierEventCodeBean queryCode(Integer eventId, Integer code);
	void updateEventReadNum(int id, int num);
	List<SupplierEventBean> queryBeforEndTimeSupplierEvent(SupplierEventBean bean);
	List<SupplierEventBean> queryAfterEndTimeSupplierEvent(SupplierEventBean bean);
}
