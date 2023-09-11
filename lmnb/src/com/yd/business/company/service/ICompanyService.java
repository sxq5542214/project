package com.yd.business.company.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yd.business.company.bean.CompanyBean;
import com.yd.business.operator.bean.OperatorBean;

public interface ICompanyService {

	CompanyBean findCompanyById(Long id);

	List<CompanyBean> queryCompanyList(CompanyBean bean);

	String saveCompanyPrintGRF(MultipartHttpServletRequest request, String remote_ip, OperatorBean op);

}
