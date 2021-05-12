/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatPayResultBean;
import com.yd.business.wechat.bean.WechatSendRedPackLogBean;
import com.yd.business.wechat.dao.IWechatPayDao;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("wechatPayService")
public class WechatPayServiceImpl extends BaseService implements IWechatPayService {
	
	@Resource
	private IWechatPayDao wechatPayDao;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	protected IWechatOriginalInfoService wechatOriginalInfoService;
	
	private Map<String,SSLConnectionSocketFactory> map_sslsf = new HashMap<String, SSLConnectionSocketFactory>();

//	private static SSLConnectionSocketFactory sslsf = null;
	
	
	public void createWechatPayBonusLog(WechatSendRedPackLogBean bean){
		wechatPayDao.createWechatPayBonusLog(bean);
	}
	
	@Override
	public WechatSendRedPackLogBean getLastTimeSendPackLog(String openid){
		return wechatPayDao.getLastTimeSendPackLog(openid);
	}
	
	@Override
	public List<WechatSendRedPackLogBean> getAllSendPackLog(String openid){
		return wechatPayDao.getAllSendPackLog(openid);
	}
	

	/**
	 * 给提现者发送红包,不超过200元
	 * payMoney 分为单位
	 * @return
	 */
	@Override
	public Boolean payBonusLimit200(String openId,int payMoney,String ipAddr,String remark,int bonusNum){
		
		
		int seq = countSendRedPackByOpenId(openId);
		
//		int balance = user.getBalance() ; // 以分为单位
		//不能超过200元
		if(payMoney > 200 * 100){
			payMoney = 200 * 100;
		}
		//不能超过用户的余额
//		if(payMoney > balance ){
//			payMoney = balance;
//		}
		//要付款的金额小于0，直接返回失败
		if( payMoney <= 0 ){
			return false;
		}
		
//		orderCode = orderCode.replaceAll("_", "X");
		log.info("GO Send user payBonus:"+ payMoney);
		return null;
	}
	
	/**
	 * 获取用户的红包总记录
	 * @param openId
	 * @return
	 */
	private int countSendRedPackByOpenId(String openId){
		int seq = 0;
		List<WechatSendRedPackLogBean> logList = this.getAllSendPackLog(openId);
		if(!StringUtil.isNull(logList)){
			seq = logList.size() + 1;
		}
		return seq;
	}

	
	
	

	/**
	 * 微信红包调用后返回的结果解析
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	private static WechatPayResultBean parseWechatBonusResult(String xml) throws DocumentException{
		WechatPayResultBean result = new WechatPayResultBean();
		Document doc = DocumentHelper.parseText(xml);
//		List<String> params = new ArrayList<String>();
//		result.setParams(params);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e: list){

			if(e.getName().equalsIgnoreCase("return_code")){
				result.setReturn_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("return_msg")){
				result.setReturn_msg(e.getText());
			}
			if(e.getName().equalsIgnoreCase("wxappid")){
				result.setAppid(e.getText());
			}
			if(e.getName().equalsIgnoreCase("mch_id")){
				result.setMch_id(e.getText());
			}
			if(e.getName().equalsIgnoreCase("sign")){
				result.setSign(e.getText());
			}
			if(e.getName().equalsIgnoreCase("result_code")){
				result.setResult_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("err_code")){
				result.setErr_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("err_code_des")){
				result.setErr_code_des(e.getText());
			}
			//支付通知扩展字段
			if(e.getName().equalsIgnoreCase("total_amount")){
				result.setCash_fee(e.getText());
			}if(e.getName().equalsIgnoreCase("re_openid")){
				result.setOpenid(e.getText());
			}if(e.getName().equalsIgnoreCase("mch_billno")){
				result.setOut_trade_no(e.getText());
			}if(e.getName().equalsIgnoreCase("send_time")){
				result.setTime_end(e.getText());
			}if(e.getName().equalsIgnoreCase("total_fee")){
				result.setTotal_fee(e.getText());
			}if(e.getName().equalsIgnoreCase("send_listid")){
				result.setTransaction_id(e.getText());
			}
		}
		return result;
	}

	private static String convertToXML(Object... params){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element root = doc.addElement("xml");
		
		for(Object p : params){
			System.out.println(p);
			String pa =String.valueOf(p);
			int index = pa.indexOf("=");
			String key = pa.substring(0,index);
			String value = pa.substring(index+1, pa.length());
			
			Element ele = root.addElement(key);
//			ele.addCDATA(value);
			ele.setText(value);
		}
		
		return doc.asXML();
	}
	

	private static String concatParam(Object... params){
		
		Arrays.sort(params);

		StringBuffer str = new StringBuffer();
		
		for(Object s : params){
			str.append("&" + s);
		}
		
		return str.substring(1);
	}
	
	/**
	 * 创建微信支付带签名的请求
	 * @param isSend  是否发送红包 true为是
	 * @return
	 */
	private CloseableHttpClient createWechatCertPostClient(WechatOriginalInfoBean originalInfo,boolean isSend){
		CloseableHttpClient httpclient = null;
		try{
			SSLConnectionSocketFactory sslsf = map_sslsf.get(originalInfo.getOriginalid());
			
			if(sslsf == null){
				KeyStore keyStore  = KeyStore.getInstance("PKCS12");
				String mch_id = originalInfo.getBonus_mch_id();
				String certFilePath = originalInfo.getBonus_pay_cert_file_path();
				if(!isSend) {
					mch_id = originalInfo.getMch_id();
					certFilePath = originalInfo.getPay_cert_file_path();
				}
		        FileInputStream instream = new FileInputStream(new File(certFilePath));
		        try {
		            keyStore.load(instream, mch_id.toCharArray());
		        } finally {
		            instream.close();
		        }
		
		        // Trust own CA and all self-signed certs
		        SSLContext sslcontext = SSLContexts.custom()
		                .loadKeyMaterial(keyStore, mch_id.toCharArray())
		                .build();
		        // Allow TLSv1 protocol only
		        sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		        
		        map_sslsf.put(originalInfo.getOriginalid(), sslsf);
			}
        	httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		}
        return httpclient;
	}
	
	
	
}
