package com.yd.business.wechat.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.user.bean.UserBWCBean;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatExtendBean;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.SignServerBean;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatTemplateMsgBean;

public interface IWechatService {

	BaseMessage handlerWechatMessage(Document doc) throws Exception;

	BaseMessage sendMessageToUser(BaseMessage base);

	void execSendMessageToUserList();
	
	void sendMessageByToUserAll(String originalIds,String mediaids);

	void sendLotteryResultToUser(int lotteryId);

	UserQrCodeBean updateUserTicket(String weixin_id, int senceCode, int senceId) throws IOException;

	void createWechatUser(String weixin_id, String parentId, Integer senceType, Integer senceId, String originalid) throws Exception;

	WechatMaterialBean findWechatMaterial(int id);

	List<WechatMaterialBean> queryWechatMaterial(WechatMaterialBean bean);
	
	List queryWechatMaterialBySucaiType(String sucaitype);

	UserWechatExtendBean getWechatUserInfo(String openId, String originalid) throws Exception;

	void syncWechatMaterial(String originalid);

	String getJsapiTicket(String originalid);

	String validateServerSign(SignServerBean bean, String originalid);

	void updateWechatJsApiTicket(String originalid);

	void updateWechatAccessToken(String originalid);

	String getOpenId(String code, String originalid);

	List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean);

	WechatOriginalInfoBean findWechatOriginalInfoByServer(String server_domain);

	WechatOriginalInfoBean findWechatOriginalInfoByOriginalid(String originalid);

	PageinationData showWechatMaterialInfoForDelivery(List<WechatOriginalInfoBean> originalList,WechatMaterialBean bean,Object start_times,Object end_time);
	
	List<WechatMaterialRelationBean> deliveryWechatMaterialInfo(String media_ids,String originalids,String action);

	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByBean(WechatMaterialBean material,WechatMaterialRelationBean relBean,String originalid);
	
	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByBean(WechatMaterialRelationBean relBean);

	WechatTemplateMsgBean queryWechatTemplateMsg(String originalid, String code);

}
