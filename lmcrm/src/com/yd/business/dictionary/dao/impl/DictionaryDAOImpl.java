package com.yd.business.dictionary.dao.impl;

/**
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.dao.IDictionaryDAO;


/**
 * @author ice_river
 *
 */
@Repository("dictionaryDAO")
public class DictionaryDAOImpl extends BaseDao implements IDictionaryDAO
{
	private static final String NAMESPACE = "dictionary.";
	
	public List<DictionaryBean> getZidianByTableAndAttribute(String table,String attribute) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("table", table);
		map.put("attribute", attribute);
		return sqlSessionTemplate.selectList(NAMESPACE+"getZidianByTableAndAttribute",map);
	}

	public List<String> getAllTablenameByZidian() throws Exception
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"getAllTablenameByZidian");
	}

	public List<String> getAllAttributeByTablename(String tablename)
			throws Exception
	{
		return sqlSessionTemplate.selectList(NAMESPACE+"getAllAttributeByTablename",tablename);
	}
	
	public List<DictionaryBean> getValueByTablenameAndAttribute(String tablename,String attribute) throws Exception
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("tablename", tablename);
		map.put("attribute", attribute);
		return sqlSessionTemplate.selectList(NAMESPACE+"getValueByTablenameAndAttribute",map);
	}

	@Override
	public List<DictionaryBean> getAllDictionaryValue() {
		return sqlSessionTemplate.selectList(NAMESPACE+"getAllDictionaryValue");
	}
	
	@Override
	public void insertDictionary(DictionaryBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"insertDictionary", bean);
	}

	@Override
	public List<DictionaryBean> queryDictionaryPage(DictionaryBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryDictionaryPage", bean,rowBound(bean));
	}

	@Override
	public int dictionaryCount(DictionaryBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"dictionaryCount",bean);
	}

	@Override
	public void deleteDictionary(DictionaryBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteDictionary", bean);
	}

	@Override
	public void updateDictionary(DictionaryBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateDictionary",bean);
	}
	
}
