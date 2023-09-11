package com.yd.business.other.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.dao.IConfigCruxDao;


/**
 * @author zxz
 *
 */

@Repository("configCruxDao")
public class ConfigCruxDaoImpl extends BaseDao implements IConfigCruxDao {

	
	private static final String NAMESPACE = "configCrux.";
	

	@Override
	public List<ConfigCruxBean> queryConfigCruxInfo(ConfigCruxBean bean){
	
		return sqlSessionTemplate.selectList(NAMESPACE+"queryConfigCruxInfo", bean);
	}

	/**
	 * 分页查询configcrux表信息
	 */
	@Override
	public List<ConfigCruxBean> queryConfigCruxInfoPage(ConfigCruxBean bean){
	
		return sqlSessionTemplate.selectList(NAMESPACE+"queryConfigCruxInfoPage", bean,rowBound(bean));
	}

	
	/**
	 * 查询config_crux表总数
	 */
	@Override
	public int configCruxCount(ConfigCruxBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE +"configCruxCount", bean);
	}
	

	/**
	 * 删除发送消息内容模版 
	 */
	@Override
	public void deleteSendMessage(ConfigCruxBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteSendMessage", bean);
	}

	
	@Override
	public void insertSendMessage(ConfigCruxBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertSendMessage", bean);

	}


	@Override
	public void updateSendMessage(ConfigCruxBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateSendMessage", bean);
	}





}
