/**
 * 
 */
package com.yd.business.area.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.dao.IAreaDataDao;
import com.yd.business.area.service.IAreaDataService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("areaDataService")
public class AreaDataServiceImpl extends BaseService implements IAreaDataService {
	@Resource
	private IAreaDataDao areaDataDao;
	
	/**
	 * 完全匹配参数
	 */
	@Override
	public AreaDataBean findAreaData(String phoneCode){
		return areaDataDao.findAreaData(phoneCode);
	}
	

	/**
	 * 根据手机的前几位取到区域数据
	 * @param phone
	 * @return
	 */
	@Override
	public AreaDataBean getAreaDataByPhone(String phone){
		AreaDataBean ad = null;
		
		if(StringUtil.isNotNull(phone) && phone.length() == 11 ){
			String pre8 = phone.substring(0, 8);
			ad = this.findAreaData(pre8);
			if(ad == null){
				String pre7 = phone.substring(0, 7);
				ad = this.findAreaData(pre7);
			}
			if(ad == null){
				String pre3 = phone.substring(0,3);
				ad = this.findAreaData(pre3);
			}
		}
		if(ad == null){
			log.error(" phone not found! "+ phone);
		}
		return ad;
	}
	
	
	
	
}
