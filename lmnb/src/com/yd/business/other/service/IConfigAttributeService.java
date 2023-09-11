package com.yd.business.other.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.other.bean.ConfigAttributeBean;

public interface IConfigAttributeService {

	ConfigAttributeBean getAttributeByCode(String code);

	String getValueByCode(String code);

	int getIntValueByCode(String code);

	boolean getBooleanValueByCode(String code);

	String getValueByCode(String code, String originalid);

	ConfigAttributeBean findAttributeByCode(String code, String originalid);

	void updateValueByCode(String code, String value, String originalid);
	
	PageinationData queryConfigAttributeForPage(ConfigAttributeBean bean);
	
	ConfigAttributeBean commitConfigAttributeBean(ConfigAttributeBean bean);
	
	List<ConfigAttributeBean> deleteConfigAttributeBean(String ids);
}
