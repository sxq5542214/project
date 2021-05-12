/**
 * 
 */
package com.yd.business.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.dao.IDictionaryDAO;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseService implements IDictionaryService {
	
	@Autowired
	private IDictionaryDAO dictionaryDAO;
	@Resource
	private IConfigCruxService configCruxService;
	
	public static final Map<String,Map<String,List<DictionaryBean>>> dictionaryCache = new HashMap<String, Map<String,List<DictionaryBean>>>();
	public static final Map<String,Map<String,Map<String,String>>> dictionaryCacheMap = new HashMap<String, Map<String,Map<String,String>>>();
	@Override
	public Map<String, Map<String,Map<String,String>>> getDictionarycache() {
		return dictionaryCacheMap; 
	}
	
	@PostConstruct
	@Override
	public void initDictData(){
		try {
			dictionaryCache.clear();
			dictionaryCacheMap.clear();
			List<DictionaryBean> list = dictionaryDAO.getAllDictionaryValue();
			
			for(DictionaryBean bean : list){
				String beanName = bean.getBeanname();
				Map<String, List<DictionaryBean>> map = dictionaryCache.get(beanName);
				if(map == null){
					map = new HashMap<String, List<DictionaryBean>>();
					dictionaryCache.put(beanName, map);
				}
				
				List<DictionaryBean> attrList = map.get(bean.getAttribute());
				if(attrList == null){
					attrList = new ArrayList<DictionaryBean>();
					map.put(bean.getAttribute(), attrList);
				}
				attrList.add(bean);
			}
			
			
			
			for(String key : dictionaryCache.keySet()) {
				Map<String, List<DictionaryBean>> old1 = dictionaryCache.get(key);
				Map<String, Map<String, String>> map1 = dictionaryCacheMap.get(key) == null? new HashMap<String, Map<String,String>>():dictionaryCacheMap.get(key);
				
				for(String key1 : old1.keySet()) {
					list = old1.get(key1);
					Map<String, String> map2 = map1.get(key1) == null? new HashMap<String, String>():map1.get(key1) ;
					for(DictionaryBean bean : list) {
						map2.put(bean.getValue(), bean.getDescription());
					}
					map1.put(key1, map2);
				}
				
				dictionaryCacheMap.put(key, map1);
			}
			
			
			
			
			
		} catch (Exception e) {
			log.error("init dictValue error!");
			e.printStackTrace();
			log.error(e,e);
		}
		System.out.println(" init  DictData successed!");
	}
	
	public List<DictionaryBean> getZidianByTableAndAttribute(String table,
			String attribute) throws Exception
	{
		return dictionaryDAO.getZidianByTableAndAttribute(table, attribute);
	}

	public List<String> getAllTablenameByZidian() throws Exception
	{
		return dictionaryDAO.getAllTablenameByZidian();
	}
	
	public List<String> getAllAttributeByTablename(String tablename) throws Exception
	{
		return dictionaryDAO.getAllAttributeByTablename(tablename);
	}

	public List<DictionaryBean> getValueByTablenameAndAttribute(String tablename,
			String attribute) throws Exception
	{
		return dictionaryDAO.getValueByTablenameAndAttribute(tablename, attribute);
	}

	public String getValueByDictionaryCache(String tablename, String attribute,
			String key) throws Exception
	{
		if(key == null){
			return "";
		}
		
		List<DictionaryBean> attributeList = getZidianBycache(tablename, attribute);
		if(attributeList != null){
			for(DictionaryBean b : attributeList)
			{
				if(b.getValue().equals(key))
				{
					return b.getDescription();
				}
			}
		}
		log.error("getValueByDictionaryCache error, nosearched ["+tablename+"."+attribute+"."+key+"]");
		return "";
	}
	
	public List<DictionaryBean> getZidianBycache(String tablename,String attribute,boolean reload) throws Exception
	{
		Map<String, List<DictionaryBean>> tableMap = dictionaryCache.get(tablename);
		if(tableMap == null)
		{
			tableMap = new HashMap<String, List<DictionaryBean>>();
			dictionaryCache.put(tablename, tableMap);
		}
		
		List<DictionaryBean> attributeList = tableMap.get(attribute);
		if(attributeList == null && reload)
		{
			log.info(this.getClass() +" no find ("+tablename+"."+attribute+") in dictionary cache ,reload.......");
			attributeList = getZidianByTableAndAttribute(tablename, attribute);
			tableMap.put(attribute, attributeList);
		}
		return attributeList;
	}
	
	public List<DictionaryBean> getZidianBycache(String tablename,String attribute) throws Exception{
		return getZidianBycache(tablename, attribute,true);
	} 
	
	@Override
	public void saveDictionary(){
		DictionaryBean bean = new DictionaryBean();
		bean.setBeanname("test");
		bean.setAttribute("test");
		
		dictionaryDAO.insertDictionary(bean );
		System.out.println(bean.getId());
//		throw new RuntimeException("test ex");
	}

	
	
	/**
	 * 分页查询
	 */
	@Override
	public PageinationData queryDictionaryPage(DictionaryBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<DictionaryBean> showList = dictionaryDAO.queryDictionaryPage(bean);
		    int count = dictionaryDAO.dictionaryCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;
		
	}

	/**
	 * 删除dictionary表信息
	 */
	@Override
	public void deteleDictionary(DictionaryBean bean) {
		dictionaryDAO.deleteDictionary(bean);
	}
	
	/**
	 * 编辑和插入dictionary表信息
	 */
	@Override
	public void editDictionary(DictionaryBean bean) {
		try{
			if(StringUtil.isNull(bean.getId())){
			    int count = dictionaryDAO.dictionaryCount(bean);
			    if(count > DictionaryBean.NUMBER_ZEOR){
					bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_DATA_REPEAT)) ;
			    }else{
					dictionaryDAO.insertDictionary(bean);
			    }
			}else{
				dictionaryDAO.updateDictionary(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
	}
			

	@Override
	public Map<String, List<DictionaryBean>> getTableAttributuByDictionaryCache(String tablename)throws Exception {
		return dictionaryCache.get(tablename);
	}

}
