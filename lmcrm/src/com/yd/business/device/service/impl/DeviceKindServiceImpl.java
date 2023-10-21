/**
 * 
 */
package com.yd.business.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.dao.IDeviceKindDao;
import com.yd.business.device.service.IDeviceKindService;

/**
 * @author ice
 *
 */
@Service("deviceKindService")
public class DeviceKindServiceImpl extends BaseService implements IDeviceKindService {
	@Autowired
	private IDeviceKindDao deviceKindDao;

	@Override
	public List<DeviceKindBean> queryDeviceKind(DeviceKindBean bean){
		return deviceKindDao.listDeviceKind(bean);
	}

	@Override
	public DeviceKindBean findDeviceKindById(long id){
		
		DeviceKindBean bean = new DeviceKindBean();
		bean.setDk_id(id);
		List<DeviceKindBean> list = deviceKindDao.listDeviceKind(bean);
		return list.size()>0 ? list.get(0):null;
	}
	
	
}
