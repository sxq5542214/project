/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.bean.UserSignBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.dao.IUserSignDao;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserSignService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("userSignService")
public class UserSignServiceImpl extends BaseService implements IUserSignService {
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IUserSignDao userSignDao;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	
	/**
	 * 用户签到功能
	 * @param openid
	 * @return 签到获得的积分
	 */
	@Override
	public UserSignBean userSignByOpenid(String openid){
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);

		//查询本月有没有签到过
		UserSignBean condition = new UserSignBean();
		String signMonth = DateUtil.getNowMonthStr();
		condition.setSign_month(signMonth);
		condition.setUser_id(user.getId());
		List<UserSignBean> list = userSignDao.queryUserSign(condition);
		UserSignBean signBean = null;
		if(list.size()>0){
			signBean  = list.get(0);
		}
		
		//检查今天是否签到过
		if(!checkTodaySign(signBean)){
			
			//获取积分
			int minSignPoints = configAttributeService.getIntValueByCode(AttributeConstant.CODE_USER_SIGN_MIN_POINTS);
			int maxSignPoints = configAttributeService.getIntValueByCode(AttributeConstant.CODE_USER_SIGN_MAX_POINTS);
			
			int point = RandomUtil.nextInt(maxSignPoints - minSignPoints) + minSignPoints;
			user.setPoints(user.getPoints() + point);
			//判断最后一次签到日期是不是昨天,如果是昨天，则连续签到+1，如果不是昨天，则连续签到修改为1
			Calendar ca = Calendar.getInstance();
			int day = ca.get(Calendar.DAY_OF_MONTH);
			ca.add(Calendar.DAY_OF_YEAR, -1);
			Date yesDay = ca.getTime();
			String yesDayStr = DateUtil.formatDateOnlyDate(yesDay);
			
			if(signBean == null){ // 这个月第一次，没有数据的情况
				signBean = new UserSignBean();
				signBean.setCreate_time(DateUtil.getNowDateStr());
				signBean.setSign_month(signMonth);
				signBean.setSign_count(0);
				signBean.setSerial_sign_count(0);
				signBean.setUser_id(user.getId());
				signBean.setSign_date(UserSignBean.SIGN_SPLIT_STR);
				signBean.setPoints_month(UserSignBean.SIGN_SPLIT_STR);
			}
			
			signBean.setLast_points(point);
			signBean.setModify_time(DateUtil.getNowDateStr());
			signBean.setLast_sign_date(DateUtil.getNowOnlyDateStr());
			signBean.setSign_count(signBean.getSign_count() + 1);
			//由于一个月是一条记录，所以部分字段需要拼接
			signBean.setSign_date(signBean.getSign_date() + day + UserSignBean.SIGN_SPLIT_STR);
			signBean.setPoints_month(signBean.getPoints_month() + point + UserSignBean.SIGN_SPLIT_STR );
			
			//昨天签到过
			if(signBean != null && yesDayStr.equals(signBean.getLast_sign_date())){
				signBean.setSerial_sign_count(signBean.getSerial_sign_count() + 1);
			}else{
				signBean.setSerial_sign_count(1);
			}
			createOrUpdateUserSign(signBean);
			userWechatService.update(user);
			//记录用户积分变更信息
			userCommissionPointsService.createUserPointLog(signBean.getUser_id(), point, "签到赠送积分");
		}
		
		return signBean;
	}
	
	@Override
	public List<UserSignBean> queryUserSignHistory(int userId){
		
		UserSignBean bean = new UserSignBean();
		bean.setUser_id(userId);
		
		return userSignDao.queryUserSign(bean );
	}
	
	
	
	
	/**
	 * 检查今天有没有签到过
	 * @return 有true 没有false
	 */
	private boolean checkTodaySign(UserSignBean signBean){
		if(signBean == null){
			return false;
		}
		String today = DateUtil.getNowOnlyDateStr();
		if(today.equalsIgnoreCase(signBean.getLast_sign_date())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 创建或修改用户签到信息
	 * @param bean
	 */
	public void createOrUpdateUserSign(UserSignBean bean){
		if(bean.getId() == null){
			userSignDao.createUserSign(bean);
		}else{
			userSignDao.updateUserSign(bean);
		}
	}
	
	
	/**
	 * 判断本日是否签到
	 */
	@Override
	public UserSignBean whetherSign(String openid){
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);

		//查询本月有没有签到过
		UserSignBean condition = new UserSignBean();
		String signMonth = DateUtil.getNowMonthStr();
		condition.setSign_month(signMonth);
		condition.setUser_id(user.getId());
		List<UserSignBean> list = userSignDao.queryUserSign(condition);
		UserSignBean signBean = null;
		if(list.size()>0){
			signBean  = list.get(0);
			if(!checkTodaySign(signBean)){
				return null;
			}else{
				return signBean;
			}
		}
		return signBean;
		
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageinationData queryUserSingPage(UserSignBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<UserSignBean> showList = userSignDao.queryUserSingPage(bean);
		    int count = userSignDao.userSingCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;	}

	/**
	 * 删除usersign表数据
	 */
	@Override
	public void deteleUserSign(UserSignBean bean) {
		userSignDao.deleteUserSign(bean);
	}

	/**
	 * 更新usersign表数据
	 */
	@Override
	public void editUserSign(UserSignBean bean) {
		try{
			if(!StringUtil.isNull(bean.getId())){
				bean.setModify_time(DateUtil.getNowDateStr());
				userSignDao.updateUserSign(bean);
		} }catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
	}
			
}
