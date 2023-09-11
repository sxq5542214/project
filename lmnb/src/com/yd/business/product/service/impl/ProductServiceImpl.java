/**
 * 
 */
package com.yd.business.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.product.service.IProductService;
import com.yd.iotbusiness.mapper.dao.LmProductModelMapper;
import com.yd.iotbusiness.mapper.model.LlSupplierModel;
import com.yd.iotbusiness.mapper.model.LmProductModel;

/**
 * @author ice
 *
 */
@Service("productService")
public class ProductServiceImpl extends BaseService implements IProductService {
	@Autowired
	private LmProductModelMapper productModelMapper;

	@Override
	public IOTWebDataBean queryProductList(LmProductModel model) {
		
		IOTWebDataBean result = new IOTWebDataBean();
		
		try {

//			LlSupplierModelExample ex = new LlSupplierModelExample();
//			LlSupplierModelExample.Criteria cri = ex.createCriteria();
			List<LmProductModel> list = productModelMapper.selectByExample(null );
			
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
