package com.yd.business.log.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.log.bean.LogTemplateBean;
import com.yd.business.log.bean.OperatorLogBean;
import com.yd.business.log.dao.IOperatorLogDao;
/**
 * 操作日志
 * @author Anlins
 *
 */
@Repository("operatorLogDao")
public class OperatorLogDaoImpl extends BaseDao implements IOperatorLogDao {
	 
	private final static String NAMESPACE = "operatorLog.";

	@Override
	public List<OperatorLogBean> listOperatorLog(OperatorLogBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryOperatorLogList", bean);
	}

	@Override
	public void insertOperatorLog(OperatorLogBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertOperatorLog", bean);
	}
	
	/***
	 * 
	 * @param method
	 * @param hierarchy	 层级-- controll 还是  service
	 * @return
	 */
	@Override
	public LogTemplateBean findLogTemplateByMethod(String method,String hierarchy){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("method", method);
		map.put("hierarchy", hierarchy);
		List<LogTemplateBean> list = sqlSessionTemplate.selectList(NAMESPACE+"findLogTemplateByMethod", map);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
}
