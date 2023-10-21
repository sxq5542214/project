package com.yd.business.other.dao;

import java.util.List;

import com.yd.business.other.bean.ConfigAttributeBean;

public interface IConfigAttributeDao {

	String getValueByCode(String code);

	ConfigAttributeBean getAttributeByCode(String code);
	
	ConfigAttributeBean findAttribute(ConfigAttributeBean bean);

	void updateValueByCode(String code, String value, String originalid);

	public List<ConfigAttributeBean> queryConfigAttributeByBean(ConfigAttributeBean bean);
	
	int queryConfigAttributeCount(ConfigAttributeBean bean);
	
	int insertIntoConfigAttributeBean(ConfigAttributeBean bean);
	
	int updateConfigAttributeBean(ConfigAttributeBean bean);
	
	int deleteConfigAttributeBean(List<ConfigAttributeBean> beans);
}
