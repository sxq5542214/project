package com.yd.business.other.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.dao.IConfigCruxDao;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author zxz
 *
 */
@Service("configCruxService")
public class ConfigCruxServiceImpl extends BaseService implements IConfigCruxService {
	@Resource
	private IConfigCruxDao configCruxDao;
	
	@Override
	public List<ConfigCruxBean> queryConfigCruxInfo(ConfigCruxBean bean){
		return configCruxDao.queryConfigCruxInfo(bean);

	}
	

	@Override
	public ConfigCruxBean getAttributeByTypeAndKey(String type,String key){
		
		ConfigCruxBean bean = new ConfigCruxBean();
		bean.setType(type);
		bean.setKey(key);
		List<ConfigCruxBean> list = queryConfigCruxInfo(bean);
		
		if(list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public String getValueByTypeAndKey(String type,String key){
		ConfigCruxBean bean = getAttributeByTypeAndKey(type, key);
		
		if(bean != null){
			return bean.getValue();
		}
		return null;
	}
	
	@Override
	public int getIntValueByTypeAndKey(String type,String key){
		String value = getValueByTypeAndKey(type,key);
		if(StringUtil.isNull(value)){
			value = "0";
		}
		return Integer.parseInt(value);
	}


	/**
	 * 	分页查询configCrux表信息
	 */
	@Override
	public PageinationData queryConfigCruxInfoPage(ConfigCruxBean bean){
		PageinationData pd = new PageinationData();
		try{
			List<ConfigCruxBean> showList = configCruxDao.queryConfigCruxInfoPage(bean);
		    int count = configCruxDao.configCruxCount(bean);
		      pd.setDataList(showList);
		      pd.setTotalcount(count);
		      pd.calculateTotalPage();
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
		
		return pd ;

	}
	
	
	@Override
	public void deleteSendMessage(ConfigCruxBean bean) {
		// TODO Auto-generated method stub
		configCruxDao.deleteSendMessage(bean);
	}



	/**
	 * 增加或者编辑发送短信消息,判断是否重复,如果重复返回false,不重复返回true,同时做出判断
	 */
	@Override
	public boolean editSendMessage(ConfigCruxBean bean) {
		// TODO Auto-generated method stub
		try{
			int count = configCruxDao.configCruxCount(bean);
			if(StringUtil.isNull(bean.getId()) || bean.getId() == ConfigCruxBean.NUMBER_ZERO ){				//如果id为null,就更改为增加
				if(count == ConfigCruxBean.NUMBER_ZERO){
					bean.setCreate_time(DateUtil.getNowDateStr());
					configCruxDao.insertSendMessage(bean);
					return true ;
				}else{
					return false;
				}
			}else{
					bean.setModify_time(DateUtil.getNowDateStr());
					configCruxDao.updateSendMessage(bean);
					return true ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
		return false ;
	}

	
}
