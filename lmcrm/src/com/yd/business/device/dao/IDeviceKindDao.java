package com.yd.business.device.dao;

import java.util.List;

import com.yd.business.device.bean.DeviceKindBean;

public interface IDeviceKindDao {

	List<DeviceKindBean> listDeviceKind(DeviceKindBean bean);

	void insertDeviceKind(DeviceKindBean bean);

	void updateDeviceKind(DeviceKindBean bean);

	void deleteDeviceKind(long id);

}
