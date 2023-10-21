package com.yd.business.other.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.other.bean.ConfigCruxBean;


public interface IConfigCruxService {
	
	
	/*
	 * 查询订购错误信息
	 * */
	
	List<ConfigCruxBean> queryConfigCruxInfo(ConfigCruxBean bean);

	/*
	 * 分页查询configcrux表信息
	 * */
	public PageinationData queryConfigCruxInfoPage(ConfigCruxBean bean);
	
	ConfigCruxBean getAttributeByTypeAndKey(String type, String key);

	String getValueByTypeAndKey(String type, String key);

	int getIntValueByTypeAndKey(String type, String key);
	
	/**
	 * 删除消息
	 */
	public void deleteSendMessage(ConfigCruxBean bean);
	

	/**
	 * 增加或者编辑发送短信消息
	 */
	public boolean editSendMessage(ConfigCruxBean bean);
	
	
	
}
