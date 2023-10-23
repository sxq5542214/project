/**
 * 
 */
package com.yd.business.print.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.print.service.IPrintService;
import com.yd.iotbusiness.mapper.dao.LmPaperModelMapper;
import com.yd.iotbusiness.mapper.dao.LmPaperrecordModelMapper;
import com.yd.iotbusiness.mapper.model.LmPaperModel;
import com.yd.iotbusiness.mapper.model.LmPaperModelExample;

/**
 * @author ice
 *
 */
@Service("printService")
public class PrintServiceImpl extends BaseService implements IPrintService {
	@Resource
	private LmPaperModelMapper paperModelMapper;
	@Resource
	private LmPaperrecordModelMapper paperrecordModelMapper;

	@Override
	public IOTWebDataBean queryPrintTemplateList(Integer systemid) {
		
		LmPaperModelExample ex = new LmPaperModelExample();
		LmPaperModelExample.Criteria cri = ex.createCriteria();
		cri.andSystemidEqualTo(systemid);
		
		List<LmPaperModel> list = paperModelMapper.selectByExampleWithBLOBs(ex );
		long count = paperModelMapper.countByExample(ex);
		
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(list);
		result.setTotal(count);
		
		return result;
	}
	@Override
	public IOTWebDataBean queryPrintTemplateById(Integer id) {
		
		LmPaperModel list = paperModelMapper.selectByPrimaryKey(id);
		
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(list);
		
		return result;
	}
	@Override
	public IOTWebDataBean updatePrintTemplateStyle(Integer id,String style,String remark) {
		
		LmPaperModel model = new LmPaperModel();
		model.setId(id);
		model.setStyle(style);
		model.setRemark(remark);
		model.setCreatetime(new Date());
		int i = paperModelMapper.updateByPrimaryKeySelective(model );
		
		
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(i);
		
		return result;
	}

	
	
	
	
}
