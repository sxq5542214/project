/**
 * 
 */
package com.yd.business.isp.dao;

import java.util.List;

import com.yd.business.isp.bean.DXInterfaceBean;
import com.yd.business.isp.bean.DaHanSanTongInterfaceBean;
import com.yd.business.isp.bean.ISPCallbackLogBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.JieTuoInterfaceBean;
import com.yd.business.isp.bean.NanJingShengShiInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.isp.bean.YDInterfaceBean;
import com.yd.business.isp.bean.YunZhangTongInterfaceBean;
import com.yd.business.isp.bean.ZhuoweiInterfaceBean;

/**
 * @author ice
 *
 */
public interface IISPInterfaceLogDao {

	void createYDInterfaceLog(YDInterfaceBean bean);

	void createDXInterfaceLog(DXInterfaceBean bean);

	void createDX_FuYangInterfaceLog(DXInterfaceBean bean);

	void createYunZhangTongInterfaceLog(YunZhangTongInterfaceBean bean);

	List<YunZhangTongInterfaceBean> queryYunZhangTongInterfaceLog(YunZhangTongInterfaceBean bean);

	void createISPCallbackLog(ISPCallbackLogBean bean);

	void createDaHanSanTongInterfaceLog(DaHanSanTongInterfaceBean bean);
	
	void createWanLiuKeJiInterfaceLog(WanLiuInterfaceBean bean);

	DaHanSanTongInterfaceBean findDaHanSanTongInterfaceByClientOrderId(String clientOrderId);
	
	List<WanLiuInterfaceBean> findWanLiuKeJiInterfaceByReq_sn(String req_sn);

	List<ISPInterfaceBean> queryInterfaceLog(String order_code, Integer status, String table_name);

	void createNanJingShengShiInterfaceLog(NanJingShengShiInterfaceBean bean);

	void updateThirdPartyCallBackInfo(ISPInterfaceBean bean);

	void createJieTuoInterfaceLog(JieTuoInterfaceBean bean);

	List<ISPInterfaceBean> queryInterfaceList(ISPInterfaceBean bean);

	void createZhuoWeiInterfaceLog(ZhuoweiInterfaceBean bean);

}
