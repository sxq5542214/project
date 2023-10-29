package com.yd.business.device.service;

import java.math.BigDecimal;
import java.util.List;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.device.bean.ChangeMeterExtBean;

public interface IChangeMeterService {

	int createChangeMeter(long user_no, BigDecimal cm_oldmetercode, int cm_type, long cm_operatorid, String cm_remark);

	List<ChangeMeterExtBean> queryChangeMeterByExt(ChangeMeterExtBean bean);

	int createChangeMeter(long user_no, BigDecimal cm_oldmetercode, BigDecimal cm_newmetercode, Long cm_newmeterno,
			int cm_type, long cm_operatorid, String cm_remark, Long deviceKindId, String old_device_company,
			String new_device_company);

	IOTWebDataBean changeMeter(String oldMeterCode, String changeMeterCode, BigDecimal changeMeterCost,
			BigDecimal oldReadNum, BigDecimal changeReadNum, String changeFixUser, String remark1, Integer operatorid);

}
