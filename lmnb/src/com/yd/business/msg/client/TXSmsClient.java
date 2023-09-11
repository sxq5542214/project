package com.yd.business.msg.client;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.yd.basic.framework.context.BaseContext;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;

public class TXSmsClient
{
	static TXSmsClient self ;
	static SmsClient client = null;
	private static String appid;
	private static String signName;
	private static IConfigAttributeService configAttributeService;
	private TXSmsClient(String secretId,String secretKey) {
		 // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(secretId, secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        client = new SmsClient(cred, "ap-nanjing", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
      
	}
	
	public static TXSmsClient getInstance() {
			if(self == null) {
				configAttributeService = (IConfigAttributeService) BaseContext.getBean("configAttributeService");
				String secretId =  configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_TX_SECRETID) ;
				String secretKey = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_TX_SECRETKEY) ;
				appid = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_TX_APPID ) ;
				signName = configAttributeService.getValueByCode(AttributeConstant.CODE_SMS_TX_SIGNNAME) ;
				
				
				self = new TXSmsClient(secretId,secretKey);
			}
			return self;
	}
	
	
	/**
	 * 发送短信,使用默认的停水通知模板
	 * @param phones
	 * @param params
	 * @return
	 * @throws TencentCloudSDKException
	 */
	public SendSmsResponse callSmsSend(String[] phones,String[] params,String tempalteId) throws TencentCloudSDKException {
         return callSmsSend(phones, params, appid, signName , tempalteId);
	}
	/**
	 * 发送短信，指定应用、签名、模板
	 * @param phones
	 * @param params
	 * @param appid
	 * @param signName
	 * @param tempalteId
	 * @return
	 * @throws TencentCloudSDKException
	 */
	private  SendSmsResponse callSmsSend(String[] phones,String[] params ,String appid,String signName,String tempalteId) throws TencentCloudSDKException {
		 SendSmsRequest req = new SendSmsRequest();

         req.setSmsSdkAppId(appid);
         req.setSignName(signName);
         req.setTemplateId(tempalteId);

         req.setPhoneNumberSet(phones);
         req.setTemplateParamSet(params);

         // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
         SendSmsResponse resp = client.SendSms(req);
         // 输出json格式的字符串回包
         return resp;
	}
	
    public static void main(String [] args) {
        try{
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"15955107747"};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId("1400665485");
            req.setSignName("龙马供水");
            req.setTemplateId("1372643");

            String[] templateParamSet1 = {"测试1", "测试2", "测试3"};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}