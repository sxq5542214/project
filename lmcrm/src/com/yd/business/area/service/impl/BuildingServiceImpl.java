/**
 * 
 */
package com.yd.business.area.service.impl;

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
	
}
