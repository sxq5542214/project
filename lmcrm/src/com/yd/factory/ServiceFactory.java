/**
 * 
 */
package com.yd.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yd.basic.framework.context.BaseContext;
import com.yd.business.dictionary.service.IDictionaryService;

/**
 * @author ice_river
 *
 */
@Component("serviceFactory")
public class ServiceFactory
{
	
//	private static ServiceFactory factory;
	public static final String SERVICE_DICTIONARY = "dictionaryService";
	public static final String SERVICE_PARTNERSERVICE= "partnerService";
	public static final String SERVICE_ORDERSERVICE = "orderService";
	public static final String SERVICE_ORDERPRODUCTLOGSERVICE = "orderProductLogService";
	public static final String SERVICE_CHANNELSERVICE = "channelService";
	
	
	
	private static Map<String,Object> serviceMap = new HashMap<String, Object>();
	
//	public static ServiceFactory getInstance()
//	{
//		if(factory == null)
//		{
//			factory = (ServiceFactory)BaseContext.getBean("serviceFactory");
//		}
//		return factory;
//	}
//	

	public static IDictionaryService getDictionaryService(){
		return getService(SERVICE_DICTIONARY);
	}
	
	public static <T> T getService(String serviceName){
		Object service = serviceMap.get(serviceName);
		if(service == null){
			service = BaseContext.getBean(serviceName);
			serviceMap.put(serviceName, service);
		}
		return (T) service;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
