package com.yd.business.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.bean.UserWechatFriendBean;

public interface IUserWechatDao {

	UserWechatBean findUserByOpenId(String openId);

	UserWechatBean findUserById(Integer id);
	void updateUserOfflineNumWechat(String parentId,Map<String,Object> map);
	int addUser(UserWechatBean userBean);
	void updateParentId(String parentId,String weixin_id);
	List<UserWechatBean> list(UserWechatBean bean);
	void update(UserWechatBean bean);
	void delete(Integer id);
	void goBlack(Date expireDate,String ids);


	int findUserOfflineNumWechat(String parentId);
	int findUserJoinOfflineNum(String openId);
	int findUserOfflineNumByOpenId(String openId);
	void updateUserWechat(UserWechatBean bean);
	
	List<UserQrCodeBean> queryQrCodeTicket(UserQrCodeBean bean);

	void deleteUserTicket(String weixin_id, int senceCode, int senceId);

	void updateUserLastAccessTime(String openId, String date);


	int queryActivityFriendLevelOneCount(int user_id, String last_order_time);

	int queryActivityFriendLevelTwoCount(int user_id, String last_order_time);

	int queryActivityFriendLevelThreeCount(int user_id, String last_order_time);

	int queryActivityFriendLevelFourCount(int user_id, String last_order_time);

	List<UserWechatBean> queryUserBetweenLastDate(int status, String startDate, String endDate);

	List<UserWechatBean> queryUserByCondition(UserWechatConditionBean bean);

	void saveUserTicket(int senceCode, int senceId, String openId, String ticket, Date date, String url,
			String originalid);

	
	
	/**
	 * 分页查询ll_user_wechat表信息
	 */
	public List<UserWechatBean> queryWechatUserPage(UserWechatBean bean);
	
	
	/**
	 * 查询ll_user_wechat表总数
	 */
	public int wechatUserCount(UserWechatBean bean);
	
	
	/**
	 * 删除ll_user_wechat表信息
	 */
	public void deleteWechatUser(UserWechatBean bean);
	
	
	/**
	 * 插入ll_user_wechat表数据
	 */
	public void insertWechatUserAdmin(UserWechatBean bean);
	
	/**
	 * 后台更新ll_user_wechat表数据
	 */
	public void updateWechatUserAdmin(UserWechatBean bean);

	List<UserWechatBean> queryMGRAdminUser();

	/**
	 * 查询访问当前action的openid 是否在后台配置中
	 * @param action
	 * @param openid
	 * @return
	 */
	List<UserWechatBean> queryWechatUserActionAgree(String action, String openid);

	/**
	 * 查询用户的好友信息
	 * @param bean
	 * @return
	 */
	List<UserWechatFriendBean> queryUserWechatFriends(UserWechatFriendBean bean);

	/**
	 * 创建用户好友关系
	 * @param bean
	 * @return
	 */
	int createUserWechatFriends(UserWechatFriendBean bean);

	/**
	 * 查找用户好友
	 * @param bean
	 * @return
	 */
	List<UserWechatBean> queryUserFriends(UserWechatBean bean);
}
