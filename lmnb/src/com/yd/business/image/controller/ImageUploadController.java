/**
 * 
 */
package com.yd.business.image.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.image.bean.UploadLogBean;
import com.yd.business.image.service.IImageUploadService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.util.FileUploadUtil;
import com.yd.util.StringUtil;

/**
 * 图片上传的控制类
 * @author ice
 *
 */
@Controller
public class ImageUploadController extends BaseController {
	
	@Resource
	private IImageUploadService imageUploadService;
	@Resource
	private IConfigAttributeService configAttributeService;
	
	
	@RequestMapping("**/image/imageUploadFromHttp.do")
	public ModelAndView imageUploadFromHttp(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String remote_ip = getRemoteHost(request);
			
			//先看Request请求里有没有参数，如果有，就先取，没有再去找json
			String httpFilePath = request.getParameter("httpFilePath");
			Integer thumbWidth = null;
			Integer thumbHeight = null;
			if(StringUtil.isNull(httpFilePath)){
				String jsonStr = request.getParameter("httpFileJson");
				JSONObject jso = new JSONObject(jsonStr);
				httpFilePath = jso.optString("httpFilePath");
				thumbWidth = (Integer) jso.opt("thumbWidth");
				thumbHeight = (Integer) jso.opt("thumbHeight");
			}
			
			if(StringUtil.isNull(httpFilePath)){
				writeJson(response, "request param : httpFilePath is null! check request param please!");
				return null;
			}
			
			UploadLogBean uploadLog = imageUploadService.saveFileFromHttp(request, httpFilePath,thumbWidth,thumbHeight, remote_ip);

			writeJson(response, uploadLog);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}
	
	

	@RequestMapping("**/image/imageUploadFromPost.do")
	public ModelAndView imageUploadFromPost(MultipartHttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String remote_ip = getRemoteHost(request);
			MultipartFile postFile = request.getFile("postFile");

			
			if(StringUtil.isNull(postFile)){
				writeJson(response, "request param : postFile is null! check request param please!");
				return null;
			}
			
			UploadLogBean uploadLog = imageUploadService.saveFileFromPostUpload(request, postFile, remote_ip);

			writeJson(response, uploadLog);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}
	
	
	
	
}
