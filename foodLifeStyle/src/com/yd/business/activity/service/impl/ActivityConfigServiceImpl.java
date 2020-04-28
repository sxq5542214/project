/**
 * 
 */
package com.yd.business.activity.service.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityConfigShowBean;
import com.yd.business.activity.bean.ActivityInstanceBean;
import com.yd.business.activity.bean.ActivityOriginalRelationBean;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.activity.controller.ActivityController;
import com.yd.business.activity.dao.IActivityConfigDao;
import com.yd.business.activity.dao.IActivityDao;
import com.yd.business.activity.dao.IActivityLimitParamDao;
import com.yd.business.activity.dao.IActivityOriginalRelationDao;
import com.yd.business.activity.dao.IActivityPrizeDao;
import com.yd.business.activity.dao.IActivityRuleDao;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("activityConfigService")
public class ActivityConfigServiceImpl extends BaseService implements IActivitConfigService {
	@Resource
	private IActivityConfigDao activityConfigDao;
	@Resource
	private IActivityDao activityDao;
	@Resource
	private IActivityLimitParamDao activityLimitParamDao;
	@Resource
	private IActivityRuleDao activityRuleDao;
	@Resource
	private IActivityPrizeDao activityPrizeDao;
	@Resource
	private IActivityOriginalRelationDao activityOringinalRelationDao;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	@Override
	public List<ActivityConfigBean> queryAllActivityConfig(Integer status){
		return activityConfigDao.queryAllActivityConfig(status);
	}
	
	@Override
	public List<ActivityConfigBean> queryActivityConfigByParam(Integer status,UserWechatBean user){
		List<ActivityConfigBean> returnList = new ArrayList<ActivityConfigBean>();
		//活动列表展示
		//获取活动所需的条件
		List<ActivityConfigBean> activityList = activityConfigDao.queryAllActivityConfig(status);
		for (ActivityConfigBean activityConfigBean2 : activityList) {
			if(!StringUtil.isNotNull(activityConfigBean2.getActivity_img())){
				activityConfigBean2.setActivity_img(ActivityConfigBean.ACTIVITY_DEFALT_IMG);
			}
			if(!StringUtil.isNotNull(activityConfigBean2.getDescription_img())){
				activityConfigBean2.setDescription_img(ActivityConfigBean.ACTIVITY_DEFALT_IMG);
			}
			if(!StringUtil.isNotNull(activityConfigBean2.getShow_list_img())){
				activityConfigBean2.setShow_list_img(ActivityConfigBean.ACTIVITY_DEFALT_LIST_IMG);
			}
			ActicityLimitParamBean paramBean = new ActicityLimitParamBean();
			paramBean.setActivity_id(activityConfigBean2.getId());
			paramBean.setStatus(ActicityLimitParamBean.ACTIVITY_LIMIT_PARAM_STATUS_ACTIVE);
			paramBean.setType(ActicityLimitParamBean.ACTIVITY_LIMIT_PARAM_TYPE_SHOW);
			List<ActicityLimitParamBean> activityParamList = queryActivityLimitParam(paramBean);
			//遍历条件，看看用户是否满足
			if(activityParamList.size() <= 0){
				returnList.add(activityConfigBean2);
			}
			for (ActicityLimitParamBean acticityLimitParam : activityParamList) {
				String querySQL = acticityLimitParam.getParam_sql();
				//占位符填充
				querySQL = querySQL.replace("#userid#", String.valueOf(user.getId()));
				if(StringUtil.isNotNull(querySQL)){
					int count = queryBySql(querySQL);
					if(count > 0){
						returnList.add(activityConfigBean2);
					}
				}else{
					returnList.add(activityConfigBean2);
				}
			}
		}
		return returnList;
	}
	
	@Override
	public void updateActivityConfigStatus(int activity_config_id, int status){
		activityConfigDao.updateActivityConfigStatus(activity_config_id, status);
	}
	
	@Override
	public ActivityConfigBean findActivityConfigByCode(String code){
		return activityConfigDao.findActivityConfigByCode(code);
	}

	@Override
	public List<ActivityConfigBean> returnActivityConfigByDate(List<ActivityConfigBean> activityConfigList,String dataType) {
		List<ActivityConfigBean> returnList = new ArrayList<ActivityConfigBean>();
		try {
			Date nowTime = new Date();
			
			if(dataType.equals(ActivityController.USER_ACTIVITY_FUTURE)){
				for (ActivityConfigBean activityConfigBean : activityConfigList) {
					if(StringUtil.isNotNull(activityConfigBean.getStart_date())){
						Date startTime = DateUtil.parseDateOnlyDate(activityConfigBean.getStart_date());
						if(startTime.getTime() >= nowTime.getTime()){
							returnList.add(activityConfigBean);
						}
					}
				}
			}else if(dataType.equals(ActivityController.USER_ACTIVITY_NOW)){
				for (ActivityConfigBean activityConfigBean : activityConfigList) {
					//8-23日结束，是到24:59:59结束的  所以要加(24 * 60 * 60 * 1000)
					if(StringUtil.isNotNull(activityConfigBean.getStart_date()) && StringUtil.isNotNull(activityConfigBean.getEnd_date())){
					Date endTime = DateUtil.parseDateOnlyDate(activityConfigBean.getEnd_date());
					Date startTime = DateUtil.parseDateOnlyDate(activityConfigBean.getStart_date());
					if(endTime.getTime()+ (24 * 60 * 60 * 1000) >= nowTime.getTime() && startTime.getTime() <= nowTime.getTime()){
						returnList.add(activityConfigBean);
					}
					}
				}
			}else if(dataType.equals(ActivityController.USER_ACTIVITY_HISTORY)){
				for (ActivityConfigBean activityConfigBean : activityConfigList) {
					if(StringUtil.isNotNull(activityConfigBean.getEnd_date())){
					Date endTime = DateUtil.parseDateOnlyDate(activityConfigBean.getEnd_date());
					if(endTime.getTime()+ (24 * 60 * 60 * 1000) < nowTime.getTime()){
						returnList.add(activityConfigBean);
					}
					}
				}
			}
		} catch (ParseException e) {
			log.error(e, e);
		}		
		return returnList;
	}

	@Override
	public List<ActivityConfigBean> queryActivityConfigByActivity(ActivityConfigBean bean) {
		
		return activityConfigDao.queryActivityConfigByActivity(bean);
	}

	@Override
	public int queryBySql(String querySQL) {
		return activityConfigDao.queryBySql(querySQL);
	}

	@Override
	public List<ActicityLimitParamBean> queryActivityLimitParam(ActicityLimitParamBean paramBean) {
		return activityConfigDao.queryActivityLimitParam(paramBean);
	}

	@Override
	public ActivityConfigBean findActivityConfigByActivityIdAndCode(Integer id,String code) {
		ActivityConfigBean bean = new ActivityConfigBean();
		bean.setId(id);
		bean.setCode(code);
		if(StringUtil.isNotNull(bean.getCode()) || !StringUtil.isNull(bean.getId())){
			List<ActivityConfigBean> configList = activityConfigDao.queryActivityConfigByActivity(bean);
			if(configList.size() > 0){
				return configList.get(0);
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public ActivityConfigShowBean getActivityConfigForMrg(Object id) {
		ActivityConfigShowBean returnBean = new ActivityConfigShowBean();
		ActivityConfigBean bean = new ActivityConfigBean();
		if(!StringUtil.isNull(id)){
			bean.setId(Integer.valueOf(id.toString()));
			List<ActicityLimitParamBean> paramList = new ArrayList<ActicityLimitParamBean>();
			//得到活动配置
			bean = this.findActivityConfigByActivityIdAndCode(bean.getId(),null);
			if(!StringUtil.isNull(bean)){
				//放置配置的基本信息
				makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_BASE_INFO,bean,ActivityConfigShowBean.SHOW_BEAN_BASE_INFO_CODE,bean);
				returnBean.setText(bean.getName());
				
				//放置限制参数
				ActicityLimitParamBean queryLimitBean = new ActicityLimitParamBean();
				queryLimitBean.setActivity_id(bean.getId());
				paramList = activityConfigDao.queryActivityLimitParam(queryLimitBean);
				makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_PARAM_INFO,paramList,ActivityConfigShowBean.SHOW_BEAN_PARAM_INFO_CODE,bean);
				
				//放置规则
				List<ActivityRule> ruleList = activityService.queryActivityRuleByActivityConfigId(bean.getId());
				makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_RULE_INFO,ruleList,ActivityConfigShowBean.SHOW_BEAN_RULE_INFO_CODE,bean);
				
				//放置奖品信息
				ActivityProductBean pBean = new ActivityProductBean();
				pBean.setActivity_id(bean.getId());
				//查询这个活动下配置的商品
				List<ActivityProductBean> activityProductList = activityDao.queryActivityProductList(pBean);
				makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_PRIZE_INFO,activityProductList,ActivityConfigShowBean.SHOW_BEAN_PRIZE_INFO_CODE,bean);
				
				//放置公众号信息
				ActivityOriginalRelationBean oBean = new ActivityOriginalRelationBean();
				oBean.setActivity_code(bean.getCode());
				//查询这个活动下配置的公众号
				List<ActivityOriginalRelationBean> activityOriginalList = activityDao.queryActivityOriginalRelationBean(oBean);
				for (ActivityOriginalRelationBean activityOriginalRelationBean : activityOriginalList) {
					WechatOriginalInfoBean o = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(activityOriginalRelationBean.getOriginal_id());
					if(!StringUtil.isNull(o)){
						activityOriginalRelationBean.setOriginal_name(o.getOriginal_name());
					}
				}
				makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_ORIGINAL_INFO,activityOriginalList,ActivityConfigShowBean.SHOW_BEAN_ORIGINAL_INFO_CODE,bean);
			}
		}else{
			bean.setName("新建配置");
			//放置配置的基本信息
			makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_BASE_INFO,bean,ActivityConfigShowBean.SHOW_BEAN_BASE_INFO_CODE,bean);
			returnBean.setText(bean.getName());
			
			makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_PARAM_INFO,null,ActivityConfigShowBean.SHOW_BEAN_PARAM_INFO_CODE,bean);
			
			//放置规则
			makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_RULE_INFO,null,ActivityConfigShowBean.SHOW_BEAN_RULE_INFO_CODE,bean);
			
			//放置奖品信息
			//查询这个活动下配置的商品
			makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_PRIZE_INFO,null,ActivityConfigShowBean.SHOW_BEAN_PRIZE_INFO_CODE,bean);
			
			//放置公众号信息
			makingReturnActivityConfBean(returnBean,ActivityConfigShowBean.SHOW_BEAN_ORIGINAL_INFO,null,ActivityConfigShowBean.SHOW_BEAN_ORIGINAL_INFO_CODE,bean);
		}
		
		return returnBean;
	}
	
	/**
	 * 构造属性节点信息
	 * @param returnBean
	 * @param info
	 * @param paramList
	 * @return
	 */
	private void makingReturnActivityConfBean(ActivityConfigShowBean returnBean,String info,Object paramList,String code,ActivityConfigBean config){
		ActivityConfigShowBean paramBean = new ActivityConfigShowBean();
		paramBean.setText(info);
		paramBean.setDetailInfo(paramList);
		paramBean.setCode(code);
		paramBean.setActivity_id(config.getId());
		paramBean.setActivity_code(config.getCode());
		returnBean.setNodes(paramBean);
	}

	@Override
	public ActicityLimitParamBean commitActivityLimitParamBeanForJson(String json) {
		ActicityLimitParamBean paramBean = new ActicityLimitParamBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(json);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,paramBean);
			if(!StringUtil.isNull(paramBean.getId()) && paramBean.getId() != 0){
				paramBean.setModify_time(DateUtil.getNowDateStrSSS());
				activityLimitParamDao.updataActicityLimitParamBean(paramBean);
			}else{
				paramBean.setCreate_time(DateUtil.getNowDateStrSSS());
				activityLimitParamDao.insertIntoActicityLimitParamBean(paramBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return paramBean;
	}

	@Override
	public void deleteActivityLimitParamBeanByIds(String ids) {
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			if(StringUtil.isNotNull(id)){
				ActicityLimitParamBean bean = new ActicityLimitParamBean();
				bean.setId(Integer.valueOf(id));
				activityLimitParamDao.deleteActicityLimitParamBean(bean);
			}
		}
	}
	
	@Override
	public ActivityRule commitActivityRuleForJson(String json) {
		ActivityRule paramBean = new ActivityRule();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(json);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,paramBean);
			if(!StringUtil.isNull(paramBean.getId()) && paramBean.getId() != 0){
				paramBean.setModify_time(DateUtil.getNowDateStrSSS());
				activityRuleDao.updataActivityRule(paramBean);
			}else{
				paramBean.setCreate_time(DateUtil.getNowDateStrSSS());
				activityRuleDao.insertIntoActivityRule(paramBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return paramBean;
	}

	@Override
	public void deleteActivityRuleByIds(String ids) {
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			if(StringUtil.isNotNull(id)){
				ActivityRule bean = new ActivityRule();
				bean.setId(Integer.valueOf(id));
				activityRuleDao.deleteActivityRule(bean);
			}
		}
	}
	
	@Override
	public ActivityProductBean commitActivityPrizeForJson(String json) {
		ActivityProductBean paramBean = new ActivityProductBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(json);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,paramBean);
			if(!StringUtil.isNull(paramBean.getId()) && paramBean.getId() != 0){
				paramBean.setModify_time(DateUtil.getNowDateStrSSS());
				activityPrizeDao.updataActivityProductBean(paramBean);
			}else{
				paramBean.setCreate_time(DateUtil.getNowDateStrSSS());
				activityPrizeDao.insertActivityProductBean(paramBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return paramBean;
	}

	@Override
	public void deleteActivityPrizeByIds(String ids) {
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			if(StringUtil.isNotNull(id)){
				ActivityProductBean bean = new ActivityProductBean();
				bean.setId(Integer.valueOf(id));
				activityPrizeDao.deleteActivityProductBean(bean);
			}
		}
	}
	@Override
	public ActivityOriginalRelationBean commitActivityOriginalForJson(String json) {
		ActivityOriginalRelationBean paramBean = new ActivityOriginalRelationBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(json);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,paramBean);
			if(!StringUtil.isNull(paramBean.getId()) && paramBean.getId() != 0){
				activityOringinalRelationDao.updataActivityOriginalRelation(paramBean);
			}else{
				activityOringinalRelationDao.insertIntoActivityOriginalRelation(paramBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return paramBean;
	}
	
	@Override
	public void deleteActivityOriginalByIds(String ids) {
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			if(StringUtil.isNotNull(id)){
				ActivityOriginalRelationBean bean = new ActivityOriginalRelationBean();
				bean.setId(Integer.valueOf(id));
				activityOringinalRelationDao.deleteActivityOriginalRelation(bean);
			}
		}
	}
	@Override
	public ActivityConfigBean commitActivityConfigForJson(String json) {
		ActivityConfigBean paramBean = new ActivityConfigBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(json);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,paramBean);
			if(!StringUtil.isNull(paramBean.getActivity_img_jump_url_code())){
				ConfigCruxBean cruxBean = configCruxService.getAttributeByTypeAndKey(ConfigCruxBean.TYPE_ACTIVITYIMG_JUMP_URL, paramBean.getActivity_img_jump_url_code());
				if(!StringUtil.isNull(cruxBean)){
					String url = cruxBean.getValue();
					paramBean.setActivity_img_jump_url(url);
				}
			}
			if(!StringUtil.isNull(paramBean.getActivity_jump_url_code())){
				ConfigCruxBean cruxBean = configCruxService.getAttributeByTypeAndKey(ConfigCruxBean.TYPE_ACTIVITY_JUMP_URL, paramBean.getActivity_jump_url_code());
				if(!StringUtil.isNull(cruxBean)){
					String url = cruxBean.getValue();
					paramBean.setActivity_jump_url(url);
				}
			}
			if(!StringUtil.isNull(paramBean.getId()) && paramBean.getId() != 0){
				activityConfigDao.updateActivityConfig(paramBean);
			}else{
				activityConfigDao.createActivityConfig(paramBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return paramBean;
	}
	
	@Override
	public void deleteActivityConfigByIds(String ids) {
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			if(StringUtil.isNotNull(id)){
				ActivityConfigBean bean = new ActivityConfigBean();
				bean.setId(Integer.valueOf(id));
				activityConfigDao.deleteActivityConfig(bean);
			}
		}
	}
	
}
