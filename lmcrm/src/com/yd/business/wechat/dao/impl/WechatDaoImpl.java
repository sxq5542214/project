/**
 * 
 */
package com.yd.business.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialDeliveryLogBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatMenuBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatTemplateMsgBean;
import com.yd.business.wechat.bean.WechatWaitSendBean;
import com.yd.business.wechat.dao.IWechatDao;
import com.yd.util.BeanUtil;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Repository("wechatDao")
public class WechatDaoImpl extends BaseDao implements IWechatDao {
	public static final String NAMESPACE = "wechat.";
	
//	public void updateWechatToken(TokenBean bean){
//		sqlSessionTemplate.update(NAMESPACE, bean);
//	}
	
	@Override
	public void saveUserEvent(BaseMessage message){
		
		Map<String,Object> m = BeanUtil.transBean2Map(message);
		sqlSessionTemplate.insert(NAMESPACE+"saveUserEvent",m);
		
	}
	
	@Override
	public void saveServerEvent(BaseMessage message){

		Map<String,Object> m = BeanUtil.transBean2Map(message);
		sqlSessionTemplate.insert(NAMESPACE+"saveServerEvent",m);
	}
	
	@Override
	public List<WechatWaitSendBean> queryWaitSendMessage(WechatWaitSendBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWaitSendMessage", bean);
	}
	
	@Override
	public void updateWaitSendMessage(WechatWaitSendBean bean){
		sqlSessionTemplate.update(NAMESPACE + "updateWaitSendMessage", bean);
	}

	@Override
	public List<Map<String,Object>> queryUnSendServerEvent(String openId,String startTime,String endTime){
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("openId", openId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		
		return sqlSessionTemplate.selectList(NAMESPACE+"queryUnSendServerEvent", map, new RowBounds(0, 3));
	}
	
	@Override
	public void createWechatMaterial(WechatMaterialBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createWechatMaterial", bean);
	}
	
	@Override
	public WechatMaterialBean findWechatMaterialById(int id){
		WechatMaterialBean bean = new WechatMaterialBean();
		bean.setId(id);
		
		return findWechatMaterial(bean);
		
	}
	
	@Override
	public WechatMaterialBean findWechatMaterial(WechatMaterialBean bean){
		List<WechatMaterialBean> list = queryWechatMaterial(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<WechatMaterialBean> queryWechatMaterial(WechatMaterialBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWechatMaterial", bean);
	}
	
	@Override
	public void deleteWechatMaterialByType(String type,String originalid){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("originalid", originalid);
		sqlSessionTemplate.delete(NAMESPACE +"deleteWechatMaterialByType", map);
	}
	
	@Override
	public List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE +"queryWechatOriginalInfo", bean);
	}
	
	@Override
	public List<WechatTemplateMsgBean> queryWechatTemplateMsg(WechatTemplateMsgBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryWechatTemplateMsg", bean);
	}
	
	@Override
	public List<WechatOriginalInfoBean> queryToOriginalInfoByFrom(String fromOriginalid){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryToOriginalInfoByFrom", fromOriginalid);
	}
	
	@Override
	public void updateWechatOriginalInfo(WechatOriginalInfoBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateWechatOriginalInfo", bean);
	}

	@Override
	public List<WechatMaterialBean> queryDistinctWechatMaterialParentIdByBean(WechatMaterialBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryDistinctWechatMaterialParentIdByBean", bean);
	}

	@Override
	public List<WechatMaterialBean> queryWechatMaterialByMediaList(List<String> list) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWechatMaterialByMediaList", list);
	}
	
	@Override
	public List<WechatMaterialBean> queryParentWechatMaterialByMediaList(List<String> list) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryParentWechatMaterialByMediaList", list);
	}

	@Override
	public List<WechatMaterialRelationBean> queryWechatMaterialRelationByMainMaterial(WechatMaterialRelationBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWechatMaterialRelationByMainMaterial", bean);

	}

	@Override
	public void saveWechatMaterialRelation(WechatMaterialRelationBean bean) {
		if(StringUtil.isNull(bean.getId())){
			sqlSessionTemplate.insert(NAMESPACE + "insertWechatMaterialRelation",bean);
		}else{
			sqlSessionTemplate.update(NAMESPACE + "updateWechatMaterialRelation",bean);
		}
	}

	@Override
	public void insertwechatMaterialDeliveryLog(WechatMaterialDeliveryLogBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "insertwechatMaterialDeliveryLog",bean);
		
	}

	@Override
	public void deleteWechatMaterialRelation(WechatMaterialRelationBean bean) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteWechatMaterialRelation",bean);
		
	}

	@Override
	public void insertWechatMenuBean(WechatMenuBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "insertWechatMenuBean",bean);
	}
	
	@Override
	public void deleteWechatMenuBean(WechatMenuBean bean) {
		if(!StringUtil.isNull(bean.getId())){
			sqlSessionTemplate.delete(NAMESPACE + "deleteWechatMenuBeanById",bean);
		}else{
			sqlSessionTemplate.delete(NAMESPACE + "deleteWechatMenuBean",bean);
		}
	}
	
	@Override
	public List<WechatMenuBean> queryWechatMenuBean(WechatMenuBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryWechatMenuBean",bean);
	}
	
	@Override
	public WechatMenuBean createOrUpdateWechatMenuBean(WechatMenuBean bean) {
		if(StringUtil.isNull(bean.getId())){
			sqlSessionTemplate.insert(NAMESPACE + "insertWechatMenuBean", bean);
		}else{
			bean.setModify_time(DateUtil.getNowDateStr());
			sqlSessionTemplate.update(NAMESPACE + "updateWechatMenuBean", bean);
		}
		return bean;
	}
}
