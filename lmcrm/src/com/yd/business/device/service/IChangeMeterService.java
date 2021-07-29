package com.yd.business.device.service;

import java.math.BigDecimal;

public interface IChangeMeterService {

	int createChangeMeter(long user_no, BigDecimal cm_oldmetercode, int cm_type, long cm_operatorid, String cm_remark);

	int createChangeMeter(long user_no, BigDecimal cm_oldmetercode, BigDecimal cm_newmetercode, Long cm_newmeterno,
			int cm_type, long cm_operatorid, String cm_remark, Long deviceKindId);

}
