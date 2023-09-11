/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.iotbusiness.mapper.dao.LlSupplierModelMapper;
import com.yd.iotbusiness.mapper.model.LlSupplierModel;
import com.yd.iotbusiness.mapper.model.LlSupplierModelExample;

/**
 * @author ice
 *
 */
@Service("supplierService")
public class SupplierServiceImpl extends BaseService implements ISupplierService {
	@Autowired
	private LlSupplierModelMapper supplierModelMapper;

	@Override
	public IOTWebDataBean querySupplierList(LlSupplierModel model) {
		
		IOTWebDataBean result = new IOTWebDataBean();
		
		try {

//			LlSupplierModelExample ex = new LlSupplierModelExample();
//			LlSupplierModelExample.Criteria cri = ex.createCriteria();
			List<LlSupplierModel> list = supplierModelMapper.selectByExample(null );
			
			result.setData(list);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		} catch (Exception e) {
			log.error(e);;
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	
	
}
