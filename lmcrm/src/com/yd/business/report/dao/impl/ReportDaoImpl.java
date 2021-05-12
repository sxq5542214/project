/**
 * 
 */
package com.yd.business.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.report.bean.ReportSimpleBean;
import com.yd.business.report.dao.IReportDao;

/**
 * @author ice
 *
 */
@Repository("reportDao")
public class ReportDaoImpl extends BaseDao implements IReportDao {
	private static final String NAMESPACE = "report.";
	
	@Override
	public List<Map<String, Object>> queryCustomSql(String sql){
		
		return sqlSessionTemplate.selectList(NAMESPACE +"queryCustomSql", sql);
	}
	
	@Override
	public List<ReportSimpleBean> queryReportSimpleList(ReportSimpleBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryReportSimpleList", bean);
	}
	
	
	
}
