package com.yd.business.area.service;

import java.util.List;

import com.yd.business.area.bean.BuildingBean;
import com.yd.business.area.bean.BuildingExtBean;

public interface IBuildingService {

	List<BuildingExtBean> queryBuildingByArea(Long areaId);

}
