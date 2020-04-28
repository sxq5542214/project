package com.yd.business.sms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.sms.bean.SmsBean;
import com.yd.business.sms.bean.SmsCustomerBean;
import com.yd.business.sms.bean.SmsLogBean;
import com.yd.business.sms.bean.SmsWaitSendBean;
import com.yd.business.sms.dao.ISmsDao;
import com.yd.business.sms.service.ISmsCustomerService;
import com.yd.business.sms.service.ISmsLogService;
import com.yd.business.sms.service.ISmsService;
import com.yd.util.DateUtil;
import com.yd.util.JsonUtil;
import com.yd.util.StringUtil;
/**
 * 消息服务类
 * @author Zhang
 *
 */
@Service("smsService")
public class SmsServiceImpl extends BaseService implements ISmsService {

	@Resource
	private ISmsDao smsDao;
	@Resource
	private ISmsLogService smsLogService;
	@Resource
	private ISmsCustomerService smsCustomerService;
	@Resource
	private IAreaDataService areaDataService;
	@Resource
	private IConfigAttributeService configAttributeService;
	
	
	@Override
	public String sendAliMsg(String smsType,String smsFreeSignName,String recNum,String smsTemplateCode,String params) throws ApiException {
		// TODO Auto-generated method stub
		TaobaoClient client = new DefaultTaobaoClient(SmsBean.serverUrl, SmsBean.appKey, SmsBean.appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType(smsType);
		req.setSmsFreeSignName(smsFreeSignName);
		req.setRecNum(recNum);
		req.setSmsTemplateCode(smsTemplateCode);
		req.setSmsParamString(params);
		AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
		String result = response.getMsg();
		insertSmsLog(recNum, params, smsFreeSignName, smsTemplateCode,result);
		return result;
	}
	/**
	 * 添加短信日志
	 * @param phone
	 * @param content
	 * @param signname
	 * @param template_id
	 */
	private void insertSmsLog(String recNum,String params,String signname,String template_id,String result){
		String[] phones = recNum.split(",");
		String content = getContent(template_id, params);
		for (int i = 0; i < phones.length; i++) {
			SmsLogBean bean = new SmsLogBean();
			bean.setPhonenumber(phones[i]);
			bean.setContent(content);
			bean.setSendtime(DateUtil.getNowDateStr());
			bean.setSignname(signname);
			bean.setTemplate_id(template_id);
			bean.setResult(result);
			smsLogService.insertSmsLog(bean);
		}
	}
	/**
	 * 获取短信内容
	 * @param tempid
	 * @param params
	 * @return
	 */
	private String getContent(String tempid,String params){
		SmsBean sms = smsDao.findByTempId(tempid);
		JSONObject obj = new JSONObject(params);
		String patternString = "\\$\\{(" + StringUtils.join(obj.keySet(), "|") + ")\\}";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(sms.getContent());
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, StringUtil.toString(obj.get(matcher.group(1))));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	@Override
	public void insertSms(SmsBean bean) {
		// TODO Auto-generated method stub
		smsDao.insertSms(bean);
	}

	@Override
	public void deleteSms(SmsBean bean) {
		// TODO Auto-generated method stub
		smsDao.deleteSms(bean);
	}

	@Override
	public List<SmsBean> listSms(SmsBean bean) {
		// TODO Auto-generated method stub
		return smsDao.listSms(bean);
	}

	@Override
	public SmsBean findById(int id) {
		// TODO Auto-generated method stub
		return smsDao.findById(id);
	}
	@Override
	public SmsBean findByTempId(String id) {
		// TODO Auto-generated method stub
		return smsDao.findByTempId(id);
	}
	@Override
	public String sendSuccessMsgMyPhoneNumAndContent(String recNum, String wechatName,String productName,
			Integer customerid) {
		//目前只发移动的
		AreaDataBean ad = areaDataService.getAreaDataByPhone(recNum);
		
		if(ad != null && ad.getBrand().equals(AreaDataBean.BRAND_YD)){
		
			SmsCustomerBean cust = new SmsCustomerBean();
			cust.setOrderStatus(SmsCustomerBean.ORDERSTATUS_SUCCESS);
			cust.setEventType(SmsCustomerBean.EVENTTYPE_ORDER);
			SmsCustomerBean bean = null;
			if(customerid != null){ //如果有传客户ID，先查这个客户ID下的
				cust.setCustomerid(customerid);
				bean = smsCustomerService.querySmsCustomerBean(cust);
			}
			if(bean == null){//如果没有个性化的，就查询默认的
				cust.setCustomerid(CustomerBean.ID_DEFAULT) ;
				bean = smsCustomerService.querySmsCustomerBean(cust);
			}
			
			String remark_info = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_REMARK_INFO);
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("wechat_name", wechatName);
			map.put("product_name", productName);
			map.put("effect_time", "立即");
			map.put("remark_info", remark_info);
			map.put("query_code", "66 到 10086");

			String result = sendSMSAndSaveLog(recNum, map, bean);
			return result;
		}
		
		return null;
	}
	
	
	/**
	 * 用户订购失败的短信通知
	 */
	@Override
	public String sendFaildMsgByOrder(String recNum, String productName,String reason,
			Integer customerid) {
		//失败的所有都发
		SmsCustomerBean cust = new SmsCustomerBean();
		cust.setOrderStatus(SmsCustomerBean.ORDERSTATUS_FAILD);
		cust.setEventType(SmsCustomerBean.EVENTTYPE_ORDER);
		SmsCustomerBean bean = null;
		if(customerid != null){ //如果有传客户ID，先查这个客户ID下的
			cust.setCustomerid(customerid);
			bean = smsCustomerService.querySmsCustomerBean(cust);
		}
		if(bean == null){//如果没有个性化的，就查询默认的
			cust.setCustomerid(CustomerBean.ID_DEFAULT) ;
			bean = smsCustomerService.querySmsCustomerBean(cust);
		}
		
//		String remark_info = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_REMARK_INFO);
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("refund_method", "至公众号的支付账户中");
		map.put("product_name", productName);
//		map.put("remark_info", remark_info);
		map.put("reason", reason);
		
		String result = sendSMSAndSaveLog(recNum, map, bean);
		return result;
		
	}
	
	/**
	 * 发送参加活动失败的消息
	 */
	@Override
	public String sendFaildMsgByActivity(String recNum, String activity_name,String reason,
			Integer customerid) {
		//失败的所有都发
		SmsCustomerBean cust = new SmsCustomerBean();
		cust.setEventType(SmsCustomerBean.EVENTTYPE_ACTIVITY);
		cust.setOrderStatus(SmsCustomerBean.ORDERSTATUS_FAILD);
		SmsCustomerBean bean = null;
		if(customerid != null){ //如果有传客户ID，先查这个客户ID下的
			cust.setCustomerid(customerid);
			bean = smsCustomerService.querySmsCustomerBean(cust);
		}
		if(bean == null){//如果没有个性化的，就查询默认的
			cust.setCustomerid(CustomerBean.ID_DEFAULT) ;
			bean = smsCustomerService.querySmsCustomerBean(cust);
		}
		
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("activity_name", activity_name);
		map.put("reason", reason);
		
		String result = sendSMSAndSaveLog(recNum, map, bean);
		return result;
	}
	
	/**
	 * 发送短信和记录日志
	 * @param recNum
	 * @param map
	 * @param bean
	 * @return
	 */
	@Override
	public String sendSMSAndSaveLog(String recNum,Map<String,String> map,SmsCustomerBean bean){
		
		if(bean == null){
			return null;
		}

		String remark_info = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_REMARK_INFO);
		map.put("remark_info", remark_info);
		String params = new JSONObject(map).toString();
		return sendSMSAndSaveLog(recNum, params, bean);
	}

	/**
	 * 发送短信和记录日志
	 * @param recNum
	 * @param map
	 * @param bean
	 * @return
	 */
	private String sendSMSAndSaveLog(String recNum,String params,SmsCustomerBean bean){
		
		if(bean == null){
			return null;
		}

		TaobaoClient client = new DefaultTaobaoClient(SmsBean.serverUrl, SmsBean.appKey, SmsBean.appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

		String smsType = bean.getSmsType();
		String smsFreeSignName = bean.getSmsFreeSignName();
		String smsTemplateCode = bean.getSmsTemplateCode();
		
		req.setSmsType(smsType);
		req.setSmsFreeSignName(smsFreeSignName);
		req.setRecNum(recNum);
		req.setSmsTemplateCode(smsTemplateCode);
		
		req.setSmsParamString(params);
		AlibabaAliqinFcSmsNumSendResponse response;
		String result;
		try {
			response = client.execute(req);
			result = String.valueOf(response.getResult().getSuccess());
		} catch (Exception e) {
			log.error(e, e);
			result= e.getMessage();
		}
		insertSmsLog(recNum, params, smsFreeSignName, smsTemplateCode,result);
		return result;
	}
	
	/**
	 * 查询并执行待发送的短信
	 */
	@Override
	public void queryWaitAndSend(){
		
		SmsWaitSendBean condition = new SmsWaitSendBean();
		condition.setStatus(SmsWaitSendBean.STATUS_WAIT);
		condition.setNow_time(DateUtil.getNowDateStr());
		//查询所有待发送
		List<SmsWaitSendBean> list = smsDao.queryWaitSendList(condition );
		
		for(SmsWaitSendBean bean : list){
			try{
				SmsCustomerBean smsCustomer = smsCustomerService.querySmsCustomerById(bean.getSms_customer_id());
				
				String param = bean.getParam();
				sendSMSAndSaveLog(bean.getPhone(), param, smsCustomer);
				
				String time = DateUtil.getNowDateStr();
				bean.setSend_time(time);
				bean.setModify_time(time);
				bean.setStatus(SmsWaitSendBean.STATUS_SEND);
				smsDao.updateWaitSend(bean);
			}catch (Exception e) {
				log.error(e, e);
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
//		SmsCustomerBean bean = new SmsCustomerBean();

		
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("在线加油站");
		req.setRecNum("15955107747");
		req.setSmsTemplateCode("SMS_128617");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("wechat_name", "冰块_ICE");
		map.put("product_name", "移动100M");
		String params = new JSONObject(map).toString();
		req.setSmsParamString(params);
		AlibabaAliqinFcSmsNumSendResponse response;
		String result;
		try {
			TaobaoClient client = new DefaultTaobaoClient(SmsBean.serverUrl, SmsBean.appKey, SmsBean.appSecret);
			response = client.execute(req);
			result = String.valueOf(response.getResult().getSuccess());
		} catch (ApiException e) {
			result= e.getMessage();
		}
		System.out.println(result);
		
	}
}
