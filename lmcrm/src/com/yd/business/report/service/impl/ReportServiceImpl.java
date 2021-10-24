/**
 * 
 */
package com.yd.business.report.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.report.bean.ReportParamsBean;
import com.yd.business.report.bean.ReportSimpleBean;
import com.yd.business.report.dao.IReportDao;
import com.yd.business.report.service.IReportService;

/**
 * @author ice
 *
 */
@Service("reportService")
public class ReportServiceImpl extends BaseService implements IReportService {
	private static final String OperatorBean = null;
	@Resource
	private IReportDao reportDao;
	
	@Override
	public List<ReportSimpleBean> queryReportSimpleList(ReportSimpleBean bean){
		
		if(bean == null) {
			bean = new ReportSimpleBean();
			bean.setCode("chart.report");
		}
		// 仅查询 chart.report 下的报表
		bean.setStatus(ReportSimpleBean.STATUS_ENABLE);
		
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
		List<ReportSimpleBean> list = reportDao.queryReportSimpleList(condition);
		for(ReportSimpleBean bean : list) {
			String execSql = convertActionParameter(bean.getData_sql(), params);
			
			List<Map<String, Object>> dataList = querySingleReportData(execSql);
			bean.setDataList(dataList);
			bean.setData_sql(null);
			
			//查询 参数列表
			List<ReportParamsBean> paramsList = queryReportParamsList(bean.getId(), params);
			bean.setParamsList(paramsList);
//			for(ReportParamsBean param : paramsList) {
//				if(params.get(param.getParam_code()) == null) {
				
//				}
//			}
			
			return bean;
		}
		
		return null;
	}
	
	public List<ReportParamsBean> queryReportParamsList(Integer reportId,Map<String,String> params) {
//		OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
		
		ReportParamsBean bean = new ReportParamsBean();
		bean.setReport_id(reportId);
		List<ReportParamsBean> paramsList = reportDao.queryReportParamsList(bean);
		
		for(ReportParamsBean param : paramsList) {
			String execSql = convertActionParameter(param.getParam_sql(), params);
			
			List<Map<String, Object>> dataList = reportDao.queryCustomSql(execSql);
			param.setDataList(dataList);
			param.setParam_sql(null);
		}
		
		return paramsList;
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
	
	@Override
	public ReportSimpleBean findReportSimpleByCode(String code) {
		ReportSimpleBean bean = new ReportSimpleBean();
		bean.setCode(code);
		List<ReportSimpleBean> list = queryReportSimpleList(bean);
		
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	

	@Override
	public List<ReportSimpleBean> queryReportSimpleListByAdminRole(Long operator_id){
		
		if(operator_id == null) {
			return Collections.EMPTY_LIST;
		}
		
		return reportDao.queryReportSimpleListByAdminRole(operator_id.intValue());
		
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
