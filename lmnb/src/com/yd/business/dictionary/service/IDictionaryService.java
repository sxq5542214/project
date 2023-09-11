package com.yd.business.dictionary.service;

/**
 * 
 */

import java.util.List;
import java.util.Map;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.iotbusiness.mapper.model.LlDictionaryModel;


/**
 * @author ice_river
 *
 */
public interface IDictionaryService
{
	public List<LlDictionaryModel> getZidianByTableAndAttribute(String table,String attribute) throws Exception;

	public List<String> getAllTablenameByZidian() throws Exception;
	
	public List<String> getAllAttributeByTablename(String tablename) throws Exception;
	
	public List<LlDictionaryModel> getValueByTablenameAndAttribute(String tablename,String attribute) throws Exception;

	public List<LlDictionaryModel> getZidianBycache(String tablename,String attribute,boolean reload) throws Exception;
	
	public List<LlDictionaryModel> getZidianBycache(String tablename,String attribute) throws Exception;
	
	public String getValueByDictionaryCache(String tablename ,String attribute,String key) throws Exception;
	
	/**
	 * 在缓存获取类的所有字典值
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<LlDictionaryModel>> getTableAttributuByDictionaryCache(String tablename) throws Exception;

	void saveDictionary();
	
	/**
	 * 初始化数据
	 */
	void initDictData();
	


	/**
	 * 删除dictionary表信息
	 */
	public void deteleDictionary(LlDictionaryModel bean);
	
	/**
	 * 更改dictionary表信息
	 */
	public void editDictionary(LlDictionaryModel bean);

	public Map<String, Map<String, Map<String, String>>> getDictionarycache();

	/**
	 * 分页查询
	 */
	IOTWebDataBean queryDictionaryList(LlDictionaryModel bean);


}
