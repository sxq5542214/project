/**
 * 
 */
package com.yd.basic.framework.context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yd.basic.framework.filter.BaseFilter;

/**
 * 需要配置到web.xml
 *
 */
public class WebContext extends BaseFilter {
	private static ServletContext servletContext;
	private static ThreadLocal<ServletRequest> requests = new ThreadLocal<ServletRequest>();
	private static Map<String,HttpSession> sessions = new HashMap<String, HttpSession>();
	//存放进行中的定单号，线程同步的，避免同一时刻多次重复定购
	private static ConcurrentHashMap<String,Object> runningCacheMap = new ConcurrentHashMap<String, Object>();

	public static final String SESSION_ATTRIBUTE_CURRENT_OPERATOR = "current_operator";
//	public static final String SESSION_ATTRIBUTE_CURRENT_SUPPLIER = "current_supplier";
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		requests.set(request);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		super.init(fc);
		setServletContext(fc.getServletContext());
	}

	public static void setServletContext(ServletContext servletContext) {
		WebContext.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	public static ServletRequest getServletRequest() {
		return requests.get();
	}
	
	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getServletRequest();
	}
	
	public static HttpSession getHttpSession(boolean force) {
		HttpServletRequest r = getHttpServletRequest();
		return r.getSession(force);
	}
	
	public static HttpSession getHttpSession() {
		return getHttpSession(true);
	}
	
	public static HttpSession getHttpSessionById(String sessionId) {
		return sessions.get(sessionId);
	}
	
	public static void setHttpSessionById(HttpSession session) {
		sessions.put(session.getId(),session);
	}

	public static ConcurrentHashMap<String, Object> getRunningCacheMap() {
		return runningCacheMap;
	}
	public static void setObejctToSession(String name,Object obj){
		getHttpSession().setAttribute(name, obj);
	}
	public static Object getObjectBySession(String name){
		return getHttpSession().getAttribute(name);
	}
}
