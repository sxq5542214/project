package com.yd.business.file.Service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

	/**
	 * 传递图片至图片服务器
	 * @param mfile
	 * @return
	 */
	String uploadImgToImgServer(MultipartFile mfile) throws Exception;
	
}
