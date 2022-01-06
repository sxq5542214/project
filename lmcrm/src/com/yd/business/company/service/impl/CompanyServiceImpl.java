/**
 * 
 */
package com.yd.business.company.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.dao.ICompanyDao;
import com.yd.business.company.service.ICompanyService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.util.StringUtil;

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
	

	@Override
	public List<CompanyBean> queryCompanyList(CompanyBean bean) {
		
		List<CompanyBean> list = companyDao.listCompany(bean );
		
		return list;
	}
	
	@Override
	public String saveCompanyPrintGRF( MultipartHttpServletRequest request,String remote_ip , OperatorBean op) {

		JSONObject jso = new JSONObject();
		
		try {
			
			String path = request.getRealPath("/");
			String dir = "assets/print/grf/";
			CompanyBean company = findCompanyById(op.getO_companyid());
			String filePath = dir + company.getC_no()+"_"+ System.currentTimeMillis() +".grf";
			MultipartFile postFile = request.getFile("printGRF");
			if(StringUtil.isNull(postFile)){
				jso.put("error", "上传文件为空，请检查上传数据！");
			}
			
			Files.write(Paths.get(path, filePath), postFile.getBytes());
			
			System.out.println(filePath);
			company.setPrint_grf_path(filePath);
			
			
			companyDao.updateCompany(company);
			
			
			
			
		} catch (Exception e) {
			log.error(e, e);
			jso.put("error", "上传失败:"+e.getMessage());
		}
		
		
		return jso.toString()  ;
	}
	
	
}
