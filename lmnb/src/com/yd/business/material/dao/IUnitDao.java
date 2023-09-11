package com.yd.business.material.dao;

import java.util.List;

import com.yd.business.material.bean.UnitBean;

public interface IUnitDao {

	List<UnitBean> queryUnitList(UnitBean bean);

	int insertUnit(UnitBean bean);

	int updateUnit(UnitBean bean);

	int deleteUnit(long id);

}
