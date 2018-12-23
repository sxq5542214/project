/**
 * 
 */
package com.yd.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

/**
 * Web相关的工具类。
 * @author cj
 */
public class WebUtil {
	/**
	 * 获取url请求中的名称部分。例如/××/init.do -> init。
	 * @param request
	 * @return
	 */
	public static String getMethodName(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		return WebUtils.extractFilenameFromUrlPath(servletPath);
	}
	
	/**
	 * 获取spring配置的bean。
	 * @param name
	 * @param servletContext
	 * @return
	 */
	public static Object getBean(String name, ServletContext servletContext){
		WebApplicationContext beans = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		Object bean = beans.getBean(name);
		return bean;
	}
	
	/**
	 * 将request参数转换成map&lt;String, String&gt;。
	 * @param request
	 * @return
	 */
	public static Map<String, String> requestToMap(HttpServletRequest request) {
		Map<String, String> m = new HashMap<String, String>();
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String k = (String) e.nextElement();
			String value = request.getParameter(k);
			m.put(k, value);
		}
		return m;
	}
	
	/**
	 * 将request参数转换成map&lt;String, String&gt;。
	 * @param request
	 * @return
	 */
	public static Map<String, Object> requestToMapObject(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String k = (String) e.nextElement();
			String value = request.getParameter(k);
			m.put(k, value);
		}
		return m;
	}
	public static Cookie getCookie(String paramString, HttpServletRequest paramHttpServletRequest)
	  {
	    Cookie[] arrayOfCookie1 = paramHttpServletRequest.getCookies();
	    if (arrayOfCookie1 != null)
	      for (Cookie localCookie : arrayOfCookie1)
	        if (localCookie.getName().equals(paramString))
	          return localCookie;
	    return null;
	  }

	  public static String getCookieValue(String paramString, HttpServletRequest paramHttpServletRequest)
	  {
	    Cookie localCookie = getCookie(paramString, paramHttpServletRequest);
	    if (localCookie != null)
	      return localCookie.getValue();
	    return null;
	  }
}
