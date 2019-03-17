package com.yd.basic.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yd.basic.framework.context.BaseContext;
import com.yd.business.customer.service.impl.CustomerServiceImpl;


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
		request.setCharacterEncoding("utf-8");
		
		if( req.getRequestURI().indexOf("/admin/") >0 ){
			Object user = req.getSession().getAttribute(CustomerServiceImpl._CURRENT_USER);
			if(user == null){
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+req.getContextPath()+"/";
				resp.sendRedirect(basePath+"login.html");
			}
		}else if( req.getRequestURI().indexOf("/app/") >0 ){
			Object user = req.getSession().getAttribute(CustomerServiceImpl._CURRENT_USER);
			if(user == null){
				resp.sendRedirect(BaseContext.getServerUrl()+"page/wechat/login.jsp");
			}
		}
		
		
//		HttpServletResponse resp = (HttpServletResponse) response;
//		ICustomerService service = (ICustomerService) BaseContext.getBeanOfType(ICustomerService.class);
// 		if(!inWhiteList(req)&&service.getUser()==null) {//用于检测当前用户是否已经登录，是否是非法进入用户
//			resp.sendRedirect(BaseContext.getServerUrl()+"login.html");
//		}else{
			cache(req,request,response,chain);
//		}
	}
	
	public void cache(HttpServletRequest req,ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException{
		if(!response.isCommitted()){
			jsNoCache(req.getRequestURI(), response);
			chain.doFilter(request, response);
		}
	}
}
