package com.yd.business.file.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.file.Service.IFileUploadService;

@Controller
public class FileUploadController extends BaseController {

	@Resource
	private IFileUploadService fileUploadService;
	
	@RequestMapping("**/admin/fileupload/uploadImgToImgServer.do")
	public ModelAndView uploadImgToImgServer(MultipartHttpServletRequest request, HttpServletResponse response){
		try {
			String json = fileUploadService.uploadImgToImgServer(request.getFile("postFile"));
			writeJson(response, json);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "FAIL");
		}
		return null;
	}
	
}
