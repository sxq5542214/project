package com.yd.business.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.dao.IDeviceKindDao;
/**
 * 操作日志
 * @author ice
 *
 */
@Repository("deviceKindDao")
public class DeviceKindDaoImpl extends BaseDao implements IDeviceKindDao {
	 
	private final static String NAMESPACE = "deviceKind.";

	@Override
	public List<DeviceKindBean> listDeviceKind(DeviceKindBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryDeviceKindList", bean);
	}

	@Override
	public void insertDeviceKind(DeviceKindBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertDeviceKind", bean);
	}

	@Override
	public void updateDeviceKind(DeviceKindBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateDeviceKind", bean);
	}

	@Override
	public void deleteDeviceKind(long id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteDeviceKind", id);
	}
	
	
	
	
}
