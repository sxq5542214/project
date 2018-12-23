/**
 * 
 */
package com.yd.business.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.user.bean.UserCommissionPointsBean;
import com.yd.business.user.bean.ConsumeTableBean;
import com.yd.business.user.bean.UserBWCBean;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserSenceLog;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.dao.IUserWechatDao;
import com.yd.util.BeanUtil;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Repository("userWechatDao")
public class UserWechatDaoImpl extends BaseDao implements IUserWechatDao {
	public static final String NAMESPACE = "userWechat.";
	
	@Override
	public UserWechatBean findUserByOpenId(String openId){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findUserByOpenId", openId);
	}

	@Override
	public UserWechatBean findUserById(Integer id){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findUserById", id);
	}

	@Override
	public int addUser(UserWechatBean userBean) {
		Map<String,Object> map = BeanUtil.transBean2Map(userBean);
		return sqlSessionTemplate.insert(NAMESPACE+"insert",map);
	}

	@Override
	public void updateParentId(String parentId, String openid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentId",parentId);
		map.put("openid",openid);
		sqlSessionTemplate.update(NAMESPACE+"updateParentId", map);
	}

	@Override
	public void saveUserTicket(int senceCode,int senceId,String openId, String ticket,Date date,String url,String originalid) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openId",openId);
		map.put("senceCode",senceCode);
		map.put("senceId",senceId);
		map.put("ticket",ticket);
		map.put("ticket_expire",date);
		map.put("ticket_url",url);
		map.put("originalid",originalid);
		map.put("createDate", DateUtil.formatDate(new Date()));
		sqlSessionTemplate.insert(NAMESPACE+"saveUserTicket", map);
	}
	@Override
	public void deleteUserTicket(String openId,int senceCode,int senceId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openId",openId);
		map.put("senceCode",senceCode);
		map.put("senceId",senceId);
		sqlSessionTemplate.delete(NAMESPACE+"deleteUserTicket", map);
	}
	

	@Override
	public List<UserWechatBean> list(UserWechatBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"select", bean);
	}
	
	@Override
	public List<UserWechatBean> queryUserByCondition(UserWechatConditionBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryUserByCondition", bean);
	}
	
	
	@Override
	public void update(UserWechatBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateUserWechat", bean);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"update", id);
	}
	@Override
	public void goBlack(Date expireDate,String ids) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("expire_date", expireDate);
		params.put("ids", ids);
		sqlSessionTemplate.update(NAMESPACE+"setblack",params);
	}
	@Override
	public void updateUserWechatJoinImageNum(String weixin_id){
		sqlSessionTemplate.update(NAMESPACE+"updateUserWechatJoinImageNum", weixin_id);
	}
	
	@Override
	public void updateUserWechatJoinOfflineNum(String weixin_id){
		sqlSessionTemplate.update(NAMESPACE+"updateUserWechatJoinOfflineNum", weixin_id);
	}
	
	@Override
	public void updateUserWechat(UserWechatBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateUserWechat",bean);
	}
	@Override
	public void updateUserOfflineNumWechat(String parentId,
			Map<String, Object> map) {
		sqlSessionTemplate.update(NAMESPACE+"updateOfflineNumById", map);
	}

	@Override
	public int findUserOfflineNumWechat(String parentId) {
		Integer offline_num= sqlSessionTemplate.selectOne(NAMESPACE+"getOfflineNumById",parentId);
		if(offline_num == null){
			offline_num = 0;
		}
		return offline_num;
	}

	@Override
	public int findUserJoinOfflineNum(String openId) {
		Integer joinNum = sqlSessionTemplate.selectOne(NAMESPACE+"findUserJoinOfflineNumByWeixinId",openId);
		return joinNum == null?0:joinNum;
	}

	@Override
	public int findUserOfflineNumByOpenId(String openId) {
		Integer offline_num= sqlSessionTemplate.selectOne(NAMESPACE+"findUserOfflineNumByOpenId",openId);
		return offline_num == null?0:offline_num;
	}

	@Override
	public List<UserQrCodeBean> queryQrCodeTicket(UserQrCodeBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryQrCodeTicket", bean);
	}

	@Override
	public List<UserWechatBean> queryUserBetweenLastDate(int status, String startDate,String endDate) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("status", status);
		return sqlSessionTemplate.selectList(NAMESPACE+"queryUserBetweenLastDate", map);
	}

	
	@Override
	public void updateUserLastAccessTime(String openid, String last_access_time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		map.put("last_access_time", last_access_time);
		sqlSessionTemplate.update(NAMESPACE + "updateUserLastAccessTime", map);
	}
	
	@Override
	public int queryActivityFriendLevelOneCount(int user_id,String last_order_time){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("last_order_time", last_order_time);
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryActivityFriendLevelOneCount", map);
	}
	
	@Override
	public int queryActivityFriendLevelTwoCount(int user_id,String last_order_time){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
//		map.put("last_order_time", last_order_time);
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryActivityFriendLevelTwoCount", map);
	}
	
	@Override
	public int queryActivityFriendLevelThreeCount(int user_id,String last_order_time){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
//		map.put("last_order_time", last_order_time);
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryActivityFriendLevelThreeCount", map);
	}
	
	@Override
	public int queryActivityFriendLevelFourCount(int user_id,String last_order_time){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
//		map.put("last_order_time", last_order_time);
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryActivityFriendLevelFourCount", map);
	}
	
	@Override
	public UserSenceLog createUserSenceLog(UserSenceLog log){
		sqlSessionTemplate.insert(NAMESPACE+"createUserSenceLog", log);
		return log;
	}
	
	@Override
	public Integer updateUserSenceLog(UserSenceLog log){
		return sqlSessionTemplate.update(NAMESPACE+"updateUserSenceLog",log);
	}
	@Override
	public UserSenceLog findUserSenceLogById(Integer id){
		return sqlSessionTemplate.selectOne(NAMESPACE + "findUserSenceLogById", id);
	}
	
	@Override
	public List<UserSenceLog> queryUserSenceLog(UserSenceLog bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryUserSenceLog", bean);
	}

	@Override
	public List<UserWechatBean> queryWechatUserPage(UserWechatBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWechatUserPage", bean,rowBound(bean));
	}

	@Override
	public int wechatUserCount(UserWechatBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"wechatUserCount",bean);
	}

	
	@Override
	public void deleteWechatUser(UserWechatBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteWechatUser", bean);
	}

	@Override
	public void insertWechatUserAdmin(UserWechatBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertWechatUserAdmin", bean);
	}

	@Override
	public void updateWechatUserAdmin(UserWechatBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateWechatUserAdmin", bean);
	}
	
	@Override
	public List<UserWechatBean> queryMGRAdminUser(){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryMGRAdminUser");
	}
	
}
