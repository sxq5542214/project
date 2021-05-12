package com.yd.business.area.dao;

import java.util.List;

import com.yd.business.area.bean.BuildingBean;
import com.yd.business.area.bean.BuildingExtBean;

public interface IBuildingDao {

	List<BuildingExtBean> listBuilding(BuildingBean bean);

	void insertBuilding(BuildingBean bean);

	void updateBuilding(BuildingBean bean);

	void deleteBuilding(long id);

}
