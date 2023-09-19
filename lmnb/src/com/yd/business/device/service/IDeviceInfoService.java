package com.yd.business.device.service;

import java.util.List;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;

public interface IDeviceInfoService {

	List<DeviceInfoBean> queryAllDeviceInfo();

	DeviceInfoBean findDeviceInfoByUserAndKind(Long userid, Long dkid);

	DeviceInfoBean findFirstDeviceInfoByUser(Long userid);

	int updateDeviceInfo(DeviceInfoBean bean);

	DeviceInfoBean createDeviceInfo(UserInfoBean user, DeviceKindBean deviceKind, PriceBean price, int chargePrice,
			String deviceCompany);

	IOTWebDataBean addOrUpdateMeter(LmMeterModel bean);

	IOTWebDataBean queryMeterList(LmMeterModel bean);

	IOTWebDataBean queryMeterAndUserList(MeterModelExtendsBean bean);

	MeterModelExtendsBean findMeterByCode(String code);

	MeterModelExtendsBean findMeterByIspid(String ispid);

	IOTWebDataBean openOrCloseMeter(String meterCode, LmOperatorModel op, boolean isOpen, String remark);

	LmMeterModel updateMeterModel(LmMeterModel bean);

}
