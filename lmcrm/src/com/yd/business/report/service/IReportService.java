/**
 * 
 */
package com.yd.business.report.service;

import java.util.List;
import java.util.Map;

import com.yd.business.report.bean.ReportSimpleBean;

/**
 * @author ice
 *
 */
public interface IReportService {

	List<ReportSimpleBean> queryReportSimpleList(ReportSimpleBean bean);
	
	ReportSimpleBean findReportSimpleById(int id);

	List<Map<String, Object>> querySingleReportData(String sql);

	ReportSimpleBean querySimpleReportAndDataByCode(String code, Map<String, String> params);

}
