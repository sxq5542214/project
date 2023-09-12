/**
 * 
 */
package com.yd.business.other.service;

import com.yd.iotbusiness.mapper.model.LmCmdModel;

/**
 * 
 */
public interface ICommandService {

	LmCmdModel findCmdModelById(int cmdid);

	LmCmdModel createCMDModel(String meterCode, String factorycode, Byte state, String type, String param, Byte isp,
			String ispid, String imei, String stationcode, String operatorname, Integer systemid, Integer productid,
			Integer meterid, Integer userid, Integer operatorid, String remark);

	LmCmdModel updateCmdStatus(int cmdid, byte state, String remark);

}
