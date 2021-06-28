package com.yd.business.company.service;

import java.util.List;

import com.yd.business.company.bean.CompanyBean;

public interface ICompanyService {

	CompanyBean findCompanyById(Long id);

	List<CompanyBean> queryCompanyList(CompanyBean bean);

}
