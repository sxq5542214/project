/**
 * 
 */
package com.yd.business.isp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
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
import com.yd.business.isp.dao.IISPInterfaceLogDao;

/**
 * @author ice
 *
 */
@Repository("ispInterfaceLogDao")
public class ISPInterfaceLogDaoImpl extends BaseDao implements IISPInterfaceLogDao {
	private static final String NAMESPACE = "ispInterfaceLog.";
	
	/**
	 * 保存接口日志
	 * @param bean
	 */
	@Override
	public void createYDInterfaceLog(YDInterfaceBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createYDInterfaceLog", bean);
	}

	@Override
	public void createDXInterfaceLog(DXInterfaceBean bean) {
		
		sqlSessionTemplate.insert(NAMESPACE+"createDXInterfaceLog", bean);
	}
	
	@Override
	public void createDX_FuYangInterfaceLog(DXInterfaceBean bean) {
		
		sqlSessionTemplate.insert(NAMESPACE+"createDX_FuYangInterfaceLog", bean);
	}
	
	@Override
	public void createYunZhangTongInterfaceLog(YunZhangTongInterfaceBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createYunZhangTongInterfaceLog", bean);
	}
	
	@Override
	public List<YunZhangTongInterfaceBean> queryYunZhangTongInterfaceLog(YunZhangTongInterfaceBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "findYunZhangTongInterfaceLog", bean);
	}

	@Override
	public void createISPCallbackLog(ISPCallbackLogBean bean) {
		
		sqlSessionTemplate.insert(NAMESPACE + "createISPCallbackLog", bean);
	}

	@Override
	public void createDaHanSanTongInterfaceLog(DaHanSanTongInterfaceBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"createDaHanSanTongInterfaceLog", bean);
	}
	
	@Override
	public DaHanSanTongInterfaceBean findDaHanSanTongInterfaceByClientOrderId(String clientOrderId){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findDaHanSanTongInterfaceByClientOrderId", clientOrderId);
	}

	@Override
	public void createWanLiuKeJiInterfaceLog(WanLiuInterfaceBean bean) {
		sqlSessionTemplate.insert(NAMESPACE+"createWanLiuKeJiInterfaceLog", bean);
	}

	@Override
	public List<WanLiuInterfaceBean> findWanLiuKeJiInterfaceByReq_sn(String req_sn) {
		return sqlSessionTemplate.selectList(NAMESPACE+"findWanLiuKeJiInterfaceByClientOrderId", req_sn);
	}
	
	@Override
	public List<ISPInterfaceBean> queryInterfaceLog(String order_code,Integer status,String table_name){
		ISPInterfaceBean bean = new ISPInterfaceBean();
		bean.setOrder_code(order_code);
		bean.setStatus(status);
		bean.setTable_name(table_name);
		return sqlSessionTemplate.selectList(NAMESPACE +"queryInterfaceLog", bean);
	}

	@Override
	public void createNanJingShengShiInterfaceLog(NanJingShengShiInterfaceBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "createNanJingShengShiInterfaceLog", bean);
		
	}
	@Override
	public void updateThirdPartyCallBackInfo(ISPInterfaceBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateThirdPartyCallBackInfo", bean);
	}

	@Override
	public void createJieTuoInterfaceLog(JieTuoInterfaceBean bean) {
		sqlSessionTemplate.insert(NAMESPACE +"createJieTuoInterfaceLog", bean);
	}
	
	@Override
	public void createZhuoWeiInterfaceLog(ZhuoweiInterfaceBean bean){
		sqlSessionTemplate.insert(NAMESPACE +"createZhuoWeiInterfaceLog", bean);
	}

	@Override
	public List<ISPInterfaceBean> queryInterfaceList(ISPInterfaceBean bean) {
		
		return sqlSessionTemplate.selectList(NAMESPACE + "queryInterfaceList", bean);
		
	}
	
}
