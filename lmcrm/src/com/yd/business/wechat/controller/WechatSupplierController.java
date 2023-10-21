/**
 * 
 */
package com.yd.business.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.SignServerBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.Sign;
import com.yd.business.wechat.util.WechatUtil;
import com.yd.util.AutoInvokeGetSetMethod;

/**
 * @author ice
 *
 */
@Controller
public class WechatSupplierController extends BaseController {
	
	@Resource
	private IWechatService wechatSupplierService;
	@Resource
	protected IWechatService wechatService;
	@Resource
	protected IUserWechatService userWechatService;
	@Resource
	protected IConfigAttributeService configAttributeService;
	
//	@RequestMapping("/wechat/supplier/handle.do")
//	public String handle(HttpServletRequest request,HttpServletResponse response){
//		setResponseCharSet(response);
//		log.debug("wechat/handleSupplier.do  method:" + request.getMethod());
//		if(request.getMethod().equals(METHOD_GET)){
//			//微信验证服务接口
//			return checkServerSign(request,response);
//			
//		}else{
//			//微信接口调用入口
//			InputStream inputStream = null;
//			String resultXml = null;
//			try {
//				
//				// 从request中取得输入流  
//				inputStream = request.getInputStream();
//		        // 读取输入流  
//		        SAXReader reader = new SAXReader();  
//		        Document document = reader.read(inputStream);  
//				log.debug("request doc==============\n"+document.asXML());
//				
//				//得到返回值，如果有，立刻返回消息
//				BaseMessage result = wechatSupplierService.handlerWechatMessage(document);
//		        if(result != null){
//		        	resultXml = WechatUtil.MessageToXml(result);
//		        	log.debug("response doc==============\n"+resultXml);
//		        	if(resultXml != null){
//						endResponse(response.getWriter(), resultXml);
//					}
//		        }
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error(e,e);
//			}finally{
//				if(inputStream != null)
//				{
//					try {
//						inputStream.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//						log.error(e,e);
//					}
//			        inputStream = null;
//				}
//			}
//			
//			return null;
//		}
//	}
	
//	/**
//	 * 微信JS-SDK签名.
//	 * @param json
//	 * @return
//	 * @throws Exception 
//	 * @throws JSONException
//	 */
//	@RequestMapping("/wechat/supplier/checkJsSign.do")
//	public ModelAndView getJsSign(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String json = request.getParameter("json");
//		JSONObject jso = null;
//		try {
//			jso = new JSONObject(json);
//		} catch (JSONException e) {
//			e.printStackTrace();
//			log.error(e,e);
//		}
//		String url = jso.optString("url");
//		String jsapi_ticket = null;
//		jsapi_ticket = wechatService.getJsapiTicket();
//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		Map<String, String> ret = Sign.sign(jsapi_ticket, url);
//		
//		String appid = configAttributeService.getValueByCode(AttributeConstant.CODE_APPID);
//		ret.put("appid", appid);
//		list.add(ret);
//		writeJson(response, list);
//		return null;
//	}
	

//	/**
//	 * 验证服务签名
//	 * @param request
//	 * @param response
//	 * @return
//	 */
////	@RequestMapping("/wechat/checkServerSign.do")
//	public String checkServerSign(HttpServletRequest request,HttpServletResponse response){
////		String signature=request.getParameter("signature");
////		String timestamp=request.getParameter("timestamp");
////		String nonce=request.getParameter("nonce");
////		String echostr=request.getParameter("echostr");
//		
////		log.debug("requestMap:" + request.getParameterMap());
//		SignServerBean bean = new SignServerBean();
//		try {
//			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
//			String value = wechatService.validateServerSign(bean);
//			
//			endResponse(response.getWriter(), value);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e,e);
//		}
//		return null;
//	}
	
	/**
	 * 结束response
	 * @param out
	 * @param value
	 */
	protected void endResponse(PrintWriter out,String value){
		if(value != null){
			out.append(value);
			out.flush();
			out.close();
		}
	}
}
