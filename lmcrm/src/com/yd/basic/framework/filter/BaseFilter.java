/**
 * 
 */
package com.yd.basic.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.yd.basic.framework.context.BaseContext;

/**
 * 基础的过虑类
 * @author ice
 *
 */
public class BaseFilter implements Filter {
//	public String[] defaultWhiteList = new String[] {".gif",".png", ".jpg","login.html","loginAdmin.jsp","error.jsp",".css",".js",".map",".ttf",".woff","article/read.do"};
//	public String[] defaultActionList = new String[] {"/images/","/customer/","/android/","/wechat/","/activity/","/hongbao/","/lottery/","/userRead/","/user/","product"};
	public String characterEncoding = "UTF-8";
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
			ServletException {
		
		arg1.setCharacterEncoding("utf-8");
//		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		arg2.doFilter(arg0, arg1);
	}

	/**
	 * 初始化
	 */
	@Override
	public void init(FilterConfig init) throws ServletException {
		BaseContext.initContext(init.getServletContext());
	}
	
	/**
	 * 不缓存js文件
	 * @param uri
	 * @param arg1
	 */
	public void jsNoCache(String uri,ServletResponse arg1){
		
		if (uri.indexOf(".js") > 0 || uri.indexOf(".css") > 0)
		{
			HttpServletResponse httpResponse = (HttpServletResponse) arg1;
			httpResponse.setHeader("Cache-Control", "no-cache");
			httpResponse.setDateHeader("Expires", 0);
			httpResponse.setHeader("Pragma", "No-cache");
		}
	}
//	/**
//	 * 检验是否在白名单内。
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public boolean inWhiteList(HttpServletRequest request) {
//		for (String white: defaultWhiteList) {
//			if (request.getRequestURI().endsWith(white)) return true;
//		}
//		
//		for(String white: defaultActionList){
//			if (request.getRequestURI().indexOf(white)>=0) return true;
//		}
//		return false;
//	}
}
