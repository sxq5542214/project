package com.yd.business.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.user.bean.UserBWCBean;
import com.yd.business.user.bean.UserInfoCenterPageBean;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserSenceLog;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;

public interface IUserWechatService {

	UserWechatBean findUserWechatByOpenId(String openid);

	void updateUserOfflineNumWechat(String parentId);

	UserWechatBean findUserWechatById(Integer id);

	void addUser(UserWechatBean userBean);
	void updateParentId(String parentId,String weixin_id);
	void updateUserWechatJoinImageNum(String weixin_id);
	void updateUserWechatJoinOfflineNum(String weixin_id);
	void updateUserWechat(UserWechatBean user);
	List<UserWechatBean> list(UserWechatBean bean);
	void update(UserWechatBean bean);
	void delete(Integer id);
	void goBlack(Date expireDate,String ids);

	UserQrCodeBean queryQrCodeTicketByUserIdAndSence(String openId, int senceCode, int senceId);

	void updateUserLastAccessTime(String fromUserName, Date date);

	Integer createUserSenceLog(String openId, int senceType, int senceId);

	UserSenceLog findUserSenceLogById(int id);

	@Deprecated
	Integer readUserSenceLog(int logId);

	Integer shareUserSenceLog(int logId, int shareType);

	void updateUserBalance(String out_trade_code, String param);

	void updateUserBalanceByOrderProduct(int product_id, UserWechatBean user);

	UserInfoCenterPageBean queryActivityFriendLevelCount(int userId);

	List<UserWechatBean> queryUserListWithin48Hour();

	Integer createUserSenceLog(UserWechatBean user, int senceType, int senceId, Integer share_type, String share_from,
			String share_time_ms);

	Integer createUserSenceLog(String openId, int senceType, int senceId, int share_type, String share_from,
			String share_time_ms);

	Integer readUserSenceLog(String openid, Integer share_type, String share_time_ms);

	void addUserToSession(String openid);

	String handlerUserShare(String openid, String share_from, int share_type, String share_time_ms, String param);

	List<UserWechatBean> queryUserBetweenLastDate(int status, String startDate, String endDate);

	List<UserWechatBean> queryUser(UserWechatConditionBean bean);

	UserQrCodeBean updateUserTicket(int senceCode, int senceId, String weixin_id, String ticket, Date date, String url,
			String originalid);

	
	
	/**
	 * 分页查询
	 */
	public PageinationData queryWechatUserPage(UserWechatBean bean);
	
	
	
	/**
	 * 删除wechat_user表信息
	 */
	public void deteleWechatUser(UserWechatBean bean);
	
	
	/**
	 * 编辑wechat_user表信息
	 */
	public void editWechatUser(UserWechatBean bean);

	List<UserWechatBean> queryMGRAdminUser();
}
