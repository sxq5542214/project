package com.yd.business.company.dao;

import java.util.List;

import com.yd.business.company.bean.CompanyBean;

public interface ICompanyDao {

	List<CompanyBean> listCompany(CompanyBean bean);

	void insertCompany(CompanyBean bean);

	void updateCompany(CompanyBean bean);

	void deleteCompany(long id);

}
