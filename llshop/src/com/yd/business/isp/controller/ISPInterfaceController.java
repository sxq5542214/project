/**
 * 
 */
package com.yd.business.isp.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.isp.bean.DaHanSanTongInterfaceBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.JieTuoInterfaceBean;
import com.yd.business.isp.bean.NanJingShengShiInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.isp.bean.YunZhangTongInterfaceBean;
import com.yd.business.isp.bean.ZhuoweiInterfaceBean;
import com.yd.business.isp.service.IAccessISPOrderInterfaceService;
import com.yd.business.isp.test.CallISP;
import com.yd.business.isp.util.ISPInterfaceUtil;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.util.AESUtils;

/**
 * @author ice
 *
 */
@Controller
public class ISPInterfaceController extends BaseController {
	
	@Resource
	private IAccessISPOrderInterfaceService accessISPOrderInterfaceService;
	@Resource
	private IConfigAttributeService configAttributeService;
	
	
	/**
	 * 从request中读取流，转换为字符串
	 * @param request
	 * @return
	 */
	protected String readRequestContent(HttpServletRequest request){
		// 从request中取得输入流  
		ServletInputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		String content = null;
		try {
			inputStream = request.getInputStream();
	        // 读取输入流  
			isr = new InputStreamReader(inputStream);
			reader = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			//循环读取
			while ((content = reader.readLine()) != null) {
				content = new String(content.getBytes(), "utf-8");
                sb.append(content);
            }
			content = sb.toString();
		} catch (IOException e) {
			log.error(e, e);
		}finally{
			try {
				//关闭流
				if(reader != null)
				{
					reader.close();
				}
				if(isr != null)
				{
					isr.close();
				}
				if(inputStream != null)
				{
					inputStream.close();
				}
			} catch (Exception e) {
				log.error(e,e);
			}
		}
		return content;
	}
	
	
	/**
	 * 处理云掌通的回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isp/handleYunZhangTongCallBack.htm")
	public ModelAndView handleYunZhangTongCallBack(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		YunZhangTongInterfaceBean bean = null;
		String content = null;
		try {
			
			content = readRequestContent(request);
			log.info("handleYunZhangTongCallBack :" + content);
			bean = ISPInterfaceUtil.convertJsonToYunZhangTongBean(content);
			
			bean = accessISPOrderInterfaceService.handleYunZhangTongCallBack(bean);
			
		} catch (Exception e) {
			bean = new YunZhangTongInterfaceBean();
			bean.setStatus(-1);
			bean.setRemark(e.getMessage());
			
			log.error(e,e);
		}

		result = ISPInterfaceUtil.convertYunZhangTongBeanToResult(bean);
		//保存日志信息
		accessISPOrderInterfaceService.createISPCallbackLog(bean.getPhone(), bean.getCpBatchId(), "YunZhangTongCallBack", bean.getStatus(), bean.getRemark(), content, result);
		
		writeJson(response, result);
		return null;
		
	}
	
	/**
	 * 处理大汉三通的回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isp/handleDaHanSanTongCallBack.htm")
	public ModelAndView handleDaHanSanTongCallBack(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		DaHanSanTongInterfaceBean bean = null;
		String content = null;
		try {
			
			content = readRequestContent(request);
			log.info("handleDaHanSanTongCallBack :" + content);
			bean = ISPInterfaceUtil.convertJsonToDaHanSanTongBean(content);
			
			bean = accessISPOrderInterfaceService.handleDaHanSanTongCallBack(bean);
			
		} catch (Exception e) {
			bean = new DaHanSanTongInterfaceBean();
			bean.setStatus(-1);
			bean.setRemark(e.getMessage());
			
			log.error(e,e);
		}

		result = ISPInterfaceUtil.convertDaHanSanTongBeanToResult(bean);
		//保存日志信息
		accessISPOrderInterfaceService.createISPCallbackLog(bean.getMobiles(), bean.getClientOrderId(), "DaHanSanTongCallBack", bean.getStatus(), bean.getRemark(), content, result);
		
		writeJson(response, result);
		return null;
		
	}
	
	/**
	 * 处理弯流科技的回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isp/handleWanLiuKeJiCallBack.htm")
	public ModelAndView handleWanLiuKeJiCallBack(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		WanLiuInterfaceBean bean = null;
		String content = null;
		try {
			CallISP t1 = new CallISP();
			t1.run();
			content = readRequestContent(request);
			log.info("handleWanLiuKeJiCallBack :" + content);
			//===============测试用=========================
//			System.out.println("+++++++handleWanLiuKeJiCallBack++++++++"+content+"========================");
			//=============================================
			bean = ISPInterfaceUtil.convertJsonToWanLiuInterBean(content);
			
			bean = accessISPOrderInterfaceService.handleWanLiuKeJiCallBack(bean);
			
		} catch (Exception e) {
			bean = new WanLiuInterfaceBean();
			bean.setStatus(-1);
			bean.setRemark(e.getMessage());
			
			log.error(e,e);
		}

		result = ISPInterfaceUtil.convertWanLiuKeJiBeanToResult(bean);
		//保存日志信息
		accessISPOrderInterfaceService.createISPCallbackLog(bean.getRep_mob_no(), bean.getOrder_sn(), "handleWanLiuKeJiCallBack", bean.getStatus(), bean.getRemark(), content, result);
		
		writeJson(response, result);
		return null;
		
	}
	

	/**
	 * 处理大汉三通的回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isp/handleNanJingShengShiCallBack.htm")
	public ModelAndView handleNanJingShengShiCallBack(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		NanJingShengShiInterfaceBean bean = new NanJingShengShiInterfaceBean();
		Map<String, String> param = getRequestParamsMap(request);
		try {
			
//			content = readRequestContent(request);
			log.info("handleNanJingShengShiCallBack :" + param);
			bean.setOrder_code(param.get("req_no"));
			String code = param.get("code");
			String msg = param.get("msg");
			bean.setResCode(code);
			bean.setResMsg(msg);
			
			if(NanJingShengShiInterfaceBean.RESCODE_SUCCESS.equals(code)){
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				bean.setRemark("订购成功");
			}else if(NanJingShengShiInterfaceBean.RESCODE_WAIT.equals(code)){
				bean.setStatus(ISPInterfaceBean.STATUS_WAIT);
				bean.setRemark(msg);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
				bean.setRemark(msg);
			}
			
			
			bean = accessISPOrderInterfaceService.handleNanJingShengshiCallBack(bean);
			
			result = "{code:1,msg:\"success\"}";
		} catch (Exception e) {
			bean = new NanJingShengShiInterfaceBean();
			bean.setStatus(-1);
			bean.setRemark(e.getMessage());
			
			log.error(e,e);
			result = "{code:-1,msg:\""+ e.getMessage() +"\"}";
		}

		//保存日志信息
		accessISPOrderInterfaceService.createISPCallbackLog(bean.getPhoneNo(), bean.getOrder_code(), "NanJingShengShiCallBack", bean.getStatus(), bean.getRemark(), param.toString(), result);
		
		writeJson(response, result);
		return null;
		
	}
	

	/**
	 * 处理卓威的回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isp/handleZhuoWeiCallBack.htm")
	public ModelAndView handleZhuoWeiCallBack(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		String content = readRequestContent(request);
		ZhuoweiInterfaceBean bean = null;
		try {
			log.info("handleZhuoWeiCallBack :" + content);
			bean = ISPInterfaceUtil.parseZhuoWeiResult(content);
			
			if(ZhuoweiInterfaceBean.RESCODE_WAIT.equals(bean.getResCode())){
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				bean.setRemark("订购成功");
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			}
			
			bean = accessISPOrderInterfaceService.handleZhuoWeiCallBack(bean);
			
			result = "OK";
		} catch (Exception e) {
			
			log.error(e,e);
			result = "{code:-1,msg:\""+ e.getMessage() +"\"}";
		}

		//保存日志信息
		accessISPOrderInterfaceService.createISPCallbackLog(bean.getPhone(), bean.getOrder_code(), "handleZhuoWeiCallBack", bean.getStatus(), bean.getRemark(), content, result);
		
		writeJson(response, result);
		return null;
		
	}
	/**
	 * 处理杰拓通信的回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isp/handleJieTuoCallBack.htm")
	public ModelAndView handleJieTuoCallBack(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		JieTuoInterfaceBean bean = new JieTuoInterfaceBean();
		String content = null;
		try {
			ServletInputStream inputStream = request.getInputStream();
			DataInputStream input = new DataInputStream(inputStream); 
			String str =input.readUTF(); 
			
			input.close();
			inputStream.close();
		
			
//			content = readRequestContent(request);
			log.info("handleJieTuoCallBack :" + str);
			
			JSONObject jso = new JSONObject(str);
			String codeStr = jso.getString("code");
			String key = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO_KEY);
			String vi = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO_VI);
			
			content = AESUtils.encrypt(codeStr, key, vi);
			jso = new JSONObject(content);
			
			bean.setOrder_code(jso.getString("order_no"));
			String status = jso.getString("order_status");
			String code = jso.getString("result_code");
			String msg = jso.getString("result_des");
			
			bean.setResCode(code);
			bean.setResMsg(msg);
			
			
			if(JieTuoInterfaceBean.RESCODE_SUCCESS.equals(status)){
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				bean.setRemark("订购成功");
			}else if(JieTuoInterfaceBean.RESCODE_WAIT.equals(status)){
				bean.setStatus(ISPInterfaceBean.STATUS_WAIT);
				bean.setRemark(msg);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
				bean.setRemark(msg);
			}
			
			
			bean = accessISPOrderInterfaceService.handleJieTuoCallBack(bean);
			
			result = "{code:1,msg:\"success\"}";
		} catch (Exception e) {
			bean = new JieTuoInterfaceBean();
			bean.setStatus(-1);
			bean.setRemark(e.getMessage());
			
			log.error(e,e);
			result = "{code:-1,msg:\""+ e.getMessage() +"\"}";
		}

		//保存日志信息
		accessISPOrderInterfaceService.createISPCallbackLog(bean.getPhoneNo(), bean.getOrder_code(), "handleJieTuoCallBack", bean.getStatus(), bean.getRemark(), content, result);
		
		writeJson(response, result);
		return null;
		
	}
	
}
