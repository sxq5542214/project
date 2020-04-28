package com.yd.business.file.Service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yd.business.file.Service.IFileUploadService;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

@Service("fileUploadService")
public class FileUploadServiceImpl implements IFileUploadService {

	@Resource
	private IConfigAttributeService configAttributeService;
	
	@Override
	public String uploadImgToImgServer(MultipartFile mfile) throws Exception {
		ConfigAttributeBean attrBean = configAttributeService.getAttributeByCode(AttributeConstant.CODE_IMAGESERVERURL_POST);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 20).setConnectTimeout(1000 * 60).build();
		// 获得utf-8编码的mbuilder
		MultipartEntityBuilder mBuilder = get_COMPATIBLE_Builder("UTF-8");
		// 设置type所以type是thumb
		String fileName = DateUtil.formatDatePure(new Date());
		// 这里就是我要上传到服务器的多媒体图片
		mBuilder.addBinaryBody("postFile",mfile.getBytes() ,ContentType.APPLICATION_OCTET_STREAM, fileName.substring(2, fileName.length())+".jpg");
		// 建造我们的http多媒体对象
		HttpEntity he = mBuilder.build();
		// post提交缩略图信息到公众号
		String reponseBean = postThumbInfo(he,requestConfig,attrBean.getValue());
		return reponseBean;
	}
	/**
	 * 向公众号post缩略图信息
	 * @param he
	 * @param requestConfig
	 * @param originalBean
	 * @return
	 * @throws Exception 
	 */
	private String postThumbInfo(HttpEntity he,RequestConfig requestConfig,String url) throws IOException{
		// 建立连接器
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			// 得到一个post请求的实体
			HttpPost post = getMultipartPost(url);
			post.setConfig(requestConfig);
			// 给请求添加参数
			post.setEntity(he);
			// 执行请求并获得结果
			CloseableHttpResponse reponse = client.execute(post);
			try {
				// 获得返回的内容
				HttpEntity entity = reponse.getEntity();
				// 输出
				String reponseStr = EntityUtils.toString(entity);
				if (StringUtil.isNotNull(reponseStr)) {
					return reponseStr;
				}
				// 消耗实体
				EntityUtils.consume(entity);
			} finally {
				// 关闭返回的reponse
				reponse.close();
			}
		} finally {
			// 关闭client
			client.close();
		}
		return null;
	}
	private MultipartEntityBuilder get_COMPATIBLE_Builder(String charSet) {
		MultipartEntityBuilder result = MultipartEntityBuilder.create();
		result.setBoundary(getBoundaryStr("7da2e536604c8")).setCharset(Charset.forName(charSet)).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		return result;
	}
	
	private String getBoundaryStr(String str) {
		return "------------" + str;
	}
	
	private HttpPost getMultipartPost(String url) {
		/* 这里设置一些post的头部信息，具体求百度吧 */
		HttpPost post = new HttpPost(url);
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Accept", "*/*");
		post.addHeader("Content-Type", "multipart/form-data;boundary="+ getBoundaryStr("7da2e536604c8"));
		post.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		return post;
	}
}
