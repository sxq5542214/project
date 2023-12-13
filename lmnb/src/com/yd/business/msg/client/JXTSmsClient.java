package com.yd.business.msg.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.yd.basic.framework.context.BaseContext;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;

public class JXTSmsClient {
	static Logger log ;

	private static JXTSmsClient self ;
	private static final String JXT_URL = "https://service.winic.org/sys_port/gateway/index.asp";
	private static String appID ;
	private static String appPWD ;
	private static IConfigAttributeService configAttributeService;
	private JXTSmsClient(String appID,String appPWD) {
		log = Logger.getLogger(getClass());
	}
	
	public static JXTSmsClient getInstance() {
		if(self == null) {
			configAttributeService = (IConfigAttributeService) BaseContext.getBean("configAttributeService");
			appID = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_JXT_APPID ) ;
			appPWD = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_JXT_APPPWD) ;
			
			self = new JXTSmsClient(appID,appPWD);
		}
		return self;
	}
	
	
	public String postSMS(String phone, String contentStr) throws Exception { 
        BufferedReader In = null;
        PrintWriter Out = null;   
        HttpURLConnection HttpConn = null;
        String data =  "id="+appID+"&pwd="+appPWD+"&to="+phone+"&content=" + URLEncoder.encode(contentStr, "gb2312")+ "&time=" ;
        		
        log.debug("JXTSmsClient_postSMS:"+data);
        System.out.println("JXTSmsClient_postSMS:"+data);
        try {
	        URL url=new URL(JXT_URL);
	        HttpConn=(HttpURLConnection)url.openConnection();
	        HttpConn.setRequestMethod("POST");
	        HttpConn.setDoInput(true);
	        HttpConn.setDoOutput(true);
	     
	        Out=new PrintWriter(HttpConn.getOutputStream());
	        Out.println(data);
	        Out.flush();
	        
	        if(HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK){
		        StringBuffer content = new StringBuffer();
		        String tempStr = "";
		        In = new BufferedReader(new InputStreamReader(HttpConn.getInputStream()));
		        while((tempStr = In.readLine()) != null){
		        	content.append(tempStr);
		        }
		        In.close();
		        return content.toString();
	        }else{
	        	log.error("JXTSmsClient_POST_ERROR_RETURN_STATUS：" + HttpConn.getResponseCode());
	        	throw new Exception("JXTSmsClient_POST_ERROR_RETURN_STATUS：" + HttpConn.getResponseCode());
	        }
        } catch (IOException e) {
        	e.printStackTrace();
        	log.error(e);
        	return "error:"+e.getMessage();
        }finally{
	        Out.close();
	        HttpConn.disconnect();
        }
//        return null;
}
           
	public static void main(String[] args) throws Exception{
		String HTTP_BACK_MESSAGE = "";
//		HTTP_BACK_MESSAGE = postSMS("https://service.winic.org/sys_port/gateway/index.asp", "id=123321&pwd=asdffda&to=手机号码&content=" + URLEncoder.encode("您好，您的验证码是884192【中正云通信】", "gb2312") + "&time=");
//		HTTP_BACK_MESSAGE = postSMS("15955107747", "测试短信");
		System.out.println(HTTP_BACK_MESSAGE); 
	}


}
