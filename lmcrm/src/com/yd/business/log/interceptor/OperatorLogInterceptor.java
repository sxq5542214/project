/**
 * 
 */
package com.yd.business.log.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.xdevapi.JsonArray;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.interceptor.BaseInterceptor;
import com.yd.business.log.service.IOperatorLogService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Component
public class OperatorLogInterceptor extends BaseInterceptor {
	@Autowired
	private IOperatorLogService operatorLogService;
	
	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#getPath()
	 */
	@Override
	protected String getPath() {
		// TODO Auto-generated method stub
		return "/admin/";
	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#preControllerHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean preControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//		System.out.println(getClass().getName() + "  preControllerHandle..." + request.getRequestURI());
		return true;
	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#postControllerHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	protected void postControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		
		try {
			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			System.out.println(DateUtil.getNowDateStr() + getClass().getName() + "  postControllerHandle... "+op.getO_name()+"  "+ request.getRequestURI());

			operatorLogService.createOperatoryLog( op  , request);
		}catch (Exception e) {
			log.error(e, e);
		}
		

	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#afterControllerCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected void afterControllerCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
//		System.out.println(getClass().getName() + "  afterControllerCompletion...");

	}

}
