package com.yd.business.company.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.company.bean.CompanyOperatorBean;
import com.yd.business.company.dao.ICompanyOperatorDao;
/**
 * 操作日志
 * @author ice
 *
 */
@Repository("companyOperatorDao")
public class CompanyOperatorDaoImpl extends BaseDao implements ICompanyOperatorDao {
	 
	private final static String NAMESPACE = "companyOperator.";

	@Override
	public List<CompanyOperatorBean> listCompanyOperator(CompanyOperatorBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCompanyOperatorList", bean);
	}

	@Override
	public void insertCompanyOperator(CompanyOperatorBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCompanyOperator", bean);
	}

	@Override
	public void updateCompanyOperator(CompanyOperatorBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCompanyOperator", bean);
	}

	@Override
	public void deleteCompanyOperator(long id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCompanyOperator", id);
	}
	
	
	
	
}
