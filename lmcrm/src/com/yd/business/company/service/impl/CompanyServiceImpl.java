/**
 * 
 */
package com.yd.business.company.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.dao.ICompanyDao;
import com.yd.business.company.service.ICompanyService;

/**
 * @author ice
 *
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseService implements ICompanyService {
	@Autowired
	private ICompanyDao companyDao;
	
	@Override
	public CompanyBean findCompanyById(Long id) {
		
		CompanyBean bean = new CompanyBean();
		bean.setC_id(id);
		List<CompanyBean> list = companyDao.listCompany(bean );
		
		return list.size()>0 ? list.get(0):null;
	}
	
}
