package com.yd.business.area.dao;

import java.util.List;

import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.bean.AreaExtBean;
import com.yd.business.area.bean.BuildingBean;

public interface IAreaDao {

	int insertArea(AreaBean bean);

	int updateArea(AreaBean bean);

	int deleteArea(long id);

	List<AreaExtBean> queryAreaList(AreaBean bean);

}
