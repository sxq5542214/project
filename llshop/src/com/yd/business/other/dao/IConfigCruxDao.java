package com.yd.business.other.dao;

import java.util.List;

import com.yd.business.other.bean.ConfigCruxBean;


/**
 * @author zxz
 *
 */
public interface IConfigCruxDao {

	
	List<ConfigCruxBean> queryConfigCruxInfo(ConfigCruxBean bean);

	/**
	 *  分页查询configcrux表信息
	 */
	List<ConfigCruxBean> queryConfigCruxInfoPage(ConfigCruxBean bean);
	
	/**
	 * 查询config_crux表总数
	 */
	public int configCruxCount(ConfigCruxBean bean);
	
	
	/**
	 * 删除发送消息信息
	 */
	public void  deleteSendMessage(ConfigCruxBean bean);


	
	/**
	 * 增加短信内容
	 */
	public void insertSendMessage(ConfigCruxBean bean );

	
	/**
	 * 更新短信内容
	 */
	public void updateSendMessage(ConfigCruxBean bean);
	
}
