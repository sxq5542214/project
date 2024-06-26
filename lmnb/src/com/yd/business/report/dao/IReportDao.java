/**
 * 
 */
package com.yd.business.report.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.report.bean.ReportParamsBean;
import com.yd.business.report.bean.ReportSimpleBean;

/**
 * @author ice
 *
 */
public interface IReportDao {

	List<ReportSimpleBean> queryReportSimpleList(ReportSimpleBean bean);

	List<Map<String, Object>> queryCustomSql(String sql);

	List<ReportParamsBean> queryReportParamsList(ReportParamsBean bean);

	List<ReportSimpleBean> queryReportSimpleListByAdminRole(int operator_id);

}
