package com.yd.business.area.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.area.bean.BuildingBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.dao.IBuildingDao;
/**
 * 操作日志
 * @author ice
 *
 */
@Repository("buildingDao")
public class BuildingDaoImpl extends BaseDao implements IBuildingDao {
	 
	private final static String NAMESPACE = "building.";

	@Override
	public List<BuildingExtBean> listBuilding(BuildingBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryBuildingList", bean);
	}

	@Override
	public void insertBuilding(BuildingBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertBuilding", bean);
	}

	@Override
	public void updateBuilding(BuildingBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateBuilding", bean);
	}

	@Override
	public void deleteBuilding(long id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteBuilding", id);
	}
	
	
	
	
}
