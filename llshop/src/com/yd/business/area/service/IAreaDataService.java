/**
 * 
 */
package com.yd.business.area.service;

import com.yd.business.order.bean.AreaData;

/**
 * @author ice
 *
 */
public interface IAreaDataService {

	AreaData findAreaData(String phoneCode);

	AreaData getAreaDataByPhone(String phone);

}
