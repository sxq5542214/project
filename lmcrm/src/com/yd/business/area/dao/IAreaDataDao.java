/**
 * 
 */
package com.yd.business.area.dao;

import com.yd.business.area.bean.AreaDataBean;

/**
 * @author ice
 *
 */
public interface IAreaDataDao {

	AreaDataBean findAreaData(String phoneCode);

}
