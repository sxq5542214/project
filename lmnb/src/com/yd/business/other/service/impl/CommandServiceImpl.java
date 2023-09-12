/**
 * 
 */
package com.yd.business.other.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.service.ICommandService;
import com.yd.iotbusiness.mapper.dao.LmCmdModelMapper;
import com.yd.iotbusiness.mapper.model.LmCmdModel;

/**
 * 
 */
@Service("commandService")
public class CommandServiceImpl extends BaseService implements ICommandService {
	@Autowired
	private LmCmdModelMapper cmdModelMapper;
	
	@Override
	public LmCmdModel createCMDModel(String meterCode,String factorycode,Byte state,String type,String param,Byte isp,
			String ispid,String imei,String stationcode,String operatorname,Integer systemid,Integer productid,Integer meterid,
			Integer userid,Integer operatorid,String remark ) {
		LmCmdModel model = new LmCmdModel();
		model.setMetercode(meterCode);
		model.setFactorycode(factorycode);
		model.setState(state);
		model.setType(type);
		model.setParam(param);
		model.setIsp(isp);
		model.setIspid(ispid);
		model.setImei(imei);
		model.setStationcode(stationcode);
		model.setOperatorid(operatorid);
		model.setOperatorname(operatorname);
		model.setSystemid(systemid);
		model.setProductid(productid);
		model.setMeterid(meterid);
		model.setUserid(userid);
		model.setRemark(remark);
		model.setCreatetime(new Date());
		
		cmdModelMapper.insertSelective(model);
		return model;
	}
	
	@Override
	public LmCmdModel findCmdModelById(int cmdid) {
		LmCmdModel cmd = cmdModelMapper.selectByPrimaryKey(cmdid);
		return cmd;
	}
	
	@Override
	public LmCmdModel updateCmdStatus(int cmdid,byte state,String remark) {
		LmCmdModel cmd = findCmdModelById(cmdid);
		cmd.setState(state);
		cmd.setExetime(new Date());
		cmd.setRemark(remark);
		cmdModelMapper.updateByPrimaryKeySelective(cmd);
		return cmd;
	}
	
	
	
}
