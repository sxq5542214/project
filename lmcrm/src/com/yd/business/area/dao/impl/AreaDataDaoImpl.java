/**
 * 
 */
package com.yd.business.area.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.dao.IAreaDataDao;

/**
 * @author ice
 *
 */
@Repository("areaDataDao")
public class AreaDataDaoImpl extends BaseDao implements IAreaDataDao {
	private static final String NAMESPACE = "areaData.";
	
	@Override
	public AreaDataBean findAreaData(String phoneCode){
		return sqlSessionTemplate.selectOne(NAMESPACE +"findAreaData", phoneCode);
	}
	
}
