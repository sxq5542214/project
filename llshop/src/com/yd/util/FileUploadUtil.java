package com.yd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yd.basic.framework.controller.BaseController;
@Controller
public class FileUploadUtil extends BaseController{
	
	public static void imgFileUpload(MultipartHttpServletRequest request,HttpServletResponse response,String path,String filePath,String file) throws Exception{
		File f = new File(filePath);
		if(!f.exists()) f.mkdirs();
		MultipartFile mfile = request.getFile(file);
		if(mfile.getSize()>0){
			InputStream is = mfile.getInputStream();
			int byteCount = 0;
			byte[] b = new byte[1024];
			FileOutputStream fos = new FileOutputStream(path);
			while((byteCount=is.read(b))!=-1){
				fos.write(b, 0, byteCount);
			}
			is.close();
			fos.close();
		}
	}
	
	public static void imgFileUpload(MultipartHttpServletRequest request,HttpServletResponse response,String path,String filePath) throws Exception{
		File file = new File(filePath);
		if(!file.exists()) file.mkdirs();
		MultipartFile mfile = request.getFile("file");
		if(mfile.getSize()>0){
			InputStream is = mfile.getInputStream();
			int byteCount = 0;
			byte[] b = new byte[1024];
			FileOutputStream fos = new FileOutputStream(path);
			while((byteCount=is.read(b))!=-1){
				fos.write(b, 0, byteCount);
			}
			is.close();
			fos.close();
		}
	}
	
	public static boolean isExists(MultipartHttpServletRequest request) throws Exception{
		boolean isExists = false;
		MultipartFile mfile = request.getFile("file");
		if(mfile.getSize()>0) isExists = true;
		return isExists;
	}
}
