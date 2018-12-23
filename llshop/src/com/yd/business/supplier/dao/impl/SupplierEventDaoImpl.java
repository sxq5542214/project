package com.yd.business.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.dao.ISupplierEventDao;
import com.yd.util.NumberUtil;
@Repository("supplierEventDao")
public class SupplierEventDaoImpl extends BaseDao implements
		ISupplierEventDao {

	private static final String NAMESPACE = "supplierEvent.";
	@Override
	public String insert(SupplierEventBean bean) {
		// TODO Auto-generated method stub
		return NumberUtil.toString(sqlSessionTemplate.insert(NAMESPACE+"insertSupplier", bean));
	}

	@Override
	public void update(SupplierEventBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateSupplier", bean);
	}

	@Override
	public void delete(SupplierArticleBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSupplier", bean);
	}

	@Override
	public SupplierEventBean queryByid(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"querySupplierById", id);
	}

	@Override
	public List<SupplierEventBean> list(SupplierEventBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplier", bean);
	}
	
	@Override
	public List<SupplierEventBean> queryBeforEndTimeSupplierEvent(SupplierEventBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryBeforEndTimeSupplierEvent", bean);
	}
	
	@Override
	public List<SupplierEventBean> queryAfterEndTimeSupplierEvent(SupplierEventBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAfterEndTimeSupplierEvent", bean);
	}
	
	@Override
	public List<SupplierEventBean> listByLimit(SupplierEventBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"querySupplier", bean,rowBound(bean));
	}
	
	@Override
	public void batchDelete(List<SupplierArticleBean> list) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"batchDelete", list);
	}
	
	@Override
	public List<SupplierEventCodeBean> queryEventCode(SupplierEventCodeBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryEventCode", bean);
	}
	
	@Override
	public Integer queryEventCodeCount(SupplierEventCodeBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryEventCodeCount", bean);
	}
	
	@Override
	public void createSupplierEventCode(SupplierEventCodeBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createSupplierEventCode",bean);
	}
	
	@Override
	public Integer queryMaxEventCode(int eventId){
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryMaxEventCode", eventId);
	}

	@Override
	public void addEventReadNum(int id, int num) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("num", num);
		sqlSessionTemplate.update("addEventReadNum", map);
		
	}

}
