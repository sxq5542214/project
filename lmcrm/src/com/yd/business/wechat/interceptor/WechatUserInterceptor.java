/**
 * 
 */
package com.yd.business.wechat.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.interceptor.BaseInterceptor;
import com.yd.business.wechat.service.IWechatService;

/**
 * 微信用户交互的拦截器类
 * @author ice
 *
 */
@Component("wechatUserInterceptor")
public class WechatUserInterceptor extends BaseInterceptor {
	
	@Resource
	private IWechatService wechatService;
	
	


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
		
//		Map<String, String> requestMap = getRequestParamsMap(request);
//		HttpSession session = WebContext.getHttpSession();
//		String openid = (String) session.getAttribute(WebContext.SESSION_ATTRIBUTE_USER_OPENID);
//		
//		if(StringUtil.isNull(openid)){
//			openid = requestMap.get("openid");
//		}
//		String code = requestMap.get("code");
//		if(StringUtil.isNull(openid) && StringUtil.isNotNull(code)){
//			openid = wechatService.getOpenId(code);
//		}
//		
//		if(StringUtil.isNotNull(openid)){
//			msgCenterActionService.saveAndHandleUserAction(openid, request.getServletPath(), null, requestMap);
//		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#postControllerHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	protected void postControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		
		
	}

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#afterControllerCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected void afterControllerCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		

	}

}
