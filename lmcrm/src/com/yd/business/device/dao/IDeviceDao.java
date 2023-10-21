package com.yd.business.device.dao;

import java.util.List;

import com.yd.business.device.bean.DeviceInfoBean;

public interface IDeviceDao {
	List<DeviceInfoBean> queryDeviceInfo(DeviceInfoBean bean);

	DeviceInfoBean createDeviceInfo(DeviceInfoBean bean);

	int updateDeviceInfo(DeviceInfoBean bean);
}
