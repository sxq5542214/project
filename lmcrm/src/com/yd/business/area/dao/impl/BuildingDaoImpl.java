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
	public int insertBuilding(BuildingBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(NAMESPACE+"insertBuilding", bean);
	}

	@Override
	public int updateBuilding(BuildingBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(NAMESPACE+"updateBuilding", bean);
	}

	@Override
	public int deleteBuilding(long id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(NAMESPACE+"deleteBuilding", id);
	}
	
	
	
	
}
