/**
 * 
 */
package com.yd.business.area.service;

import com.yd.business.area.bean.AreaDataBean;

/**
 * @author ice
 *
 */
public interface IAreaDataService {

	AreaDataBean findAreaData(String phoneCode);

	AreaDataBean getAreaDataByPhone(String phone);

}
