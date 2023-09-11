package com.yd.business.wechat.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialDeliveryLogBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatMenuBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatTemplateMsgBean;
import com.yd.business.wechat.bean.WechatWaitSendBean;

public interface IWechatDao {

	void saveUserEvent(BaseMessage message);

	void saveServerEvent(BaseMessage message);

	List<Map<String, Object>> queryUnSendServerEvent(String openId, String startTime, String endTime);

	void createWechatMaterial(WechatMaterialBean bean);

	WechatMaterialBean findWechatMaterialById(int id);

	WechatMaterialBean findWechatMaterial(WechatMaterialBean bean);

	List<WechatMaterialBean> queryWechatMaterial(WechatMaterialBean bean);

	List<WechatWaitSendBean> queryWaitSendMessage(WechatWaitSendBean bean);

	void updateWaitSendMessage(WechatWaitSendBean bean);

	List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean);

	void updateWechatOriginalInfo(WechatOriginalInfoBean bean);

	List<WechatOriginalInfoBean> queryToOriginalInfoByFrom(String fromOriginalid);
	
	List<WechatMaterialBean> queryDistinctWechatMaterialParentIdByBean(WechatMaterialBean bean);
	
	List<WechatMaterialBean> queryWechatMaterialByMediaList(List<String> list);
	
	List<WechatMaterialBean> queryParentWechatMaterialByMediaList(List<String> list);
	
	List<WechatMaterialRelationBean> queryWechatMaterialRelationByMainMaterial(WechatMaterialRelationBean bean);
	
	void saveWechatMaterialRelation(WechatMaterialRelationBean bean);
	
	void insertwechatMaterialDeliveryLog(WechatMaterialDeliveryLogBean bean);

	void deleteWechatMaterialByType(String type, String originalid);

	void deleteWechatMaterialRelation(WechatMaterialRelationBean bean);

	List<WechatTemplateMsgBean> queryWechatTemplateMsg(WechatTemplateMsgBean bean);
	
	void insertWechatMenuBean(WechatMenuBean bean);
	
	void deleteWechatMenuBean(WechatMenuBean bean);
	
	List<WechatMenuBean> queryWechatMenuBean(WechatMenuBean bean);
	
	public WechatMenuBean createOrUpdateWechatMenuBean(WechatMenuBean bean);
}
