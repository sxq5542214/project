/**
 * 
 */
package com.yd.basic.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 这里是所有controller方法的拦截器，通用的才写在这，业务层的，写在各自业务层处
 * @author ice
 *
 */
public class AllControllerInterceptor extends BaseInterceptor {
	
	@Override
	protected String getPath() {
		return "/";
	}

	@Override
	protected boolean preControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
//		System.out.println("AllControllerInterceptor.preControllerHandle");
		return true;
	}

	@Override
	protected void postControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		
	}

	@Override
	protected void afterControllerCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
	}
	
}
