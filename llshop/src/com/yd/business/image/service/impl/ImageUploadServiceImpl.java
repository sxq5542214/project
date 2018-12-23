/**
 * 
 */
package com.yd.business.image.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.image.bean.UploadLogBean;
import com.yd.business.image.dao.IImageUploadDao;
import com.yd.business.image.service.IImageUploadService;
import com.yd.util.DateUtil;
import com.yd.util.ImageUtils;
import com.yd.util.ImageUtils.ImageParam;
import com.yd.util.StringUtil;

/**
 * @author ice
 * 
 */
@Service("imageUploadService")
public class ImageUploadServiceImpl extends BaseService implements IImageUploadService {

	@Resource
	private IImageUploadDao imageUploadDao;

	private static String IMG_FILE_DIR = "images/upload";
	private static String IMG_FILE_THUMB_DIR = "images/upload/thumb";
	
	/**
	 * 保存post上来的图片文件
	 */
	@Override
	public UploadLogBean saveFileFromPostUpload(MultipartHttpServletRequest request,MultipartFile mfile,
			String remote_ip) {

		String server_name = null;
		String localPath = getLocalRealPath(request);
		String serverPath = getServerBasePath(request);

		//缩略图的宽高
		String thumbWidthStr = request.getParameter("thumbWidth");
		String thumbHeightStr = request.getParameter("thumbHeight");
		int thumbWidth = 100;
		int thumbHeight = 100;
		if(StringUtil.isNotNull(thumbWidthStr) && StringUtil.isNotNull(thumbHeightStr)){
			thumbWidth = Integer.parseInt(thumbWidthStr);
			thumbHeight = Integer.parseInt(thumbHeightStr);
		}
		
		String imgRelativePath = createImgRealPath(IMG_FILE_DIR);

		String realLocalPath = localPath + imgRelativePath;
		String realServerPath = serverPath + imgRelativePath;

		UploadLogBean bean = createUploadLog(realLocalPath, mfile.getOriginalFilename(), remote_ip, "saveFileFromPostUpload", realServerPath,
				server_name);

		boolean flag = false;
		try {
			File file = new File(realLocalPath);

			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (mfile.getSize() > 0) {
				InputStream is = mfile.getInputStream();
				int byteCount = 0;
				byte[] b = new byte[1024];
				FileOutputStream fos = new FileOutputStream(realLocalPath);
				while ((byteCount = is.read(b)) != -1) {
					fos.write(b, 0, byteCount);
				}
				is.close();
				fos.close();
				flag = true;
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		
		if(flag){
			//缩略图的生成
			imgRelativePath = createImgRealPath(IMG_FILE_THUMB_DIR);
			realLocalPath = localPath + imgRelativePath;
			realServerPath = serverPath + imgRelativePath;
			bean.setThumb_file_path(realLocalPath);
			bean.setThumb_file_url(realServerPath);
			//这里直接从本地取文件，再生成缩略图
			flag = ImageUtils.compressPic(bean.getReal_file_path(), realLocalPath, new ImageParam(thumbWidth, thumbHeight), 0.8f);
		}
		
		if (flag) {
			bean.setStatus(UploadLogBean.STATUS_ENABLE);
		} else {
			bean.setStatus(UploadLogBean.STATUS_FAILD);
		}
		updateUploadLog(bean);

		return bean;

	}

	/**
	 * 保存需要通过http下载的文件，并返回保存日志
	 */
	@Override
	public UploadLogBean saveFileFromHttp(HttpServletRequest request, String httpFilePath,Integer thumbWidth,Integer thumbHeight,  String remote_ip) {

		String server_name = null;
		String localPath = getLocalRealPath(request);
		String serverPath = getServerBasePath(request);
		//缩略图的宽高
		if(thumbWidth == null || thumbHeight == null){
			thumbWidth = 100;
			thumbHeight = 100;
		}

		String imgRelativePath = createImgRealPath(IMG_FILE_DIR);

		String realLocalPath = localPath + imgRelativePath;
		String realServerPath = serverPath + imgRelativePath;

		UploadLogBean bean = createUploadLog(realLocalPath, httpFilePath, remote_ip, "saveFileFromHttp", realServerPath,
				server_name);
		ImageParam imgParam = ImageParam.getInstance();
		boolean flag = ImageUtils.compressPic(httpFilePath, realLocalPath,imgParam, 0.8f);
		
		bean.setWidth(imgParam.getWidth());
		bean.setHeight(imgParam.getHeight());
		
		if(flag){
			//缩略图的生成
			imgRelativePath = createImgRealPath(IMG_FILE_THUMB_DIR);
			realLocalPath = localPath + imgRelativePath;
			realServerPath = serverPath + imgRelativePath;
			bean.setThumb_file_path(realLocalPath);
			bean.setThumb_file_url(realServerPath);
			//这里直接从本地取文件，再生成缩略图
			flag = ImageUtils.compressPic(bean.getReal_file_path(), realLocalPath,new ImageParam(thumbWidth, thumbHeight), 0.8f);
		}
		

		if (flag) {
			bean.setStatus(UploadLogBean.STATUS_ENABLE);
		} else {
			bean.setStatus(UploadLogBean.STATUS_FAILD);
		}
		updateUploadLog(bean);

		return bean;
	}

	public UploadLogBean createUploadLog(String real_file_path, String request_file_url, String request_ip,
			String request_method, String return_file_url, String server_name) {

		UploadLogBean bean = new UploadLogBean();
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setReal_file_path(real_file_path);
		bean.setRequest_file_url(request_file_url);
		bean.setRequest_ip(request_ip);
		bean.setRequest_method(request_method);
		bean.setReturn_file_url(return_file_url);
		bean.setServer_name(server_name);
		bean.setStatus(UploadLogBean.STATUS_DISABLE);

		imageUploadDao.createUploadLog(bean);
		return bean;
	}

	public void updateUploadLog(UploadLogBean bean) {
		bean.setModify_time(DateUtil.getNowDateStrSSS());
		imageUploadDao.updateUploadLog(bean);
	}

	protected String getServerBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		return basePath;
	}

	protected String getLocalRealPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path.replaceAll("\\\\", "/");
		return path;
	}

	protected String getImgName() {
		String d = DateUtil.formatDateToPureSSS(new Date());
		return "1_G_" + d + ".jpg";
	}

	protected String appendDirPath(String path, String dir) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		path = path + "/" + sdf.format(new Date()) + "/" + dir;
		return path;
	}

	protected String createImgRealPath(String path) {
		String imgName = getImgName();
		return appendDirPath(path, imgName);
	}

}
