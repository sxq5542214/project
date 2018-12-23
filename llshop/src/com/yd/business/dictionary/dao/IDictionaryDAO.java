package com.yd.business.dictionary.dao;

import java.util.List;

import com.yd.business.dictionary.bean.DictionaryBean;

public interface IDictionaryDAO {
	public List<DictionaryBean> getZidianByTableAndAttribute(String table,String attribute) throws Exception;

	public List<String> getAllTablenameByZidian() throws Exception;
	
	public List<String> getAllAttributeByTablename(String tablename) throws Exception;
	
	public List<DictionaryBean> getValueByTablenameAndAttribute(String tablename,String attribute) throws Exception;

	public List<DictionaryBean> getAllDictionaryValue();

	void insertDictionary(DictionaryBean bean);
	
	
	/**
	 * 分页查询ll_dictionary表信息
	 */
	public List<DictionaryBean> queryDictionaryPage(DictionaryBean bean);
	
	
	/**
	 * 查询dictionary表总数
	 */
	public int dictionaryCount(DictionaryBean bean);
	
	
	/**
	 * 删除dictionary表信息
	 */
	public void deleteDictionary(DictionaryBean bean);

	/**
	 * 更新dictionary表信息
	 */
	public void updateDictionary(DictionaryBean bean);
}
