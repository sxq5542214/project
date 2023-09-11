package com.yd.business.other.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.dao.IConfigAttributeDao;

@Repository("configAttributeDao")
public class ConfigAttributeDaoImpl extends BaseDao implements IConfigAttributeDao {
	public static final String NAMESPACE = "attribute.";
	
	@Override
	public String getValueByCode(String code){
		return sqlSessionTemplate.selectOne(NAMESPACE+"getValueByCode", code);
	}
	
	@Override
	public ConfigAttributeBean getAttributeByCode(String code){
		return sqlSessionTemplate.selectOne(NAMESPACE+"getAttributeByCode", code);
	}
	
	@Override
	public ConfigAttributeBean findAttribute(ConfigAttributeBean bean) {
		return sqlSessionTemplate.selectOne(NAMESPACE+"findAttribute", bean);
	}

	@Override
	public void updateValueByCode(String code,String value,String originalid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("value", value);
		map.put("originalid", originalid);
		sqlSessionTemplate.update(NAMESPACE+"updateValueByCode", map);
	}
	@Override
	public List<ConfigAttributeBean> queryConfigAttributeByBean(ConfigAttributeBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryConfigAttributeByBean", bean,rowBound(bean));
	}
	@Override
	public int queryConfigAttributeCount(ConfigAttributeBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryConfigAttributeCount", bean);
	}

	@Override
	public int insertIntoConfigAttributeBean(ConfigAttributeBean bean) {
		return sqlSessionTemplate.insert(NAMESPACE+"insertIntoConfigAttributeBean", bean);
	}

	@Override
	public int updateConfigAttributeBean(ConfigAttributeBean bean) {
		return sqlSessionTemplate.insert(NAMESPACE+"updateConfigAttributeBean", bean);
	}

	@Override
	public int deleteConfigAttributeBean(List<ConfigAttributeBean> beans) {
		return sqlSessionTemplate.delete(NAMESPACE+"deleteConfigAttributeBean", beans);
	}
}
