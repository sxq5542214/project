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
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.dao.IActivityConfigDao;
import com.yd.business.activity.dao.IActivityDao;
import com.yd.business.activity.dao.IActivityPrizeDao;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityPrizeService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.util.AutoInvokeGetSetMethod;
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
	private IDictionaryService dictionaryService;
	
	@Override
	public List<ActivityPrize> queryActivityPrizeByBean(ActivityPrize bean) {
		return activityPrizeDao.queryActivityPrize(bean);
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

}
