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
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmBillModelExample;
import com.yd.iotbusiness.mapper.model.LmBillModelExample.Criteria;

/**
 * @author ice
 *
 */
@Service("billService")
public class BillServiceImpl extends BaseService implements IBillService {
	
	@Resource
	private LmBillModelMapper billModelMapper;
	@Resource
	private IUserInfoService userInfoService;
	
	@Override
	public IOTWebDataBean queryBillList(LmBillModel model) {
		IOTWebDataBean result = new IOTWebDataBean();
		LmBillModelExample ex = new LmBillModelExample();
		LmBillModelExample.Criteria cri = ex.createCriteria();
		
		cri.andUseridEqualTo(model.getUserid());
		cri.andSystemidEqualTo(model.getSystemid());
		ex.setOrderByClause(" id desc ");
		List<LmBillModel> list = billModelMapper.selectByExample(ex );
		
		result.setTotal(Long.valueOf(list.size()));
		result.setData(list);
		return result;
	}
	
	
}
