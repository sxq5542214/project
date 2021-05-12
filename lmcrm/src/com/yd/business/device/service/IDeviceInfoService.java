package com.yd.business.device.service;

import java.util.List;

import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.user.bean.UserInfoBean;

public interface IDeviceInfoService {

	List<DeviceInfoBean> queryAllDeviceInfo();

	DeviceInfoBean findDeviceInfoByUserAndKind(Long userid, Long dkid);

	DeviceInfoBean createDeviceInfo(UserInfoBean user, DeviceKindBean deviceKind, PriceBean price, int chargePrice);

}
