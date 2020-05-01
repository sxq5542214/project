/**
 * 
 */
package com.yd.business.activity.service.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityBean;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityInstanceBean;
import com.yd.business.activity.bean.ActivityOlympicGuessBean;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityRemindBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.activity.dao.IActivityDao;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierVolumBean;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierVolumService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.service.impl.UserConsumeInfoServiceImpl;
import com.yd.business.wechat.bean.ImageBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("activityService")
public class ActivityServiceImpl extends BaseService implements IActivityService {
	
	@Resource
	private IActivityDao activityDao;
	@Resource
	private IActivitConfigService activityConfigService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IProductService productService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IOrderService orderService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IProductTypeService productTypeService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	/**
	 * 用户抢红包，获取金额并入库
	 * @param user
	 * @return 返回为null 代表抢到了红包
	 */
	@Override
	public String userGrabActivity(UserWechatBean user,Integer activity_config_id){
		String result = null;
		//查询用户是否已参加过活动
		ActivityUserRelationBean relation = activityDao.findRelation(user.getOpenid(), activity_config_id);
		if(relation == null){
			result = "您还没有取得参加当前活动的条件哦！请快快查看活动说明吧！";
			
		}else {
			log.debug("user: "+ user.getOpenid() +" relation activityType:"+ relation.getActivity_type());
			//判断 用户参加的活动是否是当前进行中的
			
		}
		
		return result;
	}
//	/**
//	 * 查询用户已抢到的活动
//	 * @param relation
//	 * @return
//	 */
//	private ActivityBean queryUserAlreadyGrabActivity(ActivityUserRelationBean relation){
//		ActivityBean bean =  activityDao.findActivityBean(relation.getUser_id(), relation.getActivity_config_id());
//		return bean;
//	}
	
	
	/**
	 * 入库用户可以参加的活动
	 * @param config
	 * @param image  有image代表是传图参加的，没有，代表是直接领号的
	 * @param user	 用户信息
	 */
	@Override
	public ActivityUserRelationBean saveActivityUserRelation(ActivityConfigBean config,ImageBean image ,UserWechatBean user){
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setActivity_config_id(config.getId());
		bean.setActivity_time(config.getStart_hour()+":"+config.getStart_minute());
		bean.setCategory(ActivityUserRelationBean.CATEGORY_OFFLINE);
		bean.setJoin_date(DateUtil.getNowDateStr());
		bean.setOpenid(user.getOpenid());
		bean.setUser_id(user.getId());
		bean.setActivity_name(config.getName());
		
		if(image != null){
			bean.setCategory(ActivityUserRelationBean.CATEGORY_IMAGE);
		}
		
		activityDao.saveActivityUserRelateion(bean);
		return bean;
	}
	
	
	/**
	 * 保存中奖信息到历史
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActivityWinHisBean createActivityWinHis(Integer activityId,UserWechatBean user,ActivityPrize prize){
		ActivityWinHisBean win = new ActivityWinHisBean();
//		AutoInvokeGetSetMethod.autoInvoke(activity, win);
		win.setActivity_config_id(activityId);
		win.setCategory(prize.getCategory());
		win.setUser_id(user.getId());
		win.setOpenid(user.getOpenid());
		win.setUser_name(user.getNick_name());
		win.setHead_img(user.getHead_img());
		win.setId(null);
		win.setBonus_money(prize.getBonus_money());
		win.setPrize_id(prize.getId());
		win.setPrize_name(prize.getPrize_name());
		win.setStatus(ActivityWinHisBean.STATUS_WINED);
		win.setCreate_time(DateUtil.getNowDateStr());
		activityDao.saveActivityWinHis(win);
		return win;
	}

	/**
	 * 查询中奖信息
	 */
	@Override
	public List<ActivityWinHisBean> queryActivityWinHis(ActivityWinHisBean bean) {
		// TODO Auto-generated method stub
		return activityDao.queryActivityWinHis(bean);
	}
	
	

	/**
	 * 根据用户信息和活动编码，将用户加入活动表中
	 * 
	 */
	@Override
	public ActivityUserRelationBean joinActivityRelation(UserWechatBean user,String activity_code){
		
		ActivityConfigBean activityConf = activityConfigService.findActivityConfigByCode(activity_code);
		ActivityUserRelationBean bean = activityDao.findRelation(user.getOpenid(), activityConf.getId());
		if(bean == null){
			
			//用户中奖的商品
			ActivityProductBean ab = randomActivityProduct(activityConf);
			
			if(ab != null)
			{
				bean = new ActivityUserRelationBean();
				bean.setAct_prod_id(ab.getId());
				bean.setActivity_config_id(ab.getActivity_id());
				bean.setActivity_name(activityConf.getName());
				bean.setActivity_time(activityConf.getCreate_time());
				bean.setActivity_type(activityConf.getActivity_type());
				bean.setCategory(null);
				bean.setCreate_time(DateUtil.getNowDateStr());
				bean.setJoin_date(DateUtil.getNowDateStr());
				bean.setOpenid(user.getOpenid());
				bean.setProduct_name(ab.getProduct_name());
				bean.setStatus(ActivityUserRelationBean.STATUS_WAIT);
				bean.setUser_id(user.getId());
				bean.setUser_ip(null);
				bean.setShare_status(ActivityUserRelationBean.SHARE_STATUS_NO);
				
				activityDao.saveActivityUserRelateion(bean);
			}
		}
		
		return bean;
	}
	
	
	/**
	 * 随机获取商品
	 * @param config
	 * @return
	 */
	private ActivityProductBean randomActivityProduct(ActivityConfigBean config){
		ActivityProductBean bean = new ActivityProductBean();
		bean.setActivity_id(config.getId());
		//查询这个活动下配置的商品
		List<ActivityProductBean> activityProductList = activityDao.queryActivityProductList(bean);
		
		Integer weight = 0 ;
		for(ActivityProductBean apb : activityProductList){
			//计算权重总和 和范围
			if(apb.getWeight() != null && apb.getWeight() >0){
				
				apb.setWeight_min(weight);
				weight += apb.getWeight();
				
				apb.setWeight_max(weight);
			}
		}
		
		if(weight == 0) return null;
		
		//随机权重里的取值
		int index = RandomUtil.nextInt(weight);
		//判断取值是在哪个范围
		for(ActivityProductBean apb : activityProductList){
			
			if(apb.getWeight() != null && apb.getWeight() >0  && index< apb.getWeight_max() && index >= apb.getWeight_min()){
				
				bean = apb;
			}
		}
		
		
		return bean;
	}
	
	@Override
	public ActivityUserRelationBean findUserRelation(String activity_code,String openid){
		
//		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
		ActivityConfigBean activityConf = activityConfigService.findActivityConfigByCode(activity_code);
		ActivityUserRelationBean bean = activityDao.findRelation(openid, activityConf.getId());
		
		return bean;
	}
	
	@Override
	public ActivityUserRelationBean findUserRelation(String openid,int relation_id){
		
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setOpenid(openid);
		bean.setId(relation_id);
		return activityDao.findRelationByBean(bean );
		
	}
	
	/**
	 * 重新执行活动，通过测试表
	 * @return 
	 */
	@Override
	public List<ActivityUserRelationBean> findUserRelationByTestTable(){
		
		return activityDao.findUserRelationByTestTable();
	}

	/**
	 * (关于摇一摇活动，同一个手机号码可以多次领取流量的BUG修复)
	 * 该方法，通过活动类型和手机号获取返回值
	 */
	@Override
	public boolean checkPhoneHasJoinedRelation(String openid, int relation_id, String phone) {
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setOpenid(openid);
		bean.setId(relation_id);
		bean.setStatus(ActivityUserRelationBean.STATUS_USED);
		bean = activityDao.findRelationByBean(bean);
		if(bean == null){
			return false;
		}
		int activity_config_id = bean.getActivity_config_id();
		List<ActivityUserRelationBean> activityList = activityDao.findUserRelationByPhone(activity_config_id, phone);
		if(activityList.size() > 0){
			//该号码在该活动已经享用过，返回true
			log.error("shakeAddPhone , openid:"+ openid +",relation_id:"+relation_id+",phone:"+phone+",重复提交！");
			ActivityUserRelationBean relationNo = new ActivityUserRelationBean();
			relationNo.setPhone(phone);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查询用户参加奥运竞猜活动的信息
	 * @param user_id
	 * @return
	 */
	@Override
	public List<ActivityOlympicGuessBean> queryUserOlympicGuessInfo(int user_id){
		
		ActivityOlympicGuessBean bean = new ActivityOlympicGuessBean();
		bean.setUser_id(user_id);
		return activityDao.queryOlympicGuessInfo(bean );
	}
	
	/**
	 * 参加奥运竞猜活动方法
	 * @param openid
	 * @param guess_num
	 * @return
	 */
	@Override
	public String joinOlympicGuessActivity(String openid,int guess_num,int shared){
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
		Date date = new Date();
		String today = DateUtil.formatDateOnlyDate(date);
		
		ActivityOlympicGuessBean condition = new ActivityOlympicGuessBean();
		condition.setUser_id(user.getId());
		condition.setGuess_date(today);
		
		List<ActivityOlympicGuessBean> list = activityDao.queryOlympicGuessInfo(condition);
		if(list.size()>0){
			return "您已经参加过活动啦！您竞猜明后两天总奖牌数为："+ list.get(0).getGuess_num();
		}else{
			
			condition.setCreate_time(DateUtil.formatDateSSS(date));
			condition.setGuess_num(guess_num);
			condition.setStatus(ActivityOlympicGuessBean.STATUS_JOINED);
			condition.setShared(shared);
			activityDao.createOlympicGuessInfo(condition);
		}
		
		return "参加成功！请等待开奖！";
	}
	
//	/**
//	 * 处理奥运竞猜结果
//	 * @param openid
//	 * @param guess_id
//	 */
//	public ActivityOlympicGuessBean dealOlympicActivityResult(String openid,int guess_id){
//		
//		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
//		
//		ActivityOlympicGuessBean condition = new ActivityOlympicGuessBean();
//		condition.setUser_id(user.getId());
//		condition.setId(guess_id);
//		//取竞猜数据
//		List<ActivityOlympicGuessBean> result = activityDao.queryOlympicGuessInfo(condition );
//		ActivityOlympicGuessBean bean = null;
//		if(result.size() > 0){
//			bean = result.get(0);
//			
//			if(bean.getStatus() == ActivityOlympicGuessBean.STATUS_SUCCESS){
//				return bean;
//			}
//			//判断状态
//			if(bean.getStatus() == ActivityOlympicGuessBean.STATUS_WAIT && bean.getShared() == ActivityOlympicGuessBean.SHARED_NO){
//				
//				ActivityUserRelationBean relation = joinActivityRelation(user,ActivityConfigBean.CODE_OLYMPICGUESS_ACTIVITY);
//			}
//		}
//		return null;
//	}
	/**
	 * 查询奥运竞猜参加的信息
	 * @param openid
	 * @param guess_id
	 */
	@Override
	public ActivityOlympicGuessBean findOlympicGuess(String openid,int guess_id){
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
		
		ActivityOlympicGuessBean condition = new ActivityOlympicGuessBean();
		condition.setUser_id(user.getId());
		condition.setId(guess_id);
		//取竞猜数据
		List<ActivityOlympicGuessBean> result = activityDao.queryOlympicGuessInfo(condition );
		ActivityOlympicGuessBean bean = null;
		if(result.size() > 0){
			bean = result.get(0);
			
			return bean;
		}
		return null;
	}
	
	@Override
	public void updateOlympicGuess(ActivityOlympicGuessBean bean){
		activityDao.updateOlympicGuess(bean);
	}


	@Override
	public void createOrUpdateActivityInstance(ActivityInstanceBean bean) {
		activityDao.createOrUpdateActivityInstance(bean);
	}


	@Override
	public List<ActivityInstanceBean> queryActivityInstance(ActivityInstanceBean bean) {
		return activityDao.queryActivityInstance(bean);
	}


	@Override
	public ActivityUserRelationBean findUserRelationByUserAndInstanceId(String openid, int instanceId) {
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setOpenid(openid);
		bean.setActivity_config_id(instanceId);
		return activityDao.findRelationByBean(bean );
	}
	
	@Override
	public ActivityPrize recordUserInstanceActivityInfo(UserWechatBean user,ActivityInstanceBean instanceBean) {
		ActivityUserRelationBean newRelationBean = new ActivityUserRelationBean();
		ActivityPrize prize = new ActivityPrize();
		try {
			if(instanceBean.getLife_status() == ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE){
				//得到活动的start_hour集合
				ActivityConfigBean bean = activityConfigService.findActivityConfigByActivityIdAndCode(instanceBean.getActivity_id(), null);//(bean);
				//校验活动的前提条件
				prize = checkActivityParams(prize,bean,user,instanceBean);	
				if(StringUtil.isNotNull(prize.getRemark())){
//					if(prize.getErrorCode() == ActivityPrize.ACTIVITY_PRIZE_REPEAT){
//						prize.setRemark("您已经参与过该活动了！请勿重复参与！");
//					}
					return prize;
				}
				
				//活动没有过期
				if(!checkInstanceIsExpire(DateUtil.parseDateOnlyDate(instanceBean.getStart_day()),instanceBean,bean.getLife_age(),bean.getFrequency()) && checkInstanceIsStart(instanceBean)){
					//扣除积分信息
					int userPoints = user.getPoints();
					//剩余积分要大于所需积分
					//扣除积分
					userCommissionPointsService.createUserPointLog(user.getId(), -bean.getCost_points(), "秒杀活动【"+instanceBean.getName()+"】支付积分");
					user.setPoints(userPoints-bean.getCost_points());
					userWechatService.update(user);
					//参与人数+1
					instanceBean.setReal_join_num(instanceBean.getReal_join_num()+1);					
						//记录活动关于用户信息
					newRelationBean.setActivity_config_id(instanceBean.getId());
					newRelationBean.setUser_id(user.getId());
					newRelationBean.setOpenid(user.getOpenid());
					newRelationBean.setActivity_name(instanceBean.getName());
					newRelationBean.setActivity_time(instanceBean.getStart_hour()+":00");
					newRelationBean.setJoin_date(DateUtil.getNowDateStr());
					newRelationBean.setCategory(null);
					newRelationBean.setCreate_time(DateUtil.getNowDateStr());
					newRelationBean.setStatus(ActivityUserRelationBean.STATUS_WAIT);
					newRelationBean.setUser_ip(null);
					newRelationBean.setShare_status(ActivityUserRelationBean.SHARE_STATUS_NO);
						//得到奖品信息
					prize = getPrizeByActivity(bean);
					prize = dealUserPrize(prize,newRelationBean,instanceBean,bean);
					this.createOrUpdateActivityInstance(instanceBean);
					return prize;
				}
			}	
			prize.setRemark("该活动不在活动周期内，请关注正在开放的活动！");
			return prize;
		} catch (Exception e) {
			log.error(e, e);
			prize = new ActivityPrize();
			prize.setRemark("活动失效！");
			prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
			return prize;
		}
		
	}
	
	/**
	 * 处理用户得到的奖品
	 * @param prize
	 * @param newRelationBean
	 * @param instanceBean
	 * @return
	 */
	private ActivityPrize dealUserPrize(ActivityPrize prize,ActivityUserRelationBean newRelationBean,ActivityInstanceBean instanceBean,ActivityConfigBean bean){
		String remark = "";
		boolean to_relation = false;
		//不可重复参与的
		if(bean.getIs_repeat() ==  ActivityConfigBean.ACTIVITY_IS_REPEAT_NO || (!StringUtil.isNull(prize) && !prize.getErrorCode().equals(ActivityPrize.ERROR_CODE_NO_WINNER))){
			to_relation = true;
		}
		//获得奖品为空，或者奖品为未中奖
		if(StringUtil.isNull(prize) || ((!StringUtil.isNull(prize) && prize.getErrorCode().equals(ActivityPrize.ERROR_CODE_NO_WINNER)) && bean.getIs_repeat() ==  ActivityConfigBean.ACTIVITY_IS_REPEAT_YES)){
			remark = "您已经成功参与秒杀活动！";
			if(StringUtil.isNull(prize) ){
				prize = new ActivityPrize();
			}
//			prize = new ActivityPrize();
			prize.setErrorCode(ActivityPrize.ERROR_CODE_NO_WINNER);
			prize.setRemark(remark);
			newRelationBean.setProduct_name(prize.getPrize_name()+"[数量为0]");
			newRelationBean.setProduct_id(prize.getId());
			//未中奖的，单独存储
			if(to_relation){
				activityDao.saveActivityUserRelateion(newRelationBean);
			}else{
				activityDao.saveActivityUserRelateionNoPrize(newRelationBean);
			}
			return prize;
		}else{
				//奖品数量减少，
			remark = prize.getPrize_name();
			prize.setRemark("恭喜获得："+remark+"!");
			newRelationBean.setProduct_name(prize.getPrize_name());
			newRelationBean.setProduct_id(prize.getId());
			if(to_relation){
				activityDao.saveActivityUserRelateion(newRelationBean);
			}else{
				activityDao.saveActivityUserRelateionNoPrize(newRelationBean);
			}
			//更新活动实例的实际中奖人数
			if(!prize.getErrorCode().equals(ActivityPrize.ERROR_CODE_NO_WINNER)){
				instanceBean.setReal_win_num(instanceBean.getReal_win_num()+1);
			}
//			activityDao.createOrUpdateActivityInstance(instanceBean);
			prize.setRemain_num(prize.getRemain_num()+1);
			return prize;
		}
	}
	
	/**
	 * 判断活动原有的限定条件，数据库配置层面
	 * @param activityConfigBean
	 * @param user
	 * @return
	 */
	@Override
	public String checkLimitParams(ActivityConfigBean activityConfigBean,UserWechatBean user){
		String returnMag = "";
		try {
			//获取活动所需的条件
			ActicityLimitParamBean paramBean = new ActicityLimitParamBean();
			paramBean.setActivity_id(activityConfigBean.getId());
			paramBean.setStatus(ActicityLimitParamBean.ACTIVITY_LIMIT_PARAM_STATUS_ACTIVE);
			List<ActicityLimitParamBean> activityParamList = activityConfigService.queryActivityLimitParam(paramBean);
			//遍历条件，看看用户是否满足
			for (ActicityLimitParamBean acticityLimitParam : activityParamList) {
				String querySQL = acticityLimitParam.getParam_sql();
				//占位符填充
				querySQL = querySQL.replaceAll("#userid#", String.valueOf(user.getId()));
				int count = activityConfigService.queryBySql(querySQL);
				if(count <= 0){
					returnMag = acticityLimitParam.getRemark();
					break;
				}
			}
		} catch (Exception e) {
			returnMag = "查询有误！";
			log.error(e, e);
		}
		return returnMag;
	}
	
	/**
	 * 参与活动的前提条件校验
	 * @param activityConfigBean
	 * @param user_id
	 * @return
	 */
	@Override
	public ActivityPrize checkActivityParams(ActivityPrize prize,ActivityConfigBean activityConfigBean,UserWechatBean user,ActivityInstanceBean instanceBean){
		String returnMag = checkLimitParams(activityConfigBean,user);
		if(!StringUtil.isNotNull(returnMag)){
			//得到用户参与记录
			ActivityUserRelationBean reBean = new ActivityUserRelationBean();
			reBean.setOpenid(user.getOpenid());
			reBean.setActivity_config_id(instanceBean.getId());
			List<ActivityUserRelationBean> relationBean = activityDao.findRelationByInstanceBean(reBean);
			
			if(StringUtil.isNull(instanceBean)){
				returnMag = "实例不可用!";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}
			if(instanceBean.getLife_status() != ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE){
				returnMag = "实例不可用!";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}
			//用户已经参与活动并且活动不可重复参加
			if(relationBean.size() > 0 && activityConfigBean.getIs_repeat() == ActivityConfigBean.ACTIVITY_IS_REPEAT_NO){
				if(StringUtil.isNull(relationBean.get(0).getProduct_id())){
					prize.setErrorCode(ActivityPrize.ERROR_CODE_REPEAT);
					prize.setRemark("您已经参与该活动了，请勿重复参与！");
					return prize;
				}else{
					prize = activityDao.findActivityPrizeById(relationBean.get(0).getProduct_id());
					return prize;
				}
			}
			//校验活动起止时间
			if(!checkActivityIsInLifeCycle(activityConfigBean)){
				returnMag = "非正在进行活动！不初始化实例";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}

			//活动有没有超过最大人数
			if(instanceBean.getMax_join_num() <= instanceBean.getReal_join_num()){
				returnMag = "活动太火爆了，本轮活动名额已满，欢迎关注下一阶段活动！";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}
			//活动有没有超过最大中奖人数
			if(instanceBean.getMax_win_num() <= instanceBean.getReal_win_num()){
				returnMag = "活动太火爆了，本轮奖品已被领完，欢迎关注下一阶段活动！";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}

			if(instanceBean.getActivity_id().intValue() != activityConfigBean.getId().intValue()){
				returnMag = "实例非法！";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}
			int userPoints = user.getPoints();
			//剩余积分要大于所需积分
			if(userPoints < activityConfigBean.getCost_points()){
				returnMag ="您的积分不足，您可以每日签到获得积分！";
				prize.setRemark(returnMag);
				prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
				return prize;
			}

		}
		prize.setRemark(returnMag);
		prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
		return prize;
	}
	
	/**
	 * 参与活动的前提条件校验（简易校验,校验后不需要生成数据的）
	 * @param activityConfigBean
	 * @param user_id
	 * @return
	 */
	@Override
	public ActivityPrize checkActivityParamsEasy(ActivityPrize prize,ActivityConfigBean activityConfigBean,UserWechatBean user,ActivityInstanceBean instanceBean){
		//校验活动起止时间，校验活动的前提条件
		if(!checkActivityIsInLifeCycle(activityConfigBean)){
			String remark = "活动还未开始，开始时间:"+activityConfigBean.getStart_date();
			prize.setRemark(remark);
			prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
			return prize;
		}
		if(activityConfigBean.getStatus() == ActivityConfigBean.ACTIVITY_STATUS_DISABLE){
			String remark = "活动需要是可用状态！";
			prize.setRemark(remark);
			prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
			return prize;
		}
		String returnMag = checkLimitParams(activityConfigBean,user);
		if(!StringUtil.isNotNull(returnMag)){
			//得到用户参与记录
			ActivityUserRelationBean reBean = new ActivityUserRelationBean();
			reBean.setOpenid(user.getOpenid());
			reBean.setActivity_config_id(instanceBean.getId());
			List<ActivityUserRelationBean> relationBean = activityDao.findRelationByInstanceBean(reBean);
			//用户已经参与活动并且活动不可重复参加
			if(relationBean.size() > 0 && activityConfigBean.getIs_repeat() == ActivityConfigBean.ACTIVITY_IS_REPEAT_NO){
				if(StringUtil.isNull(relationBean.get(0).getProduct_id())){
					prize.setErrorCode(ActivityPrize.ERROR_CODE_REPEAT);
					prize.setRemark("您已经参与该活动了，请勿重复参与！");
					return prize;
				}else{
					prize = activityDao.findActivityPrizeById(relationBean.get(0).getProduct_id());
					return prize;
				}
			}
		}
		prize.setRemark(returnMag);
		prize.setErrorCode(ActivityPrize.ERROR_CODE_FAIL);
		return prize;
	}
	
	/**
	 * 根据活动返回该活动获得的奖品
	 */
	private ActivityPrize getPrizeByActivity(ActivityConfigBean configBean){
		ActivityPrize prize = new ActivityPrize();
		ActivityProductBean bean = new ActivityProductBean();
		bean.setActivity_id(configBean.getId());
		//查询这个活动下配置的商品
		List<ActivityProductBean> activityProductList = activityDao.queryActivityProductList(bean);
		//根据权重获取奖品
		ActivityProductBean activityProductBean = randomActivityProduct(configBean);
		if(StringUtil.isNull(activityProductBean)){
			return null;
		}
		//得到奖品信息
		prize = activityDao.findActivityPrizeById(activityProductBean.getPrize_id());
		for (ActivityProductBean apBean : activityProductList) {
			if(apBean.getWeight_type() == ActivityProductBean.WEIGHT_TYPE_WEIGHT){
				return prize;
			}else if(apBean.getWeight_type() == ActivityProductBean.WEIGHT_TYPE_RATE){
				//匹配商品
				if(apBean.getPrize_id().intValue()== prize.getId().intValue()){
					//最后一个奖品
					if(prize.getRemain_num() == ActivityPrize.ACTIVITY_PRIZE_LAST_PRIZE){
						apBean.setReal_num(ActivityProductBean.REAL_NUM_MIN_VALUE);
						activityDao.updateActivityProduct(apBean);
					}
					prize.setRemain_num(prize.getRemain_num()-1);
					activityDao.createOrUpdateActivityPirze(prize);
					return prize;
				}
			}
		}
		return prize;
	}
	/**
	 * 初始化活动实例
	 */
	@Override
	public void initActivity(ActivityConfigBean activityConfigBean, int user_id) {
		ActivityInstanceBean instanceBean = new ActivityInstanceBean();
		instanceBean.setActivity_id(activityConfigBean.getId());
	    instanceBean.setFrequency(activityConfigBean.getFrequency());
		instanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE);
		List<ActivityInstanceBean> instanceList = queryActivityInstance(instanceBean);
		//销毁已失效的活动实例
		destroyExpireActivityInstance(activityConfigBean);
		//初始化活动实例
		createActivityInstance(activityConfigBean,instanceList);
	}
	
	/**
	 * 判断活动是否在可用范围内
	 * @return
	 */
	private boolean checkActivityIsInLifeCycle(ActivityConfigBean activityConfigBean){
		boolean flag = false;
		try {
			Date startDate = DateUtil.parseDateOnlyDate(activityConfigBean.getStart_date());
			Date endDate = DateUtil.parseDateOnlyDate(activityConfigBean.getEnd_date());
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			end.add(Calendar.DAY_OF_MONTH, 1);
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Date nowDate = new Date();
			if(nowDate.before(end.getTime()) && nowDate.after(start.getTime())){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return flag;
	}
	
	/**
	 * 返回距离现在最近的日期
	 * @param cal
	 * @return
	 */
	private Calendar returnNearTime(String start_times,int frequency){
		//初始化现在时间
		Calendar todayCal = Calendar.getInstance();
		Date nowTime = new Date();

		todayCal.setTime(nowTime);
		long flag = todayCal.getTimeInMillis();
		if(!StringUtil.isNotNull(start_times) || frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_DAY){
			return todayCal;
		}
		List<String> timesList = Arrays.asList(start_times.split(","));
		List<Calendar> calList = new ArrayList<Calendar>();
		//活动开始时间（小时）
		for (String time : timesList) {
			Calendar cal = Calendar.getInstance();
			nowTime.setHours(0);
			nowTime.setSeconds(0);
			nowTime.setMinutes(0);
			cal.setTime(nowTime);
			int day = Integer.valueOf(time);
			//周循环的活动，要对日期进行转换,礼拜一  对应Calendar是2,所以monday=day+1
			if(frequency != ActivityConfigBean.ACTIVITY_FREQUENCY_MONTH){
				day = day + 1;
				if(day > Calendar.SATURDAY){
					day = Calendar.SUNDAY;
				}
				cal.set(Calendar.DAY_OF_WEEK, day);
			}else{
				cal.set(Calendar.DAY_OF_MONTH, day);
			}
			calList.add(cal);
		}
		
		boolean isFirst = true;
		for (Calendar calendar : calList) {
			if(calendar.get(Calendar.DAY_OF_MONTH) == todayCal.get(Calendar.DAY_OF_MONTH) && frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_MONTH){
				return calendar;
			}
			if(calendar.get(Calendar.DAY_OF_WEEK) == todayCal.get(Calendar.DAY_OF_WEEK) && frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_WEEK){
				return calendar;
			}
			long diff = calendar.getTimeInMillis() - todayCal.getTimeInMillis();
			if(isFirst && diff > 0){
				flag = calendar.getTimeInMillis();
				isFirst = false;
			}else if(diff > 0){
				if(diff <= flag){
					flag = calendar.getTimeInMillis();
				}
			}
		}
		todayCal.setTimeInMillis(flag);
		return todayCal;
	}
	
	/**
	 * 返回距离现在最近的日期(多个),number:返回的时间数
	 * @param cal
	 * @return 
	 */
	private static List<Calendar> returnNearTimes(String start_times,int frequency,int number){
		List<Calendar> returnCal = new ArrayList<Calendar>();
		//初始化现在时间
		Calendar todayCal = Calendar.getInstance();
		Date nowTime = new Date();
		todayCal.setTime(nowTime);
		
		//每天生成，生成今天个明天的实例
		if(frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_DAY){
			for(int i=0;i<number;i++){
				Calendar newCal = Calendar.getInstance();
				newCal.setTime(nowTime);
				newCal.add(Calendar.DAY_OF_MONTH, i);
				returnCal.add(newCal);
			}
			return returnCal;
			//一直有效的就直接返回今天
		}else if(frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER){
			returnCal.add(todayCal);
			return returnCal;
		}else{
			List<String> timesList = Arrays.asList(start_times.split(","));
			List<Calendar> calList = new ArrayList<Calendar>();
			for (String time : timesList) {
				Calendar cal = Calendar.getInstance();
				nowTime.setHours(0);
				nowTime.setSeconds(0);
				nowTime.setMinutes(0);
				cal.setTime(nowTime);
				int day = Integer.valueOf(time);
				//周循环的活动，要对日期进行转换,礼拜一  对应Calendar是2,所以monday=day+1
				if(frequency != ActivityConfigBean.ACTIVITY_FREQUENCY_MONTH){
					day = day + 1;
					if(day > Calendar.SATURDAY){
						day = Calendar.SUNDAY;
					}
					cal.set(Calendar.DAY_OF_WEEK, day);
				}else{
					cal.set(Calendar.DAY_OF_MONTH, day);
				}
				calList.add(cal);
			}
			Collections.sort(calList);
			for(int i=0;i<number;i++){
				for (Calendar calendar : calList) {
					Calendar newCal = Calendar.getInstance();
					newCal.setTime(calendar.getTime());
					long diff = newCal.getTimeInMillis() - todayCal.getTimeInMillis();
					if(returnCal.size() >= number){
						return returnCal;
					}
					if(diff > 0){
						returnCal.add(newCal);
					}else if(newCal.get(Calendar.DAY_OF_MONTH) == todayCal.get(Calendar.DAY_OF_MONTH) && frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_MONTH){
						returnCal.add(newCal);
					}else if(newCal.get(Calendar.DAY_OF_WEEK) == todayCal.get(Calendar.DAY_OF_WEEK) && frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_WEEK){
						returnCal.add(newCal);
					}
				}
				for (Calendar calendar : calList) {
					if(frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_MONTH){
						calendar.add(Calendar.MONTH, 1);
					}else if(frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_WEEK){
						calendar.add(Calendar.WEEK_OF_YEAR, 1);
					}
				}
			}
		}
		return returnCal;
	}
	
	public static void main(String[] arg){
//		List<Calendar> returnCal = returnNearTimes("1,2,3,4,5,6,7",2,100);
//		for (Calendar calendar : returnCal) {
//			System.out.println(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
//		}
		
		String str = "2016-09-08 12:12:12.123";
		try {
			DateUtil.parseDate(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
	/**
	 * 初始化活动实例，根据活动的可参与时间，start_hour 生成相应的活动实例
	 * @param activity_config_id
	 */
	private void createActivityInstance(ActivityConfigBean bean,List<ActivityInstanceBean> instanceList){
		Calendar dateCal = Calendar.getInstance();  
		Date nowDate = new Date();
		dateCal.setTime(nowDate);
		//开始时间，每天开始的小时
		String start_hours = bean.getStart_hour();
		int frequency = bean.getFrequency();
	    List<Calendar> todayCalList = returnNearTimes(bean.getStart_times(),frequency,2);
		for (Calendar todayCal : todayCalList) {
			Date endDate;
			try {
				//活动结束的时间就不生成实例了
				endDate = DateUtil.parseDateOnlyDate(bean.getEnd_date());
				Calendar end = Calendar.getInstance();
				end.setTime(endDate);
				end.add(Calendar.DAY_OF_MONTH, 1);
				if(end.before(todayCal)){
					continue;
				}
			} catch (ParseException e) {
				log.error(e, e);
			}
		String startDate = returnStartDate(todayCal);
	    //开始小时不能为空
	    if(StringUtil.isNotNull(start_hours)){
	    	List<String> hoursList = Arrays.asList(start_hours.split(","));
			//根据实例的开始时间，依次生成
			for (String hour : hoursList) {
				ActivityInstanceBean queryBean = new ActivityInstanceBean();
				queryBean.setActivity_id(bean.getId());
				queryBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE);
				queryBean.setStart_hour(Integer.valueOf(hour));
				queryBean.setFrequency(bean.getFrequency());
				queryBean.setStart_day(startDate);
				if(checkActivityInstanceIsExist(instanceList,queryBean,bean.getFrequency(),bean.getLife_age())){
					//创建实例
					//对于每天的，将实例全部生成；对于每周 或者每月的，只生成最近一次日期的
					if(frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_WEEK){
						createInstanceActivityBean(bean,hour,todayCal.get(Calendar.DAY_OF_WEEK)-1,startDate);
					}else{
						createInstanceActivityBean(bean,hour,todayCal.get(Calendar.DAY_OF_MONTH),startDate);
					}
				}
			}
	    }
		}
	}
	
	/**
	 * 创建活动实例
	 * @param bean
	 * @param hour
	 * @param time
	 * @param todayCal
	 */
	private void createInstanceActivityBean(ActivityConfigBean bean,String hour,int time,String startDate){
		ActivityInstanceBean instanceBean = new ActivityInstanceBean();
		instanceBean.setActivity_id(bean.getId());
		instanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE);
		instanceBean.setStart_hour(Integer.valueOf(hour));
		instanceBean.setFrequency(bean.getFrequency());
		instanceBean.setTime(time);
		instanceBean.setStart_day(startDate);
		instanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE);
		instanceBean.setCreatedate(DateUtil.getNowDateStrSSS());
		instanceBean.setCode(bean.getCode());
		instanceBean.setLife_age(bean.getLife_age());
		instanceBean.setName(hour+"点，"+bean.getName());
		instanceBean.setMax_join_num(bean.getMax_join_num());
		instanceBean.setMax_win_num(bean.getMax_win_num());
		instanceBean.setReal_win_num(ActivityInstanceBean.ACTIVITYINSTANCE_REAL_WINNUM_DEFAULT);
		instanceBean.setReal_join_num(ActivityInstanceBean.ACTIVITYINSTANCE_REAL_JOINNUM_DEFAULT);
		createOrUpdateActivityInstance(instanceBean);
	}
	
	/**
	 * 判断活动实例是否已经生成,已销毁的活动实例重新启用
	 * @param instanceBean frequency life_age
	 * @return
	 */
	private boolean checkActivityInstanceIsExist(List<ActivityInstanceBean> instanceList,ActivityInstanceBean instanceBean,int frequency,int life_age){
		boolean isExist = false;
		//对于活动长久有效的实例，不需要校验startday
		if(instanceBean.getFrequency() == ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER && instanceList.size() > 0){
			return false;
		}
		if(instanceList.size() == 0){
			isExist = true;
		}
		//1、判断实例是否存在
		for (ActivityInstanceBean activityInstanceBean : instanceList) {
			try {
				if(activityInstanceBean.getActivity_id().intValue() == instanceBean.getActivity_id().intValue()
					&& instanceBean.getLife_status().intValue() == activityInstanceBean.getLife_status().intValue()
					&& instanceBean.getStart_hour().intValue() == activityInstanceBean.getStart_hour().intValue()
					&& instanceBean.getFrequency().intValue() == activityInstanceBean.getFrequency().intValue()
					&& instanceBean.getStart_day().equals(activityInstanceBean.getStart_day())){
					isExist = false;
						break;
				}
			} catch (Exception e) {
				log.error(e, e);
				continue;
			}
			isExist = true;
		}
		// 需要新增，判断该实例是否在活动的周期内；
		if (isExist) {
			isExist = !checkInstanceIsExpire(new Date(), instanceBean,life_age, frequency);
		}
	return isExist;
	}
	
	/**
	 * 销毁不在时间周期内的且已经失效的的活动实例，
	 * @param bean
	 */
	private void destroyExpireActivityInstance(ActivityConfigBean bean){
		try {
			ActivityInstanceBean instanceBean = new ActivityInstanceBean();
			instanceBean.setActivity_id(bean.getId());
			instanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE);
			List<ActivityInstanceBean> instanceList = queryActivityInstance(instanceBean);
			for (ActivityInstanceBean activityInstanceBean : instanceList) {
				//检查并销毁失效实例
				checkAndDestoryActivityInatance(activityInstanceBean,bean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	/**
	 * 检查并销毁失效实例
	 * @param activityInstanceBean
	 * @param bean
	 */
	private void checkAndDestoryActivityInatance(ActivityInstanceBean activityInstanceBean,ActivityConfigBean bean){
		try {
			int life_age = bean.getLife_age();
			int frequency = bean.getFrequency();
			
			String start_hours = bean.getStart_hour();
			List<String> hours = Arrays.asList(start_hours.split(","));
			Calendar dateCal = Calendar.getInstance(); 
			Date nowTime = new Date();
			dateCal.setTime(nowTime); 
			List<Calendar> todayCalList = returnNearTimes(bean.getStart_times(),frequency,2);
			for (Calendar todayCal : todayCalList) {
				String date = returnStartDate(todayCal);
				String startDate = returnStartDate(dateCal);			//
				if ((startDate.compareTo(activityInstanceBean.getStart_day()) > 0 && activityInstanceBean.getFrequency() != ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER) || checkInstanceIsExpire(DateUtil.parseDateOnlyDate(activityInstanceBean.getStart_day()), activityInstanceBean,life_age, frequency)) {
					activityInstanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_DIE);
					createOrUpdateActivityInstance(activityInstanceBean);
				} else {
					// 遍历活动的时间集合，销毁不在活动集合里的实例
					boolean isExist = false;
					for (String hour : hours) {
						int instanceHour = activityInstanceBean.getStart_hour();
						
						//永久有效的活动不销毁
						if(activityInstanceBean.getFrequency() == ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER){
							isExist = true;
						//有周期性的，过期要销毁
						}else if (Integer.valueOf(hour).intValue() == instanceHour&& date.compareTo(activityInstanceBean.getStart_day()) >= 0) {
							isExist = true;
						}
						if(date.compareTo(activityInstanceBean.getStart_day()) < 0){
							isExist = true;
						}
					}
					if (!isExist) {
						activityInstanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_DIE);
						createOrUpdateActivityInstance(activityInstanceBean);
					}
				}
			}
			
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	public String returnStartDate(Calendar todayCal){
		int intMonth = todayCal.get(Calendar.MONTH)+1;
		String month = StringUtil.fixIntToString(intMonth,2);
		return todayCal.get(Calendar.YEAR)+"-"+month+"-"+ StringUtil.fixIntToString(todayCal.get(Calendar.DAY_OF_MONTH),2);
	}
	
	/**
	 * 判断实例是否过期,true已过期；false未过期
	 * @param hour
	 * @param life_age
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean checkInstanceIsExpire(Date date,ActivityInstanceBean bean,long life_age,int frequency){
		boolean isExpire = false;
		try {
			Date nowTime = new Date();
			long now_time = nowTime.getTime();
		    Calendar todayCal = Calendar.getInstance();  
		    Calendar dateCal = Calendar.getInstance();  
		    todayCal.setTime(nowTime);  
		    dateCal.setTime(date);
		   
		    String todayCalTime = returnStartDate(todayCal);
		    //实例时间较大，直接返回
		    if(todayCalTime.compareTo(bean.getStart_day()) < 0){
		    	return false;
		    }
		    String dateCalTime = returnStartDate(dateCal);
		    String todayCalMonth = todayCal.get(Calendar.YEAR) + "_" + todayCal.get(Calendar.MONTH);
		    String dateCalMonth = dateCal.get(Calendar.YEAR) + "_" + dateCal.get(Calendar.MONTH);
		    String todayCalWeek = todayCal.get(Calendar.YEAR) + "_" + todayCal.get(Calendar.WEEK_OF_YEAR);
		    String dateCalWeek = dateCal.get(Calendar.YEAR) + "_" + dateCal.get(Calendar.WEEK_OF_YEAR);
			if((frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_DAY && !todayCalTime.equals(dateCalTime))
					 || (frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_MONTH && !todayCalMonth.equals(dateCalMonth))
					 || (frequency == ActivityConfigBean.ACTIVITY_FREQUENCY_WEEK && !todayCalWeek.equals(dateCalWeek))){
				isExpire = true;
			}else{
			    int instanceHour = bean.getStart_hour();
				nowTime.setHours(instanceHour);
				nowTime.setSeconds(0);
				nowTime.setMinutes(0);
				//看看实例是否在生命周期内
				long instanceTime = nowTime.getTime()+(life_age*1000);
				if(instanceTime < now_time){
					isExpire = true;
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return isExpire;
	}
	
	/**
	 * 判断活动是否开始
	 * @param bean
	 * @param life_age
	 * @return
	 */
	private boolean checkInstanceIsStart(ActivityInstanceBean bean){
		boolean isStart = false;
		Date date = new Date();
		date.setHours(bean.getStart_hour());
		date.setSeconds(0);
		date.setMinutes(0);
		if(new Date().after(date)){
			isStart = true;
		}
		return isStart;
	}
	
	@Override
	public List<ActivityInstanceBean> findActivityInstanceList(UserWechatBean user,ActivityConfigBean activityConfigBean,int count) {
		List<ActivityInstanceBean> returnList = new ArrayList<ActivityInstanceBean>();
		ActivityInstanceBean instanceBean = new ActivityInstanceBean();
		instanceBean.setActivity_id(activityConfigBean.getId());
	    instanceBean.setFrequency(activityConfigBean.getFrequency());
//	    if(activityConfigBean.getFrequency() != ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER){
//	    	instanceBean.setStart_day(todayCal.get(Calendar.YEAR)+"_"+todayCal.get(Calendar.DAY_OF_YEAR));
//	    }
		instanceBean.setLife_status(ActivityInstanceBean.ACTIVITYINSTANCE_LIFE_STATUS_ACTIVE);
		List<ActivityInstanceBean> instanceList = queryActivityInstance(instanceBean);
		int i = 0;
		for (ActivityInstanceBean activityInstanceBean : instanceList) {
			if(activityInstanceBean.getReal_join_num()<activityInstanceBean.getMax_join_num() && activityInstanceBean.getReal_win_num() < activityInstanceBean.getMax_win_num()){
				if(i == count && count != 0){
					break;
				}
				returnList.add(activityInstanceBean);
				i++;
			}
		}
		for (ActivityInstanceBean activityInstanceBean : returnList) {
			List<ActivityRemindBean> remindList = new ArrayList<ActivityRemindBean>();
			//获得活动实例的提醒
			ActivityRemindBean bean = new ActivityRemindBean();
			bean.setInstance_id(activityInstanceBean.getId());
			bean.setOpenid(user.getOpenid());
			remindList = activityDao.queryActivityRemindByBean(bean);
			activityInstanceBean.setRemindList(remindList);
		}
		return returnList;
	}

	
	@Override
	public ActivityConfigBean findEnableActivityConfigByActivityIdAndCode(Integer id,String code) {
		ActivityConfigBean bean = activityConfigService.findActivityConfigByActivityIdAndCode(id,code);
		log.info("findEnableActivityConfigByActivityIdOrCode param:id:"+id+";code:"+code);
		if(StringUtil.isNull(bean) || (!StringUtil.isNull(bean) && bean.getStatus() == ActivityConfigBean.ACTIVITY_STATUS_DISABLE)){
			return null;
		}
		if(!StringUtil.isNotNull(bean.getActivity_img())){
			bean.setActivity_img(ActivityConfigBean.ACTIVITY_DEFALT_IMG);
		}
		if(!StringUtil.isNotNull(bean.getDescription_img())){
			bean.setDescription_img(ActivityConfigBean.ACTIVITY_DEFALT_IMG);
		}
		if(!StringUtil.isNotNull(bean.getShow_list_img())){
			bean.setShow_list_img(ActivityConfigBean.ACTIVITY_DEFALT_LIST_IMG);
		}
		//获得活动规则
		List<ActivityRule> returnRuleList = queryActivityRuleByActivityConfigId(bean.getId());
		bean.setRule(returnRuleList);
		return bean;
	}


	@Override
	public ActivityConfigBean findActivityConfigByCode(String code) {
		return activityConfigService.findActivityConfigByCode(code);
	}

	@Override
	public List<ActivityUserRelationBean> findUserRelationByPage(int start,int pagesize) {
		ActivityUserRelationBean bean = new ActivityUserRelationBean();
		bean.setNowpage(start);
		bean.setPageSize(pagesize);
		return activityDao.findUserRelationByPage(bean);
	}


	@Override
	public ActivityInstanceBean findActivityInstanceByIdAndCode(String id,String code) {
		ActivityInstanceBean bean = new ActivityInstanceBean();
		if(StringUtil.isNotNull(id)){
			bean.setId(Integer.valueOf(id));
			bean.setCode(code);
			List<ActivityInstanceBean> beanList = activityDao.queryActivityInstance(bean);
			if(beanList.size() > 0){
				return beanList.get(0);
			}
		}
		
		return null;
	}


	@Override
	public List<ActivityRule> queryActivityRuleByActivityConfigId(Integer activity_id){
		ActivityRule bean = new ActivityRule();
		bean.setActivity_id(activity_id);
		return activityDao.queryActivityRuleByBean(bean);
	}


	@Override
	public ActivityRemindBean remindUserJoinActivity(String openid, String instanceid) {
		try {
			//得到用户
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			//得到活动实例
			ActivityInstanceBean instanceBean = this.findActivityInstanceByIdAndCode(instanceid, null);
			instanceBean.setOpenid(user.getOpenid());
			Date nowDate = new Date();
			nowDate = DateUtil.parseDateOnlyDate(instanceBean.getStart_day());
			nowDate.setHours(instanceBean.getStart_hour());
			nowDate.setMinutes(0);
			nowDate.setMinutes(0);
			nowDate.setSeconds(0);
			//提前3分钟提醒
			nowDate.setTime(nowDate.getTime()-(2*60*1000));
			ActivityRemindBean bean = new ActivityRemindBean();
			bean.setInstance_id(instanceBean.getId());
			bean.setOpenid(user.getOpenid());
			
			//判断用户是否已经设置了提醒
			List<ActivityRemindBean> remindList = activityDao.queryActivityRemindByBean(bean);
			if(remindList.size() == 0){
				//向消息中心发送指令，提前通知用户活动开始
				msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ACTIVITY_REMIND, instanceBean.getCode(), instanceBean, DateUtil.formatDate(nowDate));
				bean.setInstance_name(instanceBean.getName());
				bean.setRemind_time(DateUtil.formatDateSSS(nowDate));
				bean.setCreate_time(DateUtil.getNowDateStrSSS());
				activityDao.createOrInsertActivityRemind(bean);
			}else{
				return remindList.get(0);
			}
			return bean;
		} catch (Exception e) {
			log.error(e, e);
			ActivityRemindBean bean = new ActivityRemindBean();
			bean.setInstance_name("FAIL");
			return bean;
		}
	}

	
}
