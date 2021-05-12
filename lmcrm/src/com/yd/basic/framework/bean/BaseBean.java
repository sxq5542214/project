/**
 * 
 */
package com.yd.basic.framework.bean;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.factory.ServiceFactory;

/**
 * 所有实体BEAN的基类
 * @author ice
 *
 */
public class BaseBean extends PageinationData {
	protected Logger log = Logger.getLogger(getClass());
	protected int queryStatus ;
	protected String queryResult;
	
	public static final int QUERYSTATUS_ERROR = -1;
	
//	protected HashMap<String,Object> dictMap = new HashMap<String, Object>();
	
//	public HashMap<String, Object> getDictMap() {
//		return dictMap;
//	}
//	public void setDictMap(HashMap<String, Object> dictMap) {
//		this.dictMap = dictMap;
//	}1

	public String getDictValueByField(String fieldName,Integer fieldValue){
		try {
			return ServiceFactory.getDictionaryService().getValueByDictionaryCache(this.getClass().getSimpleName(), fieldName, String.valueOf(fieldValue));
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	public String getDictValueByField(String fieldName,String fieldValue){
		try {
			return ServiceFactory.getDictionaryService().getValueByDictionaryCache(this.getClass().getSimpleName(), fieldName, fieldValue);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	public String getDictValueByField(String fieldName){
		
		String getMethodName =   "get" + (fieldName.charAt(0)+"").toUpperCase() + fieldName.substring(1) ;
		try {
			Method method = this.getClass().getMethod(getMethodName );
			Object fieldValue = method.invoke(this);
			
			if(fieldValue instanceof Integer){
				return getDictValueByField( fieldName, (Integer)fieldValue);
			}
			if(fieldValue instanceof String){
				return getDictValueByField( fieldName, (String)fieldValue);
			}
			
			
		} catch (Exception e) {
			log.error(e,e);
		}
		
		return null;
	}

	public int getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(int queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	
//	/**
//	 * 需要在integer字段的get方法中调用   如：super.setDictValueToMap(source_for, "source_for");
//	 * 会自动匹配Bean类的名称去dict缓存中查
//	 * @param value
//	 * @param attribute
//	 */
//	public void setDictValueToMap(Integer value,String attribute){
//		if(value == null)
//			return;
//		try {
//			String desc = ServiceFactory.getDictionaryService().getValueByDictionaryCache(this.getClass().getSimpleName(), attribute, value);
//			dictMap.put(attribute, desc);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e,e);
//		}
//		
//	}
//	public static void main(String[] args) {
//		BaseBean ba = new BaseBean();
//		
//		ba.getDictValueByField("dictMap");
//	}
}
