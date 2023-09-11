package com.yd.basic.framework.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.util.JsonUtil;
import com.yd.util.StringUtil;


public class TokenFilter extends BaseFilter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		request.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+req.getContextPath()+"/";

		
		
		if( uri.indexOf("/admin/") >0 ){
			
			String token = req.getHeader("X-Token");  // token里存的sessionid
//			Enumeration<String> s = req.getHeaderNames();
			if(StringUtil.isNotNull(token)) {
				HttpSession oldSession = WebContext.getHttpSessionById(token);
				if(oldSession != null) {
					Object user = oldSession.getAttribute(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
					WebContext.setObejctToSession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR, user);
					if(user == null){
						responseNoLogin(request, response, chain);
//						resp.sendRedirect(basePath+"index.html");
					}
				}else {
					responseNoLogin(request, response, chain);
//					resp.sendRedirect(basePath+"index.html");
				}
				
				
			}
			
//			Object user = req.getSession().getAttribute(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
//			if(user == null){
//				resp.sendRedirect(basePath+"page/login.jsp");
//			}
		}else if( uri.indexOf("/frame/") >0 ){
			Object user = req.getSession().getAttribute(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			if(user == null){
				resp.sendRedirect(basePath+"page/login.jsp");
			}
		}
//			else if( uri.indexOf("/wx/") >0 ){
//			Object openid = req.getSession().getAttribute(WebContext.SESSION_ATTRIBUTE_USER_OPENID);
//			if(openid == null){
//				resp.sendRedirect(BaseContext.getServerUrl()+"wechat/user/toDistributeControll.do?conName=wechat.user.toReopenTips");
//			}
////		}
//		if( uri.indexOf("/supplier/shop/") >0 
//				|| uri.indexOf("/supplierProduct/") >0 
//				|| uri.indexOf("/supplier/package/") >0 
//				|| uri.indexOf("/supplier/store/") >0  ){
//			Object supplier = req.getSession().getAttribute(WebContext.SESSION_ATTRIBUTE_CURRENT_SUPPLIER);
//			//非商户商城界面
//			if(supplier == null && uri.indexOf("toSupplierShopPage") < 0){
//				resp.sendRedirect(BaseContext.getServerUrl()+"wechat/user/toDistributeControll.do?conName=wx.supplier.shop.toMySupplierShopManagerFramePage");
//			}
//		}
		
//		String openid = req.getParameter("openid");
//		if(StringUtil.isNotNull(req.getParameter("openid")) && session.getAttribute("openid") != null) {
//			req.getSession().setAttribute("openid", openid);
//		}
		
//		HttpServletResponse resp = (HttpServletResponse) response;
//		ICustomerService service = (ICustomerService) BaseContext.getBeanOfType(ICustomerService.class);
// 		if(!inWhiteList(req)&&service.getUser()==null) {//用于检测当前用户是否已经登录，是否是非法进入用户
//			resp.sendRedirect(BaseContext.getServerUrl()+"login.html");
//		}else{
			cache(req,request,response,chain);
//		}
	}
	
	private void responseNoLogin(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException {
		IOTWebDataBean res = new IOTWebDataBean();
		res.setCode(IOTWebDataBean.CODE_IOTWEB_LOGIN_EXPIRED);
		res.setMessage("当前用户未登录或登录已过期，请重新登录");

		String jsonString = JsonUtil.convertObjectToJsonString(res);
		response.setCharacterEncoding("UTF-8");
//		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.getWriter().append(jsonString);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public void cache(HttpServletRequest req,ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException{
		if(!response.isCommitted()){
			jsNoCache(req.getRequestURI(), response);
			chain.doFilter(request, response);
		}
	}
}
