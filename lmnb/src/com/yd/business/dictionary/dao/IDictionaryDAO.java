package com.yd.business.dictionary.dao;

import java.util.List;

import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.iotbusiness.mapper.model.LlDictionaryModel;

public interface IDictionaryDAO {
	public List<LlDictionaryModel> getZidianByTableAndAttribute(String table,String attribute) throws Exception;

	public List<String> getAllTablenameByZidian() throws Exception;
	
	public List<String> getAllAttributeByTablename(String tablename) throws Exception;
	
	public List<LlDictionaryModel> getValueByTablenameAndAttribute(String tablename,String attribute) throws Exception;

	public List<LlDictionaryModel> getAllDictionaryValue();

	void insertDictionary(LlDictionaryModel bean);
	
	
	/**
	 * 分页查询ll_dictionary表信息
	 */
	public List<LlDictionaryModel> queryDictionaryPage(LlDictionaryModel bean);
	
	
	/**
	 * 查询dictionary表总数
	 */
	public int dictionaryCount(LlDictionaryModel bean);
	
	
	/**
	 * 删除dictionary表信息
	 */
	public void deleteDictionary(LlDictionaryModel bean);

	/**
	 * 更新dictionary表信息
	 */
	public void updateDictionary(LlDictionaryModel bean);
}
