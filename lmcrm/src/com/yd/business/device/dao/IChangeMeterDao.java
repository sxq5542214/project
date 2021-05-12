package com.yd.business.device.dao;

import java.util.List;

import com.yd.business.device.bean.ChangeMeterBean;
import com.yd.business.device.bean.DeviceInfoBean;

public interface IChangeMeterDao {

	List<ChangeMeterBean> listChangeMeter(ChangeMeterBean bean);

	void insertChangeMeter(ChangeMeterBean bean);

	void updateChangeMeter(ChangeMeterBean bean);

	void deleteChangeMeter(long id);
}
