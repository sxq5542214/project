/**
 * 
 */
package com.yd.business.user.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.interceptor.BaseInterceptor;
import com.yd.business.log.service.ILogService;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Component
public class UserInterceptor extends BaseInterceptor {
	
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private ILogService logService;
	@Resource
	private IWechatService wechatService;

	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#getPath()
	 */
	@Override
	protected String getPath() {
		return "/";
	}
	/* (non-Javadoc)
	 * @see com.yd.basic.framework.interceptor.BaseInterceptor#preControllerHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean preControllerHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		Map<String, String> requestMap = getRequestParamsMap(request);
//		先判断session里有没有
//		HttpSession session = WebContext.getHttpSession();
//		String openid = (String) session.getAttribute(WebContext.SESSION_ATTRIBUTE_USER_OPENID);
		
		String cookieOpenid = HttpUtil.getParamByCookie(request, "openid");
		String openid = cookieOpenid;
		if(StringUtil.isNull(openid)){
			openid = requestMap.get("openid");
		}
		String userid = requestMap.get("userId");
		
		if(StringUtil.isNull(openid) && StringUtil.isNotNull(userid)){
			
			UserWechatBean user = userWechatService.findUserWechatById(Integer.parseInt(userid));
			openid = user.getOpenid();
		}

		String code = requestMap.get("code");
		if(StringUtil.isNull(openid) && StringUtil.isNotNull(code) && request.getServletPath().startsWith("/wechat/")){
			String serverName = request.getServerName();
			WechatOriginalInfoBean originalInfo = wechatService.findWechatOriginalInfoByServer(serverName);
			
			openid = wechatService.getOpenId(code,originalInfo.getOriginalid());
		}

		System.out.println("UserInterceptor.preControllerHandle openid:"+openid);
		if(StringUtil.isNotNull(openid)){
			requestMap.put("openid", openid);
			
			//记录用户操作日志
			logService.createUserOperationLog(openid, request.getServletPath(), request.getQueryString(), requestMap);
			
			//以后这里需要改造从表里读到缓存中
			String activity_config_code = requestMap.get("activity_config_code");
			String action_value = null;
			if(StringUtil.isNotNull(activity_config_code)){
				action_value = activity_config_code;
			}
			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(openid, request.getServletPath(), action_value, requestMap);
			
			//如果cookie中没有openid 就加到cookie中去
			if(StringUtil.isNull(cookieOpenid)){
				addOpenidToCookie(response, openid);
			}
		}
		return true;
	}
	
	/**
	 * 将openid加入到cookie中
	 * @param response
	 * @param openid
	 */
	private void addOpenidToCookie(HttpServletResponse response,String openid){
		Cookie cookie = new Cookie("openid", openid);
		cookie.setPath("/");
		response.addCookie(cookie);
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
		// TODO Auto-generated method stub

	}


}
