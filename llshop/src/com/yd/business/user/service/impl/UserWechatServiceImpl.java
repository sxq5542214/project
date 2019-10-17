/**
 * 
 */
package com.yd.business.user.service.impl;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.user.bean.ConsumeTableBean;
import com.yd.business.user.bean.UserBWCBean;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserInfoCenterPageBean;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserSenceLog;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.bean.UserWechatFriendBean;
import com.yd.business.user.dao.IUserWechatDao;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.util.EmojiUtil;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.business.wechat.util.WechatUtil;
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
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IProductService productService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private ISupplierEventService supplierEventService;
	
	
	private static final boolean IS_OPEN_SENCE_LOG = true;
	
	@Override
	public UserWechatBean findUserWechatByOpenId(String openId){
		if(StringUtil.isNull(openId)){
			return null;
		}
		return userWechatDao.findUserByOpenId(openId);
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
	 * 订购成功后，给各级添加积分
	 */
	@Override
	public void updateUserBalanceByOrderProduct(int product_id,UserWechatBean user){
		ProductBean product = productService.findProductById(product_id);
//		int produt_price = product.getProduct_price();
//		double persent_self = 0.05;
//		double persent_parent1 = 0.03;
//		double persent_parent2 = 0.02;
//		double persent_parent3 = 0.01;
//		double persent_parent4 = 0.01;
		int givePoints = product.getGive_points(); //自己返的积分
		
		// 自己的更新
		user.setPoints(user.getPoints() + givePoints);
		userWechatDao.update(user);
		userCommissionPointsService.createUserPointLog(user.getId(), givePoints, "订购商品【"+product.getName()+"】赠送积分");

		//找 上1级的
		if(user.getParentid() != null){
//			givePoints =  (int) (persent_parent1 * produt_price);
			UserWechatBean p1 = userWechatDao.findUserById(user.getParentid());
			updateUserPointsOrBalanceByType(p1, 1,product, "您的一级好友【"+ user.getNick_name()+"】订购商品【"+product.getName()+"】", user);
//				p1.setPoints(p1.getPoints() + givePoints);
//				userWechatDao.update(p1);
//				userCommissionPointsService.createUserPointLog(p1.getId(), givePoints,"您的好友【"+ user.getNick_name()+"】订购商品【"+product.getName()+"】赠送积分");
			//找 上2级的
			if(p1.getParentid() != null){
//				givePoints =  (int) (persent_parent2 * produt_price);
				UserWechatBean p2 = userWechatDao.findUserById(p1.getParentid());
//				p2.setPoints(p2.getPoints() + givePoints);
//				userWechatDao.update(p2);
//				userCommissionPointsService.createUserPointLog(p2.getId(), givePoints,"您好友的好友【" + user.getNick_name()+"】订购商品【"+product.getName()+"】赠送积分");
				updateUserPointsOrBalanceByType(p2, 2,product, "您的二级好友【" + user.getNick_name()+"】订购商品【"+product.getName()+"】", user);

				//找 上3级的
				if(p2.getParentid() != null){
//					givePoints =  (int) (persent_parent3 * produt_price);
					UserWechatBean p3 = userWechatDao.findUserById(p2.getParentid());
//					p3.setPoints(p3.getPoints() + givePoints);
//					userWechatDao.update(p3);
//					userCommissionPointsService.createUserPointLog(p3.getId(), givePoints, "您的三级好友【" + user.getNick_name()+"】订购商品【"+product.getName()+"】赠送积分");
					updateUserPointsOrBalanceByType(p3, 3,product, "您的三级好友【" + user.getNick_name()+"】订购商品【"+product.getName()+"】", user);

					
					//找 上4级的
					if(p3.getParentid() != null){
//						givePoints =  (int) (persent_parent4 * produt_price);
						UserWechatBean p4 = userWechatDao.findUserById(p3.getParentid());
//						p4.setPoints(p4.getPoints() + givePoints);
//						userWechatDao.update(p4);
//						userCommissionPointsService.createUserPointLog(p4.getId(), givePoints, "您的四级好友【" + user.getNick_name()+"】订购商品【"+product.getName()+"】赠送积分");
						updateUserPointsOrBalanceByType(p4, 4,product, "您的四级好友【" + user.getNick_name()+"】订购商品【"+product.getName()+"】", user);

					}
				}
			}
		}
	}
	
	/**
	 * 根据用户类别不同，来更新用户的积分或余额
	 * @param user
	 * @param pointsOrBalance
	 * @param remark
	 */
	private void updateUserPointsOrBalanceByType(UserWechatBean user,int level,ProductBean product,String remark,UserWechatBean fromUser){
		String configCode = null;
		int pointsOrBalance;
		switch (user.getType()) {
		case UserWechatBean.TYPE_NORMAL:
		case UserWechatBean.TYPE_EXTENSION:
			configCode = AttributeConstant.CODE_USER_REBATE_TYPE_LEVEL +"_"+UserWechatBean.TYPE_NORMAL+"_"+level;
			String rebate = configAttributeService.getValueByCode(configCode);
			if(StringUtil.isNotNull(rebate)){
				pointsOrBalance = (int) (product.getProduct_price() * Double.parseDouble(rebate));
				
				//普通客户放积分账户里
				user.setPoints(user.getPoints() + pointsOrBalance);
				userWechatDao.update(user);
				userCommissionPointsService.createUserPointLog(user.getId(), pointsOrBalance, remark);
			}
			break;
		case UserWechatBean.TYPE_VIP:
			configCode = AttributeConstant.CODE_USER_REBATE_TYPE_LEVEL +"_"+UserWechatBean.TYPE_VIP+"_"+level;
			rebate = configAttributeService.getValueByCode(configCode);//
			if(StringUtil.isNotNull(rebate)){
				pointsOrBalance = (int) (product.getProduct_price() * Double.parseDouble(rebate));
				//VIP客户放余额账户里
				String orderCode = userConsumeInfoService.createOutTradeNo( IUserConsumeInfoService.OUTTRADE_TYPE_SUBCOST , user.getId());
				//先创建余额明细
				userConsumeInfoService.createConsumeInfo(remark, pointsOrBalance, null, user.getId(), fromUser.getId().toString(), orderCode, IUserConsumeInfoService.OUTTRADE_TYPE_SUBCOST,UserConsumeInfoBean.EVENT_TYPE_USER_SUBCOST);
	
				updateUserBalance(orderCode, null);
			}
			break;
			
		case UserWechatBean.TYPE_DIDI:
			configCode = AttributeConstant.CODE_USER_REBATE_TYPE_LEVEL +"_"+UserWechatBean.TYPE_DIDI+"_"+level;
			rebate = configAttributeService.getValueByCode(configCode);
			if(StringUtil.isNotNull(rebate)){
				pointsOrBalance = (int) (product.getProduct_price() * Double.parseDouble(rebate));
				
				//滴滴客户放余额账户里
				String orderCode = userConsumeInfoService.createOutTradeNo( IUserConsumeInfoService.OUTTRADE_TYPE_SUBCOST , user.getId());
				//先创建余额明细
				userConsumeInfoService.createConsumeInfo(remark, pointsOrBalance, null, user.getId(), fromUser.getId().toString(), orderCode, IUserConsumeInfoService.OUTTRADE_TYPE_SUBCOST,UserConsumeInfoBean.EVENT_TYPE_USER_SUBCOST);
	
				updateUserBalance(orderCode, null);
			}
			break;
		}
		
	}
	
	
	
	/**
	 * 根据单号，对用户余额进行充值
	 * @param out_trade_code
	 * @param param
	 */
	@Override
	public void updateUserBalance(String out_trade_code,String param){
		UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(out_trade_code);	
		//通过订单号在ll_user_consume_info表中查询数据
		if(consumeInfo.getStatus() != UserConsumeInfoBean.STATUS_SUCCESS){
			
			//更新用户余额
			int userId = consumeInfo.getUser_id();
			UserWechatBean user = this.findUserWechatById(userId);
			int money =  user.getBalance() + consumeInfo.getMoney();
			user.setBalance(money);
			
			this.update(user);
			consumeInfo.setStatus(UserConsumeInfoBean.STATUS_SUCCESS);
			userConsumeInfoService.updateUserConsumeInfo(consumeInfo);
		}
	}
	
	
	
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
				bean = wechatService.updateUserTicket(bean.getOpenId(), bean.getSenceCode(), bean.getSenceId());
			} catch (IOException e) {
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
	
	/**
	 * 查询活动一级好友数量
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoCenterPageBean queryActivityFriendLevelCount(int userId){
		UserInfoCenterPageBean bean = new UserInfoCenterPageBean();
		
		int activity_day = -90; //90天以内的活动用户数
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, activity_day);
		String afterTime = DateUtil.formatDate(c.getTime());
		
		int level1 = userWechatDao.queryActivityFriendLevelOneCount(userId, afterTime);
//		int level2 = userWechatDao.queryActivityFriendLevelTwoCount(userId, afterTime);
//		int level3 = userWechatDao.queryActivityFriendLevelThreeCount(userId, afterTime);
//		int level4 = userWechatDao.queryActivityFriendLevelFourCount(userId, afterTime);
		
		bean.setLevelOneFriendCount(level1);
//		bean.setLevelTwoFriendCount(level2);
//		bean.setLevelThreeFriendCount(level3);
//		bean.setLevelFourFriendCount(level4);
		
		return bean;
	}
	
	/**
	 * 查询为参加活动用户助力的所有好友
	 * @param userid	参加活动的用户ID
	 * @param supplierEventId	活动ID
	 * @return	该项活动助力的所有好友
	 */
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
		if(IS_OPEN_SENCE_LOG){
			
			//先查询，如果有就更新，没有就创建
			UserSenceLog condition = new UserSenceLog();
			condition.setShare_type(share_type);
			condition.setShare_from(share_from);
			condition.setSence_type(senceType);
			condition.setSenceid(senceId);
			condition.setOpenid(user.getOpenid());
			List<UserSenceLog> list =userWechatDao.queryUserSenceLog(condition);
			if(list.size()>0){
				UserSenceLog bean = list.get(0);
				bean.setShare_time(DateUtil.getNowDateStr());
				bean.setShare_num(bean.getShare_num() +1);
				bean.setRead_num(bean.getRead_num() + 1);
				bean.setLast_read_time(DateUtil.getNowDateStr());
				userWechatDao.updateUserSenceLog(bean);
				return bean.getId();
			}
			
			//没有update 的记录，就创建新的
			UserSenceLog senceLog = new UserSenceLog();
			senceLog.setOpenid(user.getOpenid());
			senceLog.setUser_id(user.getId());
			senceLog.setUser_name(user.getNick_name());
			senceLog.setCratetime(DateUtil.getNowDateStr());
			senceLog.setSenceid(senceId);
			senceLog.setSence_type(senceType);
			senceLog.setShare_time(DateUtil.getNowDateStr());
			senceLog.setShare_from(share_from);
			senceLog.setShare_time_ms(share_time_ms);
			senceLog.setShare_num(1);
			senceLog.setShare_type(share_type);
			
			senceLog = userWechatDao.createUserSenceLog(senceLog);
			log.debug(" senceLogId : "+ senceLog.getId());
			return senceLog.getId();
		}
		return null;
	}
	
	/**
	 * 阅读了用户分享的场景
	 */
	@Deprecated
	@Override
	public Integer readUserSenceLog(int logId){
		UserSenceLog senceLog = findUserSenceLogById(logId);
		return readUserSenceLog(senceLog);
		
	}
	
	/**
	 * 阅读了用户分享的场景,修改阅读记录
	 * @param senceLog
	 * @return
	 */
	private Integer readUserSenceLog(UserSenceLog senceLog){
		Integer num = null;
		if(senceLog != null){
			String today = DateUtil.formatDate(new Date());
			if(senceLog.getRead_num() == 0){
				senceLog.setFirst_read_time(today);
			}
			senceLog.setRead_num(senceLog.getRead_num() + 1);
			senceLog.setLast_read_time(today);
			
			num = userWechatDao.updateUserSenceLog(senceLog);
		}
		return num;
	}
	@Override
	public Integer readUserSenceLog(String openid,Integer share_type,String share_time_ms){
		Integer num = null;
		//先查询，如果有就更新，没有就创建
		if(StringUtil.isNotNull(share_time_ms) && StringUtil.isNotNull(openid)){
			UserSenceLog condition = new UserSenceLog();
			condition.setShare_time_ms(share_time_ms);
			condition.setOpenid(openid);
			condition.setShare_type(share_type);
			List<UserSenceLog> list =userWechatDao.queryUserSenceLog(condition);
			if(list.size()>0){
				num = readUserSenceLog(list.get(0));
			}
		}
		return num;
	}
	
	/**
	 * 用户分享了场景
	 */
	@Override
	public Integer shareUserSenceLog(int logId,int shareType){
		Integer num = null;
		UserSenceLog senceLog = findUserSenceLogById(logId);
		if(senceLog != null){
			String today = DateUtil.formatDate(new Date());
			
			senceLog.setShare_type(shareType);
			senceLog.setShare_time(today);
			
			//更新用户表的分享时间和类型
			UserWechatBean user = userWechatDao.findUserByOpenId(senceLog.getOpenid());
			user.setShare_time(today);
			user.setShare_type(shareType);
			userWechatDao.update(user);
			
			num =  userWechatDao.updateUserSenceLog(senceLog);
		}
		return num;
	}
	
	@Override
	public UserSenceLog findUserSenceLogById(int id){
		return userWechatDao.findUserSenceLogById(id);
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
		int senceType = 0,senceId = 0;
		if(UserSenceLog.SHARE_FROM_MYSUPPLIER_EVENT.equalsIgnoreCase(share_from)){
			handlerUserShareFromMyEventInfo(user, share_from, share_type,param);
			senceType = WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT;
			if(StringUtil.isNotNull(param)){
				senceId = Integer.parseInt(param);
			}
		}
		if(UserSenceLog.SHARE_FROM_SUPPLIER_TOPIC.equalsIgnoreCase(share_from)){
			senceType = WechatConstant.TICKET_SENCE_CODE_SUPPLIERTOPIC;
			if(StringUtil.isNotNull(param)){
				senceId = Integer.parseInt(param);
			}
		}
		
		if(UserSenceLog.SHARE_FROM_LIULIANG1G_ACTIVITY.equalsIgnoreCase(share_from)){
			senceType = WechatConstant.TICKET_SENCE_CODE_ACTIVITY;
			if(StringUtil.isNotNull(param)){
				senceId = Integer.parseInt(param);
			}
		}
		
		if(UserSenceLog.SHARE_FROM_YYG_LOTTERY_WIN_SHARE.equalsIgnoreCase(share_from)){
			//一元购的中奖分享
			senceType = WechatConstant.TICKET_SENCE_CODE_LOTTERY;
			if(StringUtil.isNotNull(param)){
				senceId = Integer.parseInt(param);
			}
		}
		
		
		//创建分享日志
		createUserSenceLog(user, senceType, senceId, share_type, share_from, share_time_ms);

		//保存并处理用户动作
		msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_SHARE , share_from, user);
		
		user.setShare_time(DateUtil.getNowDateStrSSS());
		user.setShare_type(share_type);
		update(user);
		
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
		
		int shareAddPoints = configAttributeService.getIntValueByCode(AttributeConstant.CODE_USER_SHARE_ADD_POINTS);
		
		user.setPoints(user.getPoints() + shareAddPoints);
		update(user);
		
		userCommissionPointsService.createUserPointLog(user.getId(), shareAddPoints, "分享订购页面获得积分");
		
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
			
			supplierEventService.queryAndInitEventCode(Integer.parseInt(param), user.getId(), user.getOpenid());
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
	
	
	
}
