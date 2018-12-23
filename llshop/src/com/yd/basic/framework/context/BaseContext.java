/**
 * 
 */
package com.yd.basic.framework.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yd.business.alipay.bean.AlipayConfig;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatService;

/**
 * 程序上下文信息，spring上下文
 * @author ice
 *
 */
public class BaseContext {
	private static String SYSCONFIG = "sysconfig.properties";
	private static WebApplicationContext context;
	private static String serverUrl;
	private static String default_share_url;
	private static String default_share_title;
	private static List<WechatOriginalInfoBean> infoList;
	private static Logger log = Logger.getLogger(BaseContext.class);
	private static Properties pops;
	
	public static String POPS_SERVER_NAME = "server_name";
	
	
	public static void initContext(ServletContext servletContext){
		 context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		 
		 AlipayConfig.initValue();
		 try {
			pops = PropertiesLoaderUtils.loadAllProperties(SYSCONFIG);
			refreshParam();
		} catch (IOException e) {
			log.error(e, e);
		}
	}
	
	/**
	 * 获取在spring中配置的该类型的实例。
	 * 如有多个符合，只返回一个。
	 * @param type
	 * @return
	 */
	public static Object getBeanOfType(Class type) {
		Object[] beans = getBeansOfType(type);
		return beans.length == 0 ? null : beans[0];
	}
	
	/**
	 * 获取在spring中配置的该类型的实例。
	 * @param type
	 * @return
	 */
	public static Object[] getBeansOfType(Class type) {
		Map beans = getSpringContext().getBeansOfType(type);
		List ret = new ArrayList();
		for (Iterator itr = beans.keySet().iterator(); itr.hasNext();) {
			String beanName = (String) itr.next();
			ret.add(getBean(beanName));
		}
		return ret.toArray();
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 获取spring上下文。
	 * @return
	 */
	public static WebApplicationContext getSpringContext() {
		return context;
	}

	public static String getMchName(String originalid) {
		
		for(WechatOriginalInfoBean info : infoList){
			if(info.getOriginalid().equals(originalid)){
				return info.getMch_name();
			}
		}
		String default_mch_name = ((IConfigAttributeService)getBean("configAttributeService")).getValueByCode(AttributeConstant.CODE_DEFAULT_MCH_NAME);

		return default_mch_name;
	}
	
	public static WechatOriginalInfoBean getWechatOriginalInfo(String originalid) {
		
		for(WechatOriginalInfoBean info : infoList){
			if(info.getOriginalid().equals(originalid)){
				return info;
			}
		}
		
		return null;
	}
	public static Properties getPops() {
		return pops;
	}
	
	public static String getServerUrl() {
		return serverUrl;
	}

	public static String getPopsValueByName(String name){
		return getPops().getProperty(name);
	}

	public static String getDefault_share_url() {
		if(default_share_url == null){
			default_share_url = ((IConfigAttributeService)getBean("configAttributeService")).getValueByCode(AttributeConstant.CODE_WECHAT_DEFAULT_SHARE_URL);
		}
		return default_share_url;
	}

	public static String getDefault_share_title() {
		if(default_share_title == null){
			default_share_title = ((IConfigAttributeService)getBean("configAttributeService")).getValueByCode(AttributeConstant.CODE_WECHAT_DEFAULT_SHARE_TITLE);
		}
		return default_share_title;
	}
	
	public static void refreshParam(){
		serverUrl = ((IConfigAttributeService)getBean("configAttributeService")).getValueByCode(AttributeConstant.CODE_SERVER_URL);
		default_share_title = ((IConfigAttributeService)getBean("configAttributeService")).getValueByCode(AttributeConstant.CODE_WECHAT_DEFAULT_SHARE_TITLE);
		default_share_url = ((IConfigAttributeService)getBean("configAttributeService")).getValueByCode(AttributeConstant.CODE_WECHAT_DEFAULT_SHARE_URL);
		infoList = ((IWechatService)getBean("wechatService")).queryWechatOriginalInfo(null);
	}
}
