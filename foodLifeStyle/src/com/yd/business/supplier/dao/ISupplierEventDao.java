package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;

public interface ISupplierEventDao {

	void update(SupplierEventBean bean);
	void delete(SupplierArticleBean bean);
	SupplierEventBean queryByid(Integer id);
	void batchDelete(List<SupplierArticleBean> list);
//	List<SupplierArticleBean> queryUnSendArticleByUser(int userId, PageinationData page);
	List<SupplierEventCodeBean> queryEventCode(SupplierEventCodeBean bean);
	Integer queryMaxEventCode(int eventId);
	void createSupplierEventCode(SupplierEventCodeBean bean);
	String insert(SupplierEventBean bean);
	Integer queryEventCodeCount(SupplierEventCodeBean bean);
	List<SupplierEventBean> listByLimit(SupplierEventBean bean);
	List<SupplierEventBean> list(SupplierEventBean bean);
	void addEventReadNum(int id, int num);
	List<SupplierEventBean> queryBeforEndTimeSupplierEvent(SupplierEventBean bean);
	List<SupplierEventBean> queryAfterEndTimeSupplierEvent(SupplierEventBean bean);
}
