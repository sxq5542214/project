/**
 * 
 */
package com.yd.business.other.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.dao.IConfigAttributeDao;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("configAttributeService")
public class ConfigAttributeServiceImpl extends BaseService implements IConfigAttributeService {
	
	@Resource
	private IConfigAttributeDao configAttributeDao;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	@Override
	public ConfigAttributeBean getAttributeByCode(String code){
		return configAttributeDao.getAttributeByCode(code);
	}
	
	@Override
	public ConfigAttributeBean findAttributeByCode(String code,String originalid){
		ConfigAttributeBean bean = new ConfigAttributeBean();
		bean.setCode(code);
		bean.setOriginalid(originalid);
		return configAttributeDao.findAttribute(bean);
	}
	
	@Override
	public String getValueByCode(String code){
		return configAttributeDao.getValueByCode(code);
	}
	@Override
	public String getValueByCode(String code,String originalid){
		
		ConfigAttributeBean attr = findAttributeByCode(code, originalid);
		return attr.getValue();
	}
	
	@Override
	public int getIntValueByCode(String code){
		String value = getValueByCode(code);
		if(StringUtil.isNull(value)){
			value = "0";
		}
		return Integer.parseInt(value);
	}
	
	@Override
	public boolean getBooleanValueByCode(String code){
		String value = getValueByCode(code);
		if("true".equalsIgnoreCase(value)){
			return true;
		}
		return false;
	}
	
	@Override
	public void updateValueByCode(String code,String value,String originalid){
		configAttributeDao.updateValueByCode(code,value,originalid);
	}

	@Override
	public PageinationData queryConfigAttributeForPage(ConfigAttributeBean bean) {
		PageinationData pd = new PageinationData();
		List<ConfigAttributeBean> beanList = configAttributeDao.queryConfigAttributeByBean(bean);
		int count = configAttributeDao.queryConfigAttributeCount(bean);
		pd.setDataList(beanList);
		pd.setNowpage(bean.getNowpage() );
		pd.setTotalcount(count);
		try {
			pd.calculateTotalPage();
		} catch (Exception e) {
			log.error(e, e);
		}
		return pd;
	}

	/**
	 * 对于配置信息的新增修改
	 */
	@Override
	public ConfigAttributeBean commitConfigAttributeBean(ConfigAttributeBean bean) {
		//获取公众号信息
		WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(bean.getOriginalid());
		bean.setOriginal_name(original.getOriginal_name());
		if(StringUtil.isNull(bean.getId())){
			ConfigAttributeBean queryBean = new ConfigAttributeBean();
			queryBean.setCode(bean.getCode());
			//判断code是否冲突
			List<ConfigAttributeBean> oldBean = configAttributeDao.queryConfigAttributeByBean(queryBean);
			if(!StringUtil.isNull(oldBean) && oldBean.size() > 0){
				bean.setRemark("编码重复！");
				return bean;
			}
			configAttributeDao.insertIntoConfigAttributeBean(bean);
			bean = configAttributeDao.queryConfigAttributeByBean(bean).get(0);
			bean.setStatus_value(bean.getDictValueByField("status"));
			bean.setRemark("添加成功！");
		}else{
			configAttributeDao.updateConfigAttributeBean(bean);
			bean.setRemark("修改成功！");
		}
		return bean;
	}

	@Override
	public List<ConfigAttributeBean> deleteConfigAttributeBean(String ids) {
		List<ConfigAttributeBean> beanList = new ArrayList<ConfigAttributeBean>();
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			ConfigAttributeBean bean = new ConfigAttributeBean();
			bean.setId(Integer.valueOf(id));
			beanList.add(bean);
		}
		configAttributeDao.deleteConfigAttributeBean(beanList);
		return beanList;
	}

}
