package com.yd.business.area.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.area.bean.AreaOperatorBean;
import com.yd.business.area.dao.IAreaOperatorDao;
/**
 * 操作日志
 * @author ice
 *
 */
@Repository("areaOperatorDao")
public class AreaOperatorDaoImpl extends BaseDao implements IAreaOperatorDao {
	 
	private final static String NAMESPACE = "areaOperator.";

	@Override
	public List<AreaOperatorBean> listAreaOperator(AreaOperatorBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAreaOperatorList", bean);
	}

	@Override
	public void insertAreaOperator(AreaOperatorBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertAreaOperator", bean);
	}

	@Override
	public void updateAreaOperator(AreaOperatorBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateAreaOperator", bean);
	}

	@Override
	public void deleteAreaOperator(long id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteAreaOperator", id);
	}
	
	
	
	
}
