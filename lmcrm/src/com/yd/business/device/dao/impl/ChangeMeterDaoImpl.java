package com.yd.business.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.device.bean.ChangeMeterBean;
import com.yd.business.device.dao.IChangeMeterDao;
/**
 * 操作日志
 * @author ice
 *
 */
@Repository("changeMeterDao")
public class ChangeMeterDaoImpl extends BaseDao implements IChangeMeterDao {
	 
	private final static String NAMESPACE = "changeMeter.";

	@Override
	public List<ChangeMeterBean> listChangeMeter(ChangeMeterBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryChangeMeterList", bean);
	}

	@Override
	public void insertChangeMeter(ChangeMeterBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertChangeMeter", bean);
	}

	@Override
	public void updateChangeMeter(ChangeMeterBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateChangeMeter", bean);
	}

	@Override
	public void deleteChangeMeter(long id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteChangeMeter", id);
	}
	
	
	
	
}
