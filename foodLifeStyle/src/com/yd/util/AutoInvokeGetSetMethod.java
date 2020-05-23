/**
 * 
 */
package com.yd.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;


/**
 * @author ice_river
 *
 */
public class AutoInvokeGetSetMethod
{
	private static Logger log = Logger.getLogger(AutoInvokeGetSetMethod.class);
	private static SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 返回set后的类,OBJECT继承下来的方法被踢除
	 * @param get 要进行get的对象
	 * @param set 要进行set的对象
	 * @return
	 * @throws Exception 
	 */
	public static Object autoInvoke(Object get,Object set) throws Exception
	{
		if(get == null || set == null)
		{
			throw new Exception("要进行Invoke的get或set对象为空!");
		}
		//所有的字段,所有的get方法
		Method[] allGetMethod = get.getClass().getMethods();
		Method[] allSetMethod = set.getClass().getMethods();
		Method[] objectMethod = Object.class.getMethods();//需要去除Object的方法
		
		List<String> FieldNameList = new ArrayList<String>();
		
		for(Method setMethod : allSetMethod)
		{
			//取出时第一个字母已为大写
			String fieldName = setMethod.getName().substring(3, setMethod.getName().length());
			if(!FieldNameList.contains(fieldName))
			{
				FieldNameList.add(fieldName);
			}
		}
		
		//循环所有set中的字段
		for(String fieldName : FieldNameList)
		{
			String getMethodName = "get" + fieldName;
			String setMethodName = "set" + fieldName;
			Object getResutl = null;
			Class getResultClass = null;
			//循环取对应的get方法,并执行
			for(Method getMethod : allGetMethod)
			{
				if(getMethod.getName().equals(getMethodName))
				{
					getResultClass = getMethod.getReturnType();
					boolean flag = true;
					for(Method om : objectMethod)
					{
						if(getMethod.getName().equals(om.getName()))
						{
							flag = false;
						}
					}
					if(flag && getMethod.getParameterTypes().length == 0)
					{
						
						getResutl = getMethod.invoke(get, new Object[]{});
						break;
					}
				}
			}
			if(getResutl == null)
			{
//				System.out.println("类      " + get.getClass() + "  没有对应的方法: " + getMethodName);
//				LogUtil.info("类      " + get.getClass() + "  没有对应的方法: " + getMethodName);
			}else
			{
				if(getResutl instanceof String)
				{
					trim(getResutl);
				}
				try{
					//得到set方法,执行
					Method setMethod = set.getClass().getMethod(setMethodName, getResultClass);
					setMethod.invoke(set, getResutl);
				}catch(Exception e)
				{e.printStackTrace();
					if(e.toString().indexOf("java.lang.NoSuchMethodException") < 0)
					{
						e.printStackTrace();
						log.info("autoInvoke error:" + e);
						throw e;
					}else
					{//使用父类
						Method setMethod = set.getClass().getMethod(setMethodName, getResutl.getClass().getSuperclass());
						setMethod.invoke(set, getResutl);
					}
				}
			}
		}
		
		
		return set;
	}
	
	
	/**
	 * 将MAP转换为对象
	 * @param get
	 * @param set
	 * @return
	 * @throws Exception
	 */
	public static Object autoInvoke(Map get,Object set) throws Exception
	{
		if(get == null || set == null)
		{
			throw new Exception("要进行Invoke的get或set对象为空!");
		}
		//所有的字段,所有的set方法
//		Method[] allGetMethod = get.getClass().getMethods();
		Method[] allSetMethod = set.getClass().getMethods();
		List<String> FieldNameList = new ArrayList<String>();
		
		for(Object key : get.keySet())
		{
			//取出时第一个字母已为大写
			String methodName = "set" + key.toString().substring(0,1).toUpperCase() + key.toString().substring(1);
			Object value = get.get(key);
			if(value == null || StringUtil.isNull(value)){
				continue;
			}
			
			for(Method m : allSetMethod){
				if(methodName.equals(m.getName())){
//					System.out.println(m.getParameterTypes()[0]+","+get.get(key).getClass());
					Class<?> inType = m.getParameterTypes()[0];
					Class<?> realType = get.get(key).getClass();
					Object realValue = value;
					//转换入参值类型为 set方法对应的类型   比如 set(Integer id)   那么map.get里很可能是string的值，需要转换为Integer类型
					if( inType != realType){
						if(realType == String[].class){
							if(((String[])value).length == 0) 
								continue;
							else{
								StringBuffer str = new StringBuffer();
								for(String s : (String[])value){
									if(StringUtil.isNull(s)){
										continue;
									}
									str.append(s+",");
								}
								if(str.length() == 0)
								{
									continue;
								}
								value = str.substring(0, str.length() - 1);
								realType = value.getClass();
								if(StringUtil.isNull(value)){
									continue;
								}
							}
						}
						
						if(realType == String.class){
//	System.out.println("autoGetSet key:"+key);
							if(inType == Integer.class || inType == int.class){
								BigDecimal bd = new BigDecimal((String)value);
								realValue = bd.intValue();
							}
							if(inType == Long.class || inType == long.class){
								BigDecimal bd = new BigDecimal((String)value);
								realValue = bd.longValue();
							}
							if(inType == String.class){
								realValue = (String)value;
							}
							if(inType == BigDecimal.class){
								realValue = new BigDecimal((String)value);
							}
							if(inType == Date.class){
								realValue = df.parse((String)value);
							}
						}
					}
					m.invoke(set, realValue);
				}
			}
		}
		return set;
	}
	
	/**
	 * 将MAP转换为对象
	 * @param get
	 * @param set
	 * @return
	 * @throws Exception
	 */
	public static Object autoInvoke(JSONObject get,Object set) throws Exception
	{
		if(get == null || set == null)
		{
			throw new Exception("要进行Invoke的get或set对象为空!");
		}
		//所有的字段,所有的set方法
//		Method[] allGetMethod = get.getClass().getMethods();
		Method[] allSetMethod = set.getClass().getMethods();
		List<String> FieldNameList = new ArrayList<String>();
		Iterator iterator = get.keys();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			Object value = get.get(key);
			//取出时第一个字母已为大写
			String methodName = "set" + key.toString().substring(0,1).toUpperCase() + key.toString().substring(1);
			if(value == null || StringUtil.isNull(value)){
				continue;
			}
			for(Method m : allSetMethod){
				if(methodName.equals(m.getName())){
//					System.out.println(m.getParameterTypes()[0]+","+get.get(key).getClass());
					Class<?> inType = m.getParameterTypes()[0];
					Class<?> realType = get.get(key).getClass();
					Object realValue = value;
					//转换入参值类型为 set方法对应的类型   比如 set(Integer id)   那么map.get里很可能是string的值，需要转换为Integer类型
					if( inType != realType){
						if(realType == String[].class){
							if(((String[])value).length == 0) 
								continue;
							else{
								StringBuffer str = new StringBuffer();
								for(String s : (String[])value){
									if(StringUtil.isNull(s)){
										continue;
									}
									str.append(s+",");
								}
								if(str.length() == 0)
								{
									continue;
								}
								value = str.substring(0, str.length() - 1);
								realType = value.getClass();
								if(StringUtil.isNull(value)){
									continue;
								}
							}
						}
						
						if(realType == String.class){
							if(inType == Integer.class || inType == int.class){
								realValue = Integer.valueOf((String)value);
							}
							if(inType == Long.class || inType == long.class){
								realValue = Long.valueOf((String)value);
							}
							if(inType == String.class){
								realValue = (String)value;
							}
							if(inType == BigDecimal.class){
								realValue = new BigDecimal((String)value);
							}
							if(inType == Date.class){
								realValue = df.parse((String)value);
							}
						}
					}
					m.invoke(set, realValue);
				}
			}
		}
		return set;
	}
	
	
	private static Object trim(Object str)
	{
		if(str == null || str.equals("null"))
		{
			str = "";
		}else{
			str = str.toString().trim();
		}
		return str;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		String s = " ";
//		System.out.println(s instanceof String);
//		ParameterSet in = new ParameterSet();
//		in.setValue("dengluming", "11");
//		in.setValue("mima", "22");
//		GuanliyuanBean b = new GuanliyuanBean();
//		try
//		{
//			autoInvoke(in, b);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		System.out.println(b.getDengluming());
//		System.out.println(b.getMima());
		
	}

}
