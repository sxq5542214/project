package com.yd.business.activity.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityPrizeRelationBean;
import com.yd.business.activity.bean.ActivityPrizeRuleBean;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.activity.dao.IActivityConfigDao;
import com.yd.business.activity.dao.IActivityDao;
import com.yd.business.activity.dao.IActivityPrizeDao;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityPrizeService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;

@Service("activityPrizeService")
public class ActivityPrizeServiceImpl extends BaseService implements IActivityPrizeService {
	
	@Resource
	private IActivityPrizeDao activityPrizeDao;
	@Resource
	private IActivityDao activityDao;
	@Resource
	private IActivitConfigService activityConfigService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IWechatPayService wechatPayService;
	
	@Override
	public List<ActivityPrize> queryActivityPrizeByBean(ActivityPrize bean) {
		return activityPrizeDao.queryActivityPrize(bean);
	}
	
	@Override
	public List<ActivityPrizeRelationBean> queryActivityPrizeRelationByActivityId(int activityId){
		
		ActivityPrizeRelationBean bean = new ActivityPrizeRelationBean();
		bean.setActivity_id(activityId);
		List<ActivityPrizeRelationBean> listRelation = activityPrizeDao.queryActivityPrizeRelation(bean );
		return listRelation;
	}
	
	
	@Override
	public ActivityPrize findActivityPrizeByID(int id){
		ActivityPrize prize = new ActivityPrize();
		prize.setId(id);
		List<ActivityPrize> list = queryActivityPrizeByBean(prize);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public ActivityWinHisBean findActivityWinHisById(int id) {
		ActivityWinHisBean bean = new ActivityWinHisBean();
		bean.setId(id);
		List<ActivityWinHisBean> list = activityDao.queryActivityWinHis(bean );
		if(list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}
	
	
	@Override
	public ActivityPrize commitActivityPrizeForJson(String json) {
		ActivityPrize paramBean = new ActivityPrize();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(json);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,paramBean);
			if(!StringUtil.isNull(paramBean.getId()) && paramBean.getId() != 0){
				//判断名称是否有变更，有变更的同时跟新关联表
				ActivityPrize oldBean = activityPrizeDao.findActivityPrizeById(paramBean);
				if(!StringUtil.isNull(oldBean) && !StringUtil.isNull(paramBean.getPrize_name()) && !paramBean.getPrize_name().equals(oldBean.getPrize_name())){
					ActivityProductBean product = new ActivityProductBean();
					product.setPrize_id(oldBean.getId());
					List<ActivityProductBean> products = activityPrizeDao.queryActivityProductBean(product);
					for (ActivityProductBean activityProductBean : products) {
						activityProductBean.setProduct_name(paramBean.getPrize_name());
						activityPrizeDao.updataActivityProductBean(activityProductBean);
					}
				}
				activityPrizeDao.updataActivityPrizeBean(paramBean);
			}else{
				activityPrizeDao.insertActivityPrizeBean(paramBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		Map<String, List<DictionaryBean>> dicMap;
		try {
			dicMap = dictionaryService.getTableAttributuByDictionaryCache(paramBean.getClass().getSimpleName());
			paramBean.setDicMap(dicMap);
		} catch (Exception e) {
			log.error(e, e);
		}
		return paramBean;
	}

	@Override
	public void deleteActivityPrizeByIds(String ids) throws Exception {
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			if(StringUtil.isNotNull(id)){
				ActivityPrize bean = new ActivityPrize();
				bean.setId(Integer.valueOf(id));
				//校验该奖品是否已被活动关联
				ActivityProductBean pBean = new ActivityProductBean();
				pBean.setPrize_id(Integer.valueOf(id));
				List<ActivityProductBean> pList = activityDao.queryActivityProductList(pBean);
				if(pList.size() > 0){
					ActivityConfigBean configBean = activityConfigService.findActivityConfigByActivityIdAndCode(pList.get(0).getActivity_id(), null);
					throw new Exception("该奖品【"+pList.get(0).getProduct_name()+"】已关联活动【"+configBean.getName()+"】，不可删除");
				}else{
					activityPrizeDao.deleteActivityPrizeBean(bean);
				}
			}
		}
	}
	
	/**
	 * 处理用户获得奖品
	 * @param userId
	 * @param activityId
	 */
	@Override
	public String dealUserActivityPrize(UserWechatBean user,int activityId,int prizeId){
		String result = "";
		ActivityPrizeRelationBean bean = new ActivityPrizeRelationBean();
		bean.setActivity_id(activityId);
		bean.setActivity_prize_id(prizeId);
		List<ActivityPrizeRelationBean> relationList = activityPrizeDao.queryActivityPrizeRelation(bean );
		for(ActivityPrizeRelationBean relation : relationList){
			//是否能获取这个奖品
			boolean canGet = checkUserCanGetPrize(user, relation.getId());
			if(canGet){
				ActivityPrize prize = findActivityPrizeByID(relation.getActivity_prize_id());
				prize.setRemark(user.getNick_name());
				String taleName = prize.getProduct_table();
				switch (taleName) {
				case ActivityPrize.PRODUCT_TABLE_COUPONCONFIG:
					int couponId = prize.getProduct_id();
					//获取优惠卷
					result = supplierCouponService.reveiveCouponResult(couponId, user);
					
					if(result.indexOf("成功") >=0){
						//保存并处理用户动作
						msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE , null, prize);
						msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE_HELP_FRIEND_FRIENDS , null, prize);
						
					}
					break;
				case ActivityPrize.PRODUCT_TABLE_SUPPLIERPRODUCT:
					
					
					break;
				default:
					break;
				}

				//创建活动获奖历史
				activityService.createActivityWinHis(activityId, user, prize);
			}
		}
		
		return result;
	}
	
	
	/**
	 * 处理用户获得奖品
	 * @param userId
	 * @param activityId
	 */
	@Override
	public String dealUserActivityPrize(UserWechatBean user,int activityId,String activityCode){
//		String result = "";
		
		ActivityWinHisBean winHis = new ActivityWinHisBean();
		winHis.setActivity_config_id(activityId);
		winHis.setOpenid(user.getOpenid());
		
		//查询用户是否已中奖
		List<ActivityWinHisBean> winHisList = activityService.queryActivityWinHis(winHis );
		if(winHisList.size() > 0) {
			return winHisList.get(0).getPrize_name();
		}else {
			
			//待完善 参加但未中奖的情况
			
			
			//未参加过
			ActivityPrizeRelationBean bean = new ActivityPrizeRelationBean();
			bean.setActivity_id(activityId);
			List<ActivityPrizeRelationBean> relationList = activityPrizeDao.queryActivityPrizeRelation(bean );
			
			//通过权重计算中的奖品
			int weightSum = 0;
			for(ActivityPrizeRelationBean relation : relationList){
				weightSum += relation.getWeight();
			}
			
			if(weightSum <= 0) {
				return null;
			}
			
			int n = RandomUtil.nextInt(weightSum);
			int m = 0;
			Integer prizeId = null ;
			String prizeName = "没有中奖";
			for(ActivityPrizeRelationBean relation : relationList){
				 if (m <= n && n < m + relation.getWeight()) {  
					 prizeId = relation.getActivity_prize_id();
					 prizeName = relation.getPrize_name();
	                 break;  
	               }  
	               m += relation.getWeight();  
			}

			//创建活动获奖历史
			ActivityPrize prize = findActivityPrizeByID(prizeId);
			prize.setPrize_name(prizeName);
			activityService.createActivityWinHis(activityId, user, prize);

			return prizeName;
			
		}
		
	}
	
	
	@Override
	public String userReceiveWinHisPrize(UserWechatBean user,int winHisId ) {
		String result = "";
		ActivityWinHisBean bean = findActivityWinHisById(winHisId);
		if(bean == null || user == null || bean.getUser_id().intValue() != user.getId().intValue()) {
			result = "您没有获取该奖品";
		}else if(bean.getStatus() == ActivityWinHisBean.STATUS_ALREADYSEND) {
			result = "您已成功领取过该奖品";
		}else {
			result = userWinGetPrize(user,bean.getActivity_config_id(),bean.getPrize_id());
			
			if(result.indexOf("成功") >=0){
				//更新奖品发送状态和时间
				bean.setStatus(ActivityWinHisBean.STATUS_ALREADYSEND);
				updateActivityWinHis(bean);
			}
		}
		return result;
	}
	
	
	private String userWinGetPrize(UserWechatBean user,int activityId,Integer prizeId) {
		String result = "成功";
		if(prizeId == null || prizeId == 0) {
			return result;
		}
		
		ActivityPrize prize = findActivityPrizeByID(prizeId);
		prize.setRemark(user.getNick_name());
		int category = prize.getCategory();
		switch (category) {
		case ActivityPrize.CATEGORY_COUPON:
			int couponId = prize.getProduct_id();
			//获取优惠卷
			result = supplierCouponService.reveiveCouponResult(couponId, user);
			
			break;
		case ActivityPrize.CATEGORY_CASH_BONUS:
			// 现金红包 
			int money = prize.getBonus_money();
			Boolean flag = wechatPayService.payBonusLimit200(user.getOpenid(), money, null,prize.getRemark(),prize.getProduct_id());
			if(flag) {
				result = "红包已派发成功，请返回公众号菜单界面领取";
			}else {
				result = "红包派发失败，请联系客服人员";
			}
			break;
		default:
			break;
		}

		if(result.indexOf("成功") >=0){
			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE , null, prize);
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ACTIVITY_GET_PRIZE_HELP_FRIEND_FRIENDS , null, prize);
			
		}
//		result = prize.getPrize_name();
		return result;
	}
	
	
	/**
	 * 判断用户是否能获取这个奖品
	 * @return
	 */
	private boolean checkUserCanGetPrize(UserWechatBean user,int activityPrizeRelationId){
		ActivityPrizeRuleBean bean = new ActivityPrizeRuleBean();
		bean.setActivity_prize_relation_id(activityPrizeRelationId);
		List<ActivityPrizeRuleBean> ruleList = activityPrizeDao.queryPrizeRule(bean);
		for(ActivityPrizeRuleBean rule: ruleList){
			String sql = rule.getParam_sql();
			sql.replaceAll("#userID#", String.valueOf(user.getId()));

			int result = activityPrizeDao.execActivityPrizeRuleSQL(sql);
			if(result <= 0){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void updateActivityWinHis(ActivityWinHisBean bean) {
		bean.setModify_time(DateUtil.getNowDateStr());
		activityPrizeDao.updateActivityWinHis(bean);
	}
	
}
