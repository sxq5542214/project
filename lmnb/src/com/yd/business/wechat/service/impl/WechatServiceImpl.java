/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.SignServerBean;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatTemplateMsgBean;
import com.yd.business.wechat.bean.WechatWebAuthBean;
import com.yd.business.wechat.dao.IWechatDao;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;

/**
 * @author ice
 * 
 */
@Service("wechatService")
public class WechatServiceImpl extends BaseService implements IWechatService {
	@Resource
	protected IWechatDao wechatDao;
	@Resource
	protected IConfigAttributeService configAttributeService;
	@Resource
	protected IConfigCruxService configCruxService;
	@Resource
	protected IUserWechatService userWechatService; 
	@Resource
	protected ThreadPoolTaskExecutor taskExecutor;
	@Resource
	protected IWechatOriginalInfoService wechatOriginalInfoService;
	@Override
	public BaseMessage handlerWechatMessage(Document doc) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseMessage sendMessageToUser(BaseMessage base) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void execSendMessageToUserList() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendMessageByToUserAll(String originalIds, String mediaids) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendLotteryResultToUser(int lotteryId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void createWechatUser(String weixin_id, String parentId, Integer senceType, Integer senceId,
			String originalid) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public WechatMaterialBean findWechatMaterial(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<WechatMaterialBean> queryWechatMaterial(WechatMaterialBean bean) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List queryWechatMaterialBySucaiType(String sucaitype) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void syncWechatMaterial(String originalid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getJsapiTicket(String originalid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String validateServerSign(SignServerBean bean, String originalid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateWechatJsApiTicket(String originalid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateWechatAccessToken(String originalid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getOpenId(String code, String originalid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PageinationData showWechatMaterialInfoForDelivery(List<WechatOriginalInfoBean> originalList,
			WechatMaterialBean bean, Object start_times, Object end_time) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<WechatMaterialRelationBean> deliveryWechatMaterialInfo(String media_ids, String originalids,
			String action) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByBean(WechatMaterialBean material,
			WechatMaterialRelationBean relBean, String originalid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByBean(WechatMaterialRelationBean relBean) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public WechatTemplateMsgBean queryWechatTemplateMsg(String originalid, String code) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getOpenidByWechatCode(String code, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public WechatWebAuthBean getOpenIdByWebAuthCode(String code, String originalid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
