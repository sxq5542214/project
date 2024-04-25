/**
 * 
 */
package com.yd.business.msg.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.msg.bean.SMSSendLogBean;
import com.yd.business.msg.bean.SmsTxSendInfoLogBean;
import com.yd.business.msg.bean.SmsTxSendPhoneLogBean;
import com.yd.business.msg.client.JXTSmsClient;
import com.yd.business.msg.client.TXSmsClient;
import com.yd.business.msg.dao.ISMSDao;
import com.yd.business.msg.service.ISMSService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.ITaskSchedulerService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LlSmsSendlogModelMapper;
import com.yd.iotbusiness.mapper.model.LlSmsSendlogModel;
import com.yd.iotbusiness.mapper.model.LlSmsSendlogModelExample;
import com.yd.iotbusiness.mapper.model.LlSmsSendlogModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.iotbusiness.mapper.model.LmUserModelExample;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("smsService")
public class SMSServiceImpl extends BaseService implements ISMSService {
	
	private static String SMS_CHANNEL_JXT = "吉讯通";
	
	@Autowired
	private ISMSDao smsDao;
	@Autowired
	private LlSmsSendlogModelMapper smsSendlogModelMapper;
	
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IConfigAttributeService configAttributeService;
	@Autowired
	private ITaskSchedulerService taskSchedulerService;
	
	

	/**
	 * 保存吉讯通接口的短信请求
	 * @param phones
	 * @param params
	 * @return
	 */
	@Override
	public void saveSMSSendRequest(LmUserModel user,Integer meterid, String content,LmOperatorModel op,String sendType) {

		try {
			
				
				LlSmsSendlogModel sms = new LlSmsSendlogModel();
				sms.setCreateTime(DateUtil.getNowDateStrSSS());
				sms.setContent(content);
				sms.setOperatorid(op.getId().toString());
				sms.setPhonenumber(user.getPhone());
				sms.setSmsChannel(SMS_CHANNEL_JXT);
				sms.setUserid(user.getId());
				sms.setMeterid(meterid);
				sms.setSendtype(sendType);
				sms.setStatus(SMSSendLogBean.STATUS_WAIT);
				smsSendlogModelMapper.insertSelective(sms);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
	}

	/**
	 * 直接发送吉讯通接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	@Override
	public String sendJXTsms(List<LmUserModel> userList,String content,LmOperatorModel op,String sendType){

		try {
			
			JXTSmsClient smsClient = JXTSmsClient.getInstance();
			for(LmUserModel user : userList) {
				
				// 根据号码启动线程发送短信
				taskSchedulerService.startThreadByPool(new BaseRunable() {
					
					@Override
					public void runMethod() throws Exception {
						String response = smsClient.postSMS(user.getPhone(), content);
						
						LlSmsSendlogModel sms = new LlSmsSendlogModel();
						sms.setCreateTime(DateUtil.getNowDateStrSSS());
						sms.setContent(content);
						sms.setOperatorid(op.getId().toString());
						sms.setPhonenumber(user.getPhone());
						sms.setResult(response);
						sms.setSendtime(DateUtil.getNowDateStrSSS());
						sms.setSmsChannel(SMS_CHANNEL_JXT);
						sms.setUserid(user.getId());
						sms.setSendtype(sendType);
						try {
							if("000".equals(response.substring(0, 3))) {
								sms.setStatus(SMSSendLogBean.STATUS_SUCCESS);
							}else {
								sms.setStatus(SMSSendLogBean.STATUS_FAIL);
							}
						}catch (Exception e) {
							sms.setStatus(e.toString());
							log.error(e,e);
						}
						smsSendlogModelMapper.insertSelective(sms);
					}
				});
			}
			
		} catch (Exception e) {
			log.error(e, e);
			return "error";
		}
		return "success";
		
		
	}
	/**
	 * 直接发送吉讯通接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	@Override
	public String sendJXTsms(LmUserModel user,Integer meterid,String content,LmOperatorModel op,String sendType){

		try {
			
			JXTSmsClient smsClient = JXTSmsClient.getInstance();
				
			// 根据号码启动线程发送短信
			taskSchedulerService.startThreadByPool(new BaseRunable() {
				
				@Override
				public void runMethod() throws Exception {
					String response = smsClient.postSMS(user.getPhone(), content);
					
					LlSmsSendlogModel sms = new LlSmsSendlogModel();
					sms.setCreateTime(DateUtil.getNowDateStrSSS());
					sms.setContent(content);
					sms.setOperatorid(op.getId().toString());
					sms.setPhonenumber(user.getPhone());
					sms.setResult(response);
					sms.setSendtime(DateUtil.getNowDateStrSSS());
					sms.setSmsChannel(SMS_CHANNEL_JXT);
					sms.setUserid(user.getId());
					sms.setMeterid(meterid);
					sms.setSendtype(sendType);
					try {
						if("000".equals(response.substring(0, 3))) {
							sms.setStatus(SMSSendLogBean.STATUS_SUCCESS);
						}else {
							sms.setStatus(SMSSendLogBean.STATUS_FAIL);
						}
					}catch (Exception e) {
						sms.setStatus(e.toString());
						log.error(e,e);
					}
					smsSendlogModelMapper.insertSelective(sms);
				}
			});
			
		} catch (Exception e) {
			log.error(e, e);
			return "error";
		}
		return "success";
		
		
	}
	/**
	 * 根据待发送状态发送吉讯通接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	@Override
	public String sendJXTsmsByWait(){
		
		try {
			//查询所有待发送
			LlSmsSendlogModelExample example = new LlSmsSendlogModelExample();
			Criteria cri = example.createCriteria();
			cri.andStatusEqualTo(SMSSendLogBean.STATUS_WAIT);
			
			List<LlSmsSendlogModel> list = smsSendlogModelMapper.selectByExample(example );
			
			JXTSmsClient smsClient = JXTSmsClient.getInstance();
			for(LlSmsSendlogModel sms : list) {
				
				// 根据号码启动线程发送短信
				taskSchedulerService.startThreadByPool(new BaseRunable() {
					
					@Override
					public void runMethod() throws Exception {
						String response = smsClient.postSMS(sms.getPhonenumber(), sms.getContent());
						try {
							if("000".equals(response.substring(0, 3))) {
								sms.setStatus(SMSSendLogBean.STATUS_SUCCESS);
							}else {
								sms.setStatus(SMSSendLogBean.STATUS_FAIL);
							}
						}catch (Exception e) {
							sms.setStatus(e.toString());
							log.error(e,e);
						}
						sms.setResult(response);
						sms.setSendtime(DateUtil.getNowDateStrSSS());
						smsSendlogModelMapper.updateByPrimaryKeySelective(sms);
						
					}
				});
			}
			
		} catch (Exception e) {
			log.error(e, e);
			return "error";
		}
		return "success";
	}
	

	/**
	 * 发送腾讯接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	@Override
	public String sendTXsms(String[] phones,String[] params,String templateId,String templateStr,String smsName,OperatorBean op){
		
		try {
			
			ConfigAttributeBean attr = configAttributeService.getAttributeByCode(AttributeConstant.CODE_SMS_TX_TEMPLATE_STOP);
			String templateContent = attr.getValue();
			int paramNum = Integer.parseInt(attr.getOriginal_name());
			for(int i = 1 ; i <= paramNum ; i++) {
				templateContent = templateContent.replaceAll("\\{"+i+"\\}", params[i-1]);
			}
			
			SendSmsResponse response = TXSmsClient.getInstance().callSmsSend(phones, params,templateId) ;
			int successNum = 0;
			int errorNum = 0;
			for( SendStatus res : response.getSendStatusSet() ) {
				if("Ok".equalsIgnoreCase(res.getCode())) {
					successNum++;
				}else {
					errorNum++;
				}
				
				saveSendTxSmsPhoneLog(res.getMessage(), res.getPhoneNumber(), response.getRequestId(), res.getSerialNo(), res.getFee().intValue(), res.getCode());
			}
			saveSendTxSmsInfoLog(Integer.parseInt(templateId), smsName, templateContent, op.getO_name(), response.getRequestId(), response.getSendStatusSet().length, successNum, errorNum);
			
			
			
		} catch (TencentCloudSDKException e) {
			log.error(e, e);
			return "error";
		}
		return "success";
	}
	
	/**
	 * 发送腾讯接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	@Override
	public String sendTXsms(String addressIds,String[] params,String templateId,String templateStr,String smsName,OperatorBean op){
		
		if(addressIds.endsWith(",")) {
			addressIds = addressIds.substring(0, addressIds.length() -1);
		}
		List<UserInfoBean> listUser = userInfoService.queryUserListByAddressIds(addressIds);
		List<String> listPhone = new ArrayList<String>();
		for(int i = 1 ; i <= listUser.size(); i++) {
			String phone = listUser.get(i-1).getU_phone();
			if( StringUtil.isNotNull(phone) ) {
				listPhone.add(phone);
			}
		}
		String[] phones = listPhone.toArray(new String[0]);
		
		return sendTXsms(phones, params, templateId, templateStr, smsName, op);
	}
	/**
	 * 保存腾讯短信发送信息
	 * @param bean
	 */
	public void saveSendTxSmsInfoLog(Integer templateId ,String templateName,String send_content,String send_operator,String request_id,int send_count,int success_count,int error_count) {
		SmsTxSendInfoLogBean bean = new SmsTxSendInfoLogBean();
		bean.setTemplateId(templateId);
		bean.setTemplateName(templateName);
		bean.setError_count(error_count);
		bean.setRequest_id(request_id);
		bean.setSend_content(send_content);
		bean.setSend_count(send_count);
		bean.setSend_time(DateUtil.getNowDateStr());
		bean.setSuccess_count(success_count);
		bean.setSend_operator(send_operator);
		
		smsDao.saveTxSendInfoLog(bean );
	}
	
	/**
	 * 保存短信号码发送清单
	 * @param message
	 * @param phone
	 * @param request_id
	 * @param serialNo
	 * @param fee
	 * @param code
	 */
	public void saveSendTxSmsPhoneLog(String message, String phone, String request_id, String serialNo, Integer fee, String code) {
		SmsTxSendPhoneLogBean bean = new SmsTxSendPhoneLogBean();
		bean.setMessage(message);
		bean.setPhone(phone);
		bean.setRequest_id(request_id);
		bean.setSerialNo(serialNo);
		bean.setFee(fee);
		bean.setCode(code);
		smsDao.saveTxSendPhoneLog(bean );
	}
	
	@Override
	public List<ConfigAttributeBean> querySmsTemplateList(){
		
		ConfigAttributeBean template = configAttributeService.getAttributeByCode(AttributeConstant.CODE_SMS_TX_TEMPLATE_STOP);
		List<ConfigAttributeBean> list = new ArrayList<ConfigAttributeBean>();
		list.add(template);
		
		return list;
	}
	
	@Override
	public List<LlSmsSendlogModel> querySMSSendLogList(Integer userid,Integer meterid,String sendtype,String status,String createTime){

		//查询所有待发送
		LlSmsSendlogModelExample example = new LlSmsSendlogModelExample();
		Criteria cri = example.createCriteria();
		cri.andUseridEqualTo(userid);
		cri.andMeteridEqualTo(meterid);
		cri.andSendtypeEqualTo(sendtype);
		cri.andStatusEqualTo(status);
		cri.andCreateTimeLike(createTime);
		
		return smsSendlogModelMapper.selectByExample(example);
	}

	@Override
	public List<SmsTxSendInfoLogBean> querySmsSendInfoList(Long companyId){
		
		SmsTxSendInfoLogBean bean = new SmsTxSendInfoLogBean();
		bean.setOrderby("order by id desc ");
		List<SmsTxSendInfoLogBean> list = smsDao.queryTxSendInfoLogList(bean );
		return list;
	}

	@Override
	public List<SmsTxSendPhoneLogBean> querySmsSendPhoneList(String requestId){
		

		SmsTxSendPhoneLogBean bean = new SmsTxSendPhoneLogBean();
		bean.setRequest_id(requestId);
		bean.setOrderby("order by fee ");
		List<SmsTxSendPhoneLogBean> list = smsDao.queryTxSendPhoneLogList(bean );
		
		return list;
	}
	
	
	
	public static void main(String[] args) {
		
	}
	
}
