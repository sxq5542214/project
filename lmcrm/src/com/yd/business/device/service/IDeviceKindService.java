package com.yd.business.device.service;

import java.util.List;

import com.yd.business.device.bean.DeviceKindBean;

public interface IDeviceKindService {

	List<DeviceKindBean> queryDeviceKind(DeviceKindBean bean);

	DeviceKindBean findDeviceKindById(long id);

}
