package com.yd.business.area.dao;

import java.util.List;

import com.yd.business.area.bean.AreaOperatorBean;

public interface IAreaOperatorDao {

	List<AreaOperatorBean> listAreaOperator(AreaOperatorBean bean);

	void insertAreaOperator(AreaOperatorBean bean);

	void updateAreaOperator(AreaOperatorBean bean);

	void deleteAreaOperator(long id);

}
