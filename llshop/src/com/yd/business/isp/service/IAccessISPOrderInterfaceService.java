/**
 * 
 */
package com.yd.business.isp.service;

import java.util.List;

import com.yd.business.isp.bean.DaHanSanTongInterfaceBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.JieTuoInterfaceBean;
import com.yd.business.isp.bean.NanJingShengShiInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.isp.bean.YunZhangTongInterfaceBean;
import com.yd.business.isp.bean.ZhuoweiInterfaceBean;
import com.yd.business.order.exception.ISPException;

/**
 * @author ice
 *
 */
public interface IAccessISPOrderInterfaceService {
	
	@Deprecated
	ISPInterfaceBean accessISPOrderInterface(String order_code, String phone, Integer productId);

	void createInterfaceLog(ISPInterfaceBean bean) throws ISPException;

	ISPInterfaceBean accessISPOrderInterface(int channel_id, String order_code, String phone, Integer productId,
			String server_name);

	YunZhangTongInterfaceBean handleYunZhangTongCallBack(YunZhangTongInterfaceBean bean);

	void createISPCallbackLog(String phone, String order_code, String method_name, Integer status, String remark,
			String request_content, String response_content);

	DaHanSanTongInterfaceBean handleDaHanSanTongCallBack(DaHanSanTongInterfaceBean bean);
	
	WanLiuInterfaceBean handleWanLiuKeJiCallBack(WanLiuInterfaceBean bean);

	NanJingShengShiInterfaceBean handleNanJingShengshiCallBack(NanJingShengShiInterfaceBean bean);

	JieTuoInterfaceBean handleJieTuoCallBack(JieTuoInterfaceBean bean);

	List<ISPInterfaceBean> queryInterfaceList(ISPInterfaceBean bean);

	ZhuoweiInterfaceBean handleZhuoWeiCallBack(ZhuoweiInterfaceBean bean);
	
	

}
