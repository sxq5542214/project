package com.yd.business.system.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.system.bean.SystemMenuExtModel;
import com.yd.iotbusiness.mapper.dao.LlSystemMenuModelMapper;
import com.yd.iotbusiness.mapper.dao.LmMeterModelMapper;

public interface ISystemMenuExtendsMapper extends LlSystemMenuModelMapper{
	List<SystemMenuExtModel> querySystemMenuByExtModel(SystemMenuExtModel model);
}
