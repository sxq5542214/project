/**
 * 
 */
package com.yd.business.msg.service;

import java.util.List;

import com.yd.business.msg.bean.SmsTxSendInfoLogBean;
import com.yd.business.msg.bean.SmsTxSendPhoneLogBean;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;

/**
 * @author ice
 *
 */
public interface ISMSService {

	List<ConfigAttributeBean> querySmsTemplateList();

	/**
	 * 发送腾讯接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	String sendTXsms(String[] phones, String[] params, String templateId, String templateContent, String smsName,
			OperatorBean op);

	List<SmsTxSendPhoneLogBean> querySmsSendPhoneList(String requestId);

	List<SmsTxSendInfoLogBean> querySmsSendInfoList(Long companyId);

	/**
	 * 发送腾讯接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	String sendTXsms(String addressIds, String[] params, String templateId, String templateStr, String smsName,
			OperatorBean op);


	/**
	 * 发送吉讯通接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	String sendJXTsms(List<LmUserModel> userList, String content, LmOperatorModel op, String sendType);

	/**
	 * 发送吉讯通接口的短信
	 * @param phones
	 * @param params
	 * @return
	 */
	String sendJXTsms(String[] phones, String content, LmOperatorModel op, String sendType);

}
