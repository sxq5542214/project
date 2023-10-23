package com.yd.business.print.service;

import java.util.List;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.iotbusiness.mapper.model.LmPriceModel;
import com.yd.iotbusiness.mapper.model.LmPricedetailModel;

public interface IPrintService {

	IOTWebDataBean queryPrintTemplateList(Integer systemid);

	IOTWebDataBean updatePrintTemplateStyle(Integer id, String style, String remark);

	IOTWebDataBean queryPrintTemplateById(Integer id);


}
