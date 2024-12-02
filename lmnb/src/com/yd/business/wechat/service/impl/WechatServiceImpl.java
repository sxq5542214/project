/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.json.JSONObject;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.constant.AttributeConstant;
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
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;

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
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(originalid);
		String ticket_value = originalInfo.getJsapi_ticket();
		return ticket_value;
	}
	private String queryJsapiTicket(String originalid) {
		String url = getJsapiUrl(originalid);
		String result;
		String ticket_value="";
		try {
			result = HttpUtil.get(url);
			JSONObject jso = new JSONObject(result);
			ticket_value = jso.getString("ticket");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return ticket_value;
	}
	private String getJsapiUrl(String originalid){
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(originalid);
		String access_token = originalInfo.getAccess_token();
		String url = server_url+ WechatConstant.JSAPI_TICKET_URL +"access_token="+access_token+"&type=jsapi";
		return url;
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
	public String getOpenId(String code,String originalid) {

		String openId = null;
		
		if(StringUtil.isNull(code)){
			return openId;
		}
		
		String url =getOauthUrl(code,originalid);
		
		try {
			String result = HttpUtil.get(url);
			JSONObject jso = new JSONObject(result);
			int errcode = jso.optInt("errcode",-9999);
			if(errcode == 40163){ // 返回code been used，代表已用过,从request中找
				log.error(code + " wechat返回code been used，代表已用过,从request中找");
			}else{
				openId = jso.getString("openid");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		
		
		return openId;
	}
	private String getOauthUrl(String code,String originalid){
		String oauthUrl =configAttributeService.getValueByCode(AttributeConstant.CODE_OAUTH);
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(originalid);
		String appid = originalInfo.getAppid();
		String secret = originalInfo.getSecret();
		String url = oauthUrl+"access_token?appid="+appid+"&secret="+secret
				+"&code="+code+"&grant_type=authorization_code";
		return url;
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
