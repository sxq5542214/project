/**
 * 
 */
package com.yd.business.area.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.BuildingBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.dao.IBuildingDao;
import com.yd.business.area.service.IBuildingService;

/**
 * @author ice
 *
 */
@Service("buildingService")
public class BuildingServiceImpl extends BaseService implements IBuildingService {
	@Autowired
	private IBuildingDao buildingDao;
	
	@Override
	public List<BuildingExtBean> queryBuildingByArea(Long areaId){
		BuildingBean bean = new BuildingBean();
		bean.setB_areaid(areaId);
		return buildingDao.listBuilding(bean);
	}
	
	@Override
	public BuildingExtBean findBuildingById(Long buildingId){
		BuildingBean bean = new BuildingBean();
		bean.setB_id(buildingId);
		List<BuildingExtBean> list = buildingDao.listBuilding(bean);
		return list.size()>0 ? list.get(0): null;
	}
	
	@Override
	public int addOrUpdateBuilding(BuildingBean bean) {
		int res = 0 ; 
		
		if(bean.getB_id() == null) {
			bean.setB_createdate(new Date());
			bean.setB_updatedate(bean.getB_createdate());
			res = buildingDao.insertBuilding(bean);
		}else {
			bean.setB_updatedate(new Date());
			res = buildingDao.updateBuilding(bean);
		}
		
		
		return res;
	}
	
	
}
