/**
 * 
 */
package com.yd.business.bill.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.dao.IRecordExtendsMapper;
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
import com.yd.iotbusiness.mapper.model.LmUserModel;

/**
 * @author ice
 *
 */
@Service("recordService")
public class RecordServiceImpl extends BaseService implements IRecordService {
	
	@Resource
	private IRecordExtendsMapper recordExtendsMapper;
	@Resource
	private IUserInfoService userInfoService;
	
	@Override
	public LmRecordModel saveRecord(LmRecordModel model) {
		recordExtendsMapper.insertSelective(model);
		return model;
	}
	
	@Override
	public LmRecordModel findLastRecord(String meterCode) {
		
		
		LmRecordModelExample ex = new LmRecordModelExample();
		LmRecordModelExample.Criteria cri = ex.createCriteria();
		cri.andMetercodeEqualTo(meterCode);
		ex.setOrderByClause(" id desc ");
		List<LmRecordModel> list = recordExtendsMapper.selectByExample(ex);
		return list.size()>0 ? list.get(0):null;
	}
	
	@Override
	public IOTWebDataBean queryRecordList(LmRecordModel model) {

		long total = recordExtendsMapper.countSelectRecordList(model);
		List<LmRecordModel> list = recordExtendsMapper.selectRecordList(model);
		 
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(list);
		result.setTotal(total);
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		
		return result;
	}
	

	@Override
	public void updateRecordMeterCode(String oldMeterCode,String newMeterCode) {
		recordExtendsMapper.updateRecordMeterCode(oldMeterCode, newMeterCode);
	}
	
}
