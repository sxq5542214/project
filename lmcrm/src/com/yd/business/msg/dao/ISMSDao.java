/**
 * 
 */
package com.yd.business.msg.dao;

import java.util.List;

import com.yd.business.msg.bean.SmsTxSendInfoLogBean;
import com.yd.business.msg.bean.SmsTxSendPhoneLogBean;

/**
 * @author ice
 *
 */
public interface ISMSDao {

	void saveTxSendInfoLog(SmsTxSendInfoLogBean bean);

	List<SmsTxSendInfoLogBean> queryTxSendInfoLogList(SmsTxSendInfoLogBean bean);

	void saveTxSendPhoneLog(SmsTxSendPhoneLogBean bean);

	List<SmsTxSendPhoneLogBean> queryTxSendPhoneLogList(SmsTxSendPhoneLogBean bean);

}
