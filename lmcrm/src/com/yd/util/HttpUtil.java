package com.yd.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtil {

	/**
	 * 日志
	 */
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60).setConnectTimeout(1000 * 60).build();

	/**
	 * get请求.
	 * @param url 要请求的url
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url); // 构造请求方式及内容
			httpGet.setConfig(requestConfig);
			final String requestId = UUID.randomUUID().toString(); // 构造请求id，方便以后查找时匹配请求及返回消息
			logger.info("请求id:"+requestId+",开始请求:"+httpGet.getRequestLine());
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:"+requestId+",请求发生异常,错误代码:"+status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpClient.execute(httpGet, responseHandler);
			logger.info("请求id:"+requestId+",返回信息:"+responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}

	}
	
	/**
	 * get请求.
	 * @param url 要请求的url
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url); // 构造请求方式及内容
			httpGet.setConfig(requestConfig);
			final String requestId = UUID.randomUUID().toString(); // 构造请求id，方便以后查找时匹配请求及返回消息
			logger.info("请求id:"+requestId+",开始请求:"+httpGet.getRequestLine());
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:"+requestId+",请求发生异常,错误代码:"+status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpClient.execute(httpGet, responseHandler);
			logger.info("请求id:"+requestId+",返回信息:"+responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}

	}
	
	/**
	 * 直接post内容
	 * @param url
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String post(String url,String content) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url); // 构造请求方式及内容
			httpPost.setConfig(requestConfig);
			HttpEntity entity = new StringEntity(content,"UTF8");
			httpPost.setEntity(entity );
			
			CloseableHttpResponse responseBody = httpClient.execute(httpPost);
			result = EntityUtils.toString(responseBody.getEntity(),"UTF-8");
		} finally{
			httpClient.close();
		}
		return result;
	}
	
	/**
	 * 直接post内容
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String post(CloseableHttpClient httpClient,String url,String content) throws Exception{
		String result = null;
		try {
			 // 构造请求方式及内容
			HttpPost httpPost = new HttpPost(url); // 构造请求方式及内容
			httpPost.setConfig(requestConfig);
			HttpEntity entity = new StringEntity(content,"UTF-8");
			httpPost.setEntity(entity );
			
			CloseableHttpResponse responseBody = httpClient.execute(httpPost);
			result = EntityUtils.toString(responseBody.getEntity(),"UTF-8");
		} finally{
			httpClient.close();
		}
		return result;
	}
	
	/**
	 * post请求.
	 * @param url 请求url
	 * @param params 请求参数，请将key和value放在一个map里
	 * @return 请求结果 
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url); // 构造请求方式及内容
			httpPost.setConfig(requestConfig);
			final String requestId = UUID.randomUUID().toString(); // 构造请求id，方便以后查找时匹配请求及返回消息
			List<NameValuePair> list = new ArrayList<NameValuePair>(); // 构造请求参数
			if(params!=null&&params.size()>0) { //如果有参数
				Set<String> keys = params.keySet();
				for (String key : keys) {
					String value = params.get(key);
					NameValuePair pair = new BasicNameValuePair(key, value);
					list.add(pair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				httpPost.setEntity(entity);
			}
			logger.debug("请求id:"+requestId+",开始请求:"+httpPost.getRequestLine()+",参数:"+EntityUtils.toString(httpPost.getEntity(),
					"utf-8"));
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:"+requestId+",请求发生异常,错误代码:"+status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpClient.execute(httpPost, responseHandler);
			logger.info("请求id:"+requestId+",返回信息:"+responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}
	}
	
	/**
	 * 上传文件
	 * @param url 请求url
	 * @param params 请求参数 文件类型放入<String,File> 文本类型放入<String,String> 
	 * @return 请求结果 
	 * @throws Exception
	 */
	public static String upload(String url,Map<String, Object> params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			final String requestId = UUID.randomUUID().toString(); // 构造请求id，方便以后查找时匹配请求及返回消息
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			if(params!=null&&params.size()>0) { //如果有参数
				Set<String> keys = params.keySet();
				for (String key : keys) { //添加文本参数内容
					Object value = params.get(key);
					if(value instanceof String) { //文本类型的参数
						StringBody body = new StringBody((String)value,
								ContentType.TEXT_PLAIN);
						builder.addPart(key, body);
					}else if(value instanceof File) { //文件类型的参数
						FileBody body = new FileBody((File)value);
						builder.addPart(key, body);
					}
				}
				HttpEntity reqEntity = builder.build();
				httpPost.setEntity(reqEntity);
			}

			logger.info("请求id:"+requestId+",开始请求:"+httpPost.getRequestLine());

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:"+requestId+",请求发生异常,错误代码:"+status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			String responseBody = httpclient.execute(httpPost, responseHandler);
			logger.info("请求id:"+requestId+",返回信息:"+responseBody);
			return responseBody;
		} finally {
			httpclient.close();
		}
	}
	
	/**
	 * download请求.
	 * @param url 请求url
	 * @param params 请求参数，请将key和value放在一个map里
	 * @return 请求结果 
	 * @throws Exception
	 */
	public static byte[] download(String url, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url); // 构造请求方式及内容
			httpPost.setConfig(requestConfig);
			final String requestId = UUID.randomUUID().toString(); // 构造请求id，方便以后查找时匹配请求及返回消息
			List<NameValuePair> list = new ArrayList<NameValuePair>(); // 构造请求参数
			if(params!=null&&params.size()>0) { //如果有参数
				Set<String> keys = params.keySet();
				for (String key : keys) {
					String value = params.get(key);
					NameValuePair pair = new BasicNameValuePair(key, value);
					list.add(pair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				httpPost.setEntity(entity);
			}
			logger.info("请求id:"+requestId+",开始请求:"+httpPost.getRequestLine());
			ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public byte[] handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						logger.info(entity.getContentType().getValue());
						return entity != null ? EntityUtils.toByteArray(entity) : null;
					} else {
						logger.error("请求id:"+requestId+",请求发生异常,错误代码:"+status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			byte[] responseBody = httpClient.execute(httpPost, responseHandler);
			logger.info("请求id:"+requestId+",返回信息:"+responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}
	}
	
	/**
	 * 
	 * @Description:拼接要发送的参数
	 * @param params 参数数组 
	 * @return 拼接后的参数 
	 */
	public static String joinParams(String[] params) {
		StringBuilder params_to_send = new StringBuilder();
		for(int i=0;i<params.length;i++) {
			params_to_send.append(params[i]);
			if(i!=params.length-1) {
				params_to_send.append("&");
			}
		}
		return params_to_send.toString();
	}
	
	public static void main(String[] args) { 
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2e4ccc2c67b4743d&secret=07509f08f6a76b3017c0dc898b88b3e0";
        String result = "";
        try {
			result = HttpUtil.get(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(result);
    } 
	
	
	
	/**
	 * json入参请求.
	 * @param url 请求url
	 * @param params 
	 * @return 请求结果 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	public static String postJtForJson(String url, JSONObject params) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		RequestConfig requestConfig = RequestConfig.custom()  
//		        .setSocketTimeout(10000)  
//		        .setConnectTimeout(10000)  
//		        .build();  
		final String APPLICATION_JSON = "application/json";
		final String CONTENT_TYPE_TEXT_JSON = "application/json;charset=UTF-8";
		try {
			/*final String requestId = UUIDUtil.generate(); // 构造请求id，方便以后查找时匹配请求及返回消息
			StringBuffer sb = new StringBuffer();
			sb.append(url).append(requestId);*/
			HttpPost httpPost = new HttpPost(url); 
			httpPost.setConfig(requestConfig);
//			httpPost.setConfig(requestConfig);
			String content = params.toString();
			String encoderJson = URLEncoder.encode(content, HTTP.UTF_8);
	        StringEntity se = new StringEntity(encoderJson);
	        se.setContentType(CONTENT_TYPE_TEXT_JSON);
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			//内容实体
			httpPost.setEntity(new StringEntity(content, "UTF-8"));
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) { 
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,"utf-8") : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpClient.execute(httpPost, responseHandler);
			return responseBody;
		} finally {
			httpClient.close();
		}
	}

	
	/**
	 * 从cookie中读取数据
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getParamByCookie(HttpServletRequest request,String key){
		
		if(StringUtil.isNull(key)){
			return null;
		}
		
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		if(cookies != null){
			for(Cookie cookie : cookies){
			    if(key.equals(cookie.getName())){
			    	return cookie.getValue();
			    }
			}
		}
		
		return null;
	}
	

    /** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
*  
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
     *  
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
     * 192.168.1.100 
     *  
     * 用户真实IP为： 192.168.1.110 
     *  
     * @param request 
     * @return 
     */  
    public static String getIpAddress(HttpServletRequest request) {  
//    	String ip = request.getRemoteAddr();
    	
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
    public static String getIpAddressByRequest() {  
    	HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    	return getIpAddress(request);
    }

}
