package com.yd.business.client.service;

import org.json.JSONObject;

import com.yd.business.client.bean.QingSongInterfaceBean;
import com.yd.business.client.bean.QingSongInterfaceBean.DeviceDto;
import com.yd.business.client.bean.QingSongInterfaceBean.MeterCMD;

public interface IIOTInterfaceService {

	void saveQingSongStationCode(String stationCode);

	void updateQingSongCMDState(MeterCMD cmd);

	int handlerQingSongPostMeterReading(String requestStr);

	DeviceDto checkQingSongMeterCode(String code);

	/**
	 * 
	 * @param ispid 
	 * @param outerid 
	 * @param type 1开阀  2关阀
	 * @return
	 */
	QingSongInterfaceBean sendQingSongCmd(String ispid, String outerid, String type);
	
}
