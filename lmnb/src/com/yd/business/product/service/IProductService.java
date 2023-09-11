package com.yd.business.product.service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.iotbusiness.mapper.model.LmProductModel;

public interface IProductService {

	IOTWebDataBean queryProductList(LmProductModel model);


}
