/**
 * 
 */
package com.yd.basic.framework.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.yd.basic.framework.context.WebContext;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.impl.CustomerServiceImpl;
import com.yd.util.DateUtil;
import com.yd.util.JsonUtil;
import com.yd.util.StringUtil;


/**
 * 所有controller的基类
 * @author ice
 *
 */
public abstract class BaseController extends MultiActionController {
	protected Logger log = Logger.getLogger(getClass());
	// 保存文件的目录
	protected static String PATH_FOLDER = "/";
	// 存放临时文件的目录
	protected static String TEMP_FOLDER = "/";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	public static final String PAGESIZE = "pageSize";
	public static final String NOWPAGE = "nowPage";
	private static final String _CURRENT_USER = CustomerServiceImpl._CURRENT_USER;
//	protected Map<String, Object> model = new HashMap<String, Object>();
//	@Override
//	protected ModelAndView handleRequestInternal(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		try {
//			return super.handleRequestInternal(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Throwable cause = e;
//			while (cause.getCause() != null) cause = cause.getCause();
//			response.setStatus(500);
//			response.getWriter().print(cause.getMessage());
//			return null;
//		}
//	}
	/**
	 * 写入JSON
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public void writeJson(HttpServletResponse response,String json) {
		
		try{
			setResponseCharSet(response);
			response.getWriter().append(json);
			response.flushBuffer();
		}catch (Exception e) {
			log.error(e, e);
		}
	}
	
	/**
	 * 写入JSON
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public void writeJson(HttpServletResponse response,Object obj) {
		
		try{
			setResponseCharSet(response);
			if(obj == null){
				log.debug("writeJson obj is null!");
				return;
			}
			
//			if(obj instanceof Collection){
//				JSONArray json = new JSONArray((Collection)obj);
//				response.getWriter().append(json.toString());
//				
//				log.debug(this.getClass()+ " json:"+json.toString());
//			}else if(obj instanceof Integer){
//				response.getWriter().append(String.valueOf(obj));
//				log.debug(this.getClass()+ " json:"+obj);
//			}else{
//				JSONObject json = new JSONObject(obj);
//				response.getWriter().append(json.toString());
//				
//				log.debug(this.getClass()+ " json:"+json.toString());
//			}
			
			String jsonString = JsonUtil.convertObjectToJsonString(obj);
			log.debug(this.getClass()+ " json:"+jsonString);
			response.getWriter().append(jsonString);
			response.getWriter().flush();
			response.getWriter().close();
		}catch (Exception e) {
			log.error(e, e);
		}
//		response.flushBuffer();
	}
	
	protected void setResponseCharSet(HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
	}
	
	protected Integer getPageSizeByRequest(HttpServletRequest request) {
		return getPropertyByRequest(request, PAGESIZE);
	}
	
	protected Integer getNowPageByRequest(HttpServletRequest request) {
		return getPropertyByRequest(request, NOWPAGE);
	}
	
	
	private Integer getPropertyByRequest(HttpServletRequest request,String property) {
		Integer i = null;
		String str = request.getParameter(property);
		if(StringUtil.isNotNull(str)){
			try{
				i = Integer.parseInt(str);
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}
		}
		return i;
	}
	/**
	 * 用于界面跳转
	 * @param url
	 * @return
	 */
	public ModelAndView sendRedirect(String url){
		return new ModelAndView("redirect:"+url);
	}
	
	/**
	 * 获得远程主机域名或IP
	 * @param request
	 * @return
	 */
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    if(ip != null && ip.split(",").length >0){
	    	ip = ip.split(",")[0];
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	/**
	 * 获取请求中的参数
	 * @param request
	 * @return
	 */
	protected Map<String,String> getRequestParamsMap(HttpServletRequest request) {
		Map<String, String[]> requestParams = request.getParameterMap();

		//获取POST过来信息
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				if(StringUtil.isNotNull(values[i]))
				{
					valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
				}
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		return params;
	}
	/**
	 * 从request中获取参数值
	 * @param paramName
	 * @param request
	 * @return
	 */
	public static Integer getIntValueByRequest(String paramName,HttpServletRequest request){
		String value = request.getParameter(paramName);
		if(StringUtil.isNull(value)){
			return 0;
		}else{
			return Integer.parseInt(value);
		}
	}
	

	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @param path
	 * @param name
	 * @param goodsid
	 * @throws Exception 
	 * @param type [1、新增；2、修改]
	 */
	protected void upload(HttpServletRequest request,HttpServletResponse response, String path, String name) throws Exception {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(TEMP_FOLDER));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);

			System.out.println("存放目录:" + path);
			System.out.println("文件名:" + name);
			System.out.println("文件大小:" + item.getSize());
			System.out.println("文件上传名称:"+item.getName().substring(item.getName().indexOf(".")+1));
			// 真正写到磁盘上
			item.write(new File(path, name));
			//PrintWriter writer = response.getWriter();
			//writer.close();
		} catch (FileUploadException e) {
			e.printStackTrace();
			log.error(e,e);
			throw new Exception("图片上传失败！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			throw new Exception("图片上传失败！");
		}
	}


	private FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}
	
	
	/**
	 * 动态拼装文件路径
	 * @return
	 */
	protected String getImgPath(String img,String filename){
		StringBuffer buf = new StringBuffer(appendPath("images/upload", img));
		buf.append("/"+filename);
		return buf.toString();
	}
	protected String appendPath(String path,String dir){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		path = path+"/"+sdf.format(new Date())+"/"+dir;
		return path;
	}
	protected String getImgName(){
		int d = DateUtil.java2phpDate(new Date());
		return "1_G_"+d+".jpg";
	}

	/**
	 * 结束response
	 * @param out
	 * @param value
	 */
	protected void endResponse(PrintWriter out,String value){
		if(value != null){
			out.append(value);
			out.flush();
			out.close();
		}
	}
	
	protected CustomerBean getCurrentLoginUser() {

		return (CustomerBean) WebContext.getHttpSession().getAttribute(_CURRENT_USER);
	}
	
}
