/**
 * 
 */
package com.yd.business.report.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.report.bean.ReportSimpleBean;
import com.yd.business.report.dao.IReportDao;
import com.yd.business.report.service.IReportService;

/**
 * @author ice
 *
 */
@Service("reportService")
public class ReportServiceImpl extends BaseService implements IReportService {
	@Resource
	private IReportDao reportDao;
	
	@Override
	public List<ReportSimpleBean> queryReportSimpleList(ReportSimpleBean bean){
		
		return reportDao.queryReportSimpleList(bean);
		
	}
	
	@Override
	public List<Map<String,Object>> querySingleReportData(String sql){
		
		return reportDao.queryCustomSql(sql);
	}

	@Override
	public ReportSimpleBean querySimpleReportAndDataByCode(String code,Map<String,String> params){
		ReportSimpleBean condition = new ReportSimpleBean();
		condition.setCode(code);
		List<ReportSimpleBean> list = queryReportSimpleList(condition);
		for(ReportSimpleBean bean : list) {
			String execSql = convertActionParameter(bean.getData_sql(), params);
			
			List<Map<String, Object>> dataList = querySingleReportData(execSql);
			bean.setDataList(dataList);
			bean.setData_sql(null);
			return bean;
		}
		
		return null;
	}
	
	
	@Override
	public ReportSimpleBean findReportSimpleById(int id) {
		ReportSimpleBean bean = new ReportSimpleBean();
		bean.setId(id);
		List<ReportSimpleBean> list = queryReportSimpleList(bean);
		
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	

	/**
	 * 转换参数数据
	 * @param content
	 * @param action
	 */
	private String convertActionParameter(String content,Map<String,String> params){
		
		if(params != null) {
			for(String key : params.keySet()) {
				content = content.replaceAll("#"+ key +"#", params.get(key));
			}
		}
		return content;
	}
	
}
