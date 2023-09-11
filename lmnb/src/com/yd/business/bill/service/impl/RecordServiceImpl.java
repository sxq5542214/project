/**
 * 
 */
package com.yd.business.bill.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.service.IBillService;
import com.yd.business.bill.service.IRecordService;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;
import com.yd.iotbusiness.mapper.dao.LmRecordModelMapper;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmBillModelExample;
import com.yd.iotbusiness.mapper.model.LmBillModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmRecordModel;
import com.yd.iotbusiness.mapper.model.LmRecordModelExample;

/**
 * @author ice
 *
 */
@Service("recordService")
public class RecordServiceImpl extends BaseService implements IRecordService {
	
	@Resource
	private LmRecordModelMapper recordModelMapper;
	@Resource
	private IUserInfoService userInfoService;
	
	@Override
	public LmRecordModel saveRecord(LmRecordModel model) {
		recordModelMapper.insertSelective(model);
		return model;
	}
	
	@Override
	public LmRecordModel findLastRecord(String meterCode) {
		
		
		LmRecordModelExample ex = new LmRecordModelExample();
		LmRecordModelExample.Criteria cri = ex.createCriteria();
		cri.andMetercodeEqualTo(meterCode);
		ex.setOrderByClause(" id desc ");
		List<LmRecordModel> list = recordModelMapper.selectByExample(ex);
		return list.size()>0 ? list.get(0):null;
	}
	
	
}
