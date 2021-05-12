/**
 * 
 */
package com.yd.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author ice
 *
 */
public class JsonUtil {

//	  public static List<Map<String, Object>> parseJSON2List(String jsonStr){
//	    JSONArray jsonArr = JSONArray.fromObject(jsonStr);
//	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//	    Iterator<JSONObject> it = jsonArr.iterator();
//	    while(it.hasNext()){
//	      JSONObject json2 = it.next();
//	      list.add(parseJSON2Map(json2.toString()));
//	    }
//	    return list;
//	  }
	  
	   
	public static Map<String, Object> parseJSON2Map(String jsonStr){
	    Map<String, Object> map = new HashMap<String, Object>();
	    //最外层解析
	    JSONObject json = new JSONObject(jsonStr);
	    for(Object k : json.keySet()){
	      Object v = json.get(k.toString()); 
	      //如果内层还是数组的话，继续解析
	      if(v instanceof JSONArray){
	        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	        JSONArray array = (JSONArray)v;
//	        Iterator<JSONObject> it = 
	        for(int i = 0 ; i < array.length(); i++){
	          JSONObject json2 = array.getJSONObject(i);
	          list.add(parseJSON2Map(json2.toString()));
	        }
	        map.put(k.toString(), list);
	      } else {
	        map.put(k.toString(), v);
	      }
	    }
	    return map;
	  }
	
	public static Map<String, String> parseJSONToMap(String jsonStr){
	    Map<String, String> map = new HashMap<String, String>();
	    //最外层解析
	    JSONObject json = new JSONObject(jsonStr);
	    for(Object k : json.keySet()){
	      Object v = json.get(k.toString()); 
	      //如果内层还是数组的话，继续解析
	      if(v instanceof JSONArray){
	        JSONArray array = (JSONArray)v;
//	        Iterator<JSONObject> it = 
	        for(int i = 0 ; i < array.length(); i++){
	          JSONObject json2 = array.getJSONObject(i);
		      map.putAll(parseJSONToMap(json2.toString()));
	        }
	      } else {
	        map.put(k.toString(), v.toString());
	      }
	    }
	    return map;
	  }
	
	/**
	 * 入参param是个json格式的字符串
	 * @param param
	 * @param key
	 * @return
	 */
	public static String getParamValueByKey(JSONObject jso,String key){
		if(jso != null){
			Object value = jso.opt(key);
			if(value != null){
				return value.toString();
			}
		}
		
		return key;
	}
	
	public static String convertObjectToJsonString(Object obj){
		if(obj instanceof Collection){
			JSONArray json = new JSONArray((Collection)obj);
			return json.toString();
		}else if(obj instanceof Integer){
			return String.valueOf(obj);
		}else if(obj instanceof Long){
			return String.valueOf(obj);
		}else if(obj instanceof Map){
			JSONObject json = new JSONObject((Map)obj);
			return json.toString();
		}else{
			JSONObject json = new JSONObject(obj);
			return json.toString();
		}
	}
	  
	  
	  public static void main(String[] args) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nick_name","123321");
		  JSONObject jso = new JSONObject(map);
		  System.out.println(jso);
		  System.out.println(jso.toString());
		  System.out.println(convertObjectToJsonString(map));
		  
		  
	}
}
