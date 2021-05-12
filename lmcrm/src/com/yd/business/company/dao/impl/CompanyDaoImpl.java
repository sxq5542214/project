package com.yd.business.company.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.dao.ICompanyDao;
/**
 * 操作日志
 * @author ice
 *
 */
@Repository("companyDao")
public class CompanyDaoImpl extends BaseDao implements ICompanyDao {
	 
	private final static String NAMESPACE = "company.";

	@Override
	public List<CompanyBean> listCompany(CompanyBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCompanyList", bean);
	}

	@Override
	public void insertCompany(CompanyBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCompany", bean);
	}

	@Override
	public void updateCompany(CompanyBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCompany", bean);
	}

	@Override
	public void deleteCompany(long id) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteCompany", id);
	}
	
	
	
	
}
