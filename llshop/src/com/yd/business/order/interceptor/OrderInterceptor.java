/**
 * 
 */
package com.yd.business.order.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.interceptor.BaseInterceptor;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Component
public class OrderInterceptor extends BaseInterceptor {
	
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#getPath()
	 */
	@Override
	protected String getPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#preControllerHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean preControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		return true;
	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#postControllerHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	protected void postControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#afterControllerCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected void afterControllerCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		

	}

}
