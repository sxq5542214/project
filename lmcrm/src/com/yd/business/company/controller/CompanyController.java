/**
 * 
 */
package com.yd.business.company.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.service.IAreaService;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.bean.CompanyExtBean;
import com.yd.business.company.service.ICompanyService;
import com.yd.business.image.bean.UploadLogBean;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class CompanyController extends BaseController {
	@Autowired
	private ICompanyService companyService;
	

	/**
	 *  界面查询营业所列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/company/ajaxQueryCompanyByOperator.do")
	public ModelAndView ajaxQueryUserByCompany(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			CompanyBean bean = companyService.findCompanyById(op.getO_companyid());
			
			writeJson(response, bean );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 *  界面查询营业所列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/company/ajaxQueryCompanyList.do")
	public ModelAndView ajaxQueryCompanyList(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			Long company_id = null;
			if(op.getO_kind() != OperatorBean.KIND_SUPPERUSER) {
				company_id = op.getO_companyid();
			}
			CompanyBean bean = new CompanyBean();
			bean.setC_id(company_id);
			List<CompanyBean> list = companyService.queryCompanyList(bean );
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/company/ajaxUploadPrintGRF.do")
	public ModelAndView ajaxUploadPrintGRF(HttpServletRequest request,HttpServletResponse response) {

		try {

			String remote_ip = getRemoteHost(request);
			MultipartHttpServletRequest mtr = (MultipartHttpServletRequest)request;
			
			
			OperatorBean op = getCurrentLoginOperator();
			String result = companyService.saveCompanyPrintGRF(mtr, remote_ip, op );
			

			writeJson(response, result);
			
			
		} catch (Exception e) {
			log.error(e, e);

			writeJson(response, e.getMessage());			
		}
		return null;
	}
	

	@RequestMapping("**/admin/company/ajaxQueryCompanyPrintFileName.do")
	public ModelAndView ajaxQueryCompanyPrintFileName(HttpServletRequest request,HttpServletResponse response) {

		try {
			
			OperatorBean op = getCurrentLoginOperator();
			CompanyBean comp = companyService.findCompanyById(op.getO_companyid());
			
			writeString(response, comp.getPrint_grf_path());
			
		} catch (Exception e) {
			log.error(e, e);

			writeJson(response, e.getMessage());			
		}
		return null;
	}
	
		
		
}
