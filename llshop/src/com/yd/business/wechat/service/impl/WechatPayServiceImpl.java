/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang.ArrayUtils;
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
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatPayResultBean;
import com.yd.business.wechat.bean.WechatSendRedPackLogBean;
import com.yd.business.wechat.dao.IWechatPayDao;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5Util;
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
	private IUserConsumeInfoService userConsumeInfoService;
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
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
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
		String orderCode = userConsumeInfoService.createOutTradeNo( IUserConsumeInfoService.OUTTRADE_TYPE_WECHATBONUS , user.getId());
		
//		orderCode = orderCode.replaceAll("_", "X");
		log.info("GO Send user payBonus:"+ payMoney);
		boolean flag = createWechatPayBonus(user,seq, user.getOpenid(),payMoney,ipAddr,orderCode,bonusNum);
		
		if(flag){//成功扣减用户余额
			userConsumeInfoService.createConsumeInfo(remark, -payMoney, null, user.getId(), null, orderCode, UserConsumeInfoBean.INTERFACETYPE_WECHATBONUS_CH,UserConsumeInfoBean.EVENT_TYPE_USER_WECHAT_BOUNS);
//			userWechatService.updateUserBalance(orderCode, null);  //此处扣减余额方法并发大时有问题
		}
		
		return flag;
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
	 * 微信创建和发送红包
	 * @param openId
	 * @param price  单位，分
	 * @param bounsNum   红包数量，大于1则是裂变红包，1位普通红包
	 */
	private boolean createWechatPayBonus(UserWechatBean user ,int seq, String openId,int price,String spbill_create_ip,String order_code,int bonusNum){
		boolean flag = false;
		
		int userId = user.getId();
		
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
		
		//wx26a55db19faf530f
		String appidStr = originalInfo.getAppid();
		String appid = "wxappid=" + appidStr; 
		String mch_idStr = originalInfo.getBonus_mch_id();
		//商户号
		String mch_id = "mch_id="+ mch_idStr;
		//随机码
		String nonce_str = "nonce_str=" + UUID.randomUUID().toString().replaceAll("-", "");
//		//附加数据，原样返回
//		String attach = "attach="+ "test";
//		//商品详情
//		String body = "body=支付商品";
		//商户订单号
//		String mch_billno = consumetableService.createOutTradeNo("wechatBonus", lotteryId.toString());
		
//		String mch_billno = mch_idStr + order_code;
		
		String mch_billno = mch_idStr + DateUtil.formatOnlyDatePure(new Date()) + StringUtil.fixString(userId +""+ seq, 10);
		String out_trade_no = "mch_billno="+mch_billno;
		String mch_name = originalInfo.getMch_name();
		//商户名称
		String send_name = "send_name=" + mch_name;
		String amt_type = "amt_type=ALL_RAND";
		String scene_id = "scene_id=PRODUCT_1";

		//用户ID //oiRcFuKHjk9_V8-eWwHA1W4x1XWc
		String openid = "re_openid=" + openId;
		//付款金额,以分为单位
		String total_amount = "total_amount="+ price;
		System.out.println("_+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++total_amount="+ price);
		//红包发放总人数
		String total_num = "total_num="+bonusNum;
		//红包祝福语
		String wishing = "wishing=快关注【" + mch_name + "】公众号，更多现金红包等你来拿！11";
		//Ip地址
//		String spbill_create_ip = Struts2Utils.getRequest().getRemoteAddr();
		if(spbill_create_ip == null){
			spbill_create_ip = HttpUtil.getIpAddressByRequest();
		}
		String client_ip = "client_ip="+spbill_create_ip;
		//活动名称
		String act_name = "act_name=快关注【"+ mch_name +"】吧";
		//备注
		String remark = "remark=美味坚果和现金红包等你来，快关注【"+ mch_name +"】公众号吧！33";
		
		String key = "key=" + originalInfo.getBonus_pay_wechat_sign_key();

		
		String callUrl ;
		if(bonusNum >1) {
			callUrl = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_GROUP_BONUS_URL);
			total_num = "total_num=" + bonusNum;
			amt_type = "amt_type=ALL_RAND";
		}else {
			callUrl = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_BONUS_URL);

		}
		Object[] params={appid,amt_type,mch_id,nonce_str,send_name,total_amount,out_trade_no,total_num,
				wishing,client_ip,act_name,openid,remark,scene_id};
		String tempStr = concatParam(params);
		//签名,需要把key放在最后
		tempStr += "&"+key;
		String sign = "sign="+MD5Util.encode16(tempStr,"UTF-8").toUpperCase();
		
		Object[] newParam = ArrayUtils.addAll(params, new String[]{sign});
		String xml = convertToXML(newParam);
		
		
		long time = System.currentTimeMillis();
		try {
			//调用微信的红包接口
			CloseableHttpClient client = createWechatCertPostClient(originalInfo,true);
			String response = HttpUtil.post(client,callUrl, xml);
			WechatPayResultBean resultBean = parseWechatBonusResult(response);
			log.info("调用微信的红包接口 cost:"+ (System.currentTimeMillis() - time) +", mch_billno:"+mch_billno);
			if(resultBean.getResult_code() != null){
				//保存接口记录
				WechatSendRedPackLogBean log = new WechatSendRedPackLogBean();
				log.setAct_name(act_name);
				log.setOrder_code(order_code);
				log.setMch_billno(mch_billno);
				log.setRe_openid(openId);
				log.setSeq(seq);
				log.setTotal_amount(Integer.parseInt(resultBean.getCash_fee()));
				log.setTotal_num(bonusNum);
				log.setReturn_msg(resultBean.getReturn_msg());
				log.setSend_listid(resultBean.getTransaction_id());
				log.setSend_time(DateUtil.getNowDateStr());
				log.setReq_xml(xml);
				log.setRes_xml(response);
				if("SUCCESS".equalsIgnoreCase(resultBean.getResult_code())){
					log.setStatus(WechatSendRedPackLogBean.STATUS_SUCCESS);
					flag = true;
				}else{
					log.setStatus(WechatSendRedPackLogBean.STATUS_FAILD);
				}
				this.createWechatPayBonusLog(log);
				
				
				//返回界面需要支付的信息
//				Struts2Utils.renderJson(data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			
		}
		return flag;
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
