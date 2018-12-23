/**
 * 
 */
package com.yd.business.area.dao;

import com.yd.business.order.bean.AreaData;

/**
 * @author ice
 *
 */
public interface IAreaDataDao {

	AreaData findAreaData(String phoneCode);

}
