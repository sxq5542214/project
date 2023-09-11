package com.yd.business.company.dao;

import java.util.List;

import com.yd.business.company.bean.CompanyOperatorBean;

public interface ICompanyOperatorDao {

	List<CompanyOperatorBean> listCompanyOperator(CompanyOperatorBean bean);

	void insertCompanyOperator(CompanyOperatorBean bean);

	void updateCompanyOperator(CompanyOperatorBean bean);

	void deleteCompanyOperator(long id);

}
