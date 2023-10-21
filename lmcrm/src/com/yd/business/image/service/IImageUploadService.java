package com.yd.business.image.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yd.business.image.bean.UploadLogBean;

public interface IImageUploadService {

	UploadLogBean saveFileFromPostUpload(MultipartHttpServletRequest request, MultipartFile mfile, String remote_ip);

	UploadLogBean saveFileFromHttp(HttpServletRequest request, String httpFilePath, Integer thumbWidth,
			Integer thumbHeight, String remote_ip);

}
