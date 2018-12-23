package com.yd.business.order.util;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
public class ConnectionUrlUtil {
	private static Logger log = Logger.getLogger(ConnectionUrlUtil.class);
	/**
	 * post提交
	 * @param url 接收的url
	 * @param parameter 接收的参数
	 * @return 返回结果
	 * @throws IOException 
	 */
	public static String sendPost(String url, String parameter,String charsetName) throws IOException {
		String result = "";
		URL u0 = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u0.openConnection();
		conn.setRequestMethod("POST");
		byte contentbyte[] = parameter.toString().getBytes();
		//设置请求类型
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//设置表单长度
		conn.setRequestProperty("Content-Length", (new StringBuilder()).append(contentbyte.length).toString());
		//设置默认语言
		conn.setRequestProperty("Content-Language", "en-US");//zh-CN代表中国  默认为美式英语
		//连接主机的超时时间（单位：毫秒）
		conn.setConnectTimeout(60000);
		//从主机读取数据的超时时间（单位：毫秒)
		conn.setReadTimeout(60000);
		// Post 请求不能使用缓存
		conn.setUseCaches(false);
		// 设置是否从httpUrlConnection读入，默认情况下是true;  
		conn.setDoInput(true);
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在    2 
		// http正文内，因此需要设为true, 默认情况下是false;  
		conn.setDoOutput(true);
		BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bWriter.write(parameter.toString());
		bWriter.flush();
		bWriter.close();
		// 调用HttpURLConnection连接对象的getInputStream()函数,   
		// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
		InputStream in = conn.getInputStream();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i != -1;) {
			i = in.read();
			if (i != -1)
				buffer.append((char) i);
		}
		in.close();
		
		//此方法是用Reader读取BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream()));
		result = new String(buffer.toString().getBytes("iso-8859-1"), charsetName);
		return result;
	}
	
}
