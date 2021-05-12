/**
 * 
 */
package com.yd.business.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.dao.IDeviceDao;

/**
 * @author ice
 *
 */
@Repository("deviceDao")
public class DeviceDaoImpl extends BaseDao implements IDeviceDao {
	public static final String NAMESPACE = "device." ;

	@Override
	public List<DeviceInfoBean> queryDeviceInfo(DeviceInfoBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE +"queryDeviceInfo", bean);
	}
	
	@Override
	public DeviceInfoBean createDeviceInfo(DeviceInfoBean bean) {
		sqlSessionTemplate.insert(NAMESPACE + "createDeviceInfo", bean);
		return bean;
	}
	

}
