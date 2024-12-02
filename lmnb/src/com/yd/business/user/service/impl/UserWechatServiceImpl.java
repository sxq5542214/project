/**
 * 
 */
package com.yd.business.user.service.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.bean.UserWechatFriendBean;
import com.yd.business.user.dao.IUserWechatDao;
import com.yd.business.user.service.IUserInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.iotbusiness.mapper.dao.LmPlatformModelMapper;
import com.yd.iotbusiness.mapper.model.LmPlatformModel;
import com.yd.iotbusiness.mapper.model.LmPlatformModelExample;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("userWechatService")
public class UserWechatServiceImpl extends BaseService implements IUserWechatService {
	
	@Resource
	private IUserWechatDao userWechatDao;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private LmPlatformModelMapper platformModelMapper;
	
	
	private static final boolean IS_OPEN_SENCE_LOG = true;
	
	@Override
	public LmUserModel findLmUserByOpenId(String openId){
		if(StringUtil.isNull(openId)){
			return null;
		}
		LmPlatformModelExample exam = new LmPlatformModelExample();
		LmPlatformModelExample.Criteria cri = exam.createCriteria();
		cri.andCodeEqualTo(openId);

		List<LmPlatformModel> list = platformModelMapper.selectByExample(exam );
		if(list.size() > 0 ) {
			LmPlatformModel  model = list.get(0);
			int userid = model.getUserid();
			return userInfoService.findUserById(userid);
		}
		
		return null;
	}
	
	

	public UserWechatBean findUserWechatByOpenId(String openId){
		return null;
	}
	
	@Override
	public UserWechatBean findUserWechatById(Integer id){
		return userWechatDao.findUserById(id);
	}
	
	@Override
	public void updateUserOfflineNumWechat(String parentId) {
		int offline_num = userWechatDao.findUserOfflineNumWechat(parentId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",parentId);
		map.put("num",offline_num+1);
		userWechatDao.updateUserOfflineNumWechat(parentId,map);
	}

	@Override
	public void addUser(UserWechatBean userBean) {
		//emoji表情过虑
//		String filterName = EmojiUtil.filterEmoji(userBean.getNick_name());
//		userBean.setNick_name(filterName);
		
		try{
			if(userBean.getOpenid() != null)
			{
//				userBean.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
				userWechatDao.addUser(userBean);
			}
		}catch(Exception e){
			log.error(e, e);
//			userBean.setNick_name("微信用户"+ DateUtil.java2phpDate(System.currentTimeMillis()) );
//			userWechatDao.addUser(userBean);
		}
	}
	
	@Override
	public void updateParentId(String parentId, String weixin_id) {
		userWechatDao.updateParentId(parentId,weixin_id);
	}

	@Override
	public List<UserWechatBean> list(UserWechatBean bean) {
		return userWechatDao.list(bean);
	}
	
	@Override
	public void update(UserWechatBean bean) {
		userWechatDao.update(bean);
	}

	@Override
	public void delete(Integer id) {
		userWechatDao.delete(id);
	}
	

	
	
	/**
	 * 根据单号，对用户余额进行充值
	 * @param out_trade_code
	 * @param param
	 */
	@Override
	public void updateUserBalance(String out_trade_code,String param){}
	
	
	
	@Override
	public void goBlack(Date expireDate,String ids) {
		userWechatDao.goBlack(expireDate,ids);
	}

	@Override
	public void updateUserWechat(UserWechatBean user){
		userWechatDao.updateUserWechat(user);
	}
	
	@Override
	public UserQrCodeBean updateUserTicket(int senceCode,int senceId,String weixin_id, String ticket,Date date,String url,String originalid) {
		
		userWechatDao.deleteUserTicket(weixin_id, senceCode,senceId);
		userWechatDao.saveUserTicket(senceCode,senceId, weixin_id, ticket, date, url,originalid);
		UserQrCodeBean bean = new UserQrCodeBean();
		bean.setSenceCode(senceCode);
		bean.setSenceId(senceId);
		bean.setOpenId(weixin_id);
		bean.setTicket(ticket);
		bean.setTicket_expire(date);
		bean.setTicket_url(url);
		bean.setOriginalid(originalid);
		return bean;
	}
	
	/**
	 * 查询用户指定场景的二维码
	 * @param openId
	 * @param senceCode
	 * @param senceId
	 * @return
	 */
	@Override
	public UserQrCodeBean queryQrCodeTicketByUserIdAndSence(String openId,int senceCode,int senceId){
		
		if(openId == null){
			return null;
		}
		
		
		UserQrCodeBean bean = new UserQrCodeBean();
		bean.setOpenId(openId);
		bean.setSenceCode(senceCode);
		bean.setSenceId(senceId);
		List<UserQrCodeBean> list = userWechatDao.queryQrCodeTicket(bean );
		if(list.size() > 0){
			bean = list.get(0);
		}
		bean = checkUserQrCodeValide(bean);
		return bean;
	}
	
	/**
	 * 检查用户的二维码是否过期，如果过期则重新取
	 * @param bean
	 */
	private UserQrCodeBean checkUserQrCodeValide(UserQrCodeBean bean){
		
		if(bean == null) return null;
		//没有过期时间，或者过期时间大于当前时间
		if(bean.getTicket_expire() == null || bean.getTicket_expire().getTime() <= System.currentTimeMillis()){
			
			try {
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
			}
		}
		
		return bean;
	}

	@Override
	public List<UserWechatBean> queryUserBetweenLastDate(int status,String startDate,String endDate) {
		
		return userWechatDao.queryUserBetweenLastDate(status,startDate,endDate);
	}
	
	@Override
	public List<UserWechatBean> queryUser(UserWechatConditionBean bean){
		return userWechatDao.queryUserByCondition(bean);
	}
	
	@Override
	public List<UserWechatBean> queryMGRAdminUser(){
		
		return userWechatDao.queryMGRAdminUser();
	}
	
	@Override
	public List<UserWechatBean> queryWechatUserActionAgree(String action,String openid){
		return userWechatDao.queryWechatUserActionAgree(action, openid);
	}
	
	@Override
	public void updateUserLastAccessTime(String openId, Date date) {

		String dateStr = DateUtil.formatDate(date);
		userWechatDao.updateUserLastAccessTime(openId, dateStr);
	}
	
	
	@Override
	public List<UserWechatBean> querySupplierEventHelpUser(int userid,int supplierEventId){
		
		return null;
	}
	
	
	/**
	 * 记录用户的分享场景
	 * @param openId
	 * @param senceType
	 * @param senceId
	 * @return
	 */
	@Deprecated
	@Override
	public Integer createUserSenceLog(String openId,int senceType,int senceId ){
		if(IS_OPEN_SENCE_LOG){
			UserWechatBean user = userWechatDao.findUserByOpenId(openId);
			return createUserSenceLog(user, senceType, senceId,null,null,null);
		}
		return null;
	}
	@Override
	public Integer createUserSenceLog(String openId,int senceType,int senceId ,int share_type,String share_from,String share_time_ms){
		if(IS_OPEN_SENCE_LOG){
			UserWechatBean user = userWechatDao.findUserByOpenId(openId);
			return createUserSenceLog(user, senceType, senceId,share_type,share_from,share_time_ms);
		}
		return null;
	}
	/**
	 * 记录用户的分享场景
	 * @param user
	 * @param senceType
	 * @param senceId
	 * @return
	 */
	@Override
	public Integer createUserSenceLog(UserWechatBean user,int senceType,int senceId,Integer share_type,String share_from,String share_time_ms){
		return null;
		
	}
	
	

	@Override
	public Integer readUserSenceLog(String openid,Integer senceId,Integer senceType, String share_type){
		Integer num = null;
		if(StringUtil.isNull(openid)) {
			return num;
		}
		return num;
	}
	
	/**
	 * 用户分享了场景
	 */
	@Override
	public Integer shareUserSenceLog(int logId,int shareType){
		Integer num = null;
	
		return num;
	}
	
	
	/**
	 * 查询48小时内访问过的用户
	 */
	@Override
	public List<UserWechatBean> queryUserListWithin48Hour(){
		
		//48小时以内的用户
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -48);
		
		String startTime = DateUtil.formatDate(c.getTime());
		String endTime = DateUtil.getNowDateStrSSS();

		return userWechatDao.queryUserBetweenLastDate(UserWechatBean.STATUS_SUBSCRIBE, startTime, endTime);
	}
	
	/**
	 * 处理用户分享事件
	 * @param openid
	 * @param share_from
	 * @param share_type
	 * @return
	 */
	@Override
	public String handlerUserShare(String openid,String share_from,int share_type,String share_time_ms,String param){
		UserWechatBean user = findUserWechatByOpenId(openid);
		//来自订购页面的分享,分享一次就加积分，已不用
//		if(UserSenceLog.SHARE_FROM_ORDER_PRODUCT.equalsIgnoreCase(share_from)
//				|| UserSenceLog.SHARE_FROM_FIRST_ORDER_PRODUCT.equalsIgnoreCase(share_from)){
//		
//			handlerUserShareFromOrderProduct(user, share_from, share_type);
//		}
	
		
		return null;
	}
	/**
	 * 处理来自用户订购页面的分享
	 * @param user
	 * @param share_from
	 * @param share_type
	 * @return
	 */
	private String handlerUserShareFromOrderProduct(UserWechatBean user,String share_from,int share_type){
		
//		int shareAddPoints = configAttributeService.getIntValueByCode(AttributeConstant.CODE_USER_SHARE_ADD_POINTS);
//		
//		user.setPoints(user.getPoints() + shareAddPoints);
//		update(user);
		
//		userCommissionPointsService.createUserPointLog(user.getId(), shareAddPoints, "分享订购页面获得积分");
		
		return null;
	}
	
	

	/**
	 * 处理来自用户参加活动页面的
	 * @param user
	 * @param share_from
	 * @param share_type
	 * @return
	 */
	private String handlerUserShareFromMyEventInfo(UserWechatBean user,String share_from,int share_type,String param){
		
		if(StringUtil.isNotNull(param)){
			
		}
		return null;
	}
	
	/**
	 * 处理来自用户参加活动页面的
	 * @param user
	 * @param share_from
	 * @param share_type
	 * @return
	 */
	private String handlerYYGLotteryWinShare(UserWechatBean user,String share_from,int share_type,String param){
		
		if(StringUtil.isNotNull(param)){
			
			
			
			
		}
		return null;
	}
	
	/**
	 * 用户加入Session缓存
	 * @param openid
	 */
	@Override
	public void addUserToSession(String openid){
		
//		HttpSession session = WebContext.getHttpSession();
//		
//		if(session.getAttribute(WebContext.SESSION_ATTRIBUTE_USER_OPENID) == null){
//			session.setAttribute(WebContext.SESSION_ATTRIBUTE_USER_OPENID, openid);
//		}
		
		
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageinationData queryWechatUserPage(UserWechatBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<UserWechatBean> showList = userWechatDao.queryWechatUserPage(bean);
		    int count = userWechatDao.wechatUserCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;
		
	}

	@Override
	public void deteleWechatUser(UserWechatBean bean) {
		userWechatDao.deleteWechatUser(bean);
	}

	@Override
	public void editWechatUser(UserWechatBean bean) {
		try{
			if(StringUtil.isNull(bean.getId())){
				bean.setCreate_time(DateUtil.getNowDateStr());
				userWechatDao.insertWechatUserAdmin(bean);
			}else{
				userWechatDao.updateWechatUserAdmin(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
	}
	

	@Override
	public UserWechatFriendBean createUserWechatFriend(String openid,String friendOpenid){
		if(openid == null || friendOpenid == null){
			return null;
		}
		UserWechatBean user = findUserWechatByOpenId(openid);
		UserWechatBean friendUser = findUserWechatByOpenId(friendOpenid);
		
		return createUserWechatFriend(user, friendUser);
	}
	@Override
	public UserWechatFriendBean createUserWechatFriend(String openid,Integer friendUserId){
		if(openid == null || friendUserId == null){
			return null;
		}
		UserWechatBean user = findUserWechatByOpenId(openid);
		UserWechatBean friendUser = findUserWechatById(friendUserId);
		
		return createUserWechatFriend(user, friendUser);
	}

	@Override
	public UserWechatFriendBean createUserWechatFriend(Integer userId,String friendOpenId){
		if(userId == null || friendOpenId == null){
			return null;
		}
		UserWechatBean user = findUserWechatById(userId);
		UserWechatBean friendUser = findUserWechatByOpenId(friendOpenId);
		
		return createUserWechatFriend(user, friendUser);
	}
	
	@Override
	public UserWechatFriendBean createUserWechatFriend(UserWechatBean user,UserWechatBean friend){
		UserWechatFriendBean bean = new UserWechatFriendBean();
		
		//如果是自己和自己，则返回空
		if(StringUtil.isNotNull(user.getOpenid()) && user.getOpenid().equals(friend.getOpenid())){
			return null;
		}
		
		if(StringUtil.isNotNull(user.getUnionid()) && user.getUnionid().equals(friend.getUnionid())){
			return null;
		}
		
		bean.setFriendOpenid(friend.getOpenid());
		bean.setFriendUserid(friend.getId());
		bean.setOpenid(user.getOpenid());
		bean.setUserid(user.getId());
		
		//判断是否已有用户好友关系
		List<UserWechatFriendBean> list = userWechatDao.queryUserWechatFriends(bean);
		if(list.size() == 0){
		
			bean.setCreateDate(DateUtil.getNowDateStr());
			bean.setFromSenceId(friend.getSenceid());
			bean.setFromSenceType(friend.getSence_type());
			createUserWechatFriend(bean);
		}else{
			bean = list.get(0);
		}
		
		return bean;
	}
	
	private int createUserWechatFriend(UserWechatFriendBean bean){
		return userWechatDao.createUserWechatFriends(bean);
	}
	private List<UserWechatBean> queryUserFriends(UserWechatBean bean){
		return userWechatDao.queryUserFriends(bean);
	}

	@Override
	public List<UserWechatBean> queryUserFriends(int id){
		UserWechatBean bean = new UserWechatBean();
		bean.setId(id);
		return queryUserFriends(bean);
	}

	@Override
	public List<UserWechatBean> queryUserFriends(String openid){
		UserWechatBean bean = new UserWechatBean();
		bean.setOpenid(openid);
		return queryUserFriends(bean);
	}

	@Override
	public Integer readUserSenceLog(String openid, Integer share_type, String share_time_ms) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
