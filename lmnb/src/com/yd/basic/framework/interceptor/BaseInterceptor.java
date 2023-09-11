/**
 * 
 */
package com.yd.basic.framework.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.exception.BaseException;
import com.yd.util.StringUtil;

/**
 * 拦截器的基类
 * @author ice
 *
 */
public abstract class BaseInterceptor implements HandlerInterceptor {
	protected static final String split_symbol = "/";
//	private String path;
	protected Logger log = Logger.getLogger(getClass());
//	private static final Map<String,BaseInterceptor> interceptorMap = new HashMap<String, BaseInterceptor>();
	private static final List<BaseInterceptor> interceptorList = new ArrayList<BaseInterceptor>();
	
	public BaseInterceptor() {
		registerIntercetpor(getPath(), this);
	}
	

    /**
     * 这个方法用于注册拦截的路径
     * @return
     */
    protected abstract String getPath() ;
//    protected String getPath(){
//    	path = setPath();
//    	return path;
//    }
	/**
	 * 注册拦截器
	 * @param path
	 * @param interceptor
	 */
	protected void registerIntercetpor(String path,BaseInterceptor interceptor) {
		
		if(StringUtil.isNull(path)){
			return;
		}else{
			//如果path不为空，且list中已经有一样的类，则直接结束方法
			for(BaseInterceptor i :interceptorList){
				if(i.getPath().equals(interceptor.getPath()) && i.getClass().getName().equals(interceptor.getClass().getName())){
					return ;
				}
			}
			
			
		}
		
		if(path.startsWith(split_symbol) && path.endsWith(split_symbol)){
			interceptorList.add(interceptor);
//			interceptorMap.put(path, interceptor);
		}else{
			throw new BaseException(getClass().getName() + " registerIntercetpor error! path must start and end with '/'");
		}
		
	}
	
    /** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	
    	String path = request.getServletPath();
//    	Set<String> registerPathSet = interceptorMap.keySet();
    	boolean flag = true;
    	for(BaseInterceptor register : interceptorList){
    		if(path.startsWith(register.getPath())){
    			BaseInterceptor subIntereceptor = register;
    			
    			try{
    				flag = subIntereceptor.preControllerHandle(request, response, handler);
    			}catch (Exception e) {
					log.error(subIntereceptor.getClass().getName() +":"+e.getMessage(), e);
				}
    		}
    		
    		if(flag == false){
    			break;
    		}
    	}
    	
        return flag;  
    }
    /** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     */  
    protected abstract boolean preControllerHandle(HttpServletRequest request,HttpServletResponse response, Object handler);
    
    
    /** 
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操 
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像， 
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor 
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。 
     */  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
    	
    	String path = request.getServletPath();
    	
    	for(BaseInterceptor subIntereceptor : interceptorList){
    		if(path.startsWith(subIntereceptor.getPath())){
    			
    			subIntereceptor.postControllerHandle(request, response, handler, modelAndView);
    		}
    	}
    	
    }  
    /** 
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操 
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像， 
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor 
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。 
     */  
    protected abstract void postControllerHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView);
    
    /** 
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。 
     */  
    @Override  
    public  void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
    throws Exception {  
        
    	String path = request.getServletPath();
    	
    	for(BaseInterceptor subIntereceptor : interceptorList){
    		if(path.startsWith(subIntereceptor.getPath())){
    			
    			subIntereceptor.afterControllerCompletion(request, response, handler, ex);
    		}
    	}
    	if(ex != null){
    		log.error(ex, ex);
    	}
    }  
    /** 
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。 
     */  
    protected abstract void afterControllerCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)  ;

    
    
    protected Map<String,String> getRequestParamsMap(HttpServletRequest request) {
		Map<String, String[]> requestParams = request.getParameterMap();

		//获取POST过来信息
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		return params;
	}
}
