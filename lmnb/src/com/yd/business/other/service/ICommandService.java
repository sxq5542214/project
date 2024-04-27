/**
 * 
 */
package com.yd.business.other.service;

import java.util.Date;
import java.util.List;

import com.yd.iotbusiness.mapper.model.LmCmdModel;

/**
 * 
 */
public interface ICommandService {
	public static byte CMD_STATE_NOSEND = 0;
	public static byte CMD_STATE_WAITSEND = 1;
	public static byte CMD_STATE_SUCCESS = 2;
	public static byte CMD_STATE_FAILD = 3;
	public static byte CMD_STATE_OFFLINE = 4;
	public static byte CMD_STATE_OVERWRITE = 5;
	public static byte CMD_STATE_CANCLE = 6;
	public static String CMD_TYPE_OPEN = "1";
	public static String CMD_TYPE_CLOSE = "2";
	
	
	
	LmCmdModel findCmdModelById(int cmdid);

	LmCmdModel createCMDModel(String meterCode, String factorycode, Byte state, String type, String param, Byte isp,
			String ispid, String imei, String stationcode, String operatorname, Integer systemid, Integer productid,
			Integer meterid, Integer userid, Integer operatorid, String remark);

	LmCmdModel updateCmdStatus(int cmdid, byte state, String remark);

	List<LmCmdModel> queryCmdList(Integer userid, String metercode, Byte state, String type, Date createtimeStart,
			Date createtimeEnd);

}
